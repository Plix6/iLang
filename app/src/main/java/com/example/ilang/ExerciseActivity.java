package com.example.ilang;

import android.os.Bundle;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ilang.fragments.GuessButtonFragment;

import java.util.ArrayList;
import java.util.Collections;

public class ExerciseActivity extends AppCompatActivity {
    public ExerciseActivity() {
        super(R.layout.exercise_layout);
    }

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        if (savedInstanceState == null) {
            ArrayList<Bundle> bundles = getBundlesData(new ArrayList<String>() {
                {
                    add("test");
                    add("two");
                    add("three");
                    add("four");
                }
            } , 2);
            getSupportFragmentManager().beginTransaction()
                    .setReorderingAllowed(true)
                    .add(R.id.answerOne, GuessButtonFragment.class, bundles.get(0))
                    .add(R.id.answerTwo, GuessButtonFragment.class, bundles.get(1))
                    .add(R.id.answerThree, GuessButtonFragment.class, bundles.get(2))
                    .add(R.id.answerFour, GuessButtonFragment.class, bundles.get(3))
                    .commit();
        }
    }

    private ArrayList<Bundle> getBundlesData(ArrayList<String> words, int correctAnswer) {
        ArrayList<Bundle> bundles = new ArrayList<>();
        for (int i = 0; i < words.size(); i++) {
            bundles.get(i).putString(GuessButtonFragment.ARG_ANSWER, words.get(i));
            bundles.get(i).putBoolean(GuessButtonFragment.ARG_CORRECT, i == correctAnswer);
        }
        Collections.shuffle(bundles);

        return bundles;
    }
}
