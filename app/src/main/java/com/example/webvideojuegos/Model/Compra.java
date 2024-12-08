package com.example.webvideojuegos.Model;

import java.util.List;

public class Compra {

    public int id;
    public String fecha;
    public List<String> juegos;
    public double total;

    public Compra(int id, String fecha, List<String> juegos, double total) {
        this.id = id;
        this.fecha = fecha;
        this.juegos = juegos;
        this.total = total;
    }

    public int getId() {
        return id;
    }

    public String getFecha() {
        return fecha;
    }

    public List<String> getJuegos() {
        return juegos;
    }

    public double getTotal() {
        return total;
    }
}
