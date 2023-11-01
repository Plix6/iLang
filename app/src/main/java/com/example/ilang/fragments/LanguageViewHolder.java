package com.example.ilang.fragments;

import android.view.View;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ilang.R;

public class LanguageViewHolder extends RecyclerView.ViewHolder {
    public TextView titleTextView;

    public LanguageViewHolder(View itemView) {
        super(itemView);
        titleTextView = itemView.findViewById(R.id.languageTitle);
    }
}
