package com.ryuta.baking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeSelectFragment;

public class MainActivity extends AppCompatActivity implements RecipeSelectFragment.OnRecipeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeSelected(int recipeId) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.KEY_ID, recipeId);
        startActivity(intent);
    }
}
