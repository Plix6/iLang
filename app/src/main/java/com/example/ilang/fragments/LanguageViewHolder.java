package com.example.ilang.fragments;

import android.view.View;
import android.widget.Button;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ilang.R;

public class LanguageViewHolder extends RecyclerView.ViewHolder {
    public Button titleButton;

    public LanguageViewHolder(View itemView) {
        super(itemView);
        titleButton = itemView.findViewById(R.id.languageTitle);
    }
}
