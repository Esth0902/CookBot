package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.step.StepDto;
import com.development.cookbot.entity.RecipeEntity;
import com.development.cookbot.entity.StepEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class StepMapper {
    public StepEntity toStepEntity(StepDto stepDto, RecipeEntity savedRecipeEntity) {
        return   StepEntity.builder()
                .stepNumber(stepDto.getStepNumber())
                .description(stepDto.getDescription())
                .recipe(savedRecipeEntity)
                .build();
    }

    public List<StepDto> ToStepDto(List<StepEntity> stepEntities ) {

        List<StepDto> stepDtos = new ArrayList<>();

        for (StepEntity stepEntity:stepEntities) {
            StepDto stepDto = StepDto.builder()
                    .stepNumber(stepEntity.getStepNumber())
                    .description(stepEntity.getDescription())
                    .build();

            stepDtos.add(stepDto);
        }
        return stepDtos;
    }
}
