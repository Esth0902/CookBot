package com.development.cookbot.controller.ai;

import com.development.cookbot.dto.ai.AiDishInputDto;
import com.development.cookbot.dto.ai.AiRecipeInputDto;
import com.development.cookbot.dto.ai.AiRecipeResponseDto;
import com.development.cookbot.dto.ai.AiRecipeTitleResponseDto;
import com.development.cookbot.exception.utils.Response;
import com.development.cookbot.service.ai.AiWebCallService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/api/v1/ai")
public class AIAgentController {

    @Autowired
    private AiWebCallService aiWebCallService;

    @PostMapping(value = "/recipeTitle/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> askRecipeTitleFromImage(@RequestParam(name="file") MultipartFile file)
            throws IOException {

        AiRecipeTitleResponseDto aiRecipeTitleResponseDto = aiWebCallService.askRecipeTitleFromImage(file.getBytes());
        Response<AiRecipeTitleResponseDto> response = Response.<AiRecipeTitleResponseDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(aiRecipeTitleResponseDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping(value = "/recipe/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<Object> askRecipeFromImage(@RequestParam(name="file") MultipartFile file) throws IOException {

        AiRecipeResponseDto aiRecipeResponseDto = aiWebCallService.askRecipeFromImage(file.getBytes());
        Response<AiRecipeResponseDto> response = Response.<AiRecipeResponseDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(aiRecipeResponseDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recipeTitle")
    public ResponseEntity<Object> askRecipeTitle(@RequestBody AiRecipeInputDto aiRecipeInputDto) {

        AiRecipeTitleResponseDto aiRecipeTitleResponseDto = aiWebCallService.askRecipeTitle(aiRecipeInputDto);
        Response<AiRecipeTitleResponseDto> response = Response.<AiRecipeTitleResponseDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(aiRecipeTitleResponseDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/recipe")
    public ResponseEntity<Object> askForRecipe(@RequestBody AiRecipeInputDto aiRecipeInputDto) {

        AiRecipeResponseDto aiRecipeResponseDto = aiWebCallService.askForRecipe(aiRecipeInputDto);
        Response<AiRecipeResponseDto> response = Response.<AiRecipeResponseDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(aiRecipeResponseDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }

    @PostMapping("/dish")
    public ResponseEntity<Object> askRecipeFromDish(@RequestBody AiDishInputDto aiDishInputDto) {

        AiRecipeResponseDto aiRecipeResponseDto = aiWebCallService.askRecipeFromDish(aiDishInputDto);
        Response<AiRecipeResponseDto> response = Response.<AiRecipeResponseDto>builder()
                .responseCode(HttpStatus.OK.value())
                .responseMessage("Operation done successfully")
                .data(aiRecipeResponseDto)
                .success(true)
                .build();
        return ResponseEntity.ok(response);
    }
}