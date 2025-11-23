package com.development.cookbot.dto.embedding;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class EmbeddingDto {
    private List<String> listA;
    private List<String> listB;
}
