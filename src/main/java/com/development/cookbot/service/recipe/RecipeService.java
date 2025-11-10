package com.development.cookbot.service.recipe;

import com.development.cookbot.dto.ingredient.IngredientDto;
import com.development.cookbot.dto.recipe.RecipeInputDto;
import com.development.cookbot.dto.recipe.RecipeResponseDto;
import com.development.cookbot.dto.step.StepDto;
import com.development.cookbot.entity.IngredientEntity;
import com.development.cookbot.entity.RecipeEntity;
import com.development.cookbot.entity.StepEntity;
import com.development.cookbot.entity.UserEntity;
import com.development.cookbot.exception.NotFoundException;
import com.development.cookbot.repository.ingredient.IngredientRepository;
import com.development.cookbot.repository.recipe.RecipeRepository;
import com.development.cookbot.repository.step.StepRepository;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.service.client.AuthenticationService;
import com.development.cookbot.service.mapper.IngredientMapper;
import com.development.cookbot.service.mapper.RecipeMapper;
import com.development.cookbot.service.mapper.StepMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Service
public class RecipeService {

    @Autowired
    private RecipeRepository recipeRepository;

    @Autowired
    private StepRepository stepRepository;

    @Autowired
    private IngredientRepository ingredientRepository;

    @Autowired
    private UserRepository userRepository;

    @Autowired
    private IngredientMapper ingredientMapper;

    @Autowired
    private StepMapper stepMapper;

    @Autowired
    private RecipeMapper recipeMapper;

    @Autowired
    private AuthenticationService authenticationService;

    public String createRecipe(RecipeInputDto recipeInputDto) {

        Optional<UserEntity> userEntity = userRepository.findByUsername(authenticationService.getPrincipal().getUsername());

        if(userEntity.isPresent()) {
            RecipeEntity recipeEntity = recipeMapper.toRecipeEntity(recipeInputDto, userEntity.get());
            RecipeEntity savedRecipeEntity = recipeRepository.save(recipeEntity);

            List<IngredientEntity> ingredientEntities = new ArrayList<>();

            for (IngredientDto ingredientDto:recipeInputDto.getIngredients()) {
                IngredientEntity ingredientEntity = ingredientMapper.toIngredientEntity(ingredientDto, savedRecipeEntity);
                ingredientEntities.add(ingredientEntity);
                ingredientRepository.save(ingredientEntity);
            }

            List<StepEntity> stepEntities = new ArrayList<>();

            for(StepDto stepDto:recipeInputDto.getSteps()) {
                StepEntity stepEntity = stepMapper.toStepEntity(stepDto, savedRecipeEntity);
                stepEntities.add(stepEntity);
                stepRepository.save(stepEntity);
            }

            return "Recipe created successfully !";
        }

        throw new NotFoundException("User not found");
    }

    public RecipeResponseDto getRecipeById(Long recipeId) {

        RecipeEntity recipeEntity = recipeRepository.findById(recipeId).orElseThrow(()
                -> new NotFoundException("Recipe not found"));

        RecipeResponseDto recipeResponseDto = recipeMapper.ToRecipeResponseDto(recipeEntity);
        List<IngredientDto> ingredientDtos = ingredientMapper.TopIngredientDto(recipeEntity.getIngredients());
        List<StepDto> stepDtos = stepMapper.ToStepDto(recipeEntity.getSteps());

        recipeResponseDto.setIngredients(ingredientDtos);
        recipeResponseDto.setSteps(stepDtos);

        return recipeResponseDto;
    }

    public RecipeResponseDto updateFavoriteRecipeById(Long recipeId) {

        RecipeEntity recipeEntity = recipeRepository.findById(recipeId).orElseThrow(()
                -> new NotFoundException("Recipe not found"));

        Optional<UserEntity> userEntity = userRepository.findByUsername(authenticationService.getPrincipal().getUsername());

        if(userEntity.isPresent()) {

            if(!recipeEntity.getUser().getId().equals(userEntity.get().getId())) {
                throw new NotFoundException("User not allow to update this recipe");
            }

            recipeEntity.setIsFavorite(!recipeEntity.getIsFavorite());
            RecipeEntity recipeEntityUpdated = recipeRepository.save(recipeEntity);

            RecipeResponseDto recipeResponseDto = recipeMapper.ToRecipeResponseDto(recipeEntityUpdated);
            List<IngredientDto> ingredientDtos = ingredientMapper.TopIngredientDto(recipeEntityUpdated.getIngredients());
            List<StepDto> stepDtos = stepMapper.ToStepDto(recipeEntityUpdated.getSteps());

            recipeResponseDto.setIngredients(ingredientDtos);
            recipeResponseDto.setSteps(stepDtos);

            return recipeResponseDto;
        }
        throw new NotFoundException("User not found");
    }

    public List<RecipeResponseDto> getAllUserRecipe() {

        Optional<UserEntity> userEntity = userRepository.findByUsername(authenticationService.getPrincipal().getUsername());

        if(userEntity.isPresent()) {
            List<RecipeEntity> recipeEntities = recipeRepository.findRecipeByUser(userEntity.get().getId());
            List<RecipeResponseDto> recipeResponseDtos = new ArrayList<>();
            for(RecipeEntity recipeEntity:recipeEntities) {
                RecipeResponseDto recipeResponseDto = recipeMapper.ToRecipeResponseDto(recipeEntity);
                recipeResponseDto.setIngredients(ingredientMapper.TopIngredientDto(recipeEntity.getIngredients()));
                recipeResponseDto.setSteps(stepMapper.ToStepDto(recipeEntity.getSteps()));
                recipeResponseDtos.add(recipeResponseDto);
            }
            return recipeResponseDtos;
        }

        throw new NotFoundException("User not found");
    }

    public List<RecipeResponseDto> getAllFavoriteRecipe() {

        Optional<UserEntity> userEntity = userRepository.findByUsername(authenticationService.getPrincipal().getUsername());

        if(userEntity.isPresent()) {
            List<RecipeEntity> recipeEntities = recipeRepository.findAllFavoriteRecipes(userEntity.get().getId());
            List<RecipeResponseDto> recipeResponseDtos = new ArrayList<>();
            for(RecipeEntity recipeEntity:recipeEntities) {
                RecipeResponseDto recipeResponseDto = recipeMapper.ToRecipeResponseDto(recipeEntity);
                recipeResponseDto.setIngredients(ingredientMapper.TopIngredientDto(recipeEntity.getIngredients()));
                recipeResponseDto.setSteps(stepMapper.ToStepDto(recipeEntity.getSteps()));
                recipeResponseDtos.add(recipeResponseDto);
            }
            return recipeResponseDtos;
        }

        throw new NotFoundException("User not found");
    }

}
