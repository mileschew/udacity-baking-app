package com.ryuta.baking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import java.util.ArrayList;
import java.util.List;

public class RecipeSelectViewModel extends AndroidViewModel {

    private List<String> recipes;

    private MutableLiveData<List<String>> recipesLiveData = new MutableLiveData<>();

    public RecipeSelectViewModel(@NonNull Application application) {
        super(application);

        //TODO test data
        recipes = new ArrayList<>();
        recipes.add("burger");
        recipes.add("pizza");
        recipes.add("cereal");

        recipesLiveData.setValue(recipes);
    }

    public LiveData<List<String>> getRecipes() {
        return recipesLiveData;
    }

    public static RecipeSelectViewModel get(Fragment fragment) {
        return ViewModelProviders.of(fragment).get(RecipeSelectViewModel.class);
    }
}
