import Excepciones.CupoExcedidoException;
import Modelo.*;

import java.io.IOException;

public class App
{
    public static void main(String[] args)
    {
        try
        {
            //a) Estudiantes
            Estudiante est1 = new Estudiante("53000", "Mateo");
            Estudiante est2 = new Estudiante("53001", "Juan");
            Estudiante est3 = new Estudiante("53002", "Pablo");

            //b) Evento
            EventoUniversitario ev1 = new EventoUniversitario("0", "Evento de Sistemas", 5000, false);

            //c) Sala
            Sala sala1 = new Sala(0, "Sala Sistemas");
            ev1.asignarSala(sala1);

            //d) Actividades
            ev1.crearActividad(0, "Charla de sistemas", 50, "Charla", "Juan Perez");
            ev1.crearActividad(1, "Taller de Programación", 200, "Taller", true);

            //e) Inscribir estudiantes en la charla
            ev1.getActividad(0).inscribir(est1);
            ev1.getActividad(0).inscribir(est2);

            //e) Inscribir estudiantes en el taller
            ev1.getActividad(1).inscribir(est2);
            ev1.getActividad(1).inscribir(est3);

            // Mostrar resumen de datos del evento y total de eventos creados
            //ev1.mostrarDatos();
            System.out.println("Total de eventos creados: " + EventoUniversitario.getCantidadEventos());

            ev1.SerializarEvento("ev1.dat");
            EventoUniversitario ev1_copia = EventoUniversitario.LeerEvento("ev1.dat");

            ev1_copia.mostrarDatos();
        }
        catch(CupoExcedidoException ex)
        {
            System.out.println("Error, cupo excedido.\nExcepción: " + ex.getMessage());
            System.out.println("Ocurrido en:");
            ex.getActividadAsociada().mostrarDatosAct();
        }
        catch(IOException | ClassNotFoundException ex)
        {
            System.out.println(ex.getMessage());
        }
    }
}
