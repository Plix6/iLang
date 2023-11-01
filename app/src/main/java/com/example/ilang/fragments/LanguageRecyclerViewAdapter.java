package com.example.ilang.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ilang.R;
import com.example.ilang.pojo.Language;

import java.util.ArrayList;

public class LanguageRecyclerViewAdapter extends RecyclerView.Adapter<LanguageViewHolder> {
    private ArrayList<Language> itemList;
    private OnItemClickListener listener;

    public LanguageRecyclerViewAdapter(ArrayList<Language> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
        this.listener = listener;
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_list_item, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        Language item = itemList.get(position);
        holder.titleButton.setText(item.getTitle());
        holder.titleButton.setOnClickListener(view -> {
            onItemClicked(position);
        });
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Language language);
    }

    private void onItemClicked(int position) {
        if (listener != null) {
            listener.onItemClick(itemList.get(position));
        }
    }
}
