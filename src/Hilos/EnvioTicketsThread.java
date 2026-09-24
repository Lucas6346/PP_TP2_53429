package Hilos;

import Modelo.Actividades.Actividad;
import Modelo.EventoUniversitario;
import Modelo.Inscripcion;

public class EnvioTicketsThread extends Thread
{
    public EventoUniversitario evento;

    public EnvioTicketsThread(EventoUniversitario evento)
    {
        super("Thread-Tickets");
        this.evento = evento;
    }

    @Override
    public void run()
    {
        System.out.println("\n[" + Thread.currentThread().getName() + "]: Enviando tickets...");

        for(Actividad actividad : evento.getListaActividades())
        {
            for(Inscripcion inscripcion : actividad.getListaInscripciones())
            {
                if(inscripcion.getEstado().equals("Confirmado"))
                {
                    inscripcion.enviarTicket();
                }
            }
        }
        System.out.println("[" + Thread.currentThread().getName() + "]: Tickets enviados correctamente");
    }
}
