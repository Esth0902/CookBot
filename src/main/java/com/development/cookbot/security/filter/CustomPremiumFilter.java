package com.development.cookbot.security.filter;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.service.client.AuthenticationService;
import com.development.cookbot.service.user.UserService;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;

import java.io.IOException;
import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.Set;

@Component
@Slf4j
public class CustomPremiumFilter extends OncePerRequestFilter {

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    protected UserService userService;

    private static final Set<String> PROTECTED_ENDPOINTS = Set.of(
//            "/api/v1/ai/recipe",
//            "/api/v1/ai/recipeTitle",
//            "/api/v1/ai/recipeTitle/image",
//            "/api/v1/ai/recipe/image"
            "/api/v1/recipe/all"
    );
    @Autowired
    private UserRepository userRepository;

    @Override
    protected boolean shouldNotFilter(HttpServletRequest request) {
        String uri = request.getRequestURI();
        return PROTECTED_ENDPOINTS.stream().noneMatch(uri::startsWith);
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request,
                                    HttpServletResponse response,
                                    FilterChain filterChain)
            throws ServletException, IOException {

        UserPrincipalDto user = authenticationService.getPrincipal();

        LocalDate today = LocalDate.now();
        LocalDate lastRequest = user.getSetting().getLastRequestDate();

        // --- 1) Reset journalier si on change de jour ---
        if (lastRequest == null || !lastRequest.equals(today)) {
            user.getSetting().setLastRequestDate(today);
            user.getSetting().setRequestQuantity(0);

            UserEntity userEntity = userService.findById(user.getId());
            userEntity.setSetting(user.getSetting());
            userRepository.save(userEntity);
        }

        // --- 2) Vérification de quota pour les FREE ---
        if (user.getRole().equals("FREE") && user.getSetting().getRequestQuantity() >= 5) {

            response.setContentType(MediaType.APPLICATION_JSON_VALUE);
            response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);

            final Map<String, Object> body = new HashMap<>();
            body.put("status", HttpServletResponse.SC_UNAUTHORIZED);
            body.put("message", "You have reach your daily quota limit");
            body.put("data", null);

            final ObjectMapper mapper = new ObjectMapper();
            mapper.writeValue(response.getOutputStream(), body);
        }

        // --- 3) Incrément du compteur ---
        user.getSetting().setRequestQuantity(user.getSetting().getRequestQuantity() + 1);

        // --- 4) Sauvegarde ---
        UserEntity userEntity = userService.findById(user.getId());
        userEntity.setSetting(user.getSetting());
        userService.saveUserEntity(userEntity);

        // --- 5) Continuer le filtre ---
        filterChain.doFilter(request, response);
    }
}