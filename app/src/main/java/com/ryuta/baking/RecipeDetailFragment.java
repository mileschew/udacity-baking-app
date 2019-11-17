package com.ryuta.baking;

import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.util.StepListAdapter;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class RecipeDetailFragment extends Fragment implements StepListAdapter.OnStepClickedListener {

    public static final String KEY_ID = "id";

    private TextView titleView;
    private RecyclerView recyclerView;
    private StepListAdapter adapter;

    private RecipeDetailViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_detail, container, false);

        viewModel = RecipeDetailViewModel.get(this, 3);

        titleView = v.findViewById(R.id.tv_recipe_detail_title);
        recyclerView = v.findViewById(R.id.rv_steps);

        // init layout manager
        RecyclerView.LayoutManager manager = new LinearLayoutManager(getContext());
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getRecipeDetails().observe(this, new Observer<Recipe>() {
            @Override
            public void onChanged(Recipe recipe) {
                titleView.setText(recipe.getName());

                adapter = new StepListAdapter(recipe.getSteps(), RecipeDetailFragment.this);
                recyclerView.setAdapter(adapter);
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
