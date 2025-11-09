package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.recipe.RecipeInputDto;
import com.development.cookbot.entity.RecipeEntity;
import com.development.cookbot.entity.UserEntity;
import org.springframework.stereotype.Service;

@Service
public class RecipeMapper {

    public RecipeEntity toRecipeEntity(RecipeInputDto recipeInputDto, UserEntity userEntity) {
        return  RecipeEntity.builder()
                .name(recipeInputDto.getName())
                .durationMinutes(recipeInputDto.getDurationMinutes())
                .isFavorite(recipeInputDto.getIsFavorite())
                .user(userEntity)
                .build();
    }
}
