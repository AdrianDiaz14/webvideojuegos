package com.example.webvideojuegos.Carrito;

import android.content.Context;

import androidx.lifecycle.LiveData;
import androidx.lifecycle.MutableLiveData;
import androidx.lifecycle.ViewModel;

import com.example.webvideojuegos.Model.Compra;
import com.example.webvideojuegos.Model.JuegoPuntuado;
import com.example.webvideojuegos.Model.Videojuego;

import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class CarritoViewModel extends ViewModel {

    // Live Data para que otros Fragments puedan observar los cambios
    private MutableLiveData<List<Videojuego.Contenido>> carrito = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<Compra>> historialCompras = new MutableLiveData<>(new ArrayList<>());
    private MutableLiveData<List<JuegoPuntuado>> juegoteca = new MutableLiveData<>(new ArrayList<>());

    public LiveData<List<Videojuego.Contenido>> getCarritoItems() {
        return carrito;
    }

    public LiveData<List<Compra>> getHistorialCompras() {
        return historialCompras;
    }

    public LiveData<List<JuegoPuntuado>> getJuegoteca() {
        return juegoteca;
    }

    // Añade un nuevo item al carrito
    public void agregarAlCarrito(Videojuego.Contenido item) {
        List<Videojuego.Contenido> currentCart = new ArrayList<>(carrito.getValue());
        currentCart.add(item);
        carrito.setValue(currentCart);
    }

    // Elimina un item del carrito
    public void eliminarDelCarrito(Videojuego.Contenido item) {
        List<Videojuego.Contenido> currentCart = new ArrayList<>(carrito.getValue());
        currentCart.remove(item);
        carrito.setValue(currentCart);
    }

    // Vacía todos los juegos del carritoA
    public void vaciarCarrito() {
        carrito.setValue(new ArrayList<>());
    }

    // Método para realizar la compra
    public void realizarCompra(Context context) {
        List<Videojuego.Contenido> currentCart = carrito.getValue();
        if (currentCart != null && !currentCart.isEmpty()) {
            // Asigna un Id a la compra
            int nuevoId = historialCompras.getValue().size() + 1;

            // Utilizar Calendar para obtener la fecha y hora actual
            java.util.Calendar calendar = java.util.Calendar.getInstance();
            String fecha = String.format("%1$tF %1$tT", calendar);

            List<String> juegos = new ArrayList<>();
            double total = 0;

            // Recopila cada nombre de juego comprado y suma el total del precio de todos ellos
            for (Videojuego.Contenido item : currentCart) {
                juegos.add(item.external);
                total += Double.parseDouble(item.cheapest);
            }

            // Instancia un nuevo objeto Compra y guarda en una List todas las compras realizadas
            Compra nuevaCompra = new Compra(nuevoId, fecha, juegos, total);
            List<Compra> currentHistorial = new ArrayList<>(historialCompras.getValue());
            currentHistorial.add(nuevaCompra);
            historialCompras.setValue(currentHistorial);

            // Añadir cada juego a la juegoteca con puntuación inicial 0
            List<JuegoPuntuado> currentJuegoteca = new ArrayList<>(juegoteca.getValue());
            for (Videojuego.Contenido juego : currentCart) {
                currentJuegoteca.add(new JuegoPuntuado(juego.external, juego.thumb, 0));
            }
            juegoteca.setValue(currentJuegoteca);

            vaciarCarrito();

            //guardarCompraEnArchivo(context, nuevaCompra);
        }
    }

    /*
    private void guardarCompraEnArchivo(Context context, Compra compra) {
        File dir = new File(context.getExternalFilesDir(null), "Compras");
        if (!dir.exists()) {
            dir.mkdirs();
        }
        File file = new File(dir, "historial.txt");
        try (FileWriter writer = new FileWriter(file, true)) {
            // 'true' para añadir al final del archivo writer.write("ID: " + compra.getId() + "\n");
            writer.write("Fecha: " + compra.getFecha() + "\n");
            writer.write("Juegos:\n");
            for (String juego : compra.getJuegos()) {
                writer.write("- " + juego + "\n");
            }
            writer.write("Total: " + compra.getTotal() + "€\n");
            writer.write("--------------------\n");
            writer.flush();
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    */
}