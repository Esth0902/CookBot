package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.item.ItemDto;
import com.development.cookbot.entity.ItemEntity;
import org.springframework.stereotype.Service;

@Service
public class ItemMapper {
    public ItemEntity mapToItemEntity(ItemDto itemDto) {
        return ItemEntity.builder()
                .name(itemDto.getName())
                .quantity(itemDto.getQuantity())
                .sequence(itemDto.getSequence())
                .unit(itemDto.getUnit())
                .build();
    }
}
