package com.example.felipe_ochoa.pages;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatActivity;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;
import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.os.Bundle;
import android.widget.Toast;

import com.example.felipe_ochoa.R;
import com.example.felipe_ochoa.adapters.ApplicationsAdapter;
import com.example.felipe_ochoa.adapters.UniversitiesAdapter;
import com.example.felipe_ochoa.data.models.ApliccationModel;
import com.example.felipe_ochoa.data.models.University;
import com.example.felipe_ochoa.data.network.RestApi;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.ArrayList;
import java.util.List;

public class UniversitiesActivity extends AppCompatActivity {
    Retrofit retrofit = new Retrofit.Builder()
            .baseUrl("http://universities.hipolabs.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build();

    RestApi apiService = retrofit.create(RestApi.class);
    List<University> itemList = new ArrayList<>();
    RecyclerView recyclerView;
    private UniversitiesAdapter adapter;
    private FloatingActionButton fab;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_universities);
        fab = findViewById(R.id.btnFab);
        fab.setOnClickListener(v -> {
            // Navigate page applications
            Intent intent = new Intent(UniversitiesActivity.this, ApplicationsActivity.class);
            startActivity(intent);
        });
        getUniversitiesByCountry("chile");
    }

    private void getUniversitiesByCountry(String country) {
        Call<List<University>> call = apiService.getUniversitiesByCountry("chile");
        call.enqueue(new Callback<List<University>>() {
            @Override
            public void onResponse(@NonNull Call<List<University>> call, Response<List<University>> response) {
                if (response.isSuccessful()) {
                    List<University> universities = response.body();
                    recyclerView = findViewById(R.id.listViewUniversities);
                    // Crear un LinearLayoutManager para manejar el diseño de la lista
                    LinearLayoutManager layoutManager = new LinearLayoutManager(UniversitiesActivity.this);
                    recyclerView.setLayoutManager(layoutManager);
                    // Crear el adaptador personalizado y asignarlo al RecyclerView
                    UniversitiesAdapter adapter = new UniversitiesAdapter(UniversitiesActivity.this, universities);
                    recyclerView.setAdapter(adapter);
                    assert universities != null;
                    Toast.makeText(UniversitiesActivity.this, "Universidades encontradas: " + universities.size(), Toast.LENGTH_SHORT).show();
                } else {
                    Toast.makeText(UniversitiesActivity.this, "Error en la respuesta", Toast.LENGTH_SHORT).show();
                }
            }

            @Override
            public void onFailure(Call<List<University>> call, Throwable t) {
                // Ocurrió un error en la comunicación con el servidor
            }
        });
    }
}