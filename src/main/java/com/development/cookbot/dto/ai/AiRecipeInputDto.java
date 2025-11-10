package com.development.cookbot.dto.ai;

import com.development.cookbot.dto.ingredient.IngredientDto;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AiRecipeInputDto {
    private List<IngredientDto> ingredients = new ArrayList<>();
}
