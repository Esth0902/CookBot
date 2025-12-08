package com.development.cookbot.repository;

import com.development.cookbot.entity.Role;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.Optional;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class UserRepositoryUnitTest {

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;

    @BeforeEach
    public void setup() {
        userRepository.deleteAll();
        user = UserEntity.builder()
                .username("testuser")
                .password("password")
                .role(Role.FREE)
                .build();
    }

    @Test
    @DisplayName("Given a user is saved, when findByUsername is called, then it should return the correct user")
    public void givenUserSaved_whenFindByUsername_thenReturnUser() {
        // GIVEN
        userRepository.save(user);

        // WHEN
        Optional<UserEntity> foundUser = userRepository.findByUsername("testuser");

        // THEN
        assertThat(foundUser).isPresent();
        assertThat(foundUser.get().getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Given no user exists with a username, when findByUsername is called, then it should return empty")
    public void givenNoUser_whenFindByUsername_thenReturnEmpty() {
        // WHEN
        Optional<UserEntity> foundUser = userRepository.findByUsername("nonexistentuser");

        // THEN
        assertThat(foundUser).isNotPresent();
    }

    @Test
    @DisplayName("Given a user is saved, when existsByUsername is called, then it should return true")
    public void givenUserExists_whenExistsByUsername_thenReturnTrue() {
        // GIVEN
        userRepository.save(user);

        // WHEN
        Optional<UserEntity> exists = userRepository.findByUsername("testuser");

        // THEN
        assertThat(exists).isPresent();
    }



    @Test
    @DisplayName("Given no user exists with a username, when existsByUsername is called, then it should return false")
    public void givenNoUser_whenExistsByUsername_thenReturnFalse() {
        // WHEN
        Optional<UserEntity> exists = userRepository.findByUsername("nonexistentuser");

        // THEN
        assertThat(exists).isNotPresent();
    }

    @Test
    @DisplayName("Given a user entity, when it is saved, then it should be persisted in the database")
    public void givenUserEntity_whenSave_thenUserIsPersisted() {
        // WHEN
        UserEntity savedUser = userRepository.save(user);

        // THEN
        assertThat(savedUser).isNotNull();
        assertThat(savedUser.getId()).isNotNull();
        assertThat(savedUser.getUsername()).isEqualTo("testuser");
    }

    @Test
    @DisplayName("Given a user exists, when it is deleted, then it should be removed from the database")
    public void givenUserExists_whenDelete_thenUserIsRemoved() {
        // GIVEN
        UserEntity savedUser = userRepository.save(user);
        Long userId = savedUser.getId();

        // WHEN
        userRepository.delete(savedUser);

        // THEN
        Optional<UserEntity> deletedUser = userRepository.findById(userId);
        assertThat(deletedUser).isNotPresent();
    }
}