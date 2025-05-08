package DOO.OCP.OCP04;

class Cliente {
    public static void main(String[] args) {
        Universidad universidad = new Universidad();
        
        Alumno alumnoRegular = new Alumno("A001", "Carlos García", "carlos@email.com");
        universidad.matricularAlumno(alumnoRegular);
              
        AlumnoErasmus alumnoErasmus = new AlumnoErasmus("E001", "Sophie Martin", "sophie@email.com", "Francia", "Universidad de París");
        universidad.matricularAlumno(alumnoErasmus);
        
        AlumnoVirtual alumnoVirtual = new AlumnoVirtual("V001", "Elena López", "elena@email.com", "Campus Virtual", false);
        universidad.matricularAlumno(alumnoVirtual);
    }
}