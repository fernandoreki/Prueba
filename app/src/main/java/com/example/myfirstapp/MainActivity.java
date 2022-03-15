package com.example.myfirstapp;

import androidx.appcompat.app.AppCompatActivity;

import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.TextView;
import android.widget.Toast;

public class MainActivity extends AppCompatActivity {

    EditText txtNombre;
    TextView etiNombre;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        txtNombre = findViewById(R.id.txtNombre);
        etiNombre = findViewById(R.id.etiNombre);

    }

    public void eventoOnClick(View view) {
        switch (view.getId()){
            case R.id.btnIngresar:
                String nombre = txtNombre.getText().toString();
                etiNombre.setText("Bienvenido: " + nombre);
                Toast.makeText(this, "El nombre es: "+nombre, Toast.LENGTH_SHORT).show();
                break;

        }
    }
}