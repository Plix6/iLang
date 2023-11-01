package com.example.ilang;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.Toast;

import androidx.appcompat.app.AppCompatActivity;

import com.example.ilang.api.TranslationService;
import com.example.ilang.pojo.TranslationRequest;
import com.example.ilang.pojo.TranslationResponse;

import java.util.ArrayList;
import java.util.Collections;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

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


            Retrofit retrofit = new Retrofit.Builder()
                    .baseUrl("https://rapid-translate-multi-traduction.p.rapidapi.com/")
                    .addConverterFactory(GsonConverterFactory.create())
                    .build();

            TranslationService service = retrofit.create(TranslationService.class);


            ArrayList<String> translatedWords = new ArrayList<>();
            for (String word: words) {
                apiCall(service, word, translatedWords);
                // TODO - call translation API to translate guesses
                // TODO - get language code from bundle
                // translatedWords.add(word);
                int curIndex = words.indexOf(word);
                answers.get(curIndex).setText(translatedWords.get(curIndex));
            }


            // TODO - check if correct button is triggered + change colors + save score
            // TODO - display user info

            Button buttonToNewActivity = findViewById(R.id.backButton);
            buttonToNewActivity.setOnClickListener(v -> {
                Intent intent = new Intent(ExerciseActivity.this, MainActivity.class);
                startActivity(intent);
            });

        }
    }

    private String apiCall(TranslationService service, String wordToBeTranslated, ArrayList<String> wordsTranslated) {
        String apiKey = "b6d66b24f6msh894d87e0f3b55a8p1932bfjsn48067d837d94";
        String apiHost = "rapid-translate-multi-traduction.p.rapidapi.com";

        TranslationRequest request = new TranslationRequest("en", this.languageCode, wordToBeTranslated);
        Call<TranslationResponse> call = service.translate(apiKey, apiHost, request);

        call.enqueue(new Callback<TranslationResponse>() {
            @Override
            public void onResponse(Call<TranslationResponse> call, Response<TranslationResponse> response) {
                if (response.isSuccessful()) {
                    TranslationResponse translation = response.body();
                    wordsTranslated.add(translation.getTranslations().get(0));
                } else {
                    wordsTranslated.add("error translating word");
                }
            }

            @Override
            public void onFailure(Call<TranslationResponse> call, Throwable t) {
                displayToast("Couldn't get an API response");
            }
        });
        return "";
    }

    private void displayToast(String message) {
        // Create a Toast with the message and duration (LENGTH_SHORT or LENGTH_LONG)
        Toast.makeText(this, message, Toast.LENGTH_SHORT).show();
    }

}
