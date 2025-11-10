package com.development.cookbot.dto.ai;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class RecipeTitle {
    private String title;
    private int durationMinutes;;
}
