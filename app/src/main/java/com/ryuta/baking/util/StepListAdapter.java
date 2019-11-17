package com.ryuta.baking.util;

import android.content.Context;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.ryuta.baking.R;
import com.ryuta.baking.models.Step;

import java.util.List;

public class StepListAdapter extends RecyclerView.Adapter<StepListAdapter.StepViewHolder> {

    private List<Step> steps;
    private OnStepClickedListener onStepClickedListener;

    public StepListAdapter(List<Step> steps, OnStepClickedListener onStepClickedListener) {
        this.steps = steps;
        this.onStepClickedListener = onStepClickedListener;
    }

    @NonNull
    @Override
    public StepViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        Context context = parent.getContext();

        View view = LayoutInflater.from(context).inflate(R.layout.recyclerview_item_step, parent, false);
        return new StepViewHolder(view, onStepClickedListener);
    }

    @Override
    public void onBindViewHolder(@NonNull StepViewHolder holder, int position) {
        holder.bind(steps.get(position).getShortDescription());
    }

    @Override
    public int getItemCount() {
        return steps.size();
    }

    public class StepViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
        private TextView stepTitleView;
        private OnStepClickedListener onStepClickedListener;

        public StepViewHolder(@NonNull View itemView, OnStepClickedListener onStepClickedListener) {
            super(itemView);
            stepTitleView = itemView.findViewById(R.id.tv_step_item_title);
            this.onStepClickedListener = onStepClickedListener;
            itemView.setOnClickListener(this);
        }

        @Override
        public void onClick(View v) {
            onStepClickedListener.onStepClicked(getAdapterPosition());
        }

        public void bind(String title) {
            stepTitleView.setText(title);
        }
    }

    public interface OnStepClickedListener {
        void onStepClicked(int position);
    }
}
