package com.ryuta.baking.adapters;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.databinding.DataBindingUtil;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.R;
import com.ryuta.baking.databinding.ItemStepBinding;
import com.ryuta.baking.models.Step;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private List<Step> steps;
    private OnStepClickedListener onStepClickedListener;
    private int selectedIndex = -1;

    public StepListAdapter(List<Step> steps, OnStepClickedListener onStepClickedListener) {
        this.steps = steps;
        this.onStepClickedListener = onStepClickedListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        LayoutInflater inflater = LayoutInflater.from(parent.getContext());
        ItemStepBinding binding = DataBindingUtil.inflate(inflater, R.layout.item_step, parent, false);
        return new StepViewHolder(binding, onStepClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(steps.get(position).getShortDescription());
        holder.itemView.setSelected(position == selectedIndex);
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private ItemStepBinding binding;
        private OnStepClickedListener onStepClickedListener;

        StepViewHolder(ItemStepBinding binding, OnStepClickedListener onStepClickedListener) {
            super(binding.getRoot());
            this.binding = binding;
            this.onStepClickedListener = onStepClickedListener;
            itemView.setOnClickListener(this);
        }

        void bind(String title) {
            binding.tvStepItemTitle.setText(title);
        }

        @Override
        public void onClick(View v) {
            notifyItemChanged(selectedIndex);
            selectedIndex = getAdapterPosition();
            notifyItemChanged(selectedIndex);
            onStepClickedListener.onStepClicked(getAdapterPosition());
        }
    }

    public interface OnStepClickedListener {
        void onStepClicked(int position);
    }
}
