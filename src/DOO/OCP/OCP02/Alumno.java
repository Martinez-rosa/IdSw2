package DOO.OCP.OCP02;

class Alumno {
    private String id;
    private String nombre;
    private String email;
    protected double tasaMatricula = 1000.0;
    
    public Alumno(String id, String nombre, String email) {
        this.id = id;
        this.nombre = nombre;
        this.email = email;
    }
    
    public String getId() {
        return id;
    }
    
    public String getNombre() {
        return nombre;
    }
    
    public String getEmail() {
        return email;
    }
    
    public double calcularTasaMatricula() {
        return tasaMatricula;
    }
    
    public String generarInforme() {
        return "Alumno: " + nombre + " (ID: " + id + ")\n" +
               "Email: " + email + "\n" +
               "Tasa de matrícula: " + calcularTasaMatricula() + " €";
    }
}
