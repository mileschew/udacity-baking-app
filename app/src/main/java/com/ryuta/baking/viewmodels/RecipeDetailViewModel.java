package com.ryuta.baking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.models.Step;
import com.ryuta.baking.util.RecipeDetailViewModelProviderFactory;

public class RecipeDetailViewModel extends AndroidViewModel {

    private MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();
    public LiveData<Recipe> getRecipeDetails() {
        return recipeLiveData;
    }

    private MutableLiveData<Step> currentStepLiveData = new MutableLiveData<>();
    public LiveData<Step> getCurrentStep() {
        return currentStepLiveData;
    }

    private Recipe recipe;
    private int currentStepNumber;

    public RecipeDetailViewModel(@NonNull Application application) {
        super(application);
    }

    public RecipeDetailViewModel(@NonNull Application application, Recipe recipe) {
        super(application);
        this.recipe = recipe;
        recipeLiveData.postValue(recipe);
    }

    public void loadFirstStep() {
        currentStepNumber = 0;
        refreshStep();
    }

    public void selectStep(int position) {
        currentStepNumber = position;
        refreshStep();
    }

    public void goToPreviousStep() {
        if (hasPreviousStep()) {
            currentStepNumber--;
            refreshStep();
        }
    }

    public void goToNextStep() {
        if (hasNextStep()) {
            currentStepNumber++;
            refreshStep();
        }
    }

    public boolean hasPreviousStep() {
        return currentStepNumber > 0;
    }

    public boolean hasNextStep() {
        return currentStepNumber < (recipe.getSteps().size() - 1);
    }

    private void refreshStep() {
        currentStepLiveData.postValue(recipe.getSteps().get(currentStepNumber));
    }

    public static RecipeDetailViewModel get(FragmentActivity fragmentActivity) {
        return ViewModelProviders.of(fragmentActivity).get(RecipeDetailViewModel.class);
    }

    public static RecipeDetailViewModel get(FragmentActivity fragmentActivity, Recipe recipe) {
        RecipeDetailViewModelProviderFactory factory = new RecipeDetailViewModelProviderFactory(fragmentActivity.getApplication(), recipe);
        return ViewModelProviders.of(fragmentActivity, factory).get(RecipeDetailViewModel.class);
    }
}
