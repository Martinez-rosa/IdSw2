package DOO.OCP.OCP04;

import DOO.OCP.OCP04.SISTEMA.Alumno;
import DOO.OCP.OCP04.SISTEMA.AlumnoErasmus;
import DOO.OCP.OCP04.SISTEMA.AlumnoVirtual;
import DOO.OCP.OCP04.SISTEMA.Universidad;

class Cliente {
    public static void main(String[] args) {

        Alumno alumnoRegular = new Alumno("A001", "Carlos García", "carlos@email.com");        
        AlumnoErasmus alumnoErasmus = new AlumnoErasmus("E001", "Sophie Martin", "sophie@email.com", "Francia", "Universidad de París");
        AlumnoVirtual alumnoVirtual = new AlumnoVirtual("V001", "Elena López", "elena@email.com", "Campus Virtual", false);
        
        Universidad universidad = new Universidad();

        universidad.matricularAlumno(alumnoRegular);
        universidad.matricularAlumno(alumnoErasmus);
        universidad.matricularAlumno(alumnoVirtual);
    }
}