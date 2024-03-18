package excepciones;

public class DatosFaltantesException extends Exception {
    public DatosFaltantesException(String mensaje) {
        super(mensaje);
    }
}
