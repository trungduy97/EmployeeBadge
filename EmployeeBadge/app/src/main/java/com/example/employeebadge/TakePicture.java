package com.example.employeebadge;

import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;

import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.pdf.PdfDocument;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import android.util.DisplayMetrics;
import android.view.Display;
import android.view.View;
import android.view.WindowManager;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import android.widget.ViewFlipper;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.employeebadge.util.ScreenshotType;
import com.example.employeebadge.util.ScreenshotUtils;
import com.example.employeebadge.util.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class TakePicture extends AppCompatActivity {

    @BindView(R.id.rootView) ConstraintLayout rootContent;
    @BindView(R.id.btnTake) Button btnTake;
    @BindView(R.id.imgAvata)  SubsamplingScaleImageView imgAvata;
    @BindView(R.id.txtName) TextView txtName;
    @BindView(R.id.txtId) TextView txtId;
    @BindView(R.id.txtPosition) TextView txtPosition;




    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_take_picture);
        ButterKnife.bind(this);
        getSupportActionBar().hide();


        //set font
        Typeface tf = Typeface.createFromAsset(getAssets(),
                "font.ttf");
        txtId.setTypeface(tf);

        setImgAvata();

        btnTake.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot(ScreenshotType.FULL);
            }
        });
    }

    private void setImgAvata(){
        Intent intent = getIntent();
        String imagePath = intent.getStringExtra("imagePath");
        txtName.setText(intent.getStringExtra("name"));
        txtId.setText(intent.getStringExtra("id"));
        txtPosition.setText(intent.getStringExtra("position"));
        imgAvata.setImage(ImageSource.uri(Uri.fromFile(new File(imagePath))));
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        b = ScreenshotUtils.getScreenShot(rootContent);

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
                .setMinCropResultSize(Utils.getScreenWidth(this), (int)Utils.convertDpToPixel(594, this))
                .setMaxCropResultSize(Utils.getScreenWidth(this), (int)Utils.convertDpToPixel(594, this))
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
        String targetPdf = Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/IdCard.pdf";
        File filePath = new File(targetPdf);
        try {
            document.writeTo(new FileOutputStream(filePath));
        } catch (IOException e) {
            e.printStackTrace();
            Toast.makeText(this, "Something wrong: " + e.toString(), Toast.LENGTH_LONG).show();
        }
        document.close();

        Intent intent = new Intent(TakePicture.this, SendEmailActivity.class);
        startActivity(intent);
    }


}