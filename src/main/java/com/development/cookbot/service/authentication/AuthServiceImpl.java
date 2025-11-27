package com.development.cookbot.service.authentication;

import com.development.cookbot.dto.authentication.LoginDto;
import com.development.cookbot.dto.authentication.RegisterDto;
import com.development.cookbot.entity.Language;
import com.development.cookbot.entity.Role;
import com.development.cookbot.entity.SettingEntity;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.security.JwtTokenProvider;
import lombok.AllArgsConstructor;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.time.LocalDate;

@Service
@AllArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final AuthenticationManager authenticationManager;
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtTokenProvider jwtTokenProvider;

    @Override
    public String login(LoginDto loginDto) {
        Authentication authentication = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(
                loginDto.getUsername(), loginDto.getPassword()));

        SecurityContextHolder.getContext().setAuthentication(authentication);

        return jwtTokenProvider.generateToken(authentication);
    }

    @Override
    public String register(RegisterDto registerDto) {
        // Check if username is already taken
        if (userRepository.findByUsername(registerDto.getUsername()).isPresent()) {
            throw new RuntimeException("Username is already taken!");
        }

        UserEntity user = new UserEntity();
        user.setUsername(registerDto.getUsername());
        user.setPassword(passwordEncoder.encode(registerDto.getPassword()));
        user.setRole(Role.FREE);

        SettingEntity setting = SettingEntity.builder()
                .requestQuantity(0)
                .darkMode(false)
                .language(Language.FR)
                .nbPeople(2)
                .lastRequestDate(LocalDate.now())
                .isTrial(false)
                .user(user)
                .build();
        user.setSetting(setting);

        userRepository.save(user);

        return "User registered successfully!";
    }
}
