package com.trustrace.tiles_hub_be.controllers;


import com.trustrace.tiles_hub_be.model.Role;
import com.trustrace.tiles_hub_be.model.UserEntity;
import com.trustrace.tiles_hub_be.model.UserRole;
import com.trustrace.tiles_hub_be.payload.request.LoginRequest;
import com.trustrace.tiles_hub_be.payload.request.SignUpRequest;
import com.trustrace.tiles_hub_be.payload.response.JwtResponse;
import com.trustrace.tiles_hub_be.payload.response.MessageResponse;
import com.trustrace.tiles_hub_be.security.jwt.JwtUtils;
import com.trustrace.tiles_hub_be.security.services.UserDetailsImpl;
import com.trustrace.tiles_hub_be.service.RoleService;
import com.trustrace.tiles_hub_be.service.UserEntityService;
import com.trustrace.tiles_hub_be.template.RoleTemplate;
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
    public ResponseEntity<?> registerUser(@RequestBody SignUpRequest signUpRequest) {

        //if userNmae already exists
        if(userEntityService.existsByName(signUpRequest.getUsername())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Username is already taken!"));
        }

        //if email already exists
        if(userEntityService.existsByEmailId(signUpRequest.getEmailId())) {
            return ResponseEntity
                    .badRequest()
                    .body(new MessageResponse("Error: Email is already in use!"));
        }

        //Create a new user account
        UserEntity user = new UserEntity(
                signUpRequest.getUsername(),
                signUpRequest.getEmailId(),
                encoder.encode(signUpRequest.getPassword()) // endcoding the password
        );

        Set<String> strRoles = signUpRequest.getRoles(); //get roles that are requested
        Set<Role> roles = new HashSet<>(); // initalize a empty set to hold the roles

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
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(superAdminRole);
                        break;
                    case "admin":
                        Role adminRole = roleService.findByName("ROLE_ADMIN")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(adminRole);
                        break;
                    default:
                        Role employeeRole = roleService.findByName("ROLE_EMPLOYEE")
                                .orElseThrow(() -> new RuntimeException("Error: Role is not found."));
                        roles.add(employeeRole);
                }
            });

        }

        //assiging roles set to the user and saving to DB
        user.setRoles(roles);
        userEntityService.saveUserEntity(user);

        //returning success message
        return ResponseEntity.ok(new MessageResponse("User registered successfully!"));


    }

    /**
     * Authenticate user and return a JWT token if successful.
     *
     * @param loginRequest The login request containing username and password.
     * @return A ResponseEntity containing the JWT response or an error message.
     */
    @PostMapping("/signin")
    public ResponseEntity<?> authenticateUser(@RequestBody LoginRequest loginRequest) {

        //authenticate the user with the provided username and password
        Authentication authentication = authenticationManager.authenticate(
                new UsernamePasswordAuthenticationToken(
                        loginRequest.getUsername(),
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
        return ResponseEntity.ok(
                new JwtResponse(
                        jwt,
                        userDetails.getId(),
                        userDetails.getUsername(),
                        userDetails.getEmailId(),
                        roles
                )
        );
    }

}
