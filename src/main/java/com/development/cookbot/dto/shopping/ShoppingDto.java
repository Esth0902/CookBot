package com.development.cookbot.dto.shopping;

import com.development.cookbot.dto.item.ItemDto;
import lombok.*;

import java.time.LocalDateTime;
import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Builder
@Getter
@Setter
public class ShoppingDto {

    private Long id;
    private String shoppingListName;
    private LocalDateTime creationDate;
    private List<ItemDto> items;
}
