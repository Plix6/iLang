package com.example.ilang;

import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ilang.api.TranslationClient;
import com.example.ilang.utils.NetworkUtils;
import com.google.firebase.auth.FirebaseAuth;
import com.google.firebase.auth.FirebaseUser;
import com.google.firebase.firestore.AggregateSource;
import com.google.firebase.firestore.DocumentSnapshot;
import com.google.firebase.firestore.FirebaseFirestore;

import org.json.JSONArray;

import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.Map;


public class ExerciseActivity extends AppCompatActivity {
    TextView questionWord;
    TextView userData;
    ArrayList<Button> answers;
    private String languageCode;
    ArrayList<String> words;
    String correctWord;
    int score;
    FirebaseAuth mAuth;
    FirebaseFirestore db;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.exercise_layout);

        db = FirebaseFirestore.getInstance();
        mAuth = FirebaseAuth.getInstance();

        if (savedInstanceState == null) {
            Bundle bundle = getIntent().getExtras();
            if (bundle != null) {
                this.languageCode = bundle.getString("targetLanguageCode");
            } else {
                this.languageCode = "fr";
            }
            answers = new ArrayList<Button>(){
                {
                    add((Button) findViewById(R.id.answerOne));
                    add((Button) findViewById(R.id.answerTwo));
                    add((Button) findViewById(R.id.answerThree));
                    add((Button) findViewById(R.id.answerFour));
                }
            };

            for(Button button: answers) {
                button.setOnClickListener(v -> {
                    if (answers.indexOf(button) == words.indexOf(correctWord)) {
                        for(Button answer: answers) answer.setText("");
                        displayToast("This is the right answer !");
                        score++;
                        Map<String, Object> newScoreData = new HashMap<>();
                        newScoreData.put(languageCode, score);
                        db.collection("Users").document(mAuth.getCurrentUser().getUid())
                                .update(newScoreData);
                        getUserScore();
                    } else {
                        displayToast("Wrong answer !");
                        finish();
                    }
                });
            }

            questionWord = findViewById(R.id.questionWord);
            userData = findViewById(R.id.userData);

            getUserScore();

            Button backButton = findViewById(R.id.backButton);
            backButton.setOnClickListener(v -> {
                finish();
            });
        }
    }



    private void displayToast(String message) {
        // Create a Toast with the message and duration (LENGTH_SHORT or LENGTH_LONG)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

    private void getUserScore() {
        FirebaseUser user = mAuth.getCurrentUser();

        db.collection("Users").document(user.getUid())
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> dbUserData = document.getData();
                            try {
                                score = Long.valueOf ((long) dbUserData.get(languageCode)).intValue();
                                userData.setText("Current score : " + score);
                                getWords();
                            } catch (NullPointerException e) {
                                displayToast("User data could not be loaded, please retry later");
                                finish();
                            }
                        } else {
                            displayToast("User data not found, please restart the app");
                            finish();
                        }
                    } else {
                        displayToast("Online database could not be accessed, please retry later");
                        finish();
                    }
                });
    }

    private void getWords() {
        db.collection("Exercises").document(String.valueOf(score + 1))
                .get().addOnCompleteListener(task -> {
                    if (task.isSuccessful()) {
                        DocumentSnapshot document = task.getResult();
                        if (document.exists()) {
                            Map<String, Object> dbExerciseData = document.getData();
                            try {
                                words = new ArrayList<>();
                                for (int i = 1; i <= 4; i++) {
                                    String temp = "guess_" + i;
                                    words.add((String) dbExerciseData.get(temp));
                                }
                                correctWord = (String) dbExerciseData.get("correct");
                                translateWords();
                            } catch (NullPointerException e) {
                                displayToast("Exercise data could not be loaded, please retry later");
                                finish();
                            }
                        } else {
                            db.collection("Exercises").count().get(AggregateSource.SERVER).addOnCompleteListener(task1 -> {
                                if (task1.isSuccessful()) {
                                    if (task1.getResult().getCount() < score + 1) {
                                        displayToast("Congratulations, you have finished all the exercises for that language !");
                                        finish();
                                    } else {
                                        displayToast("Exercise data not found, please restart the app");
                                        finish();
                                    }
                                } else {
                                    displayToast("Exercise data not found for unknown reasons, please restart the app");
                                    finish();
                                }
                            });
                        }
                    } else {
                        displayToast("Online database could not be accessed, please retry later");
                        finish();
                    }
                });
    }

    private void translateWords() {
        Collections.shuffle(words);

        questionWord.setText(correctWord);

        TranslationClient translationClient = new TranslationClient();

        if (NetworkUtils.isNetworkAvailable(this)) {
            translationClient.translateText("en", this.languageCode, words, new TranslationClient.Callback() {
                @Override
                public void onResponse(String response) {
                    try {
                        JSONArray jsonArray = new JSONArray(response);
                        for (int i = 0; i < jsonArray.length(); i++) {
                            String word = jsonArray.getString(i);
                            answers.get(i).setText(word);
                        }
                    } catch (org.json.JSONException e) {
                        displayToast("Could not read received translation data");
                        finish();
                    }
                }

                @Override
                public void onFailure(String errorMessage) {
                    for(Button answer: answers) {
                        displayToast("The app did not receive the translation data");
                        finish();
                    }
                }
            });
        } else {
            displayToast("No Internet connection available...");
            finish();
        }
    }
}
