package com.development.cookbot.controller.recipe;

import com.development.cookbot.dto.recipe.RecipeInputDto;
import com.development.cookbot.dto.recipe.RecipeResponseDto;
import com.development.cookbot.exception.utils.Response;
import com.development.cookbot.service.recipe.RecipeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

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

    @GetMapping
    public ResponseEntity<Object> getAllRecipe() {
        return null;
    }

}
