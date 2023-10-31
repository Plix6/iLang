package com.example.ilang.fragments;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.fragment.app.Fragment;

import com.example.ilang.R;

public class GuessButtonFragment extends Fragment {
    public static final String ARG_ANSWER = "answerText";
    public static final String ARG_CORRECT = "correct";

    private String answer;
    private boolean correct;

    public GuessButtonFragment() {
        // Default public constructor
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (getArguments() != null) {
            answer = getArguments().getString(ARG_ANSWER);
            correct = getArguments().getBoolean(ARG_CORRECT);
        }
    }

    public void onClick() {
        if (correct) {

        } else {

        }
    }

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        return inflater.inflate(R.layout.fragment_guessbutton, container, false);
    }

    @Override
    public void onViewCreated(View view, Bundle savedInstanceState) {
        super.onViewCreated(view, savedInstanceState);

    }
}
