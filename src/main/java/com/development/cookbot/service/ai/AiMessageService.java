package com.development.cookbot.service.ai;

import com.development.cookbot.dto.ai.AiDishInputDto;
import com.development.cookbot.dto.ai.AiRecipeInputDto;
import com.development.cookbot.dto.preference.PreferenceDto;
import com.development.cookbot.service.preference.PreferenceService;
import org.springframework.ai.chat.messages.AssistantMessage;
import org.springframework.ai.chat.messages.Message;
import org.springframework.ai.chat.messages.UserMessage;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;

@Service
public class AiMessageService {

    @Autowired
    private PreferenceService preferenceService;

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

    public String formatQueryForSeasonRecipe() {

        String alergeneList =  preferenceService.getPreferenceByUserId()
                .stream()
                .map(PreferenceDto::getAllergen)
                .collect(Collectors.joining(","));

        return """
            Crée-moi une recette utilisant uniquement des produits de saison.
            Donne-moi également jusqu’à 8 conseils variés concernant la préparation, la cuisson ou l’assaisonnement du plat.
            Mais n'utilise pas d'ingrédients contenant les alergènes de cette liste ni des ingrédients dérivés : %s.
            """.formatted(alergeneList);
    }


    public String formatQueryDish(AiDishInputDto aiDishInputDto) {

        String alergeneList =  preferenceService.getPreferenceByUserId()
                .stream()
                .map(PreferenceDto::getAllergen)
                .collect(Collectors.joining(","));

        return """
        Donne moi la recette avec les ingrédients et les étapes pour le plat suivants : %s.
        Mais n'utilise pas d'ingrédients contenant les alergènes de cette liste ni des ingrédients dérivés : %s.
        """.formatted(aiDishInputDto.getDishName(),alergeneList);
    }

    public List<Message> getFewShotExamples_RecipeFromDish() {
        return List.of(
                new UserMessage("""
                    Donne moi une recette pour moi préparer le plat suivant : omelette au fromage.
                    """),
                new AssistantMessage("""
                    {
                      "name": "Omelette au fromage",
                      "durationMinutes": 10,
                      "ingredients": [
                        { "name": "Oeufs", "quantity": 3, "unit": "pièces" },
                        { "name": "Fromage râpé", "quantity": 50, "unit": "g" },
                        { "name": "Beurre", "quantity": 10, "unit": "g" },
                        { "name": "Sel", "quantity": 1, "unit": "pincée" },
                        { "name": "Poivre", "quantity": 1, "unit": "pincée" }
                      ],
                      "steps": [
                        { "stepNumber": 1, "description": "Casser les œufs dans un bol et les battre avec le sel et le poivre." },
                        { "stepNumber": 2, "description": "Faire fondre le beurre dans une poêle à feu moyen." },
                        { "stepNumber": 3, "description": "Verser les œufs battus dans la poêle et laisser cuire quelques secondes." },
                        { "stepNumber": 4, "description": "Ajouter le fromage râpé sur la surface encore légèrement liquide." },
                        { "stepNumber": 5, "description": "Replier l'omelette en deux et laisser cuire encore 1 à 2 minutes jusqu'à ce que le fromage soit fondu." },
                        { "stepNumber": 6, "description": "Servir chaud immédiatement." }
                      ]
                    }
                    """)
        );
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

    public List<Message> getFewShotExamples_SeasonRecipe() {
        return List.of(
        new UserMessage("Produits de saison visibles : poireaux, pommes de terre et carottes."),
        new AssistantMessage("""
        {
          "name": "Potée de légumes d'hiver",
          "durationMinutes": 40,
          "ingredients": [
            { "name": "poireaux", "quantity": 2, "unit": "pièces" },
            { "name": "pommes de terre", "quantity": 4, "unit": "pièces" },
            { "name": "carottes", "quantity": 3, "unit": "pièces" },
            { "name": "bouillon de légumes", "quantity": 750, "unit": "ml" },
            { "name": "huile d'olive", "quantity": 15, "unit": "ml" }
          ],
          "steps": [
            { "stepNumber": 1, "description": "Couper tous les légumes en morceaux." },
            { "stepNumber": 2, "description": "Faire revenir les poireaux dans l'huile d'olive." },
            { "stepNumber": 3, "description": "Ajouter les carottes, les pommes de terre et le bouillon." },
            { "stepNumber": 4, "description": "Laisser mijoter 30 minutes à feu doux." }
          ],
          "tips": [
            { "tips": ["Utiliser un bouillon maison pour plus de saveur.", "Ajouter une pointe de moutarde en fin de cuisson."] },
            { "tips": ["Servir avec du pain grillé pour plus de texture."] }
          ]
        }
        """),
        new UserMessage("Produits de saison visibles : épinards, champignons et oignons."),
        new AssistantMessage("""
        {
          "name": "Poêlée d'épinards et champignons",
          "durationMinutes": 15,
          "ingredients": [
            { "name": "épinards frais", "quantity": 200, "unit": "g" },
            { "name": "champignons de Paris", "quantity": 150, "unit": "g" },
            { "name": "oignon", "quantity": 1, "unit": "pièce" },
            { "name": "huile d'olive", "quantity": 10, "unit": "ml" },
            { "name": "sel", "quantity": 2, "unit": "g" }
          ],
          "steps": [
            { "stepNumber": 1, "description": "Émincer l’oignon et les champignons." },
            { "stepNumber": 2, "description": "Faire revenir l’oignon dans l’huile d’olive." },
            { "stepNumber": 3, "description": "Ajouter les champignons et laisser dorer." },
            { "stepNumber": 4, "description": "Ajouter les épinards et les faire tomber 2 minutes." }
          ],
          "tips": [
            { "tips": ["Ajouter un filet de crème pour une version plus onctueuse."] },
            { "tips": ["Assaisonner avec un peu de noix de muscade."] }
          ]
        }
        """)
        );
    }

}