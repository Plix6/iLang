package com.example.ilang;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

public class ExerciseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            // TODO - bind data to buttons
        }
        setContentView(R.layout.exercise_layout);
    }

}
