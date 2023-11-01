package com.example.ilang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;

import com.example.ilang.fragments.LanguageRecyclerViewAdapter;
import com.example.ilang.pojo.Language;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView languageRecyclerView;
    private LanguageRecyclerViewAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Language> itemList = new ArrayList<Language>(){{
            add(new Language("English", "EN-gb"));
            add(new Language("French", "FR-fr"));
            add(new Language("Italian", "IT-it"));
        }};
        // TODO - get itemList from Firebase ?

        RecyclerView languageRecyclerView = findViewById(R.id.languageRecyclerView);
        adapter = new LanguageRecyclerViewAdapter(itemList);

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        languageRecyclerView.setLayoutManager(layoutManager);

        languageRecyclerView.setAdapter(adapter);


        Button exerciseButton = findViewById(R.id.exerciseButton);
        exerciseButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
                startActivity(intent);
            }
        });
    }
}