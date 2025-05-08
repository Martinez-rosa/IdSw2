package DOO.OCP.OCP04;

public class AlumnoErasmus extends Alumno {
    private String paisOrigen;
    private String universidadOrigen;
    
    public AlumnoErasmus(String id, String nombre, String email, 
                         String paisOrigen, String universidadOrigen) {
        super(id, nombre, email);
        this.paisOrigen = paisOrigen;
        this.universidadOrigen = universidadOrigen;
        this.tasaMatricula = 500.0;
    }
    
    public String getPaisOrigen() {
        return paisOrigen;
    }
    
    public String getUniversidadOrigen() {
        return universidadOrigen;
    }
    
    @Override
    public double calcularTasaMatricula() {
        return tasaMatricula;
    }
    
    @Override
    public String generarInforme() {
        return super.generarInforme() + "\n" +
               "Tipo: Alumno Erasmus\n" +
               "País de origen: " + paisOrigen + "\n" +
               "Universidad de origen: " + universidadOrigen;
    }
}