package com.example.ilang;

import android.os.Bundle;
import android.widget.Button;

import androidx.appcompat.app.AppCompatActivity;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class ExerciseActivity extends AppCompatActivity {
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_layout);
        if (savedInstanceState == null) {
            ArrayList<Button> answers = new ArrayList<Button>(){
                {
                    add((Button) findViewById(R.id.answerOne));
                    add((Button) findViewById(R.id.answerTwo));
                    add((Button) findViewById(R.id.answerThree));
                    add((Button) findViewById(R.id.answerFour));
                }
            };

            // TODO - call database API to get data

            ArrayList<String> words = new ArrayList<String>(){
                {
                    add("test");
                    add("another test");
                    add("incorrect");
                    add("no");
                }
            };
            String correctWord = "another test";
            Collections.shuffle(words);

            ArrayList<String> translatedWords = new ArrayList<>();
            for (String word: words) {
                // TODO - call translation API to translate guesses
                translatedWords.add(word);
                int curIndex = words.indexOf(word);
                answers.get(curIndex).setText(translatedWords.get(curIndex));
            }


            // TODO - check if correct button is triggered + change colors + save score
        }
    }

}
