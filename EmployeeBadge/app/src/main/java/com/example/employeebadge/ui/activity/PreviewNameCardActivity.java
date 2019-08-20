package com.example.employeebadge.ui.activity;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.os.StrictMode;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.core.app.ActivityCompat;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;

import com.example.employeebadge.R;
import com.example.employeebadge.util.ScreenshotType;
import com.example.employeebadge.util.ScreenshotUtils;
import com.karumi.dexter.Dexter;
import com.karumi.dexter.MultiplePermissionsReport;
import com.karumi.dexter.PermissionToken;
import com.karumi.dexter.listener.PermissionRequest;
import com.karumi.dexter.listener.multi.MultiplePermissionsListener;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.ButterKnife;

public class PreviewNameCardActivity extends AppCompatActivity {

    @BindView(R.id.txtFullname) TextView txtName;
    @BindView(R.id.txtPosition) TextView txtPos;
    @BindView(R.id.txtTel) TextView txtTele;
    @BindView(R.id.txtMobile) TextView txtMobi;
    @BindView(R.id.txtFax) TextView txtFax;
    @BindView(R.id.txtEmail) TextView txtEmail;
    @BindView(R.id.txtAdd) TextView txtAdd;
    @BindView(R.id.buttonCrop) Button btnCrop;
    @BindView(R.id.root) ConstraintLayout root;

    int RESULT_SEND_EMAIL_CARD=998;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_preview_name_card);
        ButterKnife.bind(this);


        getSupportActionBar().hide();

        getData();


        btnCrop.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                btnCrop.setVisibility(View.GONE);
                takeScreenshot(ScreenshotType.FULL);
            }
        });


    }


    private void getData(){
        Intent intent = getIntent();
        txtName.setText(intent.getStringExtra("edtName"));
        txtPos.setText(intent.getStringExtra("edtPos"));
        txtTele.setText("Tel: " + intent.getStringExtra("edtTele"));
        txtMobi.setText("Mobile: " + intent.getStringExtra("edtMobi"));
        txtFax.setText("Fax: " + intent.getStringExtra("edtFax"));
        txtEmail.setText("Email: " + intent.getStringExtra("edtEmail"));
        txtAdd.setText(intent.getStringExtra("edtAddress"));
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        b = ScreenshotUtils.getScreenShot(root);

        if (b != null) {
            showScreenShotImage(b);
            File saveFile = ScreenshotUtils.getMainDirectoryName(this);
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);
        } else
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }

    /*  Show screenshot Bitmap */
    private void showScreenShotImage(Bitmap b) {
        Uri imageUri = ScreenshotUtils.getImageUri(this, b);

        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .start(this);
    }



    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {

        if (requestCode == CropImage.CROP_IMAGE_ACTIVITY_REQUEST_CODE) {
            CropImage.ActivityResult result = CropImage.getActivityResult(data);
            if (resultCode == RESULT_OK) {
                try {
                    Bitmap bitmap = MediaStore.Images.Media.getBitmap(getContentResolver(), result.getUri());
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 530, 850, true);

                    createPdf(resized);

                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }

        if(requestCode == RESULT_SEND_EMAIL_CARD){
            btnCrop.setVisibility(View.VISIBLE);
            Intent intent = new Intent(PreviewNameCardActivity.this, MainActivity.class);
            intent.addFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP | Intent.FLAG_ACTIVITY_NEW_TASK);
            startActivity(intent);
            finish();
        }
    }


    @RequiresApi(api = Build.VERSION_CODES.KITKAT)
    private void createPdf(Bitmap bitmap2){
        WindowManager wm = (WindowManager) getSystemService(Context.WINDOW_SERVICE);
        Display display = wm.getDefaultDisplay();
        DisplayMetrics displaymetrics = new DisplayMetrics();
        this.getWindowManager().getDefaultDisplay().getMetrics(displaymetrics);
        float hight = displaymetrics.heightPixels ;
        float width = displaymetrics.widthPixels ;

        int convertHighet = (int) hight, convertWidth = (int) width;


        PdfDocument document = new PdfDocument();
        PdfDocument.PageInfo pageInfo = new PdfDocument.PageInfo.Builder(530, 850, 1).create();
        PdfDocument.Page page = document.startPage(pageInfo);

        Canvas canvas = page.getCanvas();


        Paint paint = new Paint();
        paint.setColor(Color.parseColor("#ffffff"));
        canvas.drawPaint(paint);



        bitmap2 = Bitmap.createScaledBitmap(bitmap2, 530, 850, true);

        paint.setColor(Color.BLUE);
        canvas.drawBitmap(bitmap2, 0, 0 , null);
        document.finishPage(page);


        // write the document content
        String targetPdf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/NameCard.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        document.close();


        //Send Email
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
        File file = new File("//sdcard//Download/" + "/NameCard.pdf");

        ArrayList<Uri> uris = new ArrayList<>();
        uris.add(Uri.fromFile(file));

        intent.putParcelableArrayListExtra(Intent.EXTRA_STREAM, uris);

        if (intent.resolveActivity(getPackageManager()) != null) {
            if (checkPermissions(getApplicationContext())) {
                startActivityForResult(intent, RESULT_SEND_EMAIL_CARD);
            } else {
                requestCameraPermission();
            }
        }
    }
}
