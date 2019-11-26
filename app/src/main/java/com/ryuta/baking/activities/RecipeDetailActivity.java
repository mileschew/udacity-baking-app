package com.ryuta.baking.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeDetailFragment;
import com.ryuta.baking.StepDetailFragment;
import com.ryuta.baking.models.Recipe;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String KEY_RECIPE = "recipe";

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Recipe selectedRecipe = null;
        Intent intent = getIntent();
        if (intent != null)
            selectedRecipe = (Recipe) intent.getSerializableExtra(KEY_RECIPE);

        boolean isTwoPaneLayoout = findViewById(R.id.step_detail_container) != null;
        if (isTwoPaneLayoout) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, RecipeDetailFragment.newInstance(selectedRecipe))
                    .replace(R.id.step_detail_container, StepDetailFragment.newInstance(selectedRecipe))
                    .commit();
        } else {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, RecipeDetailFragment.newInstance(selectedRecipe))
                    .commit();
        }
    }
}
