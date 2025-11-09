package com.development.cookbot.dto.step;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class StepDto {
    private int stepNumber;
    private String description;
}
