package com.example.webvideojuegos.Model;

import java.util.List;

import retrofit2.Call;
import retrofit2.Retrofit;
import retrofit2.converter.gson.GsonConverterFactory;
import retrofit2.http.GET;
import retrofit2.http.Query;

public class Videojuego {

    public static class Respuesta {
        List<Contenido> results;
    }

    public static class Contenido {
        public String external;
        public String thumb;
        public String cheapest;

        public Contenido(String external, String thumb, String cheapest) {
            this.external = external;
            this.thumb = thumb;
            this.cheapest = cheapest;
        }
    }

    // Llamada a la API
    public static Api api = new Retrofit.Builder()
            .baseUrl("https://www.cheapshark.com/")
            .addConverterFactory(GsonConverterFactory.create())
            .build()
            .create(Api.class);

    public interface Api {
        @GET("api/1.0/games")
        Call<List<Contenido>> buscar(@Query("title") String titulo);
    }
}