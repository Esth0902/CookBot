package com.development.cookbot.security.filter;


import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.entity.Role;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.security.constant.AiApiUrl;
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
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
public class CustomTrialFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    private static final Set<String> PROTECTED_ENDPOINTS = Set.of(
            AiApiUrl.AI_URL_RECIPE,
            AiApiUrl.AI_URL_RECIPE_TITLE,
            AiApiUrl.AI_URL_RECIPE_TITLE_IMAGE,
            AiApiUrl.AI_URL_RECIPE_IMAGE,
            AiApiUrl.AI_URL_RECIPE_DISH
    );

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return PROTECTED_ENDPOINTS.stream().noneMatch(uri::startsWith);
    }


    @Override
    protected void doFilterInternal
            (HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws ServletException, IOException {

        UserPrincipalDto user = authenticationService.getPrincipal();
        LocalDate endedTrialDate = user.getSetting().getEndedTrialDate();

        if(user.getSetting().isTrial() && endedTrialDate.isBefore(LocalDate.now()) && user.getRole().equals("PREMIUM")) {

            // on repasse à free
            UserEntity userEntity = userRepository.findById(user.getId()).get();
            userEntity.setRole(Role.FREE);
            userRepository.save(userEntity);

            final Map<String, Object> body = new HashMap<>();
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("message", "You trial has ended, please upgrade your account to get access to all features.");
            body.put("data", null);

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
            return;
        }

        filterChain.doFilter(request, response);

    }
}
