package Modelo;

import Modelo.Actividades.*;

import java.io.Serializable;
import java.time.LocalDate;

public class Inscripcion implements Serializable {
    private LocalDate fecha;
    private String estado;
    private final Estudiante est;
    private final Actividad act;
    private TicketDeAcceso ticket;

    private class TicketDeAcceso
    {
        private String idTicket;
        private LocalDate fechaEmision;

        private static int cantidadTickets = 0;

        public TicketDeAcceso(String idTicket, LocalDate fechaEmision)
        {
            this.idTicket = idTicket;
            this.fechaEmision = fechaEmision;

            cantidadTickets++;
        }

        public void enviarTicket()
        {
            //codigo
        }
    }

    public Inscripcion(LocalDate fecha, String estado, Estudiante est, Actividad act) {
        this.fecha = fecha;
        this.estado = estado;
        this.est = est;
        this.act = act;
    }

    public void confirmarInscripcion()
    {
        estado = "Confirmado";
        ticket = new TicketDeAcceso(Integer.toString(TicketDeAcceso.cantidadTickets), LocalDate.now());
    }

    public void mostrarDatosInscripcion() {
        System.out.println("| Fecha: " + fecha);
        System.out.println("| Estado: " + estado);
        System.out.println("| Actividad: " + act.getTitulo());
        System.out.println("| Datos del estudiante:");
        est.mostrarDatosEstudiante();
    }

    public Estudiante getEstudiante()
    {
        return est;
    }
    public String getEstado() { return estado; }
}
