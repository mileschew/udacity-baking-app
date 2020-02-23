package com.ryuta.baking.adapters;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.R;
import com.ryuta.baking.databinding.ItemIngredientBinding;
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
        ItemIngredientBinding binding =
                DataBindingUtil.inflate(inflater, R.layout.item_ingredient, parent, false);
        return new IngredientViewHolder(binding);
    }

    @Override
    public void onBindViewHolder(@NonNull IngredientViewHolder holder, int position) {
        Ingredient ingredient = ingredients.get(position);
        holder.bind(position, ingredient.getIngredient(),
                ingredient.getQuantity(), ingredient.getMeasure());
    }

    @Override
    public int getItemCount() {
        return ingredients.size();
    }

    class IngredientViewHolder extends RecyclerView.ViewHolder {
        private ItemIngredientBinding binding;

        IngredientViewHolder(@NonNull ItemIngredientBinding binding) {
            super(binding.getRoot());
            this.binding = binding;
        }

        void bind(int position, String name, int quantity, String measurement) {
            if (position % 2 == 1)
                binding.getRoot().setBackgroundColor(context.getColor(R.color.lightGray));
            binding.rvIngredientItemName.setText(name);
            binding.rvIngredientItemQuantityMeasurement.setText(
                    context.getString(R.string.ingredient_quantity_measurement_format, quantity, measurement));
        }
    }
}
