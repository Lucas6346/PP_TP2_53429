package Excepciones;

import Modelo.Actividades.Actividad;

public class CupoExcedidoException extends Exception
{
    private Actividad actAsociada;

    public CupoExcedidoException(Actividad actAsociada)
    {
        super("La actividad " + actAsociada.getTitulo() + "alcanzó su cupo máximo");
        this.actAsociada = actAsociada;
    }

    public Actividad getActividadAsociada()
    {
        return actAsociada;
    }
}
