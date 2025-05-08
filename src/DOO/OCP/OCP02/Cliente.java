package DOO.OCP.OCP02;

class Cliente {
    public static void main(String[] args) {
        Universidad universidad = new Universidad();
        
        Alumno alumnoRegular = new Alumno("A001", "Carlos García", "carlos@email.com");
        universidad.matricularAlumno(alumnoRegular);
        
        System.out.println();
        
        AlumnoErasmus alumnoErasmus = new AlumnoErasmus("E001", "Sophie Martin", "sophie@email.com", "Francia", "Universidad de París");
        universidad.matricularAlumno(alumnoErasmus);
    }
}