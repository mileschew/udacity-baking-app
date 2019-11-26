package com.ryuta.baking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;

import com.ryuta.baking.databinding.FragmentStepDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class StepDetailFragment extends Fragment {

    private static final String KEY_RECIPE = "recipe";

    private FragmentStepDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        binding.setViewModel(RecipeDetailViewModel.get(this, (Recipe) getArguments().getSerializable(KEY_RECIPE)));

        binding.btnPrev.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getViewModel().goToPreviousStep();
            }
        });
        binding.btnNext.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                binding.getViewModel().goToNextStep();
            }
        });

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static StepDetailFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_RECIPE, recipe);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
