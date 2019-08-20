package com.example.employeebadge.adapter;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.employeebadge.R;
import com.example.employeebadge.adapter.viewholder.ListThemeViewHolder;

/**
 * @author Created by duydt on 8/20/2019.
 */
public class ListThemeAdapter extends RecyclerView.Adapter<ListThemeViewHolder> {


    @NonNull
    @Override
    public ListThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme, parent, false);
        return new ListThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListThemeViewHolder holder, int position) {

    }

    @Override
    public int getItemCount() {
        return 0;
    }
}
