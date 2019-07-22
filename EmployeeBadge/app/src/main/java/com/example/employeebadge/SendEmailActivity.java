package com.example.employeebadge;

import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;


import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.net.Uri;
import android.os.StrictMode;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

public class SendEmailActivity extends AppCompatActivity {
    String path="";
    int RESULTSENDEMAIL=999;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_email);
        getSupportActionBar().hide();

        Button btnSend = (Button)findViewById(R.id.btnSend);
        final EditText edtMail =(EditText)findViewById(R.id.txtMail);

        final String[] mail ={"humanresource@imt-soft.com"};
        composeEmail(mail, "IMT");
    }


    private void requestCameraPermission() {
        Dexter.withActivity(this)
                .withPermissions(Manifest.permission.READ_EXTERNAL_STORAGE)
                .withListener(new MultiplePermissionsListener() {
                    @Override
                    public void onPermissionsChecked(MultiplePermissionsReport report) {
                    }
                    @Override
                    public void onPermissionRationaleShouldBeShown(List<PermissionRequest> permissions, PermissionToken token) {
                        token.continuePermissionRequest();
                    }
                }).check();
    }
    public static boolean checkPermissions(Context context) {
        return ActivityCompat.checkSelfPermission(context, Manifest.permission.READ_EXTERNAL_STORAGE) == PackageManager.PERMISSION_GRANTED;
    }
    public void composeEmail(String[] addresses, String subject ) {  //,Uri attachment
        StrictMode.VmPolicy.Builder builder = new StrictMode.VmPolicy.Builder();
        StrictMode.setVmPolicy(builder.build());
        Intent intent = new Intent(Intent.ACTION_SEND_MULTIPLE,Uri.fromParts("mailto",addresses[0],null));
        intent.setType("plain/text");
        intent.putExtra(Intent.EXTRA_SUBJECT, subject);
        File file = new File("//sdcard//Download/" + "/IdCard.pdf");

        ArrayList<Uri> uris = new ArrayList<>();
        uris.add(Uri.fromFile(file));
        file = new File("//sdcard//Download/" + "/pictureimage.png");
        uris.add(Uri.fromFile(file));

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

        if (intent.resolveActivity(getPackageManager()) != null) {
            if (checkPermissions(getApplicationContext())) {
                startActivityForResult(intent, RESULTSENDEMAIL);
            } else {
                requestCameraPermission();
            }
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        if(requestCode == RESULTSENDEMAIL){
            Intent intent = new Intent(SendEmailActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }
}
