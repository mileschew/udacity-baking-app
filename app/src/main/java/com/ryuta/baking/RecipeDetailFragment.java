package com.ryuta.baking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;

import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class RecipeDetailFragment extends Fragment {

    public static final String KEY_ID = "id";

    private TextView titleView;

    private RecipeDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        viewModel = RecipeDetailViewModel.get(this, 3);

        titleView = v.findViewById(R.id.tv_recipe_detail_title);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getRecipeDetails().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                titleView.setText(recipe.getName());
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
}
