package com.example.webvideojuegos.Carrito;

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

import com.example.webvideojuegos.R;

public class HistorialFragment extends Fragment {

    private CarritoViewModel carritoViewModel;
    private HistorialAdapter historialAdapter;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_historial, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view_historial);
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));

        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);
        historialAdapter = new HistorialAdapter(carritoViewModel.getHistorialCompras().getValue());
        recyclerView.setAdapter(historialAdapter);

        carritoViewModel.getHistorialCompras().observe(getViewLifecycleOwner(), historialCompras -> {
            historialAdapter.setHistorialCompras(historialCompras);
        });

        return view;
    }
}
