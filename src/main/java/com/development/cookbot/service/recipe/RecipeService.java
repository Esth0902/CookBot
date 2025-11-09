package com.development.cookbot.service.recipe;

import com.development.cookbot.dto.ingredient.IngredientDto;
import com.development.cookbot.dto.recipe.RecipeInputDto;
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

}
