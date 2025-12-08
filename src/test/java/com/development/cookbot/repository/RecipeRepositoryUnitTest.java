package com.development.cookbot.repository;

import com.development.cookbot.entity.RecipeEntity;
import com.development.cookbot.entity.Role;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.repository.recipe.RecipeRepository;
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
public class RecipeRepositoryUnitTest {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private UserRepository userRepository;

    private UserEntity user;
    private RecipeEntity recipe1;
    private RecipeEntity recipe2;
    private RecipeEntity recipe3;

    @BeforeEach
    void setUp() {
        recipeRepository.deleteAll();
        userRepository.deleteAll();

        user = UserEntity.builder()
                .username("testuser")
                .password("password")
                .role(Role.FREE)
                .build();
        userRepository.save(user);

        recipe1 = RecipeEntity.builder()
                .name("Pasta Carbonara")
                .durationMinutes(30)
                .isFavorite(true)
                .user(user)
                .build();

        recipe2 = RecipeEntity.builder()
                .name("Chicken Curry")
                .durationMinutes(45)
                .isFavorite(false)
                .user(user)
                .build();

        recipe3 = RecipeEntity.builder()
                .name("Salad")
                .durationMinutes(15)
                .isFavorite(true)
                .user(user)
                .build();

        recipeRepository.saveAll(List.of(recipe1, recipe2, recipe3));
    }

    @Test
    @DisplayName("Given recipes exist for a user, when findRecipeByUser is called, then it should return all recipes for that user")
    void givenRecipesForUser_whenFindRecipeByUser_thenReturnAllUserRecipes() {
        // WHEN
        List<RecipeEntity> userRecipes = recipeRepository.findRecipeByUser(user.getId());

        // THEN
        assertThat(userRecipes).isNotNull();
        assertThat(userRecipes.size()).isEqualTo(3);
        assertThat(userRecipes).extracting(RecipeEntity::getName)
                .containsExactlyInAnyOrder("Pasta Carbonara", "Chicken Curry", "Salad");
    }

    @Test
    @DisplayName("Given no recipes exist for a user, when findRecipeByUser is called, then it should return an empty list")
    void givenNoRecipesForUser_whenFindRecipeByUser_thenReturnEmptyList() {
        // GIVEN
        UserEntity newUser = UserEntity.builder().username("newUser").password("pwd").role(Role.FREE).build();
        userRepository.save(newUser);

        // WHEN
        List<RecipeEntity> userRecipes = recipeRepository.findRecipeByUser(newUser.getId());

        // THEN
        assertThat(userRecipes).isNotNull();
        assertThat(userRecipes).isEmpty();
    }

    @Test
    @DisplayName("Given favorite recipes exist for a user, when findAllFavoriteRecipes is called, then it should return only favorite recipes")
    void givenFavoriteRecipesForUser_whenFindAllFavoriteRecipes_thenReturnOnlyFavoriteRecipes() {
        // WHEN
        List<RecipeEntity> favoriteRecipes = recipeRepository.findAllFavoriteRecipes(user.getId());

        // THEN
        assertThat(favoriteRecipes).isNotNull();
        assertThat(favoriteRecipes.size()).isEqualTo(2);
        assertThat(favoriteRecipes).extracting(RecipeEntity::getName)
                .containsExactlyInAnyOrder("Pasta Carbonara", "Salad");
    }

    @Test
    @DisplayName("Given no favorite recipes exist for a user, when findAllFavoriteRecipes is called, then it should return an empty list")
    void givenNoFavoriteRecipesForUser_whenFindAllFavoriteRecipes_thenReturnEmptyList() {
        // GIVEN
        recipe1.setIsFavorite(false);
        recipe3.setIsFavorite(false);
        // recipe2 is already not favorite
        recipeRepository.saveAll(List.of(recipe1, recipe3));

        // WHEN
        List<RecipeEntity> favoriteRecipes = recipeRepository.findAllFavoriteRecipes(user.getId());

        // THEN
        assertThat(favoriteRecipes).isNotNull();
        assertThat(favoriteRecipes).isEmpty();
    }
}
