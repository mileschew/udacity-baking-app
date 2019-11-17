package com.ryuta.baking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.R;

import java.util.List;

public class RecipeListAdapter extends RecyclerView.Adapter<RecipeListAdapter.RecipeViewHolder> {

    private List<String> recipes;
    private OnRecipeClickListener onRecipeClickListener;

    public RecipeListAdapter(List<String> recipes, OnRecipeClickListener onRecipeClickListener) {
        this.recipes = recipes;
        this.onRecipeClickListener = onRecipeClickListener;
    }

    @NonNull
    @Override
    public RecipeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_recipe, parent, false);
        return new RecipeViewHolder(view, onRecipeClickListener);
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
        private TextView titleView;
        private OnRecipeClickListener clickListener;

        public RecipeViewHolder(@NonNull View itemView, OnRecipeClickListener clickListener) {
            super(itemView);
            titleView = itemView.findViewById(R.id.tv_recipe_item_title);
            this.clickListener = clickListener;
            itemView.setOnClickListener(this);
        }

        public void bind(String title) {
            titleView.setText(title);
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
