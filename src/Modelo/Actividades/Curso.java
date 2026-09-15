package Modelo.Actividades;

import Modelo.Certificacion.Certificable;
import Modelo.Estudiante;

public class Curso extends Actividad implements Certificable
{
    private int nivel;

    public Curso(int id, String titulo, int cupoMaximo, int nivel)
    {
        super(id, titulo, cupoMaximo);
        this.nivel = nivel;
    }

    @Override
    public double calcularCostoMateriales()
    {
        return 0;
    }

    @Override
    public String getTipo()
    {
        return "Curso";
    }

    @Override
    public String generarCertificado(Estudiante est)
    {
        String txt = "La " + ENTIDAD_EMISORA + " certifica que el estudiante " + est.getNombre() + ", legajo " +
                est.getLegajo() + " completó el curso: " + getTitulo() + " de nivel " + nivel;

        return txt;
    }
}
