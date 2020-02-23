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
import com.ryuta.baking.fragments.StepDetailFragment;
import com.ryuta.baking.databinding.ActivityStepDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class StepDetailActivity extends AppCompatActivity {

    private static final String KEY_RECIPE = "recipe";
    private static final String KEY_STEP = "step";

    private ActivityStepDetailBinding binding;
    private int currentStep;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_step_detail);

        // Receive recipe and step details, pass to ViewModel
        Intent intent = getIntent();
        if (intent == null) return;     // error
        Recipe selectedRecipe = (Recipe) intent.getSerializableExtra(KEY_RECIPE);
        currentStep = intent.getIntExtra(KEY_STEP, 0);
        if (selectedRecipe == null) return;     // error
        binding.setViewModel(RecipeDetailViewModel.get(this, selectedRecipe));

        binding.getViewModel().selectStep(currentStep);
        getSupportFragmentManager().beginTransaction()
                .replace(R.id.step_detail_container, StepDetailFragment.newInstance())
                .commit();

        if (getResources().getBoolean(R.bool.isLandscape)) {    // Landscape layout
            // make fragment immersive full screen
            binding.other.setVisibility(View.GONE);
            getWindow().getDecorView().setSystemUiVisibility(
                    View.SYSTEM_UI_FLAG_FULLSCREEN | View.SYSTEM_UI_FLAG_HIDE_NAVIGATION);
            getSupportActionBar().hide();
        } else {        // Portrait layout
            binding.getViewModel().getCurrentStep().observe(this, new Observer<Step>() {
                @Override
                public void onChanged(Step step) {
                    showOrHideNavButtons();
                }
            });
        }

        // Set up toolbar
        updateTitle();
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
        currentStep--;
        updateTitle();
    }

    public void onNextButtonClicked(View v) {
        binding.getViewModel().goToNextStep();
        currentStep++;
        updateTitle();
    }

    public void onButtonFinishClicked(View v) {
        finish();
    }

    private void updateTitle() {
        binding.actionbar.setTitle(
                currentStep == 0
                        ? getString(R.string.step_detail_title_intro)
                        : getString(R.string.step_detail_title_count_format, currentStep)
        );
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

    public static void navigateTo(Context context, Recipe recipe, int currentStep) {
        Intent intent = new Intent(context, StepDetailActivity.class);
        intent.putExtra(KEY_RECIPE, recipe);
        intent.putExtra(KEY_STEP, currentStep);
        context.startActivity(intent);
    }
}
