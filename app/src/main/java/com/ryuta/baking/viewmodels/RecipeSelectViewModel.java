package com.ryuta.baking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.ViewModelProviders;

public class RecipeSelectViewModel extends AndroidViewModel {
    public RecipeSelectViewModel(@NonNull Application application) {
        super(application);
    }

    public static RecipeSelectViewModel get(Fragment fragment) {
        return ViewModelProviders.of(fragment).get(RecipeSelectViewModel.class);
    }
}
