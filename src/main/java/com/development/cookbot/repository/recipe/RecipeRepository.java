package com.development.cookbot.repository.recipe;

import com.development.cookbot.entity.RecipeEntity;
import org.springframework.data.jpa.repository.JpaRepository;

public interface RecipeRepository extends JpaRepository<RecipeEntity,Long> {
}
