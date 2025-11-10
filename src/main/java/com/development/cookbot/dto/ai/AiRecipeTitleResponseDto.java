package com.development.cookbot.dto.ai;


import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class AiRecipeTitleResponseDto {
    public List<RecipeTitle> recipeTitles;
}
