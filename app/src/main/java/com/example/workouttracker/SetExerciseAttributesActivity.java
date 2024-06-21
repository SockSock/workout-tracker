package com.example.workouttracker;

import android.content.Intent;
import android.os.Bundle;
import android.text.Editable;
import android.text.TextWatcher;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import androidx.appcompat.app.AppCompatActivity;

public class SetExerciseAttributesActivity extends AppCompatActivity {
    private EditText setsInput;
    private EditText repsInput;
    private EditText weightInput;
    private EditText incrementInput;
    private Button confirmButton;
    private Button cancelButton;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_set_exercise_attributes);

        weightInput = findViewById(R.id.weight_input);
        repsInput = findViewById(R.id.reps_input);
        setsInput = findViewById(R.id.sets_input);
        incrementInput = findViewById(R.id.increment_input);
        confirmButton = findViewById(R.id.confirm_button);
        cancelButton = findViewById(R.id.cancel_button);

        confirmButton.setEnabled(false);

        weightInput.addTextChangedListener(textWatcher);
        repsInput.addTextChangedListener(textWatcher);
        setsInput.addTextChangedListener(textWatcher);
        incrementInput.addTextChangedListener(textWatcher);

        confirmButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent resultIntent = new Intent();
                resultIntent.putExtra("sets", Integer.parseInt(setsInput.getText().toString()));
                resultIntent.putExtra("reps", Integer.parseInt(repsInput.getText().toString()));
                resultIntent.putExtra("weight", Double.parseDouble(weightInput.getText().toString()));
                resultIntent.putExtra("increment", Double.parseDouble(incrementInput.getText().toString()));
                setResult(RESULT_OK, resultIntent);
                finish();
            }
        });

        cancelButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                setResult(RESULT_CANCELED);
                finish();
            }
        });
    }

    private TextWatcher textWatcher = new TextWatcher() {
        @Override
        public void beforeTextChanged(CharSequence s, int start, int count, int after) {
            // Not used
        }

        @Override
        public void onTextChanged(CharSequence s, int start, int before, int count) {
            // Not used
        }

        @Override
        public void afterTextChanged(Editable s) {
            String sets = setsInput.getText().toString();
            String reps = repsInput.getText().toString();
            String weight = weightInput.getText().toString();
            String increment = incrementInput.getText().toString();

            confirmButton.setEnabled(!sets.isEmpty() && !reps.isEmpty() && !weight.isEmpty() && !increment.isEmpty());
        }
    };


}
