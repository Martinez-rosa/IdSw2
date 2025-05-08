package DOO.OCP.OCP01;

import DOO.OCP.OCP01.SISTEMA.Alumno;
import DOO.OCP.OCP01.SISTEMA.Universidad;

class Cliente {
    public static void main(String[] args) {
        
        Alumno alumno = new Alumno("A001", "Carlos García", "carlos@email.com");

        Universidad universidad = new Universidad();
        
        universidad.matricularAlumno(alumno);
    }
}