package com.development.cookbot.controller.shopping;

import com.development.cookbot.dto.item.ItemDto;
import com.development.cookbot.dto.item.ItemListDto;
import com.development.cookbot.dto.shopping.ShoppingDto;
import com.development.cookbot.exception.utils.Response;
import com.development.cookbot.service.shopping.ShoppingService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/shopping")
public class ShoppingController {

    @Autowired
    private ShoppingService shoppingService;

    @GetMapping
    public ResponseEntity<Object> getAllShoppingListByUser() {
        List<ShoppingDto> shoppingDtoList = shoppingService.getAllShoppingListByUser();
        Response<List<ShoppingDto>> response = Response.<List<ShoppingDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("All shopping list has been retrieved successfully")
                .data(shoppingDtoList)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteShoppingListById(@PathVariable Long id) {
        String shoppingListDelete = shoppingService.deleteShoppingListById(id);
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Shopping list has been deleted successfully")
                .data(shoppingListDelete)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping
    public ResponseEntity<Object> createShoppingList(@RequestBody ShoppingDto shoppingDto) {
        ShoppingDto shoppingDtoCreated = shoppingService.createShoppingList(shoppingDto);
        Response<ShoppingDto> response = Response.<ShoppingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Shopping list has been created successfully")
                .data(shoppingDtoCreated)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/item/{shoppingListId}")
    public ResponseEntity<Object> addItemToShoppingList(@PathVariable Long shoppingListId,
                                                        @RequestBody ItemDto itemDto) {

        ShoppingDto shoppingDtoCreated = shoppingService.addItemToShoppingList(shoppingListId,itemDto);
        Response<ShoppingDto> response = Response.<ShoppingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Item has been added to shopping list successfully")
                .data(shoppingDtoCreated)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/item/list/{shoppingListId}")
    public ResponseEntity<Object> addItemListToShoppingList(@PathVariable Long shoppingListId,
                                                            @RequestBody ItemListDto itemDtoList) {
        ShoppingDto shoppingDtoCreated = shoppingService.addItemListToShoppingList(shoppingListId,itemDtoList);
        Response<ShoppingDto> response = Response.<ShoppingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Items has been added to shopping list successfully")
                .data(shoppingDtoCreated)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping
    public ResponseEntity<Object> updateShoppingList(@RequestBody ShoppingDto shoppingDto) {
        ShoppingDto shoppingDtoRes = shoppingService.updateShoppingList(shoppingDto);
        Response<ShoppingDto> response = Response.<ShoppingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Shopping list has been updated successfully")
                .data(shoppingDtoRes)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Object> getShoppingListById(@PathVariable Long id) {
        ShoppingDto shoppingDto = shoppingService.getShoppingListById(id);
        Response<ShoppingDto> response = Response.<ShoppingDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Shopping list has been retrieved successfully")
                .data(shoppingDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}
