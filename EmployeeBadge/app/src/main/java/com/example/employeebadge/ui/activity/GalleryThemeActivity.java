package com.example.employeebadge.ui.activity;

import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.RecyclerView;

import android.os.Bundle;

import com.example.employeebadge.R;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryThemeActivity extends AppCompatActivity {
    @BindView(R.id.btnAddTheme) FloatingActionButton btnAddTheme;
    @BindView(R.id.rcvTheme) RecyclerView rcvTheme;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_theme);
        ButterKnife.bind(this);
        getSupportActionBar().setTitle("Theme");

    }
}
