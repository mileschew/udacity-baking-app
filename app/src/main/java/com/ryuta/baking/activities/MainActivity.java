package com.ryuta.baking.activities;

import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import android.os.Bundle;

import com.ryuta.baking.R;
import com.ryuta.baking.databinding.ActivityMainBinding;
import com.ryuta.baking.fragments.RecipeSelectFragment;
import com.ryuta.baking.models.Recipe;

public class MainActivity extends AppCompatActivity {

    private static final int NUMBER_COLUMNS_PHONE = 1;
    private static final int NUMBER_COLUMNS_TABLET = 3;

    private ActivityMainBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_main);

        int columnCount = NUMBER_COLUMNS_PHONE;
        if (getResources().getBoolean(R.bool.isTablet))     // add more columns if using a tablet
            columnCount = NUMBER_COLUMNS_TABLET;

        Fragment fragment = RecipeSelectFragment.newInstance(columnCount)
                .setOnRecipeSelectedListener(new RecipeSelectFragment.OnRecipeSelectedListener() {
                    @Override
                    public void onRecipeSelected(Recipe selected) {
                        RecipeDetailActivity.navigateTo(MainActivity.this, selected);
                    }
                });

        getSupportFragmentManager().beginTransaction()
                .replace(R.id.recipe_select_container, fragment)
                .commit();

        // Set up toolbar
        binding.actionbar.setTitle(getString(R.string.recipe_select_title));
        setSupportActionBar(binding.actionbar);
    }
}
