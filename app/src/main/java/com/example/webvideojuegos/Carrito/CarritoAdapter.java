package com.example.webvideojuegos.Carrito;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.webvideojuegos.Model.Videojuego;
import com.example.webvideojuegos.R;

import java.util.List;

public class CarritoAdapter extends RecyclerView.Adapter<CarritoAdapter.ViewHolder> {

    private List<Videojuego.Contenido> items;
    private OnItemDeleteClickListener onItemDeleteClickListener;

    public interface OnItemDeleteClickListener {
        void onItemDeleteClick(Videojuego.Contenido item);
    }

    public CarritoAdapter(List<Videojuego.Contenido> items, OnItemDeleteClickListener onItemDeleteClickListener) {
        this.items = items;
        this.onItemDeleteClickListener = onItemDeleteClickListener;
    }

    // Infla cada elemento del recycler con el diseño definido
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_carrito, parent, false);
        return new ViewHolder(view);
    }

    // Vincula los datos a cada vista en función de su posición
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Videojuego.Contenido item = items.get(position);
        holder.bind(item);
    }

    // Nº de items en la vista
    @Override
    public int getItemCount() {
        return items.size();
    }

    public void setItems(List<Videojuego.Contenido> items) {
        this.items = items;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView title;
        private TextView price;
        private ImageView imageDelete;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            imageDelete = itemView.findViewById(R.id.image_delete);

            imageDelete.setOnClickListener(v -> {
                int position = getAdapterPosition();
                if (position != RecyclerView.NO_POSITION && onItemDeleteClickListener != null) {
                    onItemDeleteClickListener.onItemDeleteClick(items.get(position));
                }
            });
        }

        public void bind(Videojuego.Contenido item) {
            title.setText(item.external);
            price.setText(String.format("%s€", item.cheapest));
            Glide.with(thumbnail.getContext()).load(item.thumb).into(thumbnail);
        }
    }
}
