package com.example.webvideojuegos;

import android.os.Bundle;
import android.os.Handler;
import android.os.Looper;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.navigation.NavController;
import androidx.navigation.fragment.NavHostFragment;

public class HomeFragment extends Fragment {

    private Handler handler;
    private Runnable runnable;
    private NavController navController;
    private int[] fragmentIds = {R.id.oferta1Fragment, R.id.oferta2Fragment, R.id.oferta3Fragment};
    private int currentIndex = 0;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_home, container, false);

        // Obtener NavController de NavHostFragment dentro de HomeFragment
        NavHostFragment navHostFragment = (NavHostFragment) getChildFragmentManager().findFragmentById(R.id.nav_host_fragment_home);
        navController = navHostFragment.getNavController();

        // Configurar el Handler y el Runnable para cambiar automáticamente de fragmento
        handler = new Handler(Looper.getMainLooper());
        runnable = new Runnable() {
            @Override
            public void run() {
                if (currentIndex >= fragmentIds.length) {
                    currentIndex = 0;
                }
                navController.navigate(fragmentIds[currentIndex]);
                currentIndex++;
                handler.postDelayed(this, 4000);
            }
        };

        // Iniciar el Runnable para cambiar de fragmento cada 4 segundos
        handler.postDelayed(runnable, 4000);

        return view;
    }

    @Override
    public void onDestroyView() {
        super.onDestroyView();
        // Eliminar el Runnable cuando se destruye la vista para evitar fugas de memoria
        if (handler != null && runnable != null) {
            handler.removeCallbacks(runnable);
        }
    }
}
