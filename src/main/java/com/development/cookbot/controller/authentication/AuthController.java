package com.development.cookbot.controller.authentication;

import com.development.cookbot.dto.authentication.JwtAuthResponse;
import com.development.cookbot.dto.authentication.LoginDto;
import com.development.cookbot.dto.authentication.RegisterDto;
import com.development.cookbot.exception.utils.Response;
import com.development.cookbot.service.authentication.AuthService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
@AllArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("/login")
    public ResponseEntity<Object> login(@RequestBody LoginDto loginDto) {
        String token = authService.login(loginDto);

        JwtAuthResponse jwtAuthResponse = new JwtAuthResponse();
        jwtAuthResponse.setAccessToken(token);

        Response<JwtAuthResponse> response = Response.<JwtAuthResponse>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Login done successfully")
                .data(jwtAuthResponse)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/register")
    public ResponseEntity<Object> register(@RequestBody RegisterDto registerDto) {
        String responseAuth = authService.register(registerDto);
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Registration done successfully")
                .data(responseAuth)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
