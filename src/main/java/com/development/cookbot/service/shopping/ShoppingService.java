package com.development.cookbot.service.shopping;

import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.dto.item.ItemDto;
import com.development.cookbot.dto.item.ItemListDto;
import com.development.cookbot.dto.shopping.ShoppingDto;
import com.development.cookbot.entity.ItemEntity;
import com.development.cookbot.entity.ShoppingEntity;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.exception.NotFoundException;
import com.development.cookbot.repository.shopping.ShoppingRepository;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.service.client.AuthenticationService;
import com.development.cookbot.service.mapper.ItemMapper;
import com.development.cookbot.service.mapper.ShoppingMapper;
import com.development.cookbot.service.shopping.constant.ShoppingConstant;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Service
public class ShoppingService {

    @Autowired
    private ShoppingRepository shoppingRepository;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private ShoppingMapper shoppingMapper;

    @Autowired
    private ItemMapper itemMapper;

    @Autowired
    private UserRepository userRepository;

    public ShoppingDto createShoppingList(ShoppingDto shoppingDto) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        UserEntity userEntity = userRepository.findById(userPrincipalDto.getId()).orElseThrow(() ->
                new NotFoundException("User not found with id: " + userPrincipalDto.getId()));

        Integer nbShoppingList = shoppingRepository.countNumberOfShoppingListByUser(userPrincipalDto.getId());

        if(nbShoppingList < ShoppingConstant.MAX_SHOPPING_LIST_BY_USER)
        {
            ShoppingEntity shoppingEntity = shoppingMapper.mapShoppingDtoToShoppingEntity(shoppingDto);
            shoppingEntity.setUser(userEntity);
            ShoppingEntity savedShoppingEntity = shoppingRepository.save(shoppingEntity);
            return shoppingMapper.mapShoppingEntityToShoppingDto(savedShoppingEntity);

        } else
        {
            throw new RuntimeException("You can only have 5 shopping lists");
        }
    }

    public ShoppingDto updateShoppingList(ShoppingDto shoppingDto) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        ShoppingEntity shoppingEntity = shoppingRepository.findById(shoppingDto.getId()).orElseThrow(() ->
                new NotFoundException("Shopping not found with id: " + shoppingDto.getId()));

        if(shoppingEntity.getUser().getId().equals(userPrincipalDto.getId()))
        {
            ShoppingEntity shoppingEntityToUpdate =
                    shoppingMapper.mapShoppingDtoToShoppingEntity(shoppingDto);

            shoppingEntityToUpdate.setId(shoppingDto.getId());
            shoppingEntityToUpdate.setUser(shoppingEntity.getUser());

            ShoppingEntity updatedShoppingEntity = shoppingRepository.save(shoppingEntityToUpdate);
            return shoppingMapper.mapShoppingEntityToShoppingDto(updatedShoppingEntity);
        }
        throw new RuntimeException("Shopping not found with id: " + shoppingDto.getId());
    }

    public ShoppingDto addItemListToShoppingList(Long shoppingListId, ItemListDto itemDtoList) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        ShoppingEntity shoppingEntity = shoppingRepository.findById(shoppingListId).orElseThrow(() ->
                new NotFoundException("Shopping not found with id: " + shoppingListId));

        if(shoppingEntity.getUser().getId().equals(userPrincipalDto.getId())) {
            for(ItemDto itemDto: itemDtoList.getItems()) {
                ItemEntity itemEntity = itemMapper.mapToItemEntity(itemDto);
                itemEntity.setShoppingEntity(shoppingEntity);
                shoppingEntity.getItemList().add(itemEntity);
            }
            ShoppingEntity updatedShoppingEntity = shoppingRepository.save(shoppingEntity);
            return shoppingMapper.mapShoppingEntityToShoppingDto(updatedShoppingEntity);
        }

        throw new NotFoundException("Shopping not found with id: " + shoppingListId);
    }

    public ShoppingDto addItemToShoppingList(Long shoppingListId, ItemDto itemDto) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        ShoppingEntity shoppingEntity = shoppingRepository.findById(shoppingListId).orElseThrow(() ->
                new NotFoundException("Shopping not found with id: " + shoppingListId));

        if(shoppingEntity.getUser().getId().equals(userPrincipalDto.getId())) {
            ItemEntity itemEntity = itemMapper.mapToItemEntity(itemDto);
            itemEntity.setShoppingEntity(shoppingEntity);
            shoppingEntity.getItemList().add(itemEntity);
            ShoppingEntity updatedShoppingEntity = shoppingRepository.save(shoppingEntity);
            return shoppingMapper.mapShoppingEntityToShoppingDto(updatedShoppingEntity);
        }

        throw new NotFoundException("Shopping not found with id: " + shoppingListId);

    }

    public String deleteShoppingListById(Long id) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        ShoppingEntity shoppingEntity = shoppingRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Shopping not found with id: " + id));

        if(shoppingEntity.getUser().getId().equals(userPrincipalDto.getId())) {
            shoppingRepository.deleteById(id);
            return "Shopping list deleted";
        }
        throw new RuntimeException("Shopping not found with id: " + id);
    }

    public List<ShoppingDto> getAllShoppingListByUser() {
        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        List<ShoppingEntity> shoppingEntities = shoppingRepository.findAllShoppingListByUser(userPrincipalDto.getId());

        List<ShoppingDto> shoppingDtoList = new ArrayList<>();

        for(ShoppingEntity shoppingEntity: shoppingEntities) {
            shoppingDtoList.add(shoppingMapper.mapShoppingEntityToShoppingDto(shoppingEntity));
        }

        return shoppingDtoList;
    }

    public ShoppingDto getShoppingListById(Long id) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();
        ShoppingEntity shoppingEntity = shoppingRepository.findById(id).orElseThrow(() ->
                new NotFoundException("Shopping not found with id: " + id));

        if(shoppingEntity.getUser().getId().equals(userPrincipalDto.getId())) {
            return shoppingMapper.mapShoppingEntityToShoppingDto(shoppingEntity);
        }

        throw new RuntimeException("Shopping not found with id: " + id);
    }

}
