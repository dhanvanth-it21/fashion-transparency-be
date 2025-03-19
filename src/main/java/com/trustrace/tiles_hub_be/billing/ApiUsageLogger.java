package com.trustrace.tiles_hub_be.billing;

import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;

@Component
public class ApiUsageLogger implements HandlerInterceptor {

    @Autowired
    private BillingCycleService billingCycleService;

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        if (request.getRequestURI().contains("signin")) {
            return true;
        }
        if (request.getRequestURI().contains("api/payment/webhook")) {
            return true;
        }

        String email = getAuthenticatedUserEmail();

        billingCycleService.logApiUsage(request.getMethod());

        return true;

    }

    private String getAuthenticatedUserEmail() {
        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        return ((UserDetails) principal).getUsername();
    }

}
