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
                //TODO re-test if anything broke in tablet layout
                binding.getViewModel().getCurrentStep().observe(this, new Observer<Step>() {
                    @Override
                    public void onChanged(Step step) {
                        binding.btnPrev.setVisibility(
                                binding.getViewModel().hasPreviousStep() ? View.VISIBLE : View.GONE);
                        binding.btnNext.setVisibility(
                                binding.getViewModel().hasNextStep() ? View.VISIBLE : View.GONE);
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
