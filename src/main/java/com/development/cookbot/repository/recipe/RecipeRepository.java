package com.development.cookbot.repository.recipe;

import com.development.cookbot.entity.RecipeEntity;
import jakarta.transaction.Transactional;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface RecipeRepository extends JpaRepository<RecipeEntity,Long> {
    @Transactional
    @Query(value = "SELECT r FROM RecipeEntity r WHERE r.user.id = ?1")
    List<RecipeEntity> findRecipeByUser(Long userId);

    @Transactional
    @Query(value = "SELECT r FROM RecipeEntity r WHERE r.user.id = ?1 AND r.isFavorite = true")
    List<RecipeEntity> findAllFavoriteRecipes(Long userId);
}
