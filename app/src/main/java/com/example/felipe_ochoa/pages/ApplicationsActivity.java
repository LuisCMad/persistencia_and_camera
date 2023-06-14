package com.example.felipe_ochoa.pages;

import androidx.appcompat.app.AlertDialog;
import androidx.appcompat.app.AppCompatActivity;
import androidx.core.content.ContextCompat;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import android.Manifest;
import android.content.ContentValues;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.database.sqlite.SQLiteDatabase;
import android.graphics.Bitmap;
import android.os.Bundle;
import android.provider.MediaStore;
import android.util.Base64;
import android.util.Log;
import android.view.View;
import android.widget.Toast;

import com.example.felipe_ochoa.R;
import com.example.felipe_ochoa.adapters.ApplicationsAdapter;
import com.example.felipe_ochoa.data.ApplicationsContract;
import com.example.felipe_ochoa.data.DatabaseHelper;
import com.example.felipe_ochoa.data.models.ApliccationModel;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.io.ByteArrayOutputStream;
import java.util.ArrayList;
import java.util.List;

public class ApplicationsActivity extends AppCompatActivity {

    private static final int REQUEST_IMAGE_CAPTURE = 2;
    private DatabaseHelper databaseHelper;

    List<ApliccationModel> itemList = new ArrayList<>();
    RecyclerView recyclerView;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_applications);
        databaseHelper = new DatabaseHelper(this);
        getListApplications();
    }

    private void getListApplications() {
        try {

            ApplicationsAdapter adapter1 = new ApplicationsAdapter(this, itemList);
            SQLiteDatabase db = databaseHelper.getWritableDatabase();
            List<ApliccationModel> list = databaseHelper.getAllNames();
            recyclerView = findViewById(R.id.listViewApplications);
            // Crear un LinearLayoutManager para manejar el diseño de la lista
            LinearLayoutManager layoutManager = new LinearLayoutManager(this);
            recyclerView.setLayoutManager(layoutManager);
            // Crear el adaptador personalizado y asignarlo al RecyclerView
            ApplicationsAdapter adapter = new ApplicationsAdapter(this, list);
            recyclerView.setAdapter(adapter);
            db.close();
        } catch (Exception e) {
            Toast.makeText(this, "Error al obtener las aplicaciones", Toast.LENGTH_SHORT).show();
        }
    }
}