package DOO.OCP.OCP03;

public class AlumnoVirtual extends Alumno {
    private String plataformaVirtual;
    private boolean requiereExamenesPresenciales;
    
    public AlumnoVirtual(String id, String nombre, String email, 
                         String plataformaVirtual, boolean requiereExamenesPresenciales) {
        super(id, nombre, email);
        this.plataformaVirtual = plataformaVirtual;
        this.requiereExamenesPresenciales = requiereExamenesPresenciales;
        this.tasaMatricula = 800.0;
    }
    
    public String getPlataformaVirtual() {
        return plataformaVirtual;
    }
    
    public boolean requiereExamenesPresenciales() {
        return requiereExamenesPresenciales;
    }
    
    @Override
    public double calcularTasaMatricula() {
        double tasa = tasaMatricula;
        
        if (!requiereExamenesPresenciales) {
            tasa = tasa - 100.0;
        }
        
        return tasa;
    }
    
    @Override
    public String generarInforme() {
        return super.generarInforme() + "\n" +
               "Tipo: Alumno Virtual\n" +
               "Plataforma virtual: " + plataformaVirtual + "\n" +
               "Requiere exámenes presenciales: " + (requiereExamenesPresenciales ? "Sí" : "No");
    }
}