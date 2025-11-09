package com.development.cookbot.dto.client;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class UserPrincipalDto {
    private Long id;
    private String username;
    private String role;
}
