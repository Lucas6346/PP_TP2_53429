import Excepciones.CupoExcedidoException;
import Modelo.*;
import Modelo.Certificacion.Certificable;

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
            ev1.crearActividad(0, "Charla de sistemas", 50, "Charla");
            ev1.crearActividad(1, "Taller de Programación", 200, "Taller");
            ev1.crearActividad(2, "Curso de oratoria", 100, "Curso");

            //e) Inscribir estudiantes
            ev1.getActividad(0).inscribir(est1);
            ev1.getActividad(0).inscribir(est2);

            ev1.getActividad(1).inscribir(est2);
            ev1.getActividad(1).inscribir(est3);

            ev1.getActividad(2).inscribir(est3);

            //f) Generar certificados
            est2.guardarCertificado(((Certificable)ev1.getActividad(1)).generarCertificado(est2));
            est3.guardarCertificado(((Certificable)ev1.getActividad(1)).generarCertificado(est3));
            est3.guardarCertificado(((Certificable)ev1.getActividad(2)).generarCertificado(est3));

            //g) Mostrar certificados
            System.out.println("--- Certificados de los estudiantes: ---");
            System.out.println(est2.getCertificado(0));
            System.out.println("---");
            System.out.println(est3.getCertificado(0));
            System.out.println("---");
            System.out.println(est3.getCertificado(1));
            System.out.println("----------------------------------------");

            //h) Mostrar resumen de datos del evento
            ev1.mostrarDatos();

            //Serializar eventos
            EventoUniversitario ev1_copia = null;
            if(ev1.SerializarEvento("ev1.dat"))
            {
                ev1_copia = EventoUniversitario.LeerEvento("ev1.dat");
                ev1_copia.mostrarDatos();
            }

            System.out.println("Total de eventos creados: " + EventoUniversitario.getCantidadEventos());
        }
        catch(CupoExcedidoException ex)
        {
            System.out.println("Error, cupo excedido.\nExcepción: " + ex.getMessage());
            System.out.println("Ocurrido en:");
            ex.getActividadAsociada().mostrarDatosAct();
        }
    }
}
