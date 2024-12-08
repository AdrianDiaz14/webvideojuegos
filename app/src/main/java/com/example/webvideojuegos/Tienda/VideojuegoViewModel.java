package com.example.webvideojuegos.Tienda;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.webvideojuegos.Model.Videojuego;

import java.util.List;

import retrofit2.Call;
import retrofit2.Callback;
import retrofit2.Response;

public class VideojuegoViewModel extends ViewModel {

    private MutableLiveData<List<Videojuego.Contenido>> juegos;
    private MutableLiveData<Boolean> isLoading = new MutableLiveData<>();

    public LiveData<List<Videojuego.Contenido>> getJuegos() {
        if (juegos == null) {
            juegos = new MutableLiveData<>();
        }
        return juegos;
    }

    public LiveData<Boolean> getLoadingState() {
        return isLoading;
    }

    // Maneja la lógica para buscar los juegos llamando a la API
    public void buscarJuegos(String titulo) {
        isLoading.setValue(true);
        Videojuego.api.buscar(titulo).enqueue(new Callback<List<Videojuego.Contenido>>() {
            @Override
            public void onResponse(Call<List<Videojuego.Contenido>> call, Response<List<Videojuego.Contenido>> response) {
                if (response.isSuccessful()) {
                    juegos.setValue(response.body());
                }
                isLoading.setValue(false);
            }

            @Override
            public void onFailure(Call<List<Videojuego.Contenido>> call, Throwable t) {
                isLoading.setValue(false);
            }
        });
    }
}
