import java.time.LocalDate;

public class Inscripcion {
    private LocalDate fecha;
    private String estado;
    private final Estudiante est;
    private final Actividad act;

    public Inscripcion(LocalDate fecha, String estado, Estudiante est, Actividad act) {
        this.fecha = fecha;
        this.estado = estado;
        this.est = est;
        this.act = act;
    }

    public void mostrarDatosInscripcion() {
        System.out.println("| Fecha: " + fecha);
        System.out.println("| Estado: " + estado);
        System.out.println("| Datos del estudiante:");
        est.mostrarDatosEstudiante();
    }
}
