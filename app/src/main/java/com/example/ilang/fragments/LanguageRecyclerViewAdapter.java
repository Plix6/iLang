package com.example.ilang.fragments;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.AdapterView.OnItemClickListener;

import androidx.recyclerview.widget.RecyclerView;

import com.example.ilang.R;
import com.example.ilang.pojo.Language;

import java.util.ArrayList;

public class LanguageRecyclerViewAdapter extends RecyclerView.Adapter<LanguageViewHolder> {
    private ArrayList<Language> itemList;

    public LanguageRecyclerViewAdapter(ArrayList<Language> itemList, OnItemClickListener listener) {
        this.itemList = itemList;
    }

    @Override
    public LanguageViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.language_list_item, parent, false);
        return new LanguageViewHolder(view);
    }

    @Override
    public void onBindViewHolder(LanguageViewHolder holder, int position) {
        Language item = itemList.get(position);
        holder.titleTextView.setText(item.getTitle());
    }

    @Override
    public int getItemCount() {
        return itemList.size();
    }

    public interface OnItemClickListener {
        void onItemClick(Language language);
    }
}
