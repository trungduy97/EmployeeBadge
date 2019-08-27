package com.example.employeebadge.adapter;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.employeebadge.R;
import com.example.employeebadge.adapter.viewholder.ListThemeViewHolder;
import com.example.employeebadge.model.RecyclerViewClickListener;
import com.example.employeebadge.model.Theme;

import java.util.List;

/**
 * @author Created by duydt on 8/20/2019.
 */
public class ListThemeAdapter extends RecyclerView.Adapter<ListThemeViewHolder> {
    private Context mContext;
    List<Theme> mList;
    public static RecyclerViewClickListener mItemListener;

    public ListThemeAdapter(Context context, List<Theme> list, RecyclerViewClickListener itemListener){
        mContext = context;
        mList = list;
        mItemListener = itemListener;
    }

    @NonNull
    @Override
    public ListThemeViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_theme, parent, false);
        return new ListThemeViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ListThemeViewHolder holder, int position) {
        Theme theme = mList.get(position);

        holder.txtNameTheme.setText(theme.getName());
        byte[] img = theme.getImage();
        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        holder.imgTheme.setImageBitmap(bitmap);

        if ("IMT card default".equals(theme.getName()) || "SHAREWORK card default".equals(theme.getName())) {
            holder.btnEdit.setVisibility(View.GONE);
            holder.btnRemove.setVisibility(View.GONE);
        } else {
            holder.btnEdit.setVisibility(View.VISIBLE);
            holder.btnRemove.setVisibility(View.VISIBLE);
        }

    }

    @Override
    public int getItemCount() {
        return mList != null ? mList.size() : 0;
    }
}

