package com.ryuta.baking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Toast;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.activities.MainActivity;
import com.ryuta.baking.util.RecipeListAdapter;
import com.ryuta.baking.viewmodels.RecipeSelectViewModel;

import java.util.List;

public class RecipeSelectFragment extends Fragment implements RecipeListAdapter.OnRecipeClickListener {

    private RecipeSelectViewModel viewModel;
    private RecipeListAdapter adapter;
    private RecyclerView recyclerView;
    private OnRecipeSelectedListener onRecipeSelectedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_select, container, false);

        recyclerView = v.findViewById(R.id.rv_recipes);

        viewModel = RecipeSelectViewModel.get(this);

        // init layout manager
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),  1);
        recyclerView.setLayoutManager(manager);
        recyclerView.setHasFixedSize(true);

        onRecipeSelectedListener = (MainActivity) getActivity();

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        viewModel.getRecipes().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(List<String> recipes) {
                adapter = new RecipeListAdapter(recipes, RecipeSelectFragment.this);
                recyclerView.setAdapter(adapter);
            }
        });
    }

    public static RecipeSelectFragment newInstance() {
        return new RecipeSelectFragment();
    }

    @Override
    public void onRecipeClicked(int position) {
        onRecipeSelectedListener.onRecipeSelected(position);
    }

    public interface OnRecipeSelectedListener {
        void onRecipeSelected(int recipeId);
    }
}
