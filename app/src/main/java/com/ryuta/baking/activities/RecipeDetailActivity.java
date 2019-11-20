package com.ryuta.baking.activities;

import android.content.Intent;
import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeDetailFragment;
import com.ryuta.baking.StepDetailFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    public static final String KEY_ID = "id";

    private int recipeId = -1;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        Intent intent = getIntent();
        if (intent != null)
            recipeId = intent.getIntExtra(KEY_ID, -1);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, StepDetailFragment.newInstance(recipeId))
                .commit();
    }
}
