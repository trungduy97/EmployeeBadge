package com.example.employeebadge;

import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import androidx.annotation.RequiresApi;
import androidx.constraintlayout.widget.ConstraintLayout;
import androidx.appcompat.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.Toast;

import com.davemorrissey.labs.subscaleview.ImageSource;
import com.davemorrissey.labs.subscaleview.SubsamplingScaleImageView;
import com.example.employeebadge.util.ScreenshotType;
import com.example.employeebadge.util.ScreenshotUtils;
import com.example.employeebadge.util.Utils;
import com.theartofdev.edmodo.cropper.CropImage;
import com.theartofdev.edmodo.cropper.CropImageActivity;
import com.theartofdev.edmodo.cropper.CropImageView;

import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;

import butterknife.BindView;
import butterknife.ButterKnife;

public class CropPictureCardActivity extends AppCompatActivity {

    @BindView(R.id.rootView) ConstraintLayout constraintLayout;
    @BindView(R.id.imagePictureCard) SubsamplingScaleImageView imagePictureCard;
    @BindView(R.id.btnCropImg) Button btnCropImage;


    String imgPath = "", numberId = "", name = "", position = "";


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_crop_picture_card);
        ButterKnife.bind(this);

        getData();

        imagePictureCard.setImage(ImageSource.uri(Uri.fromFile(new File(imgPath))));

        btnCropImage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                takeScreenshot(ScreenshotType.FULL);
            }
        });
    }

    private void takeScreenshot(ScreenshotType screenshotType) {
        Bitmap b = null;
        b = ScreenshotUtils.getScreenShot(constraintLayout);

        if (b != null) {
            showScreenShotImage(b);
            File saveFile = ScreenshotUtils.getMainDirectoryName(this);
            File file = ScreenshotUtils.store(b, "screenshot" + screenshotType + ".jpg", saveFile);
        } else
            Toast.makeText(this, "error", Toast.LENGTH_SHORT).show();

    }


    private void showScreenShotImage(Bitmap b) {
        Uri imageUri = ScreenshotUtils.getImageUri(this, b);

        CropImage.activity(imageUri)
                .setGuidelines(CropImageView.Guidelines.ON)
                .setMultiTouchEnabled(true)
                .setMinCropResultSize((int) Utils.convertDpToPixel(280, this), (int)Utils.convertDpToPixel(367, this))
                .setMaxCropResultSize((int)Utils.convertDpToPixel(280, this), (int)Utils.convertDpToPixel(367, this))
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
                    Bitmap resized = Bitmap.createScaledBitmap(bitmap, 294, 386, true);
                    savebitmap(resized);

                } catch (IOException e) {
                    e.printStackTrace();
                }

                Intent intent = new Intent(CropPictureCardActivity.this, TakePicture.class);
                intent.putExtra("imagePath", imgPath);
                intent.putExtra("name", name);
                intent.putExtra("id", numberId);
                intent.putExtra("position", position);
                startActivity(intent);
            }
        }
    }

    public static File savebitmap(Bitmap bmp) throws IOException {
        ByteArrayOutputStream bytes = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, bytes);
        File f = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_DOWNLOADS) + "/pictureimage.png");
        f.createNewFile();
        FileOutputStream fo = new FileOutputStream(f);
        fo.write(bytes.toByteArray());
        fo.close();
        return f;
    }

    private void getData(){
        Intent intent = getIntent();
        imgPath = intent.getStringExtra("imagePath");
        name = intent.getStringExtra("name");
        numberId = intent.getStringExtra("id");
        position = intent.getStringExtra("position");
    }
}
