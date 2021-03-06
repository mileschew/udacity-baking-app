package com.ryuta.baking.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.DividerItemDecoration;

import com.ryuta.baking.R;
import com.ryuta.baking.databinding.FragmentRecipeDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.adapters.IngredientAdapter;
import com.ryuta.baking.adapters.StepListAdapter;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class RecipeDetailFragment extends Fragment implements StepListAdapter.OnStepClickedListener {

    private FragmentRecipeDetailBinding binding;
    private StepListAdapter.OnStepClickedListener onStepClickedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        binding.setViewModel(RecipeDetailViewModel.get(getActivity()));

        // init layout manager
        binding.rvIngredients.setHasFixedSize(true);
        binding.rvIngredients.setNestedScrollingEnabled(false);
        binding.rvSteps.setHasFixedSize(true);
        DividerItemDecoration decoration = new DividerItemDecoration(getContext(), DividerItemDecoration.VERTICAL);
        binding.rvSteps.addItemDecoration(decoration);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getViewModel().getRecipeDetails().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                binding.rvIngredients.setAdapter(new IngredientAdapter(recipe.getIngredients(), getContext()));
                binding.rvSteps.setAdapter(
                        new StepListAdapter(recipe.getSteps(), RecipeDetailFragment.this));

                binding.getViewModel().loadFirstStep();
            }
        });
    }

    @Override
    public void onStepClicked(int position) {
        binding.getViewModel().selectStep(position);
        if (onStepClickedListener != null)
            onStepClickedListener.onStepClicked(position);
    }

    public RecipeDetailFragment setOnStepClickListener(StepListAdapter.OnStepClickedListener onStepClickedListener) {
        this.onStepClickedListener = onStepClickedListener;
        return this;
    }

    public static RecipeDetailFragment newInstance() {
        Bundle args = new Bundle();
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }
}
