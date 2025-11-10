package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.ingredient.IngredientDto;
import com.development.cookbot.entity.IngredientEntity;
import com.development.cookbot.entity.RecipeEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

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

    public List<IngredientDto> TopIngredientDto(List<IngredientEntity> ingredientEntities) {

        List<IngredientDto> ingredientDtos = new ArrayList<>();

        for(IngredientEntity ingredientEntity:ingredientEntities) {
            IngredientDto ingredientDto = IngredientDto.builder()
                    .name(ingredientEntity.getName())
                    .quantity(ingredientEntity.getQuantity())
                    .unit(ingredientEntity.getUnit())
                    .build();

            ingredientDtos.add(ingredientDto);
        }

        return ingredientDtos;
    }
}
