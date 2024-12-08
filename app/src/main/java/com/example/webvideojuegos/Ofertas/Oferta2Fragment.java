package com.example.webvideojuegos.Ofertas;

import android.os.Bundle;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.Navigation;

import com.example.webvideojuegos.R;

public class Oferta2Fragment extends Fragment {

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_oferta2, container, false);

        view.setOnClickListener(v -> Navigation.findNavController(view).navigate(R.id.action_oferta2Fragment_to_oferta3Fragment));

        return view;
    }
}
