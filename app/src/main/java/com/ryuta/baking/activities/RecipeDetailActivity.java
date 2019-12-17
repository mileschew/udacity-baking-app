package com.ryuta.baking.activities;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.view.View;

import androidx.annotation.Nullable;
import androidx.appcompat.app.AppCompatActivity;
import androidx.databinding.DataBindingUtil;
import androidx.lifecycle.Observer;

import com.ryuta.baking.R;
import com.ryuta.baking.RecipeDetailFragment;
import com.ryuta.baking.StepDetailFragment;
import com.ryuta.baking.databinding.ActivityRecipeDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.util.StepListAdapter;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class RecipeDetailActivity extends AppCompatActivity implements StepListAdapter.OnStepClickedListener {

    private static final String KEY_RECIPE = "recipe";

    private ActivityRecipeDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_recipe_detail);

        Recipe selectedRecipe = null;
        Intent intent = getIntent();
        if (intent != null)
            selectedRecipe = (Recipe) intent.getSerializableExtra(KEY_RECIPE);

        binding.setViewModel(RecipeDetailViewModel.get(this, selectedRecipe));
        if (selectedRecipe != null) {
            boolean isTablet = getResources().getBoolean(R.bool.isTablet);
            if (isTablet) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, RecipeDetailFragment.newInstance())
                        .replace(R.id.step_detail_container, StepDetailFragment.newInstance())
                        .commit();
                binding.getViewModel().getCurrentStep().observe(this, new Observer<Step>() {
                    @Override
                    public void onChanged(Step step) {
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
                });
            } else {
                RecipeDetailFragment fragment = RecipeDetailFragment.newInstance();
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.recipe_detail_container, fragment)
                        .commit();
                fragment.setOnStepClickListener(this);
            }
        }
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

    public static void navigateTo(Context context, Recipe recipe) {
        Intent intent = new Intent(context, RecipeDetailActivity.class);
        intent.putExtra(RecipeDetailActivity.KEY_RECIPE, recipe);
        context.startActivity(intent);
    }

    @Override
    public void onStepClicked(int position) {
        StepDetailActivity.navigateTo(this,
                binding.getViewModel().getRecipeDetails().getValue(),
                position);
    }
}
