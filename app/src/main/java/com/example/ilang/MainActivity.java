package com.example.ilang;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import com.example.ilang.fragments.LanguageRecyclerViewAdapter;
import com.example.ilang.pojo.Language;

import java.util.ArrayList;

public class MainActivity extends AppCompatActivity {
    private RecyclerView languageRecyclerView;
    private LanguageRecyclerViewAdapter adapter;
    private TextView currentTextView;
    private boolean isDropdownOpen = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        ArrayList<Language> itemList = generateItems();

        languageRecyclerView = findViewById(R.id.languageRecyclerView);
        currentTextView = findViewById(R.id.selectedItemRecyclerView);
        adapter = new LanguageRecyclerViewAdapter(itemList, new LanguageRecyclerViewAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(Language language) {
                currentTextView.setText(language.getTitle());
                toggleDropdown();
            }
        });

        LinearLayoutManager layoutManager = new LinearLayoutManager(this);
        languageRecyclerView.setLayoutManager(layoutManager);

        languageRecyclerView.setAdapter(adapter);

        Button toggleDropdownButton = findViewById(R.id.toggleDropdownButton);
        toggleDropdownButton.setOnClickListener(v -> toggleDropdown());


        Button exerciseButton = findViewById(R.id.exerciseButton);
        exerciseButton.setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, ExerciseActivity.class);
            startActivity(intent);
        });
    }

    private ArrayList<Language> generateItems() {
        ArrayList<Language> itemList = new ArrayList<Language>(){{
            add(new Language("English", "EN-gb"));
            add(new Language("French", "FR-fr"));
            add(new Language("Italian", "IT-it"));
        }};
        // TODO - get from FireBase

        return itemList;
    }

    private void toggleDropdown() {
        if (isDropdownOpen) {
            languageRecyclerView.setVisibility(View.GONE);
        } else {
            languageRecyclerView.setVisibility(View.VISIBLE);
        }
        isDropdownOpen = !isDropdownOpen;
    }
}