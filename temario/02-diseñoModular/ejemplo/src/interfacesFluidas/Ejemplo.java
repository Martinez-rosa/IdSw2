package interfacesFluidas;
import java.time.LocalDateTime;

public class Ejemplo {
    public static void main(String[] args) {
        
        ResultadoEnvio resultado = new NotificacionBuilder()
            .conTitulo("Bienvenido a nuestra plataforma")
            .conMensaje("Gracias por registrarte. Confirma tu cuenta haciendo clic en el enlace.")
            .para("usuario@ejemplo.com")
            .deTipo(NotificacionBuilder.TipoNotificacion.INFORMACION)
            .conEtiqueta("registro")
            .enviar();
            
        System.out.println("Resultado del envío: " + resultado.esExitoso());
        
        Notificacion recordatorio = new NotificacionBuilder()
            .conTitulo("Recordatorio: Reunión importante")
            .conMensaje("No olvides la reunión de planificación de mañana a las 10:00")
            .para("equipo@empresa.com")
            .deTipo(NotificacionBuilder.TipoNotificacion.ADVERTENCIA)
            .urgente()
            .conEtiqueta("reunión")
            .conEtiqueta("planificación")
            .programarPara(LocalDateTime.now().plusDays(1).minusHours(2))
            .construir();
            
        System.out.println("Notificación creada: " + recordatorio);
    }
}