package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.ingredient.IngredientDto;
import com.development.cookbot.entity.IngredientEntity;
import com.development.cookbot.entity.RecipeEntity;
import org.springframework.stereotype.Service;

@Service
public class IngredientMapper {
    public IngredientEntity toIngredientEntity(IngredientDto ingredientDto, RecipeEntity savedRecipeEntity) {
        return  IngredientEntity.builder()
                .name(ingredientDto.getName())
                .quantity(ingredientDto.getQuantity())
                .recipe(savedRecipeEntity)
                .unit(ingredientDto.getUnit())
                .build();
    }
}
