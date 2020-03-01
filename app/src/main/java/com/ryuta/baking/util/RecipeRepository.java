package com.ryuta.baking.util;

import android.content.Context;

import com.ryuta.baking.models.Recipe;

import java.util.ArrayList;

public class RecipeRepository {
    private RecipeRepository() { }

    static public ArrayList<Recipe> getRecipes(Context context) {
        return (ArrayList<Recipe>) StringUtil.getRecipesFromJson(context);
    }

    static public Recipe getRecipe(ArrayList<Recipe> recipes, String name) {
        for (Recipe recipe : recipes) {
            if (recipe.getName().equals(name))
                return recipe;
        }
        return null;
    }

    static public Recipe getRecipe(ArrayList<Recipe> recipes, int id) {
        for (Recipe recipe : recipes) {
            if (recipe.getId() == id)
                return recipe;
        }
        return null;
    }
}
