package com.development.cookbot.service.ai;

import com.development.cookbot.dto.ai.AiRecipeInputDto;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiService {
    public String formatQuery(AiRecipeInputDto aiRecipeInputDto) {
        String ingredientsList = aiRecipeInputDto.getIngredients().stream()
                .map(ingredient -> "- "
                        + ingredient.getName() + " : "
                        + ingredient.getQuantity() + " "
                        + ingredient.getUnit())
                .collect(Collectors.joining("\n"));


        return """
        Crée une recette avec les ingrédients suivants :
        %s
        """.formatted(ingredientsList);
    }

    public List<Message> fewShotMessages() {
        return List.of(
                new UserMessage("""
                        Crée une recette avec les ingrédients, quantités et unités suivantes :
                        - Tranches de pain : 2 tranches de pain
                        - Oeufs : 2
                        - lait : 1 litre
                        - Sucre : 50 grammes
                        - Beurre : 100 grammes
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
    }

    public String formatTitleQuery(AiRecipeInputDto aiRecipeInputDto) {
        String ingredientsList = aiRecipeInputDto.getIngredients().stream()
                .map(ingredient -> "- "
                        + ingredient.getName() + " : "
                        + ingredient.getQuantity() + " "
                        + ingredient.getUnit())
                .collect(Collectors.joining("\n"));

        return """
        Donne moi des noms de recettes avec leur temps de cuisson pour les ingrédients suivants :
        %s
        """.formatted(ingredientsList);
    }

    public List<Message> fewShotRecipeMessagesFromImages() {
        return List.of(
                new UserMessage("Image montrant des œufs, du pain et du lait."),
                new AssistantMessage("""
                {
                  "name": "Pain perdu",
                  "durationMinutes": 15,
                  "ingredients": [
                    { "name": "pain", "quantity": 4, "unit": "tranches" },
                    { "name": "œufs", "quantity": 2, "unit": "pièces" },
                    { "name": "lait", "quantity": 100, "unit": "ml" },
                    { "name": "sucre", "quantity": 10, "unit": "g" },
                    { "name": "beurre", "quantity": 10, "unit": "g" }
                  ],
                  "steps": [
                    { "stepNumber": 1, "description": "Battre les œufs avec le lait et le sucre." },
                    { "stepNumber": 2, "description": "Tremper les tranches de pain dans le mélange." },
                    { "stepNumber": 3, "description": "Faire dorer les tranches dans le beurre à la poêle." }
                  ]
                }
            """),
                new UserMessage("Image montrant des tomates, des pâtes et du fromage râpé."),
                new AssistantMessage("""
                {
                  "name": "Pâtes à la sauce tomate",
                  "durationMinutes": 20,
                  "ingredients": [
                    { "name": "pâtes", "quantity": 200, "unit": "g" },
                    { "name": "tomates", "quantity": 3, "unit": "pièces" },
                    { "name": "ail", "quantity": 1, "unit": "gousse" },
                    { "name": "huile d'olive", "quantity": 10, "unit": "ml" },
                    { "name": "fromage râpé", "quantity": 20, "unit": "g" }
                  ],
                  "steps": [
                    { "stepNumber": 1, "description": "Faire cuire les pâtes dans de l'eau bouillante salée." },
                    { "stepNumber": 2, "description": "Préparer une sauce avec les tomates, l’ail et l’huile d’olive." },
                    { "stepNumber": 3, "description": "Mélanger les pâtes et la sauce, puis parsemer de fromage." }
                  ]
                }
            """)
        );
    }

    public List<Message> fewShotRecipeTitleMessages() {
        return List.of(
                new UserMessage("""
                        Donne moi des noms de recettes avec leur temps de cuisson pour les ingrédients suivants :
                        - Tranches de pain : 2 tranches de pain
                        - Oeufs : 2
                        - lait : 1 litre
                        - Sucre : 50 grammes
                        - Beurre : 100 grammes
                        """),
                new AssistantMessage("""
                        title : pain perdu
                        Temps de cuisson : 15 mn
                        
                        title : omelette avec du pain
                        Temps de cuisson : 10 mn
                        """)
        );
    }
}
