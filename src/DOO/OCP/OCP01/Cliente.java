package DOO.OCP.OCP01;

class Cliente {
    public static void main(String[] args) {
        Universidad universidad = new Universidad();
        Alumno alumno = new Alumno("A001", "Carlos García", "carlos@email.com");
        universidad.matricularAlumno(alumno);
    }
}