package com.example.webvideojuegos.Tienda;

import android.content.Context;
import android.os.Bundle;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.inputmethod.EditorInfo;
import android.view.inputmethod.InputMethodManager;
import android.widget.EditText;
import android.widget.ProgressBar;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webvideojuegos.Carrito.CarritoViewModel;
import com.example.webvideojuegos.R;
import com.google.android.material.chip.Chip;
import com.google.android.material.chip.ChipGroup;
import com.google.android.material.floatingactionbutton.FloatingActionButton;

import java.util.Collections;
import java.util.Comparator;

public class TiendaFragment extends Fragment {

    private VideojuegoViewModel videojuegoViewModel;
    private CarritoViewModel carritoViewModel;
    private VideojuegoAdapter videojuegoAdapter;
    private ChipGroup chipGroup;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_tienda, container, false);

        EditText searchInput = view.findViewById(R.id.search_input);
        FloatingActionButton searchButton = view.findViewById(R.id.search_button);
        FloatingActionButton fabSort = view.findViewById(R.id.fab_sort);
        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_tienda);
        ProgressBar progressBar = view.findViewById(R.id.progress_bar);
        chipGroup = view.findViewById(R.id.chip_group);
        Chip chipSortName = view.findViewById(R.id.chip_sort_name);
        Chip chipSortPrice = view.findViewById(R.id.chip_sort_price);

        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);
        videojuegoViewModel = new ViewModelProvider(this).get(VideojuegoViewModel.class);
        videojuegoAdapter = new VideojuegoAdapter(carritoViewModel);

        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(videojuegoAdapter);

        // Observa VideojuegoViewModel y recoge la lista de juegos para darsela al Adapter y mostrarla al usuario
        videojuegoViewModel.getJuegos().observe(getViewLifecycleOwner(), juegos -> {
            if (juegos != null) {
                videojuegoAdapter.setJuegos(juegos);
            } else {
                Toast.makeText(getContext(), "No se encontraron resultados", Toast.LENGTH_SHORT).show();
            }
        });

        videojuegoViewModel.getLoadingState().observe(getViewLifecycleOwner(), isLoading -> {
            progressBar.setVisibility(isLoading ? View.VISIBLE : View.GONE);
        });

        // Listener del botón de búsqueda
        searchButton.setOnClickListener(v -> {
            realizarBusqueda(searchInput.getText().toString());
            ocultarTeclado(searchInput);
        });

        // Hace visible o invisible los Chips de ordenar cuando se pulsa el fab
        fabSort.setOnClickListener(v -> {
            int visibility = chipGroup.getVisibility() == View.VISIBLE ? View.GONE : View.VISIBLE;
            chipGroup.setVisibility(visibility);
        });

        // Ordena la lista de juegos por nombre
        chipSortName.setOnClickListener(v -> {
            if (videojuegoAdapter.getItemCount() > 0) {
                Collections.sort(videojuegoAdapter.getJuegos(), Comparator.comparing(juego -> juego.external));
                videojuegoAdapter.notifyDataSetChanged();
                chipGroup.setVisibility(View.GONE); // Ocultar chips al seleccionar una opción
            }
        });

        // Ordena la lista de juegos por precio
        chipSortPrice.setOnClickListener(v -> {
            if (videojuegoAdapter.getItemCount() > 0) {
                Collections.sort(videojuegoAdapter.getJuegos(), Comparator.comparing(juego -> Double.parseDouble(juego.cheapest)));
                videojuegoAdapter.notifyDataSetChanged();
                chipGroup.setVisibility(View.GONE); // Ocultar chips al seleccionar una opción
            }
        });

        // Gestiona las interacciones del usuario en el campo de búsqueda de los juegos
        searchInput.setOnEditorActionListener((TextView v, int actionId, KeyEvent event) -> {
            if (actionId == EditorInfo.IME_ACTION_SEARCH ||
                    actionId == EditorInfo.IME_ACTION_DONE ||
                    event != null && event.getAction() == KeyEvent.ACTION_DOWN &&
                            event.getKeyCode() == KeyEvent.KEYCODE_ENTER) {
                if (event == null || !event.isShiftPressed()) {
                    realizarBusqueda(searchInput.getText().toString());
                    ocultarTeclado(searchInput);
                    return true;
                }
            }
            return false;
        });
        return view;
    }

    public void ocultarTeclado(View view) {
        InputMethodManager imm = (InputMethodManager) getActivity().getSystemService(Context.INPUT_METHOD_SERVICE);
        if (imm != null) {
            imm.hideSoftInputFromWindow(view.getWindowToken(), 0);
        }
    }


    private void realizarBusqueda(String titulo) {
        if (!titulo.isEmpty()) {
            videojuegoViewModel.buscarJuegos(titulo);
        } else {
            Toast.makeText(getContext(), "Por favor ingrese un título para buscar", Toast.LENGTH_SHORT).show();
        }
    }
}
