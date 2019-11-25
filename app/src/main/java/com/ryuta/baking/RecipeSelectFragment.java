package com.ryuta.baking;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.Observer;
import androidx.recyclerview.widget.GridLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.activities.MainActivity;
import com.ryuta.baking.databinding.FragmentRecipeSelectBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.util.RecipeListAdapter;
import com.ryuta.baking.viewmodels.RecipeSelectViewModel;

import java.util.List;

public class RecipeSelectFragment extends Fragment implements RecipeListAdapter.OnRecipeClickListener {

    private static final String TAG = RecipeSelectFragment.class.getName();

    private RecipeListAdapter adapter;
    private FragmentRecipeSelectBinding binding;
    private OnRecipeSelectedListener onRecipeSelectedListener;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.fragment_recipe_select, container, false);
        binding.setViewModel(RecipeSelectViewModel.get(this));

        // init layout manager
        RecyclerView.LayoutManager manager = new GridLayoutManager(getContext(),  1);
        binding.rvRecipes.setLayoutManager(manager);
        binding.rvRecipes.setHasFixedSize(true);

        onRecipeSelectedListener = (MainActivity) getActivity();

        return binding.getRoot();
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

        binding.getViewModel().getRecipes().observe(this, new Observer<List<Recipe>>() {
            @Override
            public void onChanged(List<Recipe> recipes) {
                if (recipes == null) {
                    Log.e(TAG, "Recipe list is null");
                    return;
                }
                adapter = new RecipeListAdapter(recipes, RecipeSelectFragment.this);
                binding.rvRecipes.setAdapter(adapter);
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
