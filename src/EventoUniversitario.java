import java.util.ArrayList;
import java.util.List;

public class EventoUniversitario {
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
            act.mostrarDatosAct();
            act.mostrarIdentificacion();
            act.mostrarInscripciones();
            System.out.println("----------------------------------------");
        }

        System.out.println("=======================================================\n");
    }

    public void asignarSala(Sala sala) {
        this.sala = sala;
    }

    public void crearActividad(int i, String titulo, int cupoMax, String tipo, String disertante)
    {
        if(tipo.equals("Charla"))
        {
            listaActividades.add(new Charla(i, titulo, cupoMax, disertante));
        }
        else
        {
            System.out.println("Error en el tipo");
        }
    }

    public void crearActividad(int i, String titulo, int cupoMax, String tipo, boolean requiereNotebook)
    {
        if(tipo.equals("Taller"))
        {
            listaActividades.add(new Taller(i, titulo, cupoMax, requiereNotebook));
        }
        else
        {
            System.out.println("Error en el tipo");
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

    public static int getCantidadEventos() {
        return cantidadEventos;
    }

    public Actividad getActividad(int i)
    {
        return listaActividades.get(i);
    }
}
