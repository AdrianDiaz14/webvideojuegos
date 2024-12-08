package com.example.webvideojuegos.Carrito;

import androidx.annotation.NonNull;
import androidx.fragment.app.Fragment;
import androidx.fragment.app.FragmentActivity;
import androidx.viewpager2.adapter.FragmentStateAdapter;

public class CarritoHistorialAdapter extends FragmentStateAdapter {

    public CarritoHistorialAdapter(@NonNull FragmentActivity fragmentActivity) {
        super(fragmentActivity);
    }

    @NonNull
    @Override
    public Fragment createFragment(int position) {
        switch (position) {
            case 0:
                return new CarritoFragment();
            case 1:
                return new HistorialFragment();
            default:
                return new CarritoFragment();
        }
    }

    @Override
    public int getItemCount() {
        return 2;
    }
}
