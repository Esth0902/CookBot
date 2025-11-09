package com.development.cookbot.service.authentication;

import com.development.cookbot.dto.authentication.LoginDto;
import com.development.cookbot.dto.authentication.RegisterDto;

public interface AuthService {
    String login(LoginDto loginDto);
    String register(RegisterDto registerDto);
}
