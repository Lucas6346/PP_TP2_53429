import Excepciones.CupoExcedidoException;
import Modelo.*;
import Modelo.Actividades.Actividad;
import Modelo.Certificacion.Certificable;

import java.util.*;

//TODO asignar automaticamente el id de eventos y actividades? (con el static cantidad)

//FIXME revisar forma de asignar salas y actividades

public class App
{
    public static void main(String[] args)
    {
        Scanner scanner = new Scanner(System.in);
        boolean est = true;
        boolean ev = true;
        boolean sala = true;
        boolean act = true;
        boolean ins = true;
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

            //b) Evento
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

            //c) Sala
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

            //d) Actividades
            do
            {
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

                System.out.print("Desea seguir creando actividades (S/N)?: ");
                if(scanner.nextLine().equals("N"))
                {
                    act = false;
                }

            } while(act);

            //e) Inscribir estudiantes
            try
            {
                do
                {
                    int legajo;
                    int idActividad;
                    int idEvento;
                    EventoUniversitario eventoUni;
                    Estudiante estudiante;

                    System.out.println("Ingrese el legajo del estudiante a inscribir: ");
                    listarEstudiantes(listaEstudiantes);
                    System.out.print("Legajo: ");
                    legajo = Integer.parseInt(scanner.nextLine());
                    estudiante = listaEstudiantes.get(dictLegajos.get(legajo));

                    System.out.println("Ingrese el id del evento correspondiente: ");
                    listarEventos(listaEventos);
                    System.out.print("Id: ");
                    idEvento = Integer.parseInt(scanner.nextLine());
                    eventoUni = listaEventos.get(idEvento);

                    System.out.println("Ingrese el id de la actividad correspondiente: ");
                    listarActividades(eventoUni);
                    System.out.print("Id: ");
                    idActividad = Integer.parseInt(scanner.nextLine());
                    eventoUni.getActividad(idActividad).inscribir(estudiante);

                    System.out.print("Desea seguir inscribiendo estudiantes? (S/N): ");
                    if(scanner.nextLine().equals("N"))
                    {
                        ins = false;
                    }

                } while(ins);
            }
            catch(CupoExcedidoException ex)
            {
                System.out.println("Error, cupo excedido.\nExcepción: " + ex.getMessage());
                System.out.println("Ocurrido en:");
                ex.getActividadAsociada().mostrarDatosAct();
            }

            //f) Generar certificados
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

            //g) Mostrar certificados
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

            //h) Mostrar resumen de datos del evento
            for(EventoUniversitario evento : listaEventos)
            {
                evento.mostrarDatos();
            }
            System.out.println("Total de eventos creados: " + EventoUniversitario.getCantidadEventos());

            //Serializar eventos
            for(EventoUniversitario evento : listaEventos)
            {
                if(evento.SerializarEvento(evento.getTitulo() + "- Serializacion.dat"))
                {
                    System.out.println("Evento [" + evento.getTitulo() + "] serializado correctamente.");
                }
            }
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
    public static void listarEstudiantes(List<Estudiante> listaEst)
    {
        for (Estudiante est : listaEst)
        {
            System.out.println(est.getLegajo() + " | " + est.getNombre());
        }
    }
}
