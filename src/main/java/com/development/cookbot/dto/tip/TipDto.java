package com.development.cookbot.dto.tip;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class TipDto {
    List<String> tips;
}
