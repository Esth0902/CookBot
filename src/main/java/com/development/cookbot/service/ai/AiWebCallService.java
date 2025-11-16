package com.development.cookbot.service.ai;

import com.development.cookbot.dto.ai.AiRecipeInputDto;
import com.development.cookbot.dto.ai.AiRecipeResponseDto;
import com.development.cookbot.dto.ai.AiRecipeTitleResponseDto;
import com.development.cookbot.dto.client.UserPrincipalDto;
import com.development.cookbot.exception.NotFoundException;
import com.development.cookbot.repository.user.UserRepository;
import com.development.cookbot.service.ai.constant.AiConstant;
import com.development.cookbot.service.client.AuthenticationService;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AiWebCallService {

    @Autowired
    private AiMessageService aiMessageService;

    @Autowired
    private AuthenticationService authenticationService;

    @Autowired
    private UserRepository userRepository;

    private final ChatClient chatClient;

    public AiWebCallService(ChatClient.Builder builder, ChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    public AiRecipeTitleResponseDto askRecipeTitle(AiRecipeInputDto aiRecipeInputDto) {

        String query = aiMessageService.formatTitleQuery(aiRecipeInputDto);
        List<Message> examples = aiMessageService.getFewShotExamples_TitleFromText();

        return chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_TITLE_FROM_TEXT)
                .messages(examples)
                .user(query)
                .call()
                .entity(AiRecipeTitleResponseDto.class);
    }

    public AiRecipeResponseDto askForRecipe(AiRecipeInputDto aiRecipeInputDto) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();

        if(userPrincipalDto == null) {
            throw new NotFoundException("User not found");
        }

        int nbPeople = userRepository.findById(userPrincipalDto.getId()).get().getSetting().getNbPeople();

        if(nbPeople < 1) {
            throw new RuntimeException("You must have at least 1 person to make a recipe");
        }

        String query = aiMessageService.formatQuery(aiRecipeInputDto);
        List<Message> examples = aiMessageService.getFewShotExamples_RecipeFromText();

        AiRecipeResponseDto aiRecipeResponseDto = chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_RECIPE_FROM_TEXT)
                .messages(examples)
                .user(query)
                .call()
                .entity(AiRecipeResponseDto.class);

        if(nbPeople > 1) {
            assert aiRecipeResponseDto != null;
            aiRecipeResponseDto.getIngredients().forEach(ingredient -> {
                double newQty = ingredient.getQuantity() * nbPeople;
                ingredient.setQuantity(newQty);
            });
        }

        return aiRecipeResponseDto;
    }

    public AiRecipeResponseDto askRecipeFromImage(byte[] bytes) {

        UserPrincipalDto userPrincipalDto = authenticationService.getPrincipal();

        if(userPrincipalDto == null) {
            throw new NotFoundException("User not found");
        }

        int nbPeople = userRepository.findById(userPrincipalDto.getId()).get().getSetting().getNbPeople();

        if(nbPeople < 1) {
            throw new RuntimeException("You must have at least 1 person to make a recipe");
        }

        List<Message> examples = aiMessageService.getFewShotExamples_RecipeFromImage();

        AiRecipeResponseDto aiRecipeResponseDto = chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_RECIPE_FROM_IMAGE)
                .messages(examples)
                .user(u -> u.text(AiConstant.SYSTEM_PROMPT_RECIPE_FROM_IMAGE_MEDIA_TEXT).
                        media(MediaType.IMAGE_PNG, new ByteArrayResource(bytes))
                ).call().entity(AiRecipeResponseDto.class);

        if(nbPeople > 1) {
            assert aiRecipeResponseDto != null;
            aiRecipeResponseDto.getIngredients().forEach(ingredient -> {
                double newQty = ingredient.getQuantity() * nbPeople;
                ingredient.setQuantity(newQty);
            });
        }

        return aiRecipeResponseDto;
    }

    public AiRecipeTitleResponseDto askRecipeTitleFromImage(byte[] bytes) {
        return chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_TITLE_FROM_IMAGE)
                .user(u -> u.
                        text(AiConstant.SYSTEM_PROMPT_TITLE_FROM_IMAGE_MEDIA_TEXT).
                        media(MediaType.IMAGE_PNG, new ByteArrayResource(bytes))
                ).call().entity(AiRecipeTitleResponseDto.class);
    }

}
