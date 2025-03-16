package com.trustrace.tiles_hub_be.usage;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

import java.util.HashMap;
import java.util.Map;

@Component
public class ApiUsageTracker implements HandlerInterceptor {


    @Autowired
    private ApiUsageTemplate apiUsageTemplate;



    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {

        if(request.getRequestURI().contains("signin")) {
            return true;
        }

        String emial = getAuthenticatedUserEmail();

        ApiUsage apiUsage = ApiUsage.builder()
                .apiEndPoint(request.getRequestURI())
                .apiMethod(request.getMethod())
                .email(emial)
                .price(0.30)
                .build();

        apiUsageTemplate.save(apiUsage);
        return true;
    }

    private String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }
}
