package com.development.cookbot.controller.ai;

import com.development.cookbot.dto.ai.AiRecipeInputDto;
import com.development.cookbot.dto.ai.AiRecipeResponseDto;
import com.development.cookbot.dto.ai.AiRecipeTitleResponseDto;
import com.development.cookbot.service.ai.AiService;
import com.development.cookbot.service.ai.constant.AiConstant;
import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.Message;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import reactor.core.publisher.Flux;

import java.io.IOException;
import java.util.List;

@RestController
@RequestMapping("/api/auth")
public class AIAgentController {

    @Autowired
    private AiService aiService;

    private final ChatClient chatClient;

    public AIAgentController(ChatClient.Builder builder, ChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @PostMapping(value = "/recipeTitle/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AiRecipeTitleResponseDto askRecipeTitleFromImage(@RequestParam(name="file") MultipartFile file)
            throws IOException {

        byte[] bytes = file.getBytes();
        return chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_TITLE_FROM_IMAGE)
                .user(u -> u.
                        text(AiConstant.SYSTEM_PROMPT_TITLE_FROM_IMAGE_MEDIA_TEXT).
                        media(MediaType.IMAGE_PNG, new ByteArrayResource(bytes))
                ).call().entity(AiRecipeTitleResponseDto.class);
    }

    @PostMapping(value = "/recipe/image", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public AiRecipeResponseDto askRecipeFromImage(@RequestParam(name="file") MultipartFile file) throws IOException {

        byte[] bytes = file.getBytes();
        List<Message> examples = aiService.getFewShotExamples_RecipeFromImage();

        return chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_RECIPE_FROM_IMAGE)
                .messages(examples)
                .user(u -> u.text(AiConstant.SYSTEM_PROMPT_RECIPE_FROM_IMAGE_MEDIA_TEXT).
                        media(MediaType.IMAGE_PNG, new ByteArrayResource(bytes))
                ).call().entity(AiRecipeResponseDto.class);
    }

    @GetMapping("/chat")
    public Flux<String> askChat(String query) {
        return chatClient.prompt()
                .user(query)
                .stream().content();
    }

    @PostMapping("/recipeTitle")
    public AiRecipeTitleResponseDto askRecipeTitle(@RequestBody AiRecipeInputDto aiRecipeInputDto) {

        String query = aiService.formatTitleQuery(aiRecipeInputDto);
        List<Message> examples = aiService.getFewShotExamples_TitleFromText();

        return chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_TITLE_FROM_TEXT)
                .messages(examples)
                .user(query)
                .call()
                .entity(AiRecipeTitleResponseDto.class);
    }

    @PostMapping("/recipe")
    public AiRecipeResponseDto askForRecipe(@RequestBody AiRecipeInputDto aiRecipeInputDto) {

        String query = aiService.formatQuery(aiRecipeInputDto);
        List<Message> examples = aiService.getFewShotExamples_RecipeFromText();

        return chatClient.prompt()
                .system(AiConstant.SYSTEM_PROMPT_RECIPE_FROM_TEXT)
                .messages(examples)
                .user(query)
                .call()
                .entity(AiRecipeResponseDto.class);
    }
}