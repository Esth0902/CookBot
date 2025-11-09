package com.development.cookbot.dto.recipe;

import com.development.cookbot.dto.ingredient.IngredientDto;
import com.development.cookbot.dto.step.StepDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeResponseDto {
    private String name;

    private int durationMinutes;

    private Boolean isFavorite;

    private List<IngredientDto> ingredients = new ArrayList<>();

    private List<StepDto> steps = new ArrayList<>();
}
