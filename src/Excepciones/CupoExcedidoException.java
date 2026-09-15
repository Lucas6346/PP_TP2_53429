package Excepciones;

import Modelo.Actividades.Actividad;

public class CupoExcedidoException extends Exception
{
    private Actividad actAsociada;

    public CupoExcedidoException(Actividad actAsociada)
    {
        super();
        this.actAsociada = actAsociada;
    }

    public Actividad getActividadAsociada()
    {
        return actAsociada;
    }
}
