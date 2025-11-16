package com.development.cookbot.service.ai;

import com.development.cookbot.dto.ai.AiRecipeInputDto;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;
@Service
public class AiMessageService {
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


    /**
     * Exemples pour SYSTEM_PROMPT_RECIPE_FROM_TEXT
     */
    public List<Message> getFewShotExamples_RecipeFromText() {
        return List.of(
                new UserMessage("""
                        Crée une recette avec les ingrédients, quantités et unités suivantes :
                        - Tranches de pain : 2 tranches
                        - Oeufs : 2
                        - lait : 1 litre
                        - Sucre : 50 grammes
                        - Beurre : 100 grammes
                        """),
                new AssistantMessage("""
                        {
                          "name": "Pain perdu facile",
                          "durationMinutes": 15,
                          "ingredients": [
                            { "name": "Tranches de pain", "quantity": 2, "unit": "tranches" },
                            { "name": "Oeufs", "quantity": 2, "unit": "pièces" },
                            { "name": "lait", "quantity": 100, "unit": "ml" },
                            { "name": "Sucre", "quantity": 20, "unit": "g" },
                            { "name": "Beurre", "quantity": 15, "unit": "g" }
                          ],
                          "steps": [
                            { "stepNumber": 1, "description": "Dans une assiette creuse, battre les œufs avec le lait et le sucre." },
                            { "stepNumber": 2, "description": "Tremper rapidement les tranches de pain des deux côtés dans le mélange." },
                            { "stepNumber": 3, "description": "Faire fondre le beurre dans une poêle à feu moyen." },
                            { "stepNumber": 4, "description": "Faire dorer les tranches de pain 2-3 minutes de chaque côté jusqu'à ce qu'elles soient bien dorées." },
                            { "stepNumber": 5, "description": "Servir chaud, saupoudré d'un peu de sucre si désiré." }
                          ]
                        }
                        """)
        );
    }

    /**
     * Exemples pour SYSTEM_PROMPT_TITLE_FROM_TEXT
     */
    public List<Message> getFewShotExamples_TitleFromText() {
        return List.of(
                new UserMessage("""
                        Donne moi des noms de recettes avec leur temps de cuisson pour les ingrédients suivants :
                        - Tranches de pain : 2 tranches
                        - Oeufs : 2
                        - lait : 1 litre
                        - Sucre : 50 grammes
                        - Beurre : 100 grammes
                        """),
                new AssistantMessage("""
                        {
                          "recipeTitles": [
                            { "title": "Pain perdu", "durationMinutes": 15 },
                            { "title": "Omelette sucrée au pain", "durationMinutes": 10 }
                          ]
                        }
                        """)
        );
    }

    /**
     * Exemples pour SYSTEM_PROMPT_RECIPE_FROM_IMAGE
     */
    public List<Message> getFewShotExamples_RecipeFromImage() {
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
}