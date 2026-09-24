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

    private class TicketDeAcceso implements Serializable
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
            System.out.println("Enviando ticket " + ticket.idTicket + " al estudiante " + est.getNombre() + " legajo " +
                    est.getLegajo() + ". Corresponde a la actividad: " + act.getTitulo() + ". El ticket fue emitido el " +
                    ticket.fechaEmision);
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
        ticket = new TicketDeAcceso("TK-" + TicketDeAcceso.cantidadTickets, LocalDate.now());
    }

    public void mostrarDatosInscripcion() {
        System.out.println("| Fecha: " + fecha);
        System.out.println("| Estado: " + estado);
        System.out.println("| Actividad: " + act.getTitulo());
        System.out.println("| Datos del estudiante:");
        est.mostrarDatosEstudiante();
    }

    public void enviarTicket()
    {
        if(ticket != null) ticket.enviarTicket();
    }

    public Estudiante getEstudiante()
    {
        return est;
    }
    public String getEstado() { return estado; }
}
