package com.example.workouttracker;

import android.text.Editable;
import android.text.TextWatcher;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import java.util.ArrayList;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private ArrayList<Exercise> exerciseList;

    public static class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseName;
        EditText sets, reps, weight;
        Button buttonPlus, buttonMinus;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseName = itemView.findViewById(R.id.exerciseName);
            sets = itemView.findViewById(R.id.sets);
            reps = itemView.findViewById(R.id.reps);
            weight = itemView.findViewById(R.id.weight);
            buttonPlus = itemView.findViewById(R.id.buttonPlus);
            buttonMinus = itemView.findViewById(R.id.buttonMinus);
        }
    }

    public ExerciseAdapter(ArrayList<Exercise> exerciseList) {
        this.exerciseList = exerciseList;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_exercise, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exerciseList.get(position);
        holder.exerciseName.setText(exercise.getName());
        holder.sets.setText(String.valueOf(exercise.getSets()));
        holder.reps.setText(String.valueOf(exercise.getReps()));
        holder.weight.setText(String.valueOf(exercise.getWeight()));

        holder.sets.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    exercise.setSets(Integer.parseInt(s.toString()));
                }
            }
        });

        holder.reps.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    exercise.setReps(Integer.parseInt(s.toString()));
                }
            }
        });

        holder.weight.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {}

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {}

            @Override
            public void afterTextChanged(Editable s) {
                if (!s.toString().isEmpty()) {
                    exercise.setWeight(Double.parseDouble(s.toString()));
                }
            }
        });

        holder.buttonPlus.setOnClickListener(v -> {
            exercise.increaseWeight();
            holder.weight.setText(String.valueOf(exercise.getWeight()));

        });

        holder.buttonMinus.setOnClickListener(v -> {
            exercise.decreaseWeight();
            holder.weight.setText(String.valueOf(exercise.getWeight()));
        });
    }

    @Override
    public int getItemCount() {
        return exerciseList.size();
    }
}
