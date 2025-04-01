package interfacesFluidas;
import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.List;

class Notificacion {
    private final String titulo;
    private final String mensaje;
    private final String destinatario;
    private final NotificacionBuilder.TipoNotificacion tipo;
    private final List<String> etiquetas;
    private final boolean urgente;
    private final LocalDateTime programacion;
    
    public Notificacion(String titulo, String mensaje, String destinatario,
                        NotificacionBuilder.TipoNotificacion tipo, List<String> etiquetas,
                        boolean urgente, LocalDateTime programacion) {
        this.titulo = titulo;
        this.mensaje = mensaje;
        this.destinatario = destinatario;
        this.tipo = tipo;
        this.etiquetas = new ArrayList<>(etiquetas);
        this.urgente = urgente;
        this.programacion = programacion;
    }
    
    @Override
    public String toString() {
        return "Notificacion{" +
                "titulo='" + titulo + '\'' +
                ", destinatario='" + destinatario + '\'' +
                ", tipo=" + tipo +
                ", urgente=" + urgente +
                ", programacion=" + programacion +
                '}';
    }
    
    public String getTitulo() {
        return titulo;
    }
    
    public String getMensaje() {
        return mensaje;
    }
    
    public String getDestinatario() {
        return destinatario;
    }
    
    public NotificacionBuilder.TipoNotificacion getTipo() {
        return tipo;
    }
    
    public List<String> getEtiquetas() {
        return new ArrayList<>(etiquetas);
    }
    
    public boolean isUrgente() {
        return urgente;
    }
    
    public LocalDateTime getProgramacion() {
        return programacion;
    }
}
