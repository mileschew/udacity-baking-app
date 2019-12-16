package com.ryuta.baking.activities;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeSelectFragment;
import com.ryuta.baking.models.Recipe;

public class MainActivity extends AppCompatActivity implements RecipeSelectFragment.OnRecipeSelectedListener {

    private static final int NUMBER_COLUMNS_PHONE = 1;
    private static final int NUMBER_COLUMNS_TABLET = 3;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        int columnCount = NUMBER_COLUMNS_PHONE;
        if (getResources().getBoolean(R.bool.isTablet))     // add more columns if using a tablet
            columnCount = NUMBER_COLUMNS_TABLET;

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_select_container, RecipeSelectFragment.newInstance(columnCount))
                .commit();
    }

    @Override
    public void onRecipeSelected(Recipe selected) {
        RecipeDetailActivity.navigateTo(this, selected);
    }
}
