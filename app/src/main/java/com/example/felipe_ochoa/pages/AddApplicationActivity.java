package com.example.felipe_ochoa.pages;

import androidx.appcompat.app.AppCompatActivity;

import android.content.ContentValues;
import android.content.Intent;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.media.MediaPlayer;
import android.os.Bundle;
import android.provider.MediaStore;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.Base64;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.example.felipe_ochoa.R;
import com.example.felipe_ochoa.data.ApplicationsContract;
import com.example.felipe_ochoa.data.DatabaseHelper;

import java.io.ByteArrayOutputStream;

public class AddApplicationActivity extends AppCompatActivity {
    String encodedImage;
    private Button save, takePhoto;
    private DatabaseHelper databaseHelper;
    private static final int REQUEST_IMAGE_CAPTURE = 2;

    private String universityName, userName;

    private EditText editText;

    private ImageView imageView;

    private MediaPlayer mediaPlayer;
    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_add_application);
        databaseHelper = new DatabaseHelper(this);
        Intent intent = getIntent();
        universityName = intent.getStringExtra("university");
        save = findViewById(R.id.save);
        editText = findViewById(R.id.editTextName);
        takePhoto = findViewById(R.id.buttonTakePhoto);
        imageView = findViewById(R.id.imageViewPhoto);
         mediaPlayer = MediaPlayer.create(this, R.raw.ok);
        takePhoto.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent takePictureIntent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
                if (takePictureIntent.resolveActivity(getPackageManager()) != null) {
                    startActivityForResult(takePictureIntent, REQUEST_IMAGE_CAPTURE);
                } else {
                    Toast.makeText(AddApplicationActivity.this, "No se pudo abrir la cámara", Toast.LENGTH_SHORT).show();
                }
            }
        });
        editText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence s, int start, int count, int after) {
                // Este método se llama antes de que el texto cambie
            }

            @Override
            public void onTextChanged(CharSequence s, int start, int before, int count) {
                userName = s.toString();
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });
        save.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                try {
                    if (validateValues()) {
                        saveApplicationDB();
                    }
                } catch (Exception e) {
                    Toast.makeText(AddApplicationActivity.this, "Error al insertar" + e.toString(), Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private Boolean validateValues() {
        if (userName == null || userName.isEmpty()) {
            Toast.makeText(this, "Debe ingresar un nombre", Toast.LENGTH_SHORT).show();
            return false;
        }
        if (encodedImage == null) {
            Toast.makeText(this, "Debe tomar una foto", Toast.LENGTH_SHORT).show();
            return false;
        }
        return true;
    }

    private void saveApplicationDB() {
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        ContentValues values = new ContentValues();
        values.put(ApplicationsContract.COLUMN_UNIVERSITY_NAME, universityName);
        values.put(ApplicationsContract.COLUMN_USER_NAME, userName);
        values.put(ApplicationsContract.COLUMN_USER_IMAGE, encodedImage);
        long newRowId = db.insert(ApplicationsContract.TABLE_NAME, null, values);
        db.close();
        Toast.makeText(this, "Insertado correctamente", Toast.LENGTH_SHORT).show();
        mediaPlayer.start();
        //Navigate application list
        Intent intent = new Intent(this, ApplicationsActivity.class);
        startActivity(intent);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (requestCode == REQUEST_IMAGE_CAPTURE && resultCode == RESULT_OK) {
            // set image to ImageView
            imageView.setImageBitmap((Bitmap) data.getExtras().get("data"));
            // **********************************************************
            Bundle extras = data.getExtras();
            Bitmap capturedImageBitmap = (Bitmap) extras.get("data");
            // Utiliza el objeto Bitmap según tus necesidades
            ByteArrayOutputStream byteArrayOutputStream = new ByteArrayOutputStream();
            capturedImageBitmap.compress(Bitmap.CompressFormat.PNG, 100, byteArrayOutputStream);
            byte[] imageBytes = byteArrayOutputStream.toByteArray();
            encodedImage = Base64.encodeToString(imageBytes, Base64.DEFAULT);
        }
    }
}