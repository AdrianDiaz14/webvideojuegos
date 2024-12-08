package com.example.webvideojuegos;

import android.content.Context;
import android.content.Intent;
import android.content.SharedPreferences;
import android.os.Bundle;
import android.view.Menu;
import android.view.MenuItem;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.appcompat.app.ActionBarDrawerToggle;
import androidx.appcompat.app.AppCompatActivity;
import androidx.appcompat.widget.Toolbar;
import androidx.core.view.GravityCompat;
import androidx.drawerlayout.widget.DrawerLayout;
import androidx.fragment.app.Fragment;

import com.example.webvideojuegos.Carrito.CarritoHistorialFragment;
import com.example.webvideojuegos.Juegoteca.JuegotecaFragment;
import com.example.webvideojuegos.Tienda.TiendaFragment;
import com.google.android.material.navigation.NavigationView;

public class MainActivity extends AppCompatActivity {

    private DrawerLayout drawerLayout;
    private SharedPreferences sharedPreferences;
    private SharedPreferences.OnSharedPreferenceChangeListener preferenceChangeListener;
    private TextView headerTextView;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_main);

        // Se define la toolbar
        Toolbar toolbar = findViewById(R.id.toolbar);
        setSupportActionBar(toolbar);

        // Se define el Drawer Layout
        drawerLayout = findViewById(R.id.drawer_layout);
        NavigationView navigationView = findViewById(R.id.nav_view);

        // Configurar el ActionBarDrawerToggle. Gestiona apertura y cierre del Drawer
        ActionBarDrawerToggle toggle = new ActionBarDrawerToggle(
                this, drawerLayout, toolbar,
                R.string.navigation_drawer_open, R.string.navigation_drawer_close);
        drawerLayout.addDrawerListener(toggle);
        toggle.syncState();

        // Obtener referencia al TextView del header
        headerTextView = navigationView.getHeaderView(0).findViewById(R.id.textViewNombre);

        // Configurar el OnClickListener para el drawer_header. Al pulsar en él, lleva a NuevoUsuarioActivity
        navigationView.getHeaderView(0).findViewById(R.id.drawer_header).setOnClickListener(v -> {
            Intent intent = new Intent(MainActivity.this, NuevoUsuarioActivity.class);
            startActivity(intent);
            drawerLayout.closeDrawer(GravityCompat.START);
        });

        // Nuevo icono para la toolbar
        toolbar.setNavigationIcon(R.mipmap.ic_launcher_foreground);


        // Cargar el nombre del usuario de SharedPreferences
        sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String nombreUsuario = sharedPreferences.getString("nombre", "Nuevo usuario");
        headerTextView.setText(nombreUsuario);

        // Configurar el Listener para detectar cambios en SharedPreferences
        preferenceChangeListener = (sharedPreferences, key) -> {
            if (key.equals("nombre")) {
                String updatedNombre = sharedPreferences.getString("nombre", "Nuevo usuario");
                headerTextView.setText(updatedNombre);
            }
        };

        sharedPreferences.registerOnSharedPreferenceChangeListener(preferenceChangeListener);

        // Menú de navegación
        navigationView.setNavigationItemSelectedListener(item -> {
            Fragment selectedFragment = null;
            int itemId = item.getItemId();

            if (itemId == R.id.nav_home) {
                selectedFragment = new HomeFragment();
                toolbar.setTitle("The Gamer Shop");
            } else if (itemId == R.id.nav_shop) {
                selectedFragment = new TiendaFragment();
                toolbar.setTitle("Tienda");
            } else if (itemId == R.id.nav_cart) {
                selectedFragment = new CarritoHistorialFragment();
                toolbar.setTitle("Carrito");
            } else if (itemId == R.id.nav_juegoteca) {
                selectedFragment = new JuegotecaFragment();
                toolbar.setTitle("Juegoteca");
            }

            if (selectedFragment != null) {
                getSupportFragmentManager().beginTransaction()
                        .replace(R.id.fragment_container_view, selectedFragment)
                        .commit();
                drawerLayout.closeDrawer(GravityCompat.START);
            }
            return true;
        });

        // Cargar el fragmento por defecto
        if (savedInstanceState == null) {
            getSupportFragmentManager().beginTransaction()
                    .replace(R.id.fragment_container_view, new HomeFragment())
                    .commit();
            navigationView.setCheckedItem(R.id.nav_home);
        }
    }


    @Override
    public void onDestroy() {
        super.onDestroy();
        sharedPreferences.unregisterOnSharedPreferenceChangeListener(preferenceChangeListener);

        // Borrar el nombre del usuario de SharedPreferences al cerrar la aplicación
        SharedPreferences.Editor editor = sharedPreferences.edit();
        editor.remove("nombre");
        editor.apply();
    }

    // Verificar si un usuario está registrado
    public boolean isUserRegistered() {
        SharedPreferences sharedPreferences = getSharedPreferences("UserPrefs", Context.MODE_PRIVATE);
        String nombreUsuario = sharedPreferences.getString("nombre", null);
        return nombreUsuario != null && !nombreUsuario.equals("Nuevo usuario");
    }

    // Inflar el menú de opciones
    @Override
    public boolean onCreateOptionsMenu(Menu menu) {
        getMenuInflater().inflate(R.menu.options_menu, menu);
        return true;
    }

    //Menú de opciones
    @Override
    public boolean onOptionsItemSelected(@NonNull MenuItem item) {
        int id = item.getItemId();
        if (id == R.id.action_help) {
            // Iniciar la actividad de ayuda
            Intent intent = new Intent(MainActivity.this, HelpActivity.class);
            startActivity(intent);
            return true;
        } else if (id == R.id.action_exit) {
            // Cerrar la aplicación
            finish();
            return true;
        }
        return super.onOptionsItemSelected(item);
    }

}
