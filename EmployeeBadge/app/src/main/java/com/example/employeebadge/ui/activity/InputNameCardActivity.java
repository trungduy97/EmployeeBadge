package com.example.employeebadge.ui.activity;

import android.content.Intent;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

import com.example.employeebadge.R;

import butterknife.BindView;
import butterknife.ButterKnife;

public class InputNameCardActivity extends AppCompatActivity {
    @BindView(R.id.edtFullName) EditText edtName;
    @BindView(R.id.edtPosition) EditText edtPos;
    @BindView(R.id.edtTelephone) EditText edtTele;
    @BindView(R.id.edtMobilephone) EditText edtMobi;
    @BindView(R.id.edtFax) EditText edtFax;
    @BindView(R.id.edtEmail) EditText edtEmail;
    @BindView(R.id.edtAddress) EditText edtAddress;
    @BindView(R.id.buttonPreview) Button btnPreview;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name_card);
        ButterKnife.bind(this);

        getSupportActionBar().setTitle("Name Card Details");

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPreview();
            }
        });
    }


    private void moveToPreview(){
        Intent intent = new Intent(InputNameCardActivity.this, PreviewNameCardActivity.class);
        intent.putExtra("edtName", edtName.getText().toString());
        intent.putExtra("edtPos", edtPos.getText().toString());
        intent.putExtra("edtTele", edtTele.getText().toString());
        intent.putExtra("edtMobi", edtMobi.getText().toString());
        intent.putExtra("edtFax", edtFax.getText().toString());
        intent.putExtra("edtEmail", edtEmail.getText().toString());
        intent.putExtra("edtAddress", edtAddress.getText().toString());
        startActivity(intent);
    }
}
