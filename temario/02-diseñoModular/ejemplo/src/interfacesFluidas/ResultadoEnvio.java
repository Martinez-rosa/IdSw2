package interfacesFluidas;
class ResultadoEnvio {
    private final boolean exitoso;
    private final String mensaje;
    
    public ResultadoEnvio(boolean exitoso, String mensaje) {
        this.exitoso = exitoso;
        this.mensaje = mensaje;
    }
    
    public boolean esExitoso() {
        return exitoso;
    }
    
    public String getMensaje() {
        return mensaje;
    }
}