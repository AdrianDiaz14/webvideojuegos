package com.example.webvideojuegos.Carrito;

import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.Button;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.fragment.app.Fragment;
import androidx.lifecycle.ViewModelProvider;
import androidx.recyclerview.widget.LinearLayoutManager;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webvideojuegos.MainActivity;
import com.example.webvideojuegos.Model.Videojuego;
import com.example.webvideojuegos.NotificationHelper;
import com.example.webvideojuegos.R;

import java.util.List;

public class CarritoFragment extends Fragment {

    private CarritoViewModel carritoViewModel;
    private CarritoAdapter carritoAdapter;
    private TextView totalAmount;
    private Button buttonOrder;
    private Button buttonCancel;

    @Nullable
    @Override
    public View onCreateView(@NonNull LayoutInflater inflater, @Nullable ViewGroup container, @Nullable Bundle savedInstanceState) {
        View view = inflater.inflate(R.layout.fragment_carrito, container, false);

        RecyclerView recyclerView = view.findViewById(R.id.recycler_view);
        totalAmount = view.findViewById(R.id.total_amount);
        buttonOrder = view.findViewById(R.id.button_order);
        buttonCancel = view.findViewById(R.id.button_cancel);

        NotificationHelper.createNotificationChannel(requireContext());

        carritoViewModel = new ViewModelProvider(requireActivity()).get(CarritoViewModel.class);

        carritoAdapter = new CarritoAdapter(carritoViewModel.getCarritoItems().getValue(), item -> {
            carritoViewModel.eliminarDelCarrito(item);
            carritoAdapter.notifyItemRemoved(carritoViewModel.getCarritoItems().getValue().indexOf(item));
        });
        recyclerView.setLayoutManager(new LinearLayoutManager(getContext()));
        recyclerView.setAdapter(carritoAdapter);

        carritoViewModel.getCarritoItems().observe(getViewLifecycleOwner(), carritoItems -> {
            carritoAdapter.setItems(carritoItems);
            Log.d("CarritoFragment", "Items en el carrito: " + carritoItems.size());
            actualizarTotal(carritoItems);
        });

        // Al pulsar el botón para hacer la compra
        buttonOrder.setOnClickListener(v -> {
            MainActivity mainActivity = (MainActivity) getActivity();
            // Comprueba si hay un usuario registrado
            if (mainActivity != null && !mainActivity.isUserRegistered()) {
                Toast.makeText(mainActivity, "Por favor, introduce tus datos personales.", Toast.LENGTH_SHORT).show();
                return;
            }
            carritoViewModel.realizarCompra(requireContext());
            NotificationHelper.sendOrderNotification(requireContext());
        });

        // Al pulsar el botón para cancelar el pedido
        buttonCancel.setOnClickListener(v -> carritoViewModel.vaciarCarrito());

        return view;
    }

    // Calcula el precio total acumulado de los elementos de la lista
    private void actualizarTotal(List<Videojuego.Contenido> carritoItems) {
        double total = 0;
        for (Videojuego.Contenido item : carritoItems) {
            total += Double.parseDouble(item.cheapest);
        }
        totalAmount.setText(String.format("Total: %.2f€", total));
    }
}
