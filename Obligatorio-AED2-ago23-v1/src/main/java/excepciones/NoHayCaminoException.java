package excepciones;

public class NoHayCaminoException extends Exception {
    public NoHayCaminoException(String mensaje) {
        super(mensaje);
    }
}
