import Excepciones.CupoExcedidoException;
import Hilos.EnvioTicketsThread;
import Modelo.*;
import Modelo.Actividades.*;
import Modelo.Certificacion.Certificable;

import java.util.*;

//FIXME
//  asignar automaticamente el id de eventos y actividades? (con el static cantidad)
//  revisar forma de asignar salas
//  mejorar manejo de errores

public class App
{
    public static void main(String[] args)
    {
        //------------- EJ4 -------------
        Scanner scanner = new Scanner(System.in);
        boolean est = true;
        boolean ev = true;
        boolean sala = true;
        boolean act = true;
        boolean ins;
        List<Estudiante> listaEstudiantes = new ArrayList<>();
        HashMap<Integer, Integer> dictLegajos = new HashMap<>();
        List<EventoUniversitario> listaEventos = new ArrayList<>();
        List<Sala> listaSalas = new ArrayList<>();

        //a) Estudiantes
        try
        {
            do
            {
                String nombre;
                String legajo;

                System.out.print("Ingrese nombre del estudiante: ");
                nombre = scanner.nextLine();

                System.out.print("Ingrese legajo del estudiante: ");
                legajo = scanner.nextLine();
                dictLegajos.put(Integer.parseInt(legajo), Estudiante.getCantidadEstudiantes());

                listaEstudiantes.add(new Estudiante(legajo, nombre));

                System.out.print("Desea agregar otro estudiante? (S/N): ");
                if(scanner.nextLine().equals("N"))
                {
                    est = false;
                }
            } while(est);

            //b) Eventos
            do
            {
                String nombre;
                int costoBase;
                boolean gratuito = true;

                System.out.print("Ingrese nombre del evento: ");
                nombre = scanner.nextLine();
                System.out.print("Ingrese el costo base: ");
                costoBase = Integer.parseInt(scanner.nextLine());
                System.out.print("Es gratuito? (S/N): ");
                if(scanner.nextLine().equals("N"))
                {
                    gratuito = false;
                }
                listaEventos.add(new EventoUniversitario(Integer.toString(EventoUniversitario.getCantidadEventos()), nombre, costoBase, gratuito));

                System.out.print("Desea crear otro evento? (S/N): ");
                if(scanner.nextLine().equals("N"))
                {
                    ev = false;
                }
            } while(ev);

            //b) Salas
            do
            {
                int id;
                String nombre;

                System.out.print("Ingrese el nombre de sala: ");
                nombre = scanner.nextLine();
                listaSalas.add(new Sala(Sala.getCantidadSalas(), nombre));

                System.out.println("Ingrese el id del evento al cual asignar la sala: ");
                listarEventos(listaEventos);
                System.out.print("Id: ");
                id = Integer.parseInt(scanner.nextLine());
                listaEventos.get(id).asignarSala(listaSalas.get(Sala.getCantidadSalas() - 1));

                System.out.print("Desea seguir asignando salas? (S/N): ");
                if(scanner.nextLine().equals("N"))
                {
                    sala = false;
                }
            } while(sala);

            //b) Actividades
            do
            {
                ins = true;

                int id;
                String nombre;
                int cupoMaximo;
                String tipo;

                System.out.print("Ingrese nombre de actividad: ");
                nombre = scanner.nextLine();
                System.out.print("Ingrese cupo máximo: ");
                cupoMaximo = Integer.parseInt(scanner.nextLine());
                System.out.print("Ingrese el tipo de actividad (Charla | Taller | Curso): ");
                tipo = scanner.nextLine();

                System.out.println("Ingrese el id del evento al que pertenecerá la actividad: ");
                listarEventos(listaEventos);
                System.out.print("Id: ");
                id = Integer.parseInt(scanner.nextLine());
                listaEventos.get(id).crearActividad(Actividad.getCantidadActividades(), nombre, cupoMaximo, tipo);

                //c) Inscribir estudiantes
                do
                {
                    int legajo;
                    Estudiante estudiante;

                    System.out.println("\nIngrese el legajo del estudiante a inscribir a: " + nombre);
                    listarEstudiantes(listaEstudiantes);
                    System.out.print("Legajo: ");
                    legajo = Integer.parseInt(scanner.nextLine());
                    estudiante = listaEstudiantes.get(dictLegajos.get(legajo));

                    try
                    {
                        listaEventos.get(id).getActividad(Actividad.getCantidadActividades() - 1).inscribir(estudiante);
                    }
                    catch(CupoExcedidoException ex)
                    {
                        System.out.println("Error, cupo excedido.\nExcepción: " + ex.getMessage());
                        System.out.println("Ocurrido en:");
                        ex.getActividadAsociada().mostrarIdentificacion();
                    }

                    System.out.print("Desea seguir inscribiendo estudiantes? (S/N): ");
                    if(scanner.nextLine().equals("N"))
                    {
                        ins = false;
                    }
                } while (ins);

                System.out.print("Desea seguir creando actividades (S/N)?: ");
                if(scanner.nextLine().equals("N"))
                {
                    act = false;
                }

            } while(act);


            //d) Confirmar algunas inscripciones
            //e) Generar tickets de acceso
            Random rnd = new Random();
            for(EventoUniversitario evento : listaEventos)
            {
                for(Actividad actividad : evento.getListaActividades())
                {
                    if(!actividad.getListaInscripciones().isEmpty())
                    {
                        int num = rnd.nextInt(actividad.getListaInscripciones().size());
                        actividad.getListaInscripciones().get(num).confirmarInscripcion();
                    }
                }
            }


            //f) Enviar los tickets generados (Hilo)
            //g) Mostrar los datos del evento
            //h) Evidenciar en consola los dos flujos en ejecución
            try
            {
                for(EventoUniversitario evento : listaEventos)
                {
                    EnvioTicketsThread hilo = new EnvioTicketsThread(evento);
                    hilo.start();

                    System.out.println("[" + Thread.currentThread().getName() + "]: Mostrando datos...");
                    evento.mostrarDatos();
                    System.out.println("[" + Thread.currentThread().getName() + "]: Datos mostrados");
                    hilo.join();
                }
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
            for(EventoUniversitario evento : listaEventos)
            {
                if(evento.SerializarEvento(evento.getTitulo() + "- Serializacion.dat"))
                {
                    System.out.println("Evento [" + evento.getTitulo() + "] serializado correctamente.");
                }
            }
            */
            //------------- FIN EJ1 -------------

            //------------- EJ2 -------------
            /*
            //Generar certificados
            for(EventoUniversitario evento : listaEventos)
            {
                for(Actividad actividad : evento.getListaActividades())
                {
                    if(actividad instanceof Certificable)
                    {
                        for(Inscripcion inscripcion : actividad.getListaInscripciones())
                        {
                            String certificado = ((Certificable) evento).generarCertificado(inscripcion.getEstudiante());
                            inscripcion.getEstudiante().guardarCertificado(certificado);
                        }
                    }
                }
            }

            //Mostrar certificados
            System.out.println("===== Mostrando Certificados =====");
            for(Estudiante estudiante : listaEstudiantes)
            {
                System.out.println("===== Certificado/s del estudiante: " + estudiante.getNombre());
                for(String certificado : estudiante.getListaCertificados())
                {
                    System.out.println(certificado);
                    System.out.println("-----");
                }
            }
            */
            //------------- FIN EJ2 -------------

            //------------- EJ3 -------------
            /*
            //Filtrar lista de actividades por tipo concreto
            //Mostrar total de actividades de cada tipo por evento
            //Costo de materiales correspondiente
            //Filtrado correcto
            System.out.println("\nFiltrando actividades por tipo...");
            for(EventoUniversitario evento : listaEventos)
            {
                List<Charla> charlas = evento.filtrarActividesPorTipo(Charla.class);
                List<Taller> talleres = evento.filtrarActividesPorTipo(Taller.class);
                List<Curso> cursos = evento.filtrarActividesPorTipo(Curso.class);

                System.out.println("Evento: " + evento.getTitulo());
                System.out.println("Charlas | Total: " + charlas.size() + " | Costo de materiales: $" + evento.calcularCostoMateriales(charlas));
                listarActividades(charlas);
                System.out.println("Talleres | Total: " + talleres.size() + " | Costo de materiales: $" + evento.calcularCostoMateriales(talleres));
                listarActividades(talleres);
                System.out.println("Cursos | Total: " + cursos.size() + " | Costo de materiales: $" + evento.calcularCostoMateriales(cursos));
                listarActividades(cursos);

                System.out.println("\nLista de charlas: " + charlas);
                System.out.println("Lista de talleres: " + talleres);
                System.out.println("Lista de cursos: " + cursos);
            }
            */
            //------------- FIN EJ3 -------------
        }
        catch (Exception e)
        {
            System.out.println(e.getMessage());
        }
    }
    public static void listarEventos(List<EventoUniversitario> listaEv)
    {
        for (EventoUniversitario evento : listaEv)
        {
            System.out.println(evento.getId() + " | " + evento.getTitulo());
        }
    }
    public static void listarActividades(EventoUniversitario ev)
    {
        for (Actividad act : ev.getListaActividades())
        {
            System.out.println(act.getId() + " | " + act.getTitulo());
        }
    }
    public static <T extends Actividad> void  listarActividades(List<T> lista)
    {
        for(T act : lista)
        {
            System.out.println(act.getId() + " | " + act.getTitulo());
        }
    }
    public static void listarEstudiantes(List<Estudiante> listaEst)
    {
        for (Estudiante est : listaEst)
        {
            System.out.println(est.getLegajo() + " | " + est.getNombre());
        }
    }
}
