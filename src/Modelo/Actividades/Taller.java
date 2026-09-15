package Modelo.Actividades;

import Modelo.Certificacion.Certificable;
import Modelo.Estudiante;

public class Taller extends Actividad implements Certificable
{
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook)
    {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    @Override
    public double calcularCostoMateriales()
    {
        if(requiereNotebook)
        {
            return 5000;
        }
        return 2000;
    }

    @Override
    public String getTipo()
    {
        return "Taller";
    }

    @Override
    public String generarCertificado(Estudiante est)
    {
        String txt = "La " + ENTIDAD_EMISORA + " certifica que el estudiante " + est.getNombre() + ", legajo " +
                est.getLegajo() + " completó el taller: " + getTitulo();

        return txt;
    }
}
