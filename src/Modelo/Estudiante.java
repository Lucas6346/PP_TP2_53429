package Modelo;

import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

public class Estudiante implements Serializable
{
    private String legajo;
    private String nombre;
    private List<String> listaCertificados;

    public Estudiante(String legajo, String nombre) {
        this.legajo = legajo;
        this.nombre = nombre;

        listaCertificados = new ArrayList<>();
    }

    public void mostrarDatosEstudiante() {
        System.out.println("| Legajo: " + legajo);
        System.out.println("| Nombre: " + nombre);
    }

    public void guardarCertificado(String certificado)
    {
        listaCertificados.add(certificado);
    }

    public String getCertificado(int i)
    {
        return listaCertificados.get(i);
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
