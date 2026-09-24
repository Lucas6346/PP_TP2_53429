import Hilos.EnvioTicketsThread;
import Modelo.*;
import Excepciones.CupoExcedidoException;
import Modelo.Actividades.*;
import Modelo.Certificacion.Certificable;

import java.util.List;
import java.util.Random;

public class AppSinScanner
{
    public static void main(String[] args)
    {
        //------------- EJ4 -------------
        //a) Estudiantes
        Estudiante est1 = new Estudiante("53000", "Mateo");
        Estudiante est2 = new Estudiante("53001", "Juan");
        Estudiante est3 = new Estudiante("53002", "Pablo");
        Estudiante est4 = new Estudiante("53003", "Pedro");

        //b) Evento con sala y actividades
        EventoUniversitario ev1 = new EventoUniversitario("0", "Evento de Sistemas", 5000, true);

        Sala sala1 = new Sala(0, "Sala Sistemas");
        ev1.asignarSala(sala1);

        ev1.crearActividad(0, "Charla de sistemas", 50, "Charla");
        ev1.crearActividad(1, "Taller de Programación", 200, "Taller");
        ev1.crearActividad(2, "Curso de oratoria", 100, "Curso");

        //c) Inscribir estudiantes
        try
        {
            ev1.getActividad(0).inscribir(est1);
            ev1.getActividad(0).inscribir(est2);

            ev1.getActividad(1).inscribir(est1);
            ev1.getActividad(1).inscribir(est2);
            ev1.getActividad(1).inscribir(est3);
            ev1.getActividad(1).inscribir(est4);

            ev1.getActividad(2).inscribir(est3);
            ev1.getActividad(2).inscribir(est4);
        }
        catch(CupoExcedidoException ex)
        {
            System.out.println("Error, cupo excedido.\nExcepción: " + ex.getMessage());
            System.out.println("Ocurrido en:");
            ex.getActividadAsociada().mostrarIdentificacion();
        }

        //d) Confirmar algunas inscripciones
        //e) Generar tickets de acceso
        Random rnd = new Random();
        for(Actividad actividad : ev1.getListaActividades())
        {
            if(!actividad.getListaInscripciones().isEmpty())
            {
                int num = rnd.nextInt(actividad.getListaInscripciones().size());
                actividad.getListaInscripciones().get(num).confirmarInscripcion();
            }
        }

        //f) Enviar los tickets generados (Hilo)
        //g) Mostrar los datos del evento
        //h) Evidenciar en consola los dos flujos en ejecución
        try
        {
            EnvioTicketsThread hilo = new EnvioTicketsThread(ev1);
            hilo.start();

            System.out.println("[" + Thread.currentThread().getName() + "]: Mostrando datos...");
            ev1.mostrarDatos();
            System.out.println("[" + Thread.currentThread().getName() + "]: Datos mostrados");

            hilo.join();
        }
        catch (InterruptedException e)
        {
            System.out.println("Error de interrupción de hilos");
            System.out.println(e.getMessage());
        }

        //------------- FIN EJ4 -------------

        //------------- EJ1 -------------

        /*
        //Serializar eventos
        EventoUniversitario ev1_copia = null;
        if(ev1.SerializarEvento("ev1.dat"))
        {
            ev1_copia = EventoUniversitario.LeerEvento("ev1.dat");

            System.out.println("\nDatos del evento serializado:");
            ev1_copia.mostrarDatos();
        }
        */

        //------------- FIN EJ1 -------------

        //------------- EJ2 -------------

        /*
        //Generar certificados
        est2.guardarCertificado(((Certificable)ev1.getActividad(1)).generarCertificado(est2));
        est3.guardarCertificado(((Certificable)ev1.getActividad(1)).generarCertificado(est3));
        est3.guardarCertificado(((Certificable)ev1.getActividad(2)).generarCertificado(est3));
        */

        /*
        //Mostrar certificados
        System.out.println("--- Certificados de los estudiantes: ---");
        System.out.println(est2.getCertificado(0));
        System.out.println("---");
        System.out.println(est3.getCertificado(0));
        System.out.println("---");
        System.out.println(est3.getCertificado(1));
        System.out.println("----------------------------------------");
        */

        //------------- FIN EJ2 -------------

        //------------- EJ3 -------------

        /*
        //Filtrar lista de actividades por tipo concreto
        //Mostrar total de actividades de cada tipo por evento
        //Costo de materiales correspondiente
        //Filtrado correcto
        System.out.println("\nFiltrando actividades por tipo...");

        List<Charla> charlas = ev1.filtrarActividadesPorTipo(Charla.class);
        List<Taller> talleres = ev1.filtrarActividadesPorTipo(Taller.class);
        List<Curso> cursos = ev1.filtrarActividadesPorTipo(Curso.class);

        System.out.println("Evento: " + ev1.getTitulo());
        System.out.println("Charlas | Total: " + charlas.size() + " | Costo de materiales: $" + ev1.calcularCostoMateriales(charlas));
        App.listarActividades(charlas);
        System.out.println("Talleres | Total: " + talleres.size() + " | Costo de materiales: $" + ev1.calcularCostoMateriales(talleres));
        App.listarActividades(talleres);
        System.out.println("Cursos | Total: " + cursos.size() + " | Costo de materiales: $" + ev1.calcularCostoMateriales(cursos));
        App.listarActividades(cursos);

        System.out.println("\nLista de charlas: " + charlas);
        System.out.println("Lista de talleres: " + talleres);
        System.out.println("Lista de cursos: " + cursos);
        */

        //------------- FIN EJ3 -------------
    }
}
