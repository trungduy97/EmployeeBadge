package com.example.employeebadge.ui.activity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.app.ActivityCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.app.Activity;
import android.app.AlertDialog;
import android.app.Dialog;
import android.content.DialogInterface;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.content.res.Resources;
import android.database.Cursor;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.drawable.BitmapDrawable;
import android.net.Uri;
import android.os.Bundle;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.employeebadge.R;
import com.example.employeebadge.adapter.ListThemeAdapter;
import com.example.employeebadge.model.Card;
import com.example.employeebadge.model.RecyclerViewClickListener;
import com.example.employeebadge.model.SQLiteHelper;
import com.example.employeebadge.model.Theme;
import com.example.employeebadge.util.Utils;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import org.parceler.Parcels;

import java.io.ByteArrayOutputStream;
import java.io.FileNotFoundException;
import java.io.InputStream;
import java.util.ArrayList;

import butterknife.BindView;
import butterknife.ButterKnife;

public class GalleryThemesActivity extends AppCompatActivity implements RecyclerViewClickListener {
    @BindView(R.id.rcvTheme) RecyclerView rcvTheme;
    @BindView(R.id.btnAddTheme) FloatingActionButton btnAddTheme;
    Card card = new Card();
    public static SQLiteHelper sqLiteHelper;

    ArrayList<Theme> list = new ArrayList<>();
    ListThemeAdapter adapter = null;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_gallery_theme);
        ButterKnife.bind(this);
        setArgument();
        Utils.savePreferences("check", "1", getBaseContext());

        sqLiteHelper = new SQLiteHelper(this, "ThemeDB.sqlite", null, 1);
        sqLiteHelper.queryData("CREATE TABLE IF NOT EXISTS THEME(Id INTEGER PRIMARY KEY AUTOINCREMENT, name VARCHAR, image BLOB)");


        initRecycleView();


        btnAddTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(GalleryThemesActivity.this, AddThemeActivity.class);
                startActivity(intent);
            }
        });

    }

    private void setArgument() {
        Bundle arg = getIntent().getExtras();
        if (arg == null) {
            throw new IllegalArgumentException(
                    "MUST create CropCardActivity starter static function");
        }
        card = Parcels.unwrap(arg.getParcelable("CARD"));
        if (card == null) {
            throw new NullPointerException(
                    "No ARG_CLASS_INFO provided for CropCardActivity creation.");
        }
    }

    @Override
    protected void onResume() {
        super.onResume();
        initRecycleView();
    }

    private void initRecycleView() {
        adapter = new ListThemeAdapter(this, list, this);
        rcvTheme.setAdapter(adapter);
        rcvTheme.setLayoutManager(new LinearLayoutManager(GalleryThemesActivity.this));

        // get all data from sqlite
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM THEME");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new Theme(name, image, id));
        }


        if (list != null && list.size() == 0)
        {
            sqLiteHelper.insertData(
                "IMT card default",
                    imageToByte()
            );
            Toast.makeText(this, "listnull", Toast.LENGTH_SHORT).show();
        }

        adapter.notifyDataSetChanged();
    }

    @Override
    public void recyclerViewListClicked(View v, int position) {
        if (v.getId() == R.id.btnEdit){
            Cursor cursor = sqLiteHelper.getData("SELECT * FROM THEME");
            ArrayList<Theme> listThemes = new ArrayList<>();
            while (cursor.moveToNext()) {
                int id = cursor.getInt(0);
                String name = cursor.getString(1);
                byte[] image = cursor.getBlob(2);

                listThemes.add(new Theme(name, image, id));
            }

            showDialogUpdate(GalleryThemesActivity.this, listThemes.get(position).getId(), listThemes.get(position).getName(), listThemes.get(position).getImage());
        }


        if (v.getId() == R.id.btnRemove){
            Cursor c = sqLiteHelper.getData("SELECT id FROM THEME");
            ArrayList<Integer> arrID = new ArrayList<Integer>();
            while (c.moveToNext()){
                arrID.add(c.getInt(0));
            }
            showDialogDelete(arrID.get(position));
        }

        if (v.getId() == R.id.rlItem){
            Bundle arg = new Bundle();
            card.setPos(position);
            arg.putParcelable("CARD", Parcels.wrap(card));
            Intent starter = new Intent(GalleryThemesActivity.this, CropCardActivity.class);
            starter.putExtras(arg);
            startActivity(starter);
        }

    }


    ImageView imgTheme;
    private void showDialogUpdate(Activity activity, final int position, String name, byte[] img){

        final Dialog dialog = new Dialog(activity);
        dialog.setContentView(R.layout.update_theme_activity);
        dialog.setTitle("Update");

        imgTheme = (ImageView) dialog.findViewById(R.id.imgTheme);
        final EditText edtName = (EditText) dialog.findViewById(R.id.edtName);
        Button btnUpdate = (Button) dialog.findViewById(R.id.btnUpdate);


        Bitmap bitmap = BitmapFactory.decodeByteArray(img, 0, img.length);
        imgTheme.setImageBitmap(bitmap);
        edtName.setText(name);


        // set width for dialog
        int width = (int) (activity.getResources().getDisplayMetrics().widthPixels * 0.95);
        // set height for dialog
        int height = (int) (activity.getResources().getDisplayMetrics().heightPixels * 0.7);
        dialog.getWindow().setLayout(width, height);
        dialog.show();

        imgTheme.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                // request photo library
                ActivityCompat.requestPermissions(
                        GalleryThemesActivity.this,
                        new String[]{Manifest.permission.READ_EXTERNAL_STORAGE},
                        888
                );
            }
        });

        btnUpdate.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    sqLiteHelper.updateData(
                            edtName.getText().toString().trim(),
                            AddThemeActivity.imageViewToByte(imgTheme),
                            position
                    );
                    dialog.dismiss();
                    Toast.makeText(getApplicationContext(), "Update successfully!!!",Toast.LENGTH_SHORT).show();
                }
                catch (Exception error) {
                    Log.e("Update error", error.getMessage());
                }
                updateThemeList();
            }
        });
    }

    private void showDialogDelete(final int idTheme){
        final AlertDialog.Builder dialogDelete = new AlertDialog.Builder(GalleryThemesActivity.this);

        dialogDelete.setTitle("Warning!!");
        dialogDelete.setMessage("Are you sure you want to this delete?");
        dialogDelete.setPositiveButton("OK", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                try {
                    sqLiteHelper.deleteData(idTheme);
                    Toast.makeText(getApplicationContext(), "Delete successfully!!!",Toast.LENGTH_SHORT).show();
                } catch (Exception e){
                    Log.e("error", e.getMessage());
                }
                updateThemeList();
            }
        });

        dialogDelete.setNegativeButton("Cancel", new DialogInterface.OnClickListener() {
            @Override
            public void onClick(DialogInterface dialog, int which) {
                dialog.dismiss();
            }
        });
        dialogDelete.show();
    }

    private void updateThemeList(){
        // get all data from sqlite
        Cursor cursor = sqLiteHelper.getData("SELECT * FROM THEME");
        list.clear();
        while (cursor.moveToNext()) {
            int id = cursor.getInt(0);
            String name = cursor.getString(1);
            byte[] image = cursor.getBlob(2);

            list.add(new Theme(name, image, id));
        }
        adapter.notifyDataSetChanged();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {

        if(requestCode == 888){
            if(grantResults.length >0 && grantResults[0] == PackageManager.PERMISSION_GRANTED){
                Intent intent = new Intent(Intent.ACTION_PICK);
                intent.setType("image/*");
                startActivityForResult(intent, 888);
            }
            else {
                Toast.makeText(getApplicationContext(), "You don't have permission to access file location!", Toast.LENGTH_SHORT).show();
            }
            return;
        }
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {

        if(requestCode == 888 && resultCode == RESULT_OK && data != null){
            Uri uri = data.getData();
            try {
                InputStream inputStream = getContentResolver().openInputStream(uri);
                Bitmap bitmap = BitmapFactory.decodeStream(inputStream);
                imgTheme.setImageBitmap(bitmap);

            } catch (FileNotFoundException e) {
                e.printStackTrace();
            }
        }

        super.onActivityResult(requestCode, resultCode, data);
    }

    public byte[] imageToByte() {
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.backgroundimage);
        ByteArrayOutputStream stream = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.PNG, 100, stream);
        byte[] byteArray = stream.toByteArray();
        return byteArray;
    }
}
