package com.example.ilang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ilang.api.TranslationClient;
import com.example.ilang.utils.NetworkUtils;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;


public class ExerciseActivity extends AppCompatActivity {
    private String languageCode;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_layout);
        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                this.languageCode = bundle.getString("targetLanguageCode");
            } else {
                this.languageCode = "fr";
            }
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
                    add("gift");
                    add("high");
                    add("incorrect");
                    add("no");
                }
            };
            String correctWord = "gift";
            Collections.shuffle(words);

            TextView questionWord = findViewById(R.id.questionWord);
            questionWord.setText(correctWord);

            TranslationClient translationClient = new TranslationClient();

            if (NetworkUtils.isNetworkAvailable(this)) {
                translationClient.translateText("en", this.languageCode, words, new TranslationClient.Callback() {
                    @Override
                    public void onResponse(String response) {
                        try {
                            // TODO - check once daily limit has expired
                            JSONArray jsonArray = new JSONArray(response);
                            for (int i = 0; i < jsonArray.length(); i++) {
                                String word = jsonArray.getString(i);
                                answers.get(i).setText(word);
                            }
                        } catch (org.json.JSONException e) {
                            for(Button answer: answers) {
                                answer.setText("error");
                            }
                        }
                    }

                    @Override
                    public void onFailure(String errorMessage) {
                        for(Button answer: answers) {
                            answer.setText("error");
                        }
                    }
                });
            } else {
                displayToast("No Internet connection available...");
            }




            // TODO - check if correct button is triggered + change colors + save score
            // TODO - display user info

            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(v -> {
                Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
                startActivity(intent);
            });

        }
    }

    private void displayToast(String message) {
        // Create a Toast with the message and duration (LENGTH_SHORT or LENGTH_LONG)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
