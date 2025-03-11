package com.example.demo;

import org.springframework.ai.chat.model.ChatModel;
import org.springframework.ai.chat.prompt.Prompt;
import org.springframework.ai.chat.prompt.PromptTemplate;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public class RecipeService {
    private final ChatModel chatModel;

    public RecipeService(ChatModel chatModel) {
        this.chatModel = chatModel;
    }

    public String createRecipe(String ingredients,
                               String cuisine,
                               String dietaryRestrictions) {
        var template = """
                Create a detailed recipe based on the following requirements:
                
                 - **Ingredients**: {ingredients}
                 - **Cuisine**: {cuisine}
                 - **Dietary Restrictions**: {dietaryRestrictions}
                               
                 The recipe should include:
                 1. A **title** that reflects the dish.
                 2. A complete **list of ingredients** with quantities.
                 3. Step-by-step **cooking instructions**.
                 4. (Optional) Additional tips for enhancing flavor or presentation.
                           
                Ensure the recipe is **well-structured and easy to follow**.
                """;
        PromptTemplate promptTemplate = new PromptTemplate(template);
        Map<String, Object> params = Map.of(
                "ingredients",ingredients,
                "cuisine", cuisine,
                "dietaryRestrictions", dietaryRestrictions
        );

        Prompt prompt = promptTemplate.create(params);
        return chatModel.call(prompt).getResult().getOutput().getText();
    }

}