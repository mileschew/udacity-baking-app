package com.ryuta.baking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.R;
import com.ryuta.baking.databinding.RecyclerviewItemIngredientBinding;
import com.ryuta.baking.models.Ingredient;

import java.util.List;

public class IngredientAdapter extends RecyclerView.Adapter<IngredientAdapter.IngredientViewHolder> {

    private List<Ingredient> ingredients;
    private Context context;

    public IngredientAdapter(List<Ingredient> ingredients, Context context) {
        this.ingredients = ingredients;
        this.context = context;
    }

    @NonNull
    @Override
    public IngredientViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        RecyclerviewItemIngredientBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.recyclerview_item_ingredient, parent, false);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(ingredient.getIngredient(), String.valueOf(ingredient.getQuantity()), ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private RecyclerviewItemIngredientBinding binding;

        IngredientViewHolder(@NonNull RecyclerviewItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(String name, String quantity, String measurement) {
            binding.rvIngredientItemName.setText(name);
            binding.rvIngredientItemQuantityMeasurement.setText(
                    context.getString(R.string.ingredient_quantity_measurement_format, quantity, measurement));
        }
    }
}
