import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

public abstract class Actividad {
    private int id;
    private String titulo;
    private int cupoMaximo;
    public static final int CUPO_MINIMO = 10;

    private List<Inscripcion> listaInscripciones;

    public Actividad(int id, String titulo, int cupoMaximo) {
        this.id = id;
        this.titulo = titulo;
        this.cupoMaximo = cupoMaximo;

        this.listaInscripciones = new ArrayList<>();
    }

    public void inscribir(Estudiante estudiante) {
        Inscripcion ins = new Inscripcion(LocalDate.now(), "Inscripto", estudiante, this);
        listaInscripciones.add(ins);
    }

    public void mostrarInscripciones() {
        System.out.println("======= Inscripciones =======");
        System.out.println("| Hay un total de " + listaInscripciones.size() + " inscripciones");
        System.out.println("| -----");
        for(Inscripcion ins : listaInscripciones)
        {
            ins.mostrarDatosInscripcion();
            System.out.println("| -----");
        }
    }

    public void mostrarDatosAct() {
        System.out.println("| Actividad: " + titulo);
        System.out.println("| ID de actividad: " + id);
        System.out.println("| Cupo máximo: " + cupoMaximo);
    }

    public final void mostrarIdentificacion()
    {
        System.out.println("| Esta actividad es de tipo: " + this.getTipo());
    }

    public abstract double calcularCostoMateriales();
    public abstract String getTipo();
}
