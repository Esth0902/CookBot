package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.step.StepDto;
import com.development.cookbot.entity.RecipeEntity;
import com.development.cookbot.entity.StepEntity;
import org.springframework.stereotype.Service;

@Service
public class StepMapper {
    public StepEntity toStepEntity(StepDto stepDto, RecipeEntity savedRecipeEntity) {
        return   StepEntity.builder()
                .stepNumber(stepDto.getStepNumber())
                .description(stepDto.getDescription())
                .recipe(savedRecipeEntity)
                .build();
    }
}
