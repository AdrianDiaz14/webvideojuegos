package com.example.webvideojuegos.Juegoteca;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;
import com.example.webvideojuegos.Model.JuegoPuntuado;

import java.util.ArrayList;
import java.util.List;

public class JuegotecaViewModel extends ViewModel {

    private static MutableLiveData<List<JuegoPuntuado>> juegoteca = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<JuegoPuntuado>> getJuegoteca() {
        return juegoteca;
    }

    // Método para puntuar los juegos de la biblioteca
    public static void puntuarJuego(String titulo, float puntuacion) {
        List<JuegoPuntuado> currentJuegoteca = new ArrayList<>(juegoteca.getValue());
        for (JuegoPuntuado juego : currentJuegoteca) {
            if (juego.getTitulo().equals(titulo)) {
                juego.setPuntuacion(puntuacion);
                break;
            }
        }
        juegoteca.setValue(currentJuegoteca);
    }

}
