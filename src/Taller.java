public class Taller extends Actividad
{
    private boolean requiereNotebook;

    public Taller(int id, String titulo, int cupoMaximo, boolean requiereNotebook)
    {
        super(id, titulo, cupoMaximo);
        this.requiereNotebook = requiereNotebook;
    }

    public double calcularCostoMateriales()
    {
        if(requiereNotebook)
        {
            return 5000;
        }
        return 2000;
    }

    public String getTipo()
    {
        return "Taller";
    }
}
