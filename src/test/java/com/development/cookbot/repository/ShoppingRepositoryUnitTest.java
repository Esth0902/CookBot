package com.development.cookbot.repository;

import com.development.cookbot.entity.Role;
import com.development.cookbot.entity.ShoppingEntity;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.shopping.ShoppingRepository;
import com.development.cookbot.repository.user.UserRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.ActiveProfiles;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest
@ActiveProfiles("test")
public class ShoppingRepositoryUnitTest {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;
    private ShoppingEntity shoppingList1;
    private ShoppingEntity shoppingList2;

    @BeforeEach
    void setUp() {
        shoppingRepository.deleteAll();
        userRepository.deleteAll();

        user = UserEntity.builder()
                .username("testuser")
                .password("password")
                .role(Role.FREE)
                .build();
        userRepository.save(user);

        shoppingList1 = ShoppingEntity.builder()
                .shoppingListName("Weekly Groceries")
                .user(user)
                .build();

        shoppingList2 = ShoppingEntity.builder()
                .shoppingListName("Party Supplies")
                .user(user)
                .build();

        shoppingRepository.saveAll(List.of(shoppingList1, shoppingList2));
    }

    @Test
    @DisplayName("Given shopping lists exist for a user, when findAllShoppingListByUser is called, then it should return all shopping lists for that user")
    void givenShoppingListsForUser_whenFindAllShoppingListByUser_thenReturnAllUserShoppingLists() {
        // WHEN
        List<ShoppingEntity> userShoppingLists = shoppingRepository.findAllShoppingListByUser(user.getId());

        // THEN
        assertThat(userShoppingLists).isNotNull();
        assertThat(userShoppingLists.size()).isEqualTo(2);
        assertThat(userShoppingLists).extracting(ShoppingEntity::getShoppingListName)
                .containsExactlyInAnyOrder("Weekly Groceries", "Party Supplies");
    }

    @Test
    @DisplayName("Given no shopping lists exist for a user, when findAllShoppingListByUser is called, then it should return an empty list")
    void givenNoShoppingListsForUser_whenFindAllShoppingListByUser_thenReturnEmptyList() {
        // GIVEN
        UserEntity newUser = UserEntity.builder().username("newUser").password("pwd").role(Role.FREE).build();
        userRepository.save(newUser);

        // WHEN
        List<ShoppingEntity> userShoppingLists = shoppingRepository.findAllShoppingListByUser(newUser.getId());

        // THEN
        assertThat(userShoppingLists).isNotNull();
        assertThat(userShoppingLists).isEmpty();
    }

    @Test
    @DisplayName("Given shopping lists exist for a user, when countNumberOfShoppingListByUser is called, then it should return the correct count")
    void givenShoppingListsForUser_whenCountNumberOfShoppingListByUser_thenReturnCorrectCount() {
        // WHEN
        Integer count = shoppingRepository.countNumberOfShoppingListByUser(user.getId());

        // THEN
        assertThat(count).isEqualTo(2);
    }

    @Test
    @DisplayName("Given no shopping lists exist for a user, when countNumberOfShoppingListByUser is called, then it should return zero")
    void givenNoShoppingListsForUser_whenCountNumberOfShoppingListByUser_thenReturnZero() {
        // GIVEN
        UserEntity newUser = UserEntity.builder().username("newUser").password("pwd").role(Role.FREE).build();
        userRepository.save(newUser);

        // WHEN
        Integer count = shoppingRepository.countNumberOfShoppingListByUser(newUser.getId());

        // THEN
        assertThat(count).isEqualTo(0);
    }
}