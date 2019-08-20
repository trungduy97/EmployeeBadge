package com.example.employeebadge.adapter.viewholder;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import androidx.recyclerview.widget.RecyclerView;

import com.example.employeebadge.R;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Created by duydt on 8/20/2019.
 */
public class ListThemeViewHolder extends RecyclerView.ViewHolder {
    @BindView(R.id.imgTheme) public ImageView tvStudentName;
    @BindView(R.id.txtNameTheme) public TextView tvStudentStatus;

    public ListThemeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);
    }
}
