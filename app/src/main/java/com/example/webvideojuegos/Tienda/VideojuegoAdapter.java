package com.example.webvideojuegos.Tienda;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.webvideojuegos.Carrito.CarritoViewModel;
import com.example.webvideojuegos.Model.Videojuego;
import com.example.webvideojuegos.R;

import java.util.ArrayList;
import java.util.List;

public class VideojuegoAdapter extends RecyclerView.Adapter<VideojuegoAdapter.ViewHolder> {

    private List<Videojuego.Contenido> juegos = new ArrayList<>();
    private CarritoViewModel carritoViewModel;

    public VideojuegoAdapter(CarritoViewModel carritoViewModel) {
        this.carritoViewModel = carritoViewModel;
    }

    public void setJuegos(List<Videojuego.Contenido> juegos) {
        this.juegos = juegos;
        notifyDataSetChanged();
    }

    public List<Videojuego.Contenido> getJuegos() {
        return juegos;
    }

    // Infla cada elemento del recycler con el diseño definido
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_videojuego, parent, false);
        return new ViewHolder(view);
    }

    // Vincula los datos a cada vista en función de su posición
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        Videojuego.Contenido juego = juegos.get(position);
        holder.bind(juego);
    }

    // Nº de items en la vista
    @Override
    public int getItemCount() {
        return juegos.size();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView title;
        private TextView price;
        private ImageButton buttonCart;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            price = itemView.findViewById(R.id.price);
            buttonCart = itemView.findViewById(R.id.button_cart);
        }

        public void bind(Videojuego.Contenido juego) {
            title.setText(juego.external);
            price.setText(String.format("%s€", juego.cheapest));
            Glide.with(thumbnail.getContext()).load(juego.thumb).into(thumbnail);
            buttonCart.setOnClickListener(v -> {
                carritoViewModel.agregarAlCarrito(juego);
                Toast.makeText(itemView.getContext(), "Añadido al carrito", Toast.LENGTH_SHORT).show();
            });
        }
    }
}
