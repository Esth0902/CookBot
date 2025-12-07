package com.development.cookbot.dto.item;

import lombok.*;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemDto {
    private Long id;
    private String name;
    private double quantity;
    private String unit;
    private boolean bought;
    private Integer sequence;
}