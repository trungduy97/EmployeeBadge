package com.example.employeebadge;

import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;

public class InputNameCard extends AppCompatActivity {
    EditText edtName, edtPos, edtTele, edtMobi, edtFax, edtEmail, edtAddress;
    Button btnPreview;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_input_name_card);
        getSupportActionBar().hide();

        init();

        btnPreview.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                moveToPreview();
            }
        });
    }

    private void init(){
        edtName = (EditText)findViewById(R.id.edtFullName);
        edtPos = (EditText)findViewById(R.id.edtPosition);
        edtTele = (EditText)findViewById(R.id.edtTelephone);
        edtMobi = (EditText)findViewById(R.id.edtMobilephone);
        edtFax = (EditText)findViewById(R.id.edtFax);
        edtEmail = (EditText)findViewById(R.id.edtEmail);
        edtAddress = (EditText)findViewById(R.id.edtAddress);
        btnPreview = (Button)findViewById(R.id.buttonPreview);
    }

    private void moveToPreview(){
        Intent intent = new Intent(InputNameCard.this, PreviewNameCardActivity.class);
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
