package com.example.webvideojuegos.Juegoteca;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webvideojuegos.Carrito.CarritoViewModel;
import com.example.webvideojuegos.R;

public class JuegotecaFragment extends Fragment {

    private CarritoViewModel carritoViewModel;
    private JuegotecaAdapter juegotecaAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_juegoteca, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_juegoteca);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);
        juegotecaAdapter = new JuegotecaAdapter(carritoViewModel.getJuegoteca().getValue(), carritoViewModel);
        recyclerView.setAdapter(juegotecaAdapter);

        // Observador de los cambios en el LiveData de CarritoViewModel
        carritoViewModel.getJuegoteca().observe(getViewLifecycleOwner(), juegoteca -> {
            juegotecaAdapter.setJuegoteca(juegoteca);
        });

        return view;
    }
}
