package com.ryuta.baking.viewmodels;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.fragment.app.FragmentActivity;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModelProviders;

import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.util.RecipeRepository;

import java.util.List;

public class RecipeSelectViewModel extends AndroidViewModel {

    private MutableLiveData<List<Recipe>> recipesLiveData = new MutableLiveData<>();
    public LiveData<List<Recipe>> getRecipes() {
        return recipesLiveData;
    }

    private List<Recipe> recipes;

    public RecipeSelectViewModel(@NonNull Application application) {
        super(application);
        recipes = RecipeRepository.getRecipes(application.getBaseContext());
        recipesLiveData.setValue(recipes);
    }

    public Recipe getRecipeAt(int position) {
        return recipes.get(position);
    }

    public static RecipeSelectViewModel get(FragmentActivity fragmentActivity) {
        return ViewModelProviders.of(fragmentActivity).get(RecipeSelectViewModel.class);
    }
}
