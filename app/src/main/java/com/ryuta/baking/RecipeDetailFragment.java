package com.ryuta.baking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.databinding.FragmentRecipeDetailBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.util.StepListAdapter;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class RecipeDetailFragment extends Fragment implements StepListAdapter.OnStepClickedListener {

    public static final String KEY_ID = "id";

    private StepListAdapter adapter;
    private FragmentRecipeDetailBinding binding;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_detail, container, false);
        binding.setViewModel(RecipeDetailViewModel.get(this, 3));

        // init layout manager
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        binding.rvSteps.setLayoutManager(manager);
        binding.rvSteps.setHasFixedSize(true);

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getViewModel().getRecipeDetails().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                binding.tvRecipeDetailTitle.setText(recipe.getName());
                adapter = new StepListAdapter(recipe.getSteps(), RecipeDetailFragment.this);
                binding.rvSteps.setAdapter(adapter);
            }
        });
    }

    public static RecipeDetailFragment newInstance(int recipeId) {
        Bundle args = new Bundle();
        args.putInt(KEY_ID, recipeId);
        RecipeDetailFragment fragment = new RecipeDetailFragment();
        fragment.setArguments(args);
        return fragment;
    }

    @Override
    public void onStepClicked(int position) {
        Toast.makeText(getContext(), "Step clicked: " + position, Toast.LENGTH_SHORT).show();
    }
}
