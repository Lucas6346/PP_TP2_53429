package Modelo;

import java.io.Serializable;

public class Estudiante implements Serializable
{
    private String legajo;
    private String nombre;

    public Estudiante(String legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;
    }

    public void mostrarDatosEstudiante() {
        System.out.println("| Legajo: " + legajo);
        System.out.println("| Nombre: " + nombre);
    }

    public String getNombre()
    {
        return nombre;
    }

    public String getLegajo()
    {
        return legajo;
    }
}