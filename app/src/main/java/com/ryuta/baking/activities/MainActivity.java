package com.ryuta.baking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeSelectFragment;
import com.ryuta.baking.models.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeSelectFragment.OnRecipeSelectedListener {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);
    }

    @Override
    public void onRecipeSelected(Recipe selected) {
        Intent intent = new Intent(this, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.KEY_RECIPE, selected);
        startActivity(intent);
    }
}
