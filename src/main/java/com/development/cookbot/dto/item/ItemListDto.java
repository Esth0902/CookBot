package com.development.cookbot.dto.item;

import lombok.*;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ItemListDto {
    List<ItemDto> items;
}
