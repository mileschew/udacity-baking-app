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

import com.google.android.exoplayer2.ExoPlayer;
import com.ryuta.baking.databinding.FragmentStepDetailBinding;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.util.MediaUtil;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class StepDetailFragment extends Fragment {

    private FragmentStepDetailBinding binding;
    private ExoPlayer exoPlayer;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_step_detail, container, false);
        binding.setViewModel(RecipeDetailViewModel.get(getActivity()));

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getViewModel().getCurrentStep().observe(getViewLifecycleOwner(), new Observer<Step>() {
            @Override
            public void onChanged(Step step) {
                if (exoPlayer != null) {
                    exoPlayer.stop();
                }
                if (!getResources().getBoolean(R.bool.isLandscape)) {
                    binding.tvDescription.setText(step.getDescription());
                    attemptLoadThumbnail(step.getThumbnailURL());
                }
                attemptLoadVideo(step.getVideoURL());
            }
        });
    }

    @Override
    public void onPause() {
        super.onPause();
        if (exoPlayer != null)
            exoPlayer.stop(false);
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        if (exoPlayer != null)
            exoPlayer.release();
    }

    public static StepDetailFragment newInstance() {
        Bundle args = new Bundle();
        StepDetailFragment fragment = new StepDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    private void attemptLoadThumbnail(String url) {     // if there's a thumbnail provided, load it and show
        if (url == null || url.isEmpty()) {
            binding.ivThumbnail.setVisibility(View.GONE);
            return;
        }
        MediaUtil.loadImage(binding.ivThumbnail, url);
        binding.ivThumbnail.setVisibility(View.VISIBLE);
    }

    private void attemptLoadVideo(String url) {         // if there's a video, load it and show
        if (url == null || url.isEmpty()) {
            binding.viewVideoPlayer.setVisibility(View.GONE);
            return;
        }
        exoPlayer = MediaUtil.loadVideo(getContext(), binding.viewVideoPlayer, url, false);
        binding.viewVideoPlayer.setVisibility(View.VISIBLE);
    }
}
