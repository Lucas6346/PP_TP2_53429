package Modelo;

import Modelo.Actividades.*;

import java.io.*;
import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

//FIXME:
//  -cambiar formas de mostrar los datos implementando getters en las clases necesarias en lugar de submetodos mostrar
//  -en mostrar datos, tambien mostrar datos propios de cada subclase actividad (disertante, nivel, ...)

public class EventoUniversitario implements Serializable {
    private final String id;
    private String titulo;
    private double costoBase;
    private boolean gratuito;

    private Sala sala;
    private List<Actividad> listaActividades;

    private static int cantidadEventos = 0;

    public EventoUniversitario(String id, String titulo, double costoBase, boolean gratuito) {
        this.id = id;
        this.titulo = titulo;
        this.costoBase = costoBase;
        this.gratuito = gratuito;

        this.listaActividades = new ArrayList<>();

        cantidadEventos++;
    }

    public EventoUniversitario(EventoUniversitario otro) {
        this.id = otro.id;
        this.titulo = otro.titulo;
        this.costoBase = otro.costoBase;
        this.gratuito = otro.gratuito;
        this.sala = otro.sala;

        this.listaActividades = new ArrayList<>(otro.listaActividades);

        cantidadEventos++;
    }

    public void mostrarDatos() {

        String strEsGratis = "No";
        System.out.println("============== Mostrando datos de evento ==============");

        System.out.println("Id del evento: " + id);
        System.out.println("Título: " + titulo);
        System.out.println("Costo estimado: " + calcularCostoEstimado() + "$");

        if (gratuito) {
            strEsGratis = "Sí";
        }
        System.out.println("¿Es gratuito?: " + strEsGratis);

        System.out.println("-------------------- Sala asignada --------------------");
        sala.mostrarDatosSala();

        System.out.println("--------------------- Actividades ---------------------");

        for(Actividad act : listaActividades)
        {
            act.mostrarIdentificacion();
            act.mostrarInscripciones();
            System.out.println("----------------------------------------");
        }

        System.out.println("=======================================================\n");
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void crearActividad(int i, String titulo, int cupoMax, String tipoActividad)
    {
        Scanner scanner = new Scanner(System.in);

        if(tipoActividad.equals("Charla"))
        {
            System.out.print("Ingrese el nombre del disertante: ");
            String disertante = scanner.nextLine();
            listaActividades.add(new Charla(i, titulo, cupoMax, disertante));
        }
        else if(tipoActividad.equals("Taller"))
        {
            boolean requiereNotebook = false;

            System.out.print("Requiere notebook? (S/N): ");
            if(scanner.nextLine().equals("S"))
            {
                requiereNotebook = true;
            }

            listaActividades.add(new Taller(i, titulo, cupoMax, requiereNotebook));
        }
        else if(tipoActividad.equals("Curso"))
        {
            System.out.print("Ingrese el nivel del curso: ");
            int nivel = Integer.parseInt(scanner.nextLine());
            listaActividades.add(new Curso(i, titulo, cupoMax, nivel));
        }
    }

    public double calcularCostoEstimado()
    {
        double res = costoBase;

        if(gratuito)
        {
            return 0;
        }

        for(Actividad act : listaActividades)
        {
            res += act.calcularCostoMateriales();
        }

        return res * 1.21;
    }

    public boolean SerializarEvento(String name)
    {
        try
        {
            FileOutputStream fos = new FileOutputStream(name);
            ObjectOutputStream oos = new ObjectOutputStream(fos);
            oos.writeObject(this);
            oos.close();

            return true;
        }
        catch (IOException ex)
        {
            System.out.println("Error al serializar evento:");
            System.out.println(ex.getMessage());

            return false;
        }
    }

    public static EventoUniversitario LeerEvento(String path)
    {
        EventoUniversitario ev = null;

        try
        {
            FileInputStream fis = new FileInputStream(path);
            ObjectInputStream ois = new ObjectInputStream(fis);
            ev = (EventoUniversitario) ois.readObject();
            ois.close();
        }
        catch (IOException ex)
        {
            System.out.println("Error al serializar evento:");
            System.out.println(ex.getMessage());
        }
        catch (ClassNotFoundException ex)
        {
            System.out.println("Error al serializar evento, clase no encontrada:");
            System.out.println(ex.getMessage());
        }

        return ev;
    }

    public <T extends Actividad> List<T> filtrarActividesPorTipo(Class<T> tipo)
    {
        List<T> lista = new ArrayList<>();

        for(Actividad act : listaActividades)
        {
            if(tipo.isInstance(act))
            {
                lista.add(tipo.cast(act));
            }
        }

        return lista;
    }

    public double calcularCostoMateriales(List<? extends Actividad> actividades)
    {
        double total = 0;

        for (int i = 0; i < actividades.size(); i++)
        {
            total += actividades.get(i).calcularCostoMateriales();
        }

        return total;
    }

    public String getId()
    {
        return id;
    }
    public String getTitulo()
    {
        return titulo;
    }
    public Actividad getActividad(int i)
    {
        return listaActividades.get(i);
    }
    public List<Actividad> getListaActividades()
    {
        return listaActividades;
    }

    public static int getCantidadEventos() {
        return cantidadEventos;
    }
}
