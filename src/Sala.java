public class Sala {
    private int id;
    private String nombre;

    public Sala(int id, String nombre) {
        this.id = id;
        this.nombre = nombre;
    }

    public void mostrarDatosSala() {
        System.out.println("Id: " + id);
        System.out.println("Nombre: " + nombre);
    }
}
