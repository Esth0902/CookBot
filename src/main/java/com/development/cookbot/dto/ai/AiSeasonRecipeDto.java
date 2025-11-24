package com.development.cookbot.dto.ai;

import com.development.cookbot.dto.ingredient.IngredientDto;
import com.development.cookbot.dto.step.StepDto;
import com.development.cookbot.dto.tip.TipDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AiSeasonRecipeDto {
    private String name;

    private int durationMinutes;

    private List<IngredientDto> ingredients = new ArrayList<>();

    private List<StepDto> steps = new ArrayList<>();

    private List<TipDto> tips = new ArrayList<>();
}
