package com.example.webvideojuegos.Model;

public class JuegoPuntuado {
    public String titulo;
    public String thumb;
    public float puntuacion;

    public JuegoPuntuado(String titulo, String thumb, float puntuacion) {
        this.titulo = titulo;
        this.thumb = thumb;
        this.puntuacion = puntuacion;
    }

    public String getTitulo() {
        return titulo;
    }

    public String getThumb() {
        return thumb;
    }

    public float getPuntuacion() {
        return puntuacion;
    }

    public void setPuntuacion(float puntuacion) {
        this.puntuacion = puntuacion;
    }
}
