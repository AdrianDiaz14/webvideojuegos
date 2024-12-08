package com.example.webvideojuegos.Juegoteca;

import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.RatingBar;
import android.widget.TextView;
import androidx.annotation.NonNull;
import androidx.recyclerview.widget.RecyclerView;
import com.bumptech.glide.Glide;
import com.example.webvideojuegos.Carrito.CarritoViewModel;
import com.example.webvideojuegos.Model.JuegoPuntuado;
import com.example.webvideojuegos.R;

import java.util.List;

public class JuegotecaAdapter extends RecyclerView.Adapter<JuegotecaAdapter.ViewHolder> {

    private List<JuegoPuntuado> juegoteca;
    private CarritoViewModel carritoViewModel;

    public JuegotecaAdapter(List<JuegoPuntuado> juegoteca, CarritoViewModel carritoViewModel) {
        this.juegoteca = juegoteca;
        this.carritoViewModel = carritoViewModel;
    }

    // Infla cada elemento del recycler con el diseño definido
    @NonNull
    @Override
    public ViewHolder onCreateViewHolder(@NonNull ViewGroup parent, int viewType) {
        View view = LayoutInflater.from(parent.getContext()).inflate(R.layout.item_juegoteca, parent, false);
        return new ViewHolder(view);
    }

    // Vincula los datos a cada vista en función de su posición
    @Override
    public void onBindViewHolder(@NonNull ViewHolder holder, int position) {
        JuegoPuntuado juego = juegoteca.get(position);
        holder.bind(juego);
    }

    // Nº de items en la vista
    @Override
    public int getItemCount() {
        return juegoteca.size();
    }

    public void setJuegoteca(List<JuegoPuntuado> juegoteca) {
        this.juegoteca = juegoteca;
        notifyDataSetChanged();
    }

    public class ViewHolder extends RecyclerView.ViewHolder {

        private ImageView thumbnail;
        private TextView title;
        private RatingBar ratingBar;

        public ViewHolder(@NonNull View itemView) {
            super(itemView);
            thumbnail = itemView.findViewById(R.id.thumbnail);
            title = itemView.findViewById(R.id.title);
            ratingBar = itemView.findViewById(R.id.ratingBar);
        }

        public void bind(JuegoPuntuado juego) {
            title.setText(juego.getTitulo());
            Glide.with(thumbnail.getContext()).load(juego.getThumb()).into(thumbnail);

            ratingBar.setRating(juego.getPuntuacion());

            ratingBar.setOnRatingBarChangeListener((ratingBar, rating, fromUser) -> {
                if (fromUser) {
                    JuegotecaViewModel.puntuarJuego(juego.getTitulo(), rating);
                }
            });
        }
    }
}
