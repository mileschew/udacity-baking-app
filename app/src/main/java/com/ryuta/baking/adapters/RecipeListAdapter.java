package com.ryuta.baking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.R;
import com.ryuta.baking.databinding.RecyclerviewItemRecipeBinding;
import com.ryuta.baking.models.Recipe;
import com.ryuta.baking.util.MediaUtil;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<Recipe> recipes;
    private Context context;
    private OnRecipeClickListener onRecipeClickListener;

    public RecipeListAdapter(List<Recipe> recipes, Context context, OnRecipeClickListener onRecipeClickListener) {
        this.recipes = recipes;
        this.context = context;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerviewItemRecipeBinding binding = DataBindingUtil.inflate(inflater, R.layout.recyclerview_item_recipe, parent, false);
        return new RecipeViewHolder(binding, onRecipeClickListener);
    }

    @Override
    public void onBindViewHolder(@NonNull RecipeViewHolder holder, int position) {
        holder.bind(recipes.get(position));
    }

    @Override
    public int getItemCount() {
        return recipes.size();
    }

    public class RecipeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private RecyclerviewItemRecipeBinding binding;
        private OnRecipeClickListener clickListener;

        RecipeViewHolder(RecyclerviewItemRecipeBinding binding, OnRecipeClickListener clickListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        void bind(Recipe recipe) {
            binding.tvRecipeItemTitle.setText(recipe.getName());
            int stepCount = recipe.getSteps().size();
            binding.tvRecipeItemSteps.setText(context.getString(R.string.recipe_item_step_count_format, stepCount));
            MediaUtil.loadImage(binding.ivRecipeItemThumbnail, recipe.getImage(), R.drawable.pie);
        }

        @Override
        public void onClick(View v) {
            clickListener.onRecipeClicked(getAdapterPosition());
        }
    }

    public interface OnRecipeClickListener {
        void onRecipeClicked(int position);
    }
}
