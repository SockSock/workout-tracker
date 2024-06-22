package com.example.workouttracker;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import java.util.List;

public class ExerciseAdapter extends RecyclerView.Adapter<ExerciseAdapter.ExerciseViewHolder> {
    private List<Exercise> exercises;

    public ExerciseAdapter(List<Exercise> exercises) {
        this.exercises = exercises;
    }

    @NonNull
    @Override
    public ExerciseViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.exercise_item, parent, false);
        return new ExerciseViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ExerciseViewHolder holder, int position) {
        Exercise exercise = exercises.get(position);
        holder.bind(exercise);
    }

    @Override
    public int getItemCount() {
        return exercises.size();
    }

    class ExerciseViewHolder extends RecyclerView.ViewHolder {
        TextView exerciseNameTextView;
        EditText setsEditText;
        EditText repsEditText;
        EditText weightEditText;
        Button weightMinusButton;
        Button weightPlusButton;

        public ExerciseViewHolder(@NonNull View itemView) {
            super(itemView);
            exerciseNameTextView = itemView.findViewById(R.id.exercise_name);
            setsEditText = itemView.findViewById(R.id.sets_edit_text);
            repsEditText = itemView.findViewById(R.id.reps_edit_text);
            weightEditText = itemView.findViewById(R.id.weight_edit_text);
            weightMinusButton = itemView.findViewById(R.id.weight_minus_button);
            weightPlusButton = itemView.findViewById(R.id.weight_plus_button);

            weightMinusButton.setOnClickListener(v -> {
                Exercise exercise = exercises.get(getAdapterPosition());
                exercise.decreaseWeight();
                weightEditText.setText(String.valueOf(exercise.getWeight()));
            });

            weightPlusButton.setOnClickListener(v -> {
                Exercise exercise = exercises.get(getAdapterPosition());
                exercise.increaseWeight();
                weightEditText.setText(String.valueOf(exercise.getWeight()));
            });
        }

        public void bind(Exercise exercise) {
            exerciseNameTextView.setText(exercise.getName());
            setsEditText.setText(String.valueOf(exercise.getSets()));
            repsEditText.setText(String.valueOf(exercise.getReps()));
            weightEditText.setText(String.valueOf(exercise.getWeight()));

            setsEditText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    exercise.setSets(Integer.parseInt(setsEditText.getText().toString()));
                }
            });

            repsEditText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    exercise.setReps(Integer.parseInt(repsEditText.getText().toString()));
                }
            });

            weightEditText.setOnFocusChangeListener((v, hasFocus) -> {
                if (!hasFocus) {
                    exercise.setWeight(Double.parseDouble(weightEditText.getText().toString()));
                }
            });
        }
    }
}
