package com.ryuta.baking;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;

import com.ryuta.baking.viewmodels.RecipeSelectViewModel;

public class RecipeSelectFragment extends Fragment {

    private RecipeSelectViewModel viewModel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View v = inflater.inflate(R.layout.fragment_recipe_select, container, false);

        viewModel = RecipeSelectViewModel.get(this);

        return v;
    }

    @Override
    public void onViewCreated(@NonNull View view, @Nullable Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);
    }

    public static RecipeSelectFragment newInstance() {
        return new RecipeSelectFragment();
    }
}
