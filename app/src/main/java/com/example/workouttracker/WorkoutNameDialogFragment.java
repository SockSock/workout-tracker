package com.example.workouttracker;

import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.EditText;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.DialogFragment;

public class WorkoutNameDialogFragment extends DialogFragment {

    public interface WorkoutNameDialogListener {
        void onWorkoutNameEntered(String workoutName);
    }

    private WorkoutNameDialogListener listener;

    @NonNull
    @Override
    public Dialog onCreateDialog(@Nullable Bundle savedInstanceState) {
        AlertDialog.Builder builder = new AlertDialog.Builder(getActivity());
        LayoutInflater inflater = requireActivity().getLayoutInflater();
        View view = inflater.inflate(R.layout.dialog_workout_name, null);
        EditText editTextWorkoutName = view.findViewById(R.id.edit_workout_name);

        builder.setView(view)
                .setTitle("Enter Workout Name")
                .setPositiveButton("Create", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        String workoutName = editTextWorkoutName.getText().toString().trim();
                        listener.onWorkoutNameEntered(workoutName);
                    }
                })
                .setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
                    @Override
                    public void onClick(DialogInterface dialog, int which) {
                        dismiss();
                    }
                });

        return builder.create();
    }

    public void setListener(WorkoutNameDialogListener listener) {
        this.listener = listener;
    }
}
