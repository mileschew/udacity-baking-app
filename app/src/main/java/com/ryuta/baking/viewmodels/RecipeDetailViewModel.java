package com.ryuta.baking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.util.RecipeDetailViewModelProviderFactory;

import java.util.ArrayList;
import java.util.List;

public class RecipeDetailViewModel extends AndroidViewModel {
    private Recipe recipe;

    private MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();
    private MutableLiveData<Step> currentStepLiveData = new MutableLiveData<>();

    public RecipeDetailViewModel(@NonNull Application application, Recipe recipe) {
        super(application);
        this.recipe = recipe;
        recipeLiveData.postValue(recipe);
    }

    public LiveData<Recipe> getRecipeDetails() {
        return recipeLiveData;
    }

    public LiveData<Step> getCurrentStep() {
        return currentStepLiveData;
    }

    public void goToPreviousStep() {
    }

    public void goToNextStep() {
    }

    public boolean hasPreviousStep() {
        return true;
    }

    public boolean hasNextStep() {
        return true;
    }

    public static RecipeDetailViewModel get(Fragment fragment, Recipe recipe) {
        RecipeDetailViewModelProviderFactory factory = new RecipeDetailViewModelProviderFactory(fragment.getActivity().getApplication(), recipe);
        return ViewModelProviders.of(fragment, factory).get(RecipeDetailViewModel.class);
    }
}
