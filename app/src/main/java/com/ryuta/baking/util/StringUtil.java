package com.ryuta.baking.util;

import android.content.Context;
import android.util.Log;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.reflect.TypeToken;
import com.ryuta.baking.models.Recipe;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.lang.reflect.Type;
import java.util.ArrayList;
import java.util.List;

public class StringUtil {

    private static final String TAG = StringUtil.class.getName();
    private static final String FILENAME_RECIPE_ASSETS = "recipes.json";

    public static List<Recipe> getRecipesFromJson(Context context) {
        Gson gson = new GsonBuilder()
                .registerTypeAdapterFactory(new LenientTypeAdapterFactory())
                .create();
        Type recipeListType = new TypeToken<ArrayList<Recipe>>(){}.getType();
        List<Recipe> result = null;
        try (BufferedReader reader = new BufferedReader(
                new InputStreamReader(context.getAssets().open(FILENAME_RECIPE_ASSETS)))) {
            result = gson.fromJson(reader, recipeListType);
        } catch (Exception ex) {
            Log.e(TAG, "error parsing asset file", ex);
        }

        return result;
    }
}
