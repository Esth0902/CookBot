package com.development.cookbot.service.client;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.entity.UserEntity;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationService
{
    public UserPrincipalDto getPrincipal() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        UserEntity userDetails = (UserEntity) authentication.getPrincipal();
        return UserPrincipalDto.builder()
                .id(userDetails.getId())
                .username(userDetails.getUsername())
                .role(userDetails.getRole().name())
                .build();
    }
}
