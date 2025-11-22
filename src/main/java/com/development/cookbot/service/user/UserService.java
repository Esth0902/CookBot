package com.development.cookbot.service.user;

import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.user.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {
    @Autowired
    private UserRepository userRepository;

    public UserEntity findById(Long id) {
        return userRepository.findById(id).orElseThrow(() -> new RuntimeException("User not found with id: " + id));
    }

    public void saveUserEntity(UserEntity userEntity) {
        userRepository.save(userEntity);
    }
}
