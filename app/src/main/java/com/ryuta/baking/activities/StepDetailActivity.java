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
import com.ryuta.baking.StepDetailFragment;
import com.ryuta.baking.databinding.ActivityStepDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class StepDetailActivity extends AppCompatActivity {

    private static final String KEY_RECIPE = "recipe";
    private static final String KEY_STEP = "step";

    private ActivityStepDetailBinding binding;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


        binding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);

        Recipe selectedRecipe = null;
        int currentStep = 0;
        Intent intent = getIntent();
        if (intent != null) {
            selectedRecipe = (Recipe) intent.getSerializableExtra(KEY_RECIPE);
            currentStep = intent.getIntExtra(KEY_STEP, 0);
        }

        binding.setViewModel(RecipeDetailViewModel.get(this, selectedRecipe));
        if (selectedRecipe != null) {
            binding.getViewModel().selectStep(currentStep);
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.step_detail_container, StepDetailFragment.newInstance())
                    .commit();

            binding.getViewModel().getCurrentStep().observe(this, new Observer<Step>() {
                @Override
                public void onChanged(Step step) {
                    binding.btnPrev.setVisibility(
                            binding.getViewModel().hasPreviousStep() ? View.VISIBLE : View.GONE);
                    binding.btnNext.setVisibility(
                            binding.getViewModel().hasNextStep() ? View.VISIBLE : View.GONE);
                }
            });
        }
    }

    public void onPreviousButtonClicked(View v) {
        binding.getViewModel().goToPreviousStep();
    }

    public void onNextButtonClicked(View v) {
        binding.getViewModel().goToNextStep();
    }

    public static void navigateTo(Context context, Recipe recipe, int currentStep) {
        Intent intent = new Intent(context, StepDetailActivity.class);
        intent.putExtra(KEY_RECIPE, recipe);
        intent.putExtra(KEY_STEP, currentStep);
        context.startActivity(intent);
    }
}
