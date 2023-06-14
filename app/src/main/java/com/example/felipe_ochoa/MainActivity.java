package com.example.felipe_ochoa;

import androidx.appcompat.app.AppCompatActivity;

import android.content.Intent;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Toast;

import com.example.felipe_ochoa.pages.ApplicationsActivity;
import com.example.felipe_ochoa.pages.UniversitiesActivity;

public class MainActivity extends AppCompatActivity {

    private EditText edtUsername;
    private EditText edtPassword;
    private Button btnLogin;

    private String validUsername = "admin@example.com";
    private String validPassword = "123";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        edtUsername = findViewById(R.id.username);
        edtPassword = findViewById(R.id.password);
        btnLogin = findViewById(R.id.btn_login);
        edtUsername.setText("admin@example.com");
        edtPassword.setText("123");

        /*Validando Login*/
        btnLogin.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String username = edtUsername.getText().toString().trim();
                String password = edtPassword.getText().toString().trim();

                if (isValidCredentials(username, password)) {
                    // Las credenciales son válidas, realizar acción adicional (ejemplo: navegar a otra actividad)
                    Toast.makeText(MainActivity.this, "Inicio de sesión exitoso", Toast.LENGTH_SHORT).show();
                    // Navigate to ApplicationsActivity
                    Intent intent = new Intent(MainActivity.this, UniversitiesActivity.class);
                    startActivity(intent);
                } else {
                    // Las credenciales son inválidas
                    Toast.makeText(MainActivity.this, "Credenciales incorrectas", Toast.LENGTH_SHORT).show();
                }
            }
        });
    }

    private boolean isValidCredentials(String username, String password) {
        /*Se utiliza la funcion nativa equals para validar la cadena de texto */
        return username.equals(validUsername) && password.equals(validPassword);
    }
}