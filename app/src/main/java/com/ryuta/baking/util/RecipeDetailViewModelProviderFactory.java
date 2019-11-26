package com.ryuta.baking.util;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.ViewModel;
import androidx.lifecycle.ViewModelProvider;

import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.viewmodels.RecipeDetailViewModel;

public class RecipeDetailViewModelProviderFactory implements ViewModelProvider.Factory {

    private Application application;
    private Recipe recipe;

    public RecipeDetailViewModelProviderFactory(Application application, Recipe recipe) {
        this.application = application;
        this.recipe = recipe;
    }

    @NonNull
    @Override
    public <T extends ViewModel> T create(@NonNull Class<T> modelClass) {
        return (T) new RecipeDetailViewModel(application, recipe);
    }
}
