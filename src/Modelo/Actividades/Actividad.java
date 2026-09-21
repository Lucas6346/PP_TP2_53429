package Modelo.Actividades;

import Excepciones.CupoExcedidoException;
import Modelo.*;

import java.io.Serializable;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad implements Serializable {
    private int id;
    private String titulo;
    private int cupoMaximo;

    private List<Inscripcion> listaInscripciones;

    public static final int CUPO_MINIMO = 10;
    private static int cantidadActividades = 0;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;

        this.listaInscripciones = new ArrayList<>();

        cantidadActividades++;
    }

    public void inscribir(Estudiante estudiante) throws CupoExcedidoException {
        Inscripcion ins = new Inscripcion(LocalDate.now(), "Registrado", estudiante, this);

        if(listaInscripciones.size() == cupoMaximo) throw new CupoExcedidoException(this);
        listaInscripciones.add(ins);
    }

    public void mostrarInscripciones() {
        System.out.println("======= Inscripciones =======");
        System.out.println("| Hay un total de " + listaInscripciones.size() + " inscripciones");
        System.out.println("| -----");
        for(Inscripcion ins : listaInscripciones)
        {
            ins.mostrarDatosInscripcion();
            System.out.println("| -----");
        }
    }

    public final void mostrarIdentificacion()
    {
        System.out.println("- " + getTipo() + ": " + titulo + " (id=" + id + ")" + " - Cupo máximo: " + cupoMaximo);
    }

    public List<Inscripcion> getListaInscripciones() { return listaInscripciones; }
    public String getTitulo()
    {
        return titulo;
    }
    public int getId()
    {
        return id;
    }
    public static int getCantidadActividades() { return cantidadActividades; }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();
}
