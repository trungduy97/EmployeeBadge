package com.example.employeebadge.adapter.viewholder;

import android.view.View;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;


import androidx.recyclerview.widget.RecyclerView;

import com.example.employeebadge.R;
import com.example.employeebadge.adapter.ListThemeAdapter;

import butterknife.BindView;
import butterknife.ButterKnife;

/**
 * @author Created by duydt on 8/20/2019.
 */
public class ListThemeViewHolder extends RecyclerView.ViewHolder implements View.OnClickListener {
    @BindView(R.id.imgTheme) public ImageView imgTheme;
    @BindView(R.id.txtNameTheme) public TextView txtNameTheme;
    @BindView(R.id.btnEdit) public ImageButton btnEdit;
    @BindView(R.id.btnRemove) public ImageButton btnRemove;
    @BindView(R.id.rlItem) public RelativeLayout rlItem;

    public ListThemeViewHolder(View itemView) {
        super(itemView);
        ButterKnife.bind(this, itemView);

        btnEdit.setOnClickListener(this);
        btnRemove.setOnClickListener(this);
        rlItem.setOnClickListener(this);
    }

    @Override
    public void onClick(View view) {
        ListThemeAdapter.mItemListener.recyclerViewListClicked(view, this.getLayoutPosition());
    }
}
