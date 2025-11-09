package com.development.cookbot.dto.ingredient;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class IngredientDto {
    private String name;
    private double quantity;
    private String unit;
}
