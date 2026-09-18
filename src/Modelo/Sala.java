package Modelo;

import java.io.Serializable;

public class Sala implements Serializable {
    private int id;
    private String nombre;
    private static int cantidadSalas = 0;

    public Sala(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;

        cantidadSalas++;
    }

    public void mostrarDatosSala() {
        System.out.println("Id: " + id);
        System.out.println("Nombre: " + nombre);
    }

    public String getNombre()
    {
        return nombre;
    }

    public static int getCantidadSalas()
    {
        return cantidadSalas;
    }
}
