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
