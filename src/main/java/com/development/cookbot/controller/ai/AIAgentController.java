package com.development.cookbot.controller.ai;

import org.springframework.ai.chat.client.ChatClient;
import org.springframework.ai.chat.client.advisor.MessageChatMemoryAdvisor;
import org.springframework.ai.chat.client.advisor.SimpleLoggerAdvisor;
import org.springframework.ai.chat.memory.ChatMemory;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;

import java.util.List;

@RestController
@RequestMapping("/api/v1/ai")
public class AIAgentController {

    private final ChatClient chatClient;

    public AIAgentController(ChatClient.Builder builder, ChatMemory memory) {
        this.chatClient = builder
                .defaultAdvisors(MessageChatMemoryAdvisor.builder(memory).build())
                .defaultAdvisors(new SimpleLoggerAdvisor())
                .build();
    }

    @GetMapping("/chat")
    //@PreAuthorize("hasRole('ADMIN')")
    public Flux<String> askChat(String query) {
        return chatClient.prompt()
                .user(query)
                .stream().content();
    }

    @GetMapping("/recette")
    public String askLLM() {

        String query = """
               Crée une recette avec les ingrédients suivant :
                - 2 Tomates
                - 4 oeufs
                - 1 Oignon
                - Sel
                - Fine herbes
               """;

        List<Message> examples = List.of(
                new UserMessage("""
                        Crée une recette avec les ingrédients suivant :
                        - Tranches de pain
                        - 2 oeufs
                        - 1 litre de lait
                        - 50 grammes de sucre
                        - 100 grammes de beurre
                        """),
                new AssistantMessage("""
                        Plat : pain perdu
                        Temps de cuisson : 15 mn
                        Etape 1 : Battez grossièrement les deux oeufs avec le lait et le sucre dans une assiette creuse. Coupez les toasts en deux dans le sens de la diagonale.
                        Etape 2 : Trempez rapidement les demi-toasts dans le mélange ainsi obtenu (si vous les laissez trop longtemps, ils risquent de s'émietter).
                        Etape 3 : Saisissez-les sur votre plus grande poêle dans laquelle vous aurez placé une noix de beurre, 1 mn environ de chaque coté.
                        Etape 4 : Servez, la première fournée est prête, enchainez avec la suivante.
                        """)
        );

        return chatClient.prompt()
                .user(query)
                .messages(examples)
                .call().content();
    }
}
