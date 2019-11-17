package com.ryuta.baking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.util.RecipeDetailViewModelProviderFactory;

public class RecipeDetailViewModel extends AndroidViewModel {
    private int recipeId;

    private MutableLiveData<Recipe> recipeLiveData = new MutableLiveData<>();

    public RecipeDetailViewModel(@NonNull Application application, int recipeId) {
        super(application);
        this.recipeId = recipeId;

        fetchData();
    }

    public LiveData<Recipe> getRecipeDetails() {
        return recipeLiveData;
    }

    private void fetchData() {
        //TODO temp data
        Recipe sample = new Recipe(recipeId, "Mac & Cheese", null, null, 4, "");
        recipeLiveData.postValue(sample);
    }

    public static RecipeDetailViewModel get(Fragment fragment, int recipeId) {
        RecipeDetailViewModelProviderFactory factory = new RecipeDetailViewModelProviderFactory(fragment.getActivity().getApplication(), recipeId);
        return ViewModelProviders.of(fragment, factory).get(RecipeDetailViewModel.class);
    }
}
