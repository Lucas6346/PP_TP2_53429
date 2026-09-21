import Excepciones.CupoExcedidoException;
import Modelo.*;
import Modelo.Actividades.*;
import Modelo.Certificacion.Certificable;

import java.util.*;

//TODO asignar automaticamente el id de eventos y actividades? (con el static cantidad)

//FIXME
//  revisar forma de asignar salas y actividades
//  mejorar manejo de errores

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

            //c) Inscribir estudiantes
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

                try
                {
                    System.out.println("Ingrese el id de la actividad correspondiente: ");
                    listarActividades(eventoUni);
                    System.out.print("Id: ");
                    idActividad = Integer.parseInt(scanner.nextLine());
                    eventoUni.getActividad(idActividad).inscribir(estudiante);
                }
                catch(CupoExcedidoException ex)
                {
                    System.out.println("Error, cupo excedido.\nExcepción: " + ex.getMessage());
                    System.out.println("Ocurrido en:");
                    ex.getActividadAsociada().mostrarDatosAct();
                }

                System.out.print("Desea seguir inscribiendo estudiantes? (S/N): ");
                if(scanner.nextLine().equals("N"))
                {
                    ins = false;
                }

            } while(ins);


            //d) Filtrar lista de actividades por tipo concreto
            System.out.println("\nFiltrando actividades por tipo...");
            for(EventoUniversitario evento : listaEventos)
            {
                List<Charla> charlas = evento.filtrarActividesPorTipo(Charla.class);
                List<Taller> talleres = evento.filtrarActividesPorTipo(Taller.class);
                List<Curso> cursos = evento.filtrarActividesPorTipo(Curso.class);

                System.out.println("Evento: " + evento.getTitulo());
                System.out.println("Charlas: ");
                listarActividades(charlas);
                System.out.println("Talleres: ");
                listarActividades(talleres);
                System.out.println("Cursos: ");
                listarActividades(cursos);
            }

            //e) Mostrar cantidad de actividades por cada tipo

            //f) Costo de materiales correspondiente

            //g) Filtrado correcto???

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
