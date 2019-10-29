package com.ryuta.baking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;

public class RecipeDetailViewModel extends AndroidViewModel {
    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public static RecipeDetailViewModel get(Fragment fragment) {
        return ViewModelProviders.of(fragment).get(RecipeDetailViewModel.class);
    }
}
