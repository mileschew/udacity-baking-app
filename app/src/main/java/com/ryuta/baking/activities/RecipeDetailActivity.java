package com.ryuta.baking.activities;

import android.os.Bundle;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeDetailFragment;

public class RecipeDetailActivity extends AppCompatActivity {

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_recipe_detail);

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_detail_container, RecipeDetailFragment.newInstance())
                .commit();
    }
}
