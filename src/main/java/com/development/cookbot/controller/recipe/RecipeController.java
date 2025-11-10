package com.development.cookbot.controller.recipe;

import com.development.cookbot.dto.recipe.RecipeInputDto;
import com.development.cookbot.dto.recipe.RecipeResponseDto;
import com.development.cookbot.exception.utils.Response;
import com.development.cookbot.service.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/v1/recipe")
public class RecipeController {

    @Autowired
    private RecipeService recipeService;

    @PostMapping
    public ResponseEntity<Object> createRecipe(@RequestBody RecipeInputDto recipeInputDto) {
        String responseRecipe = recipeService.createRecipe(recipeInputDto);
        Response<String> response = Response.<String>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(responseRecipe)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PatchMapping("/{recipeId}")
    public ResponseEntity<Object> switchFavoriteRecipe(@PathVariable Long recipeId) {
        RecipeResponseDto recipeResponseDto = recipeService.updateFavoriteRecipeById(recipeId);
        Response<RecipeResponseDto> response = Response.<RecipeResponseDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Recipe retrieved successfully")
                .data(recipeResponseDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PutMapping("/{recipeId}")
    public ResponseEntity<Object> updateRecipe(@PathVariable Long recipeId, @RequestBody RecipeInputDto recipeInputDto) {
        return null;
    }

    @GetMapping("/{recipeId}")
    public ResponseEntity<Object> getRecipe(@PathVariable Long recipeId) {
        RecipeResponseDto recipeResponseDto = recipeService.getRecipeById(recipeId);
        Response<RecipeResponseDto> response = Response.<RecipeResponseDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Recipe retrieved successfully")
                .data(recipeResponseDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/favorites")
    public ResponseEntity<Object> getUserFavoriteRecipe() {
        List<RecipeResponseDto> recipeResponseDtos = recipeService.getAllFavoriteRecipe();
        Response<List<RecipeResponseDto>> response = Response.<List<RecipeResponseDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("All favorite recipes was retrieved successfully")
                .data(recipeResponseDtos)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @GetMapping("/all")
    public ResponseEntity<Object> getAllUserRecipe() {
        List<RecipeResponseDto> recipeResponseDtos = recipeService.getAllUserRecipe();
        Response<List<RecipeResponseDto>> response = Response.<List<RecipeResponseDto>>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Recipes retrieved successfully")
                .data(recipeResponseDtos)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

}
