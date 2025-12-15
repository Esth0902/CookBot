package com.development.cookbot.service.user;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.service.client.AuthenticationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private AuthenticationService authenticationService;

    private String randomString() {
        return "Anonymate_user_" + Long.toString(System.nanoTime(), 36);
    }

    public String anonymizeUser() {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        UserEntity userEntity = findById(userPrincipalDto.getId());
        userEntity.setPassword(this.randomString());
        userEntity.setUsername(this.randomString());
        userRepository.save(userEntity);
        return "user has been anonymized";
    }

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void saveUserEntity(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
