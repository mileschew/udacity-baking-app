package com.ryuta.baking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.ryuta.baking.databinding.FragmentStepDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.util.MediaUtil;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class StepDetailFragment extends Fragment {

    private static final String KEY_RECIPE = "recipe";

    private FragmentStepDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        binding.setViewModel(RecipeDetailViewModel.get(getActivity(), (Recipe) getArguments().getSerializable(KEY_RECIPE)));

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

        binding.getViewModel().getCurrentStep().observe(this, new Observer<Step>() {
            @Override
            public void onChanged(Step step) {
                binding.tvDescription.setText(step.getDescription());

                attemptLoadThumbnail(step.getThumbnailURL());
                attemptLoadVideo(step.getVideoURL());

                binding.btnPrev.setVisibility(
                        binding.getViewModel().hasPreviousStep() ? View.VISIBLE : View.GONE);
                binding.btnNext.setVisibility(
                        binding.getViewModel().hasNextStep() ? View.VISIBLE : View.GONE);
            }
        });
    }

    public static StepDetailFragment newInstance(Recipe recipe) {
        Bundle args = new Bundle();
        args.putSerializable(KEY_RECIPE, recipe);
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void attemptLoadThumbnail(String url) {
        if (url == null || url.isEmpty()) {
            binding.ivThumbnail.setVisibility(View.GONE);
            return;
        }
        MediaUtil.loadImage(binding.ivThumbnail, url);
        binding.ivThumbnail.setVisibility(View.VISIBLE);
    }

    private void attemptLoadVideo(String url) {
        if (url == null || url.isEmpty()) {
            binding.ivVideo.setVisibility(View.GONE);
            return;
        }
        MediaUtil.loadVideo(binding.ivVideo, url);
        binding.ivVideo.setVisibility(View.VISIBLE);
    }
}
