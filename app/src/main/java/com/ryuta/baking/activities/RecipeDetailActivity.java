package com.ryuta.baking.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeDetailFragment;
import com.ryuta.baking.StepDetailFragment;
import com.ryuta.baking.databinding.ActivityRecipeDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.util.StepListAdapter;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class RecipeDetailActivity extends AppCompatActivity {

    private static final String KEY_RECIPE = "recipe";

    private ActivityRecipeDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        // Receive recipe details and pass to ViewModel
        Intent intent = getIntent();
        if (intent == null) return;     // error
        Recipe selectedRecipe = (Recipe) intent.getSerializableExtra(KEY_RECIPE);
        if (selectedRecipe == null) return;     // error
        binding.setViewModel(RecipeDetailViewModel.get(this, selectedRecipe));

        if (getResources().getBoolean(R.bool.isTablet)) {   // Tablet layout
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, RecipeDetailFragment.newInstance())
                    .replace(R.id.step_detail_container, StepDetailFragment.newInstance())
                    .commit();
            binding.getViewModel().getCurrentStep().observe(this, new Observer<Step>() {
                @Override
                public void onChanged(Step step) {
                    showOrHideNavButtons();
                }
            });
        } else {        // Phone layout
            Fragment fragment = RecipeDetailFragment.newInstance()
                    .setOnStepClickListener(new StepListAdapter.OnStepClickedListener() {
                        @Override
                        public void onStepClicked(int position) {
                            StepDetailActivity.navigateTo(RecipeDetailActivity.this,
                                    binding.getViewModel().getRecipeDetails().getValue(),
                                    position);
                        }
                    });
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.recipe_detail_container, fragment)
                    .commit();
        }

        // Set up toolbar
        binding.actionbar.setTitle(selectedRecipe.getName());
        setSupportActionBar(binding.actionbar);
        getSupportActionBar().setDisplayHomeAsUpEnabled(true);
        getSupportActionBar().setDisplayShowHomeEnabled(true);
        binding.actionbar.setNavigationOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onBackPressed();
            }
        });
    }

    public void onPreviousButtonClicked(View v) {
        binding.getViewModel().goToPreviousStep();
    }

    public void onNextButtonClicked(View v) {
        binding.getViewModel().goToNextStep();
    }

    public void onButtonFinishClicked(View v) {
        finish();
    }

    private void showOrHideNavButtons() {
        if (binding.getViewModel().hasPreviousStep()) {
            binding.btnPrev.setVisibility(View.VISIBLE);
            binding.viewNavSpace.setVisibility(View.VISIBLE);
        } else {
            binding.viewNavSpace.setVisibility(View.GONE);
            binding.btnPrev.setVisibility(View.GONE);
        }
        if (binding.getViewModel().hasNextStep()) {
            binding.btnNext.setVisibility(View.VISIBLE);
            binding.btnFin.setVisibility(View.GONE);
        } else {
            binding.btnNext.setVisibility(View.GONE);
            binding.btnFin.setVisibility(View.VISIBLE);
        }
    }

    public static void navigateTo(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.KEY_RECIPE, recipe);
        context.startActivity(intent);
    }
}
