package com.example.webvideojuegos;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.inputmethod.EditorInfo;
import android.widget.ArrayAdapter;
import android.widget.Button;
import android.widget.EditText;
import android.widget.Spinner;
import android.widget.TextView;
import android.widget.Toast;
import androidx.appcompat.app.AppCompatActivity;

public class NuevoUsuarioActivity extends AppCompatActivity {

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_nuevo_usuario);

        // Inicializa las vistas
        EditText editTextNombre = findViewById(R.id.editTextNombre);
        EditText editTextEmail = findViewById(R.id.editTextEmail);
        EditText editTextTelefono = findViewById(R.id.editTextTelefono);
        Spinner spinnerGenero = findViewById(R.id.spinnerGenero);
        Button buttonGuardar = findViewById(R.id.buttonGuardar);

        // Configurar el Spinner
        ArrayAdapter<CharSequence> adapter = ArrayAdapter.createFromResource(
                this, R.array.genero_array, android.R.layout.simple_spinner_item);
        adapter.setDropDownViewResource(android.R.layout.simple_spinner_dropdown_item);
        spinnerGenero.setAdapter(adapter);

        // Configurar las casillas de texto para evitar salto de línea al pulsar Enter
            //NOMBRE
            editTextNombre.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            editTextNombre.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    editTextEmail.requestFocus();
                    return true;
                }
                return false;
            });
            //EMAIL
            editTextEmail.setImeOptions(EditorInfo.IME_ACTION_NEXT);
            editTextEmail.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_NEXT || actionId == EditorInfo.IME_ACTION_DONE) {
                    editTextTelefono.requestFocus();
                    return true;
                }
                return false;
            });
            //TELÉFONO
            editTextTelefono.setImeOptions(EditorInfo.IME_ACTION_DONE);
            editTextTelefono.setOnEditorActionListener((v, actionId, event) -> {
                if (actionId == EditorInfo.IME_ACTION_DONE) {
                    spinnerGenero.performClick();
                    return true;
                }
                return false;
            });

        buttonGuardar.setOnClickListener(v -> {
            String nombre = editTextNombre.getText().toString();
            String email = editTextEmail.getText().toString();
            String telefono = editTextTelefono.getText().toString();
            String genero = spinnerGenero.getSelectedItem().toString();

            // Guardar datos en SharedPreferences
            SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
            SharedPreferences.Editor editor = sharedPreferences.edit();
            editor.putString("nombre", nombre);
            editor.putString("email", email);
            editor.putString("telefono", telefono);
            editor.putString("genero", genero);
            editor.apply();

            // Mostrar mensaje de bienvenida
            Toast.makeText(NuevoUsuarioActivity.this, "Bienvenido " + nombre, Toast.LENGTH_SHORT).show();

            // Cerrar la actividad después de guardar
            finish();
        });
    }
}
