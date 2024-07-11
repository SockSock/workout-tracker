package com.example.workouttracker;

import android.app.AlertDialog;
import android.content.Context;
import android.graphics.Color;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.RecyclerView;

import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ViewHolder> {

    private ArrayList<Exercise> itemList;
    private ArrayList<Exercise> selectedItems = new ArrayList<>();
    private Context context;

    public ExerciseAdapter(Context context, ArrayList<Exercise> itemList) {
        this.context = context;
        this.itemList = itemList;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Exercise item = itemList.get(position);
        holder.textViewName.setText(item.getName());

        if (selectedItems.contains(item)) {
            holder.itemView.setBackgroundColor(ContextCompat.getColor(context, R.color.selectedItemColor));
        } else {
            holder.itemView.setBackgroundColor(Color.TRANSPARENT); // Reset background color
        }

        holder.itemView.setOnClickListener(v -> {
            if (selectedItems.contains(item)) {
                selectedItems.remove(item);
            } else {
                selectedItems.add(item);
                showAttributesDialog(item);
            }
            notifyDataSetChanged();
        });
    }

    private void showAttributesDialog(Exercise item) {
        AlertDialog.Builder builder = new AlertDialog.Builder(context);
        builder.setTitle(item.getName());

        View dialogView = LayoutInflater.from(context).inflate(R.layout.dialog_set_attributes, null);
        builder.setView(dialogView);

        EditText editTextSets = dialogView.findViewById(R.id.editTextSets);
        EditText editTextReps = dialogView.findViewById(R.id.editTextReps);
        EditText editTextWeight = dialogView.findViewById(R.id.editTextWeight);
        EditText editTextIncrement = dialogView.findViewById(R.id.editTextIncrement);

        editTextSets.setText(String.valueOf(item.getSets()));
        editTextReps.setText(String.valueOf(item.getReps()));
        editTextWeight.setText(String.valueOf(item.getWeight()));
        editTextIncrement.setText(String.valueOf(item.getIncrement()));

        builder.setPositiveButton("OK", (dialog, which) -> {
            int sets = parseOrDefault(editTextSets.getText().toString(), 0);
            int reps = parseOrDefault(editTextReps.getText().toString(), 0);
            double weight = parseOrDefaultDouble(editTextWeight.getText().toString(), 0.0);
            double increment = parseOrDefaultDouble(editTextIncrement.getText().toString(), 0.0);

            item.setSets(sets);
            item.setReps(reps);
            item.setWeight(weight);
            item.setIncrement(increment);
        });
        builder.setNegativeButton("Cancel", (dialog, which) -> {
            selectedItems.remove(item);
            notifyDataSetChanged();
        });
        builder.create().show();
    }

    private int parseOrDefault(String value, int defaultValue) {
        if (value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Integer.parseInt(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    private double parseOrDefaultDouble(String value, double defaultValue) {
        if (value.trim().isEmpty()) {
            return defaultValue;
        }
        try {
            return Double.parseDouble(value);
        } catch (NumberFormatException e) {
            return defaultValue;
        }
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public ArrayList<Exercise> getSelectedItems() {
        return selectedItems;
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {
        TextView textViewName;

        public ViewHolder(View itemView) {
            super(itemView);
            textViewName = itemView.findViewById(R.id.textViewName);
        }
    }
}
