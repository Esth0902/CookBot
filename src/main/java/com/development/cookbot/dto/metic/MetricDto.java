package com.development.cookbot.dto.metic;

import lombok.*;

import java.sql.Timestamp;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class MetricDto {
    private Long id;
    private Long executionTime;
    private Long inputToken;
    private Long outputToken;
    private Long totalToken;
    private Timestamp creationDate;
    private String username;
    private Long userId;

}
