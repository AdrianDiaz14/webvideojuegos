package com.example.webvideojuegos.Carrito;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;

import com.example.webvideojuegos.Model.Compra;
import com.example.webvideojuegos.R;

import java.util.List;

public class HistorialAdapter extends RecyclerView.Adapter<HistorialAdapter.ViewHolder> {

    private List<Compra> historialCompras;

    public HistorialAdapter(List<Compra> historialCompras) {
        this.historialCompras = historialCompras;
    }

    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_historial, parent, false);
        return new ViewHolder(view);
    }

    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Compra compra = historialCompras.get(position);
        holder.bind(compra);
    }

    @Override
    public int getItemCount() {
        return historialCompras.size();
    }

    public void setHistorialCompras(List<Compra> historialCompras) {
        this.historialCompras = historialCompras;
        notifyDataSetChanged();
    }

    public static class ViewHolder extends RecyclerView.ViewHolder {

        private TextView pedidoHeader;
        private TextView juegosList;
        private TextView totalPrice;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            pedidoHeader = itemView.findViewById(R.id.pedido_header);
            juegosList = itemView.findViewById(R.id.juegos_list);
            totalPrice = itemView.findViewById(R.id.total_price);
        }

        public void bind(Compra compra) {
            pedidoHeader.setText(String.format("Pedido #%04d - %s", compra.getId(), compra.getFecha()));
            juegosList.setText(String.join(", ", compra.getJuegos()));
            totalPrice.setText(String.format("Total: %.2f€", compra.getTotal()));
        }
    }
}
