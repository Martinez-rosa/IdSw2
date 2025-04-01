package interfacesFluidas;
class ServicioNotificaciones {
    private static final ServicioNotificaciones INSTANCIA = new ServicioNotificaciones();
    
    private ServicioNotificaciones() {}
    
    public static ServicioNotificaciones getInstancia() {
        return INSTANCIA;
    }
    
    public ResultadoEnvio procesarEnvio(Notificacion notificacion) {
        System.out.println("Enviando notificación: " + notificacion.getTitulo() + 
                          " para " + notificacion.getDestinatario());
        
        return new ResultadoEnvio(true, "Notificación enviada con éxito");
    }
}