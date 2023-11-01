package com.example.ilang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.widget.Button;
import android.widget.TextView;

import com.example.ilang.fragments.LanguageRecyclerViewAdapter;
import com.example.ilang.pojo.Language;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView languageRecyclerView;
    private LanguageRecyclerViewAdapter adapter;
    private TextView currentTextView;
    private String languageCode;
    // TODO - give language code to exercise activity in bundle

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Language> itemList = generateItems();

        languageRecyclerView = findViewById(R.id.languageRecyclerView);
        currentTextView = findViewById(R.id.selectedItemRecyclerView);
        adapter = new LanguageRecyclerViewAdapter(itemList, language -> {
            currentTextView.setText(language.getTitle());
            languageCode = language.getCode();
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        languageRecyclerView.setLayoutManager(layoutManager);

        languageRecyclerView.setAdapter(adapter);


        Button exerciseButton = findViewById(R.id.exerciseButton);
        exerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
            startActivity(intent);
        });
    }

    private ArrayList<Language> generateItems() {
        ArrayList<Language> itemList = new ArrayList<Language>(){{
            add(new Language("English", "en"));
            add(new Language("French", "fr"));
            add(new Language("Italian", "it"));
        }};
        // TODO - get from FireBase

        return itemList;
    }
}