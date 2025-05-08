package DOO.OCP.OCP02;

import DOO.OCP.OCP02.SISTEMA.Alumno;
import DOO.OCP.OCP02.SISTEMA.AlumnoErasmus;
import DOO.OCP.OCP02.SISTEMA.Universidad;

class Cliente {
    public static void main(String[] args) {

        Alumno alumnoRegular = new Alumno("A001", "Carlos García", "carlos@email.com");
        AlumnoErasmus alumnoErasmus = new AlumnoErasmus("E001", "Sophie Martin", "sophie@email.com", "Francia", "Universidad de París");
        
        Universidad universidad = new Universidad();
        
        universidad.matricularAlumno(alumnoRegular);
        universidad.matricularAlumno(alumnoErasmus);
    }
}