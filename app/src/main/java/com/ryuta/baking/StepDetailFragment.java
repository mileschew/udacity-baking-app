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
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class StepDetailFragment extends Fragment {

    public static final String KEY_ID = "id";

    private FragmentStepDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        binding.setViewModel(RecipeDetailViewModel.get(this, getArguments().getInt(KEY_ID, -1)));

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

    public static StepDetailFragment newInstance(int recipeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID, recipeId);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
