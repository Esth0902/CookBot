package com.development.cookbot.service.mapper;

import com.development.cookbot.dto.item.ItemDto;
import com.development.cookbot.dto.shopping.ShoppingDto;
import com.development.cookbot.entity.ItemEntity;
import com.development.cookbot.entity.ShoppingEntity;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingMapper {

    public ShoppingDto mapShoppingEntityToShoppingDto(ShoppingEntity shoppingEntity) {

        ShoppingDto shoppingDto = new ShoppingDto();

        shoppingDto.setId(shoppingEntity.getId());
        shoppingDto.setShoppingListName(shoppingEntity.getShoppingListName());
        shoppingDto.setCreationDate(shoppingEntity.getCreationDate());

        List<ItemDto> itemDtos = new ArrayList<>();

        for(ItemEntity itemEntity: shoppingEntity.getItemList()) {
            ItemDto itemDto = new ItemDto();
            itemDto.setId(itemEntity.getId());
            itemDto.setUnit(itemEntity.getUnit());
            itemDto.setName(itemEntity.getName());
            itemDto.setQuantity(itemEntity.getQuantity());
            itemDto.setSequence(itemEntity.getSequence());
            itemDtos.add(itemDto);
            shoppingDto.setItems(itemDtos);
        }

        return shoppingDto;
    }

    public ShoppingEntity mapShoppingDtoToShoppingEntity(ShoppingDto shoppingDto) {

        ShoppingEntity shoppingEntity = new ShoppingEntity();
        shoppingEntity.setShoppingListName(shoppingDto.getShoppingListName());
        shoppingEntity.setCreationDate(shoppingDto.getCreationDate());

        List<ItemEntity> itemEntities = new ArrayList<>();

        for(ItemDto itemDto: shoppingDto.getItems()) {
            ItemEntity itemEntity = new ItemEntity();
            itemEntity.setUnit(itemDto.getUnit());
            itemEntity.setName(itemDto.getName());
            itemEntity.setQuantity(itemDto.getQuantity());
            itemEntity.setSequence(itemDto.getSequence());
            itemEntity.setShoppingEntity(shoppingEntity);
            itemEntities.add(itemEntity);
        }

        shoppingEntity.setItemList(itemEntities);
        return shoppingEntity;
    }
}
