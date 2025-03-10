package com.trustrace.tiles_hub_be.controllers;


import com.trustrace.tiles_hub_be.exceptionHandlers.RoleNotFoundException;
import com.trustrace.tiles_hub_be.model.responseWrapper.ApiResponse;
import com.trustrace.tiles_hub_be.model.responseWrapper.ResponseUtil;
import com.trustrace.tiles_hub_be.model.user.Role;
import com.trustrace.tiles_hub_be.model.user.UserEntity;
import com.trustrace.tiles_hub_be.payload.request.LoginRequest;
import com.trustrace.tiles_hub_be.payload.request.SignUpRequest;
import com.trustrace.tiles_hub_be.payload.response.JwtResponse;
import com.trustrace.tiles_hub_be.payload.response.MessageResponse;
import com.trustrace.tiles_hub_be.security.jwt.JwtUtils;
import com.trustrace.tiles_hub_be.security.services.UserDetailsImpl;
import com.trustrace.tiles_hub_be.service.RoleService;
import com.trustrace.tiles_hub_be.service.UserEntityService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.web.bind.annotation.*;

import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

@CrossOrigin(origins = "*", maxAge = 3600)
@RestController
@RequestMapping("/api/auth")
public class AuthController {

    @Autowired
    private PasswordEncoder encoder;

    @Autowired
    private RoleService roleService;

    @Autowired
    private UserEntityService userEntityService;

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private JwtUtils jwtUtils;




    /**
     * Register a new user account.
     *
     * @param signUpRequest The signup request containing user details.
     * @return A ResponseEntity indicating success or error message.
     */
    @PostMapping("/signup")
    public ResponseEntity<ApiResponse<Object>> registerUser(@RequestBody SignUpRequest signUpRequest) {

        //if userNmae already exists
        if(userEntityService.existsByName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.error("Error: Username is already taken!", null));
        }

        //if email already exists
        if(userEntityService.existsByEmail(signUpRequest.getEmail())) {
            return ResponseEntity
                    .badRequest()
                    .body(ResponseUtil.error("Error: Email is already in use!", null));
        }

        //Create a new user account
        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getEmail(),
                encoder.encode(signUpRequest.getPassword()) // encoding the password
        );

        Set<String> strRoles = signUpRequest.getRoles(); //get roles that are requested
        Set<Role> roles = new HashSet<>(); // initialize empty set to hold the roles

        //assign the requested role or the default employee role
        if(strRoles == null) {
            Role employeeRole = roleService.findByName("ROLE_EMPLOYEE")
                            .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
            roles.add(employeeRole);
        }
        else {
            strRoles.forEach(role -> {
                switch (role) {
                    case "super_admin":
                        Role superAdminRole = roleService.findByName("ROLE_SUPER_ADMIN")
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(superAdminRole);
                        break;
                    case "admin":
                        Role adminRole = roleService.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role employeeRole = roleService.findByName("ROLE_EMPLOYEE")
                                .orElseThrow(() -> new RoleNotFoundException("Error: Role is not found."));
                        roles.add(employeeRole);
                }
            });

        }

        //assiging roles set to the user and saving to DB
        user.setRoles(roles);
        userEntityService.saveUserEntity(user);

        //returning success message
        return ResponseEntity.ok(ResponseUtil.success("User registered successfully!", null, null));


    }

    /**
     * Authenticate user and return a JWT token if successful.
     *
     * @param loginRequest The login request containing username and password.
     * @return A ResponseEntity containing the JWT response or an error message.
     */
    @PostMapping("/signin")
    public ResponseEntity<ApiResponse<JwtResponse>> authenticateUser(@RequestBody LoginRequest loginRequest) {

        //authenticate the user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getEmail(),
                        loginRequest.getPassword()
                )
        );

        //set the authentication in the security context
        SecurityContextHolder.getContext().setAuthentication(authentication);

        //generate the JWT token based on the authentication
        String jwt = jwtUtils.generateJwtToken(authentication);

        //get user details from the authentication object
        UserDetailsImpl userDetails = (UserDetailsImpl) authentication.getPrincipal();

        //extract user roles into a list
        List<String> roles = userDetails.getAuthorities().stream()
                .map(item -> item.getAuthority())
                .collect(Collectors.toList());

        //return a response containing the JWT and user details
        JwtResponse jwtResponse = new JwtResponse(
                jwt,
                userDetails.getId(),
                userDetails.getUsername(),
                userDetails.getEmail(),
                roles
        );
        return ResponseEntity.ok(
                ResponseUtil.success("User logged in successfully!", jwtResponse, null)
        );
    }

}
