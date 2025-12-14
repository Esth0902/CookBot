package com.development.cookbot.security.filter;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.security.constant.ApiUrl;
import com.development.cookbot.service.client.AuthenticationService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CustomAdminFilter extends OncePerRequestFilter {
    @Autowired
    private AuthenticationService authenticationService;

    private static final Set<String> PROTECTED_ENDPOINTS = Set.of(
            ApiUrl.METRIC_URL_BY_USER
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return PROTECTED_ENDPOINTS.stream().noneMatch(uri::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        UserPrincipalDto user = authenticationService.getPrincipal();
        if(!user.getRole().equals("ADMIN")) {
            final Map<String, Object> body = new HashMap<>();
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("message", "You cannot access this admin feature");
            body.put("data", null);

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
            return;
        }

        filterChain.doFilter(request, response);
    }
}
