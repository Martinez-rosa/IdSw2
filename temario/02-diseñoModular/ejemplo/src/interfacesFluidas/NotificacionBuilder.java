package interfacesFluidas;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class NotificacionBuilder {

    private String titulo;
    private String mensaje;
    private String destinatario;
    private TipoNotificacion tipo = TipoNotificacion.INFORMACION;
    private List<String> etiquetas = new ArrayList<>();
    private boolean urgente = false;
    private LocalDateTime programacion;

    public NotificacionBuilder conTitulo(String titulo) {
        this.titulo = titulo;
        return this;
    }

    public NotificacionBuilder conMensaje(String mensaje) {
        this.mensaje = mensaje;
        return this;
    }

    public NotificacionBuilder para(String email) {
        this.destinatario = email;
        return this;
    }

    public NotificacionBuilder deTipo(TipoNotificacion tipo) {
        this.tipo = tipo;
        return this;
    }

    public NotificacionBuilder conEtiqueta(String etiqueta) {
        this.etiquetas.add(etiqueta);
        return this;
    }

    public NotificacionBuilder urgente() {
        this.urgente = true;
        return this;
    }

    public NotificacionBuilder programarPara(LocalDateTime fechaHora) {
        this.programacion = fechaHora;
        return this;
    }

    public Notificacion construir() {
        assert (titulo == null || mensaje == null || destinatario == null) : "Título, mensaje y destinatario son obligatorios";
        return new Notificacion(titulo, mensaje, destinatario, tipo, etiquetas, urgente, programacion);
    }

    public ResultadoEnvio enviar() {
        Notificacion notificacion = construir();
        ServicioNotificaciones servicio = ServicioNotificaciones.getInstancia();
        return servicio.procesarEnvio(notificacion);
    }

    public enum TipoNotificacion {
        INFORMACION, ADVERTENCIA, ERROR, EXITO
    }
}