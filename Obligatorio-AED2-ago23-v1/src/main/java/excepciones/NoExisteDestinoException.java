package excepciones;

public class NoExisteDestinoException extends Exception{
    public NoExisteDestinoException(String mensaje) {
        super(mensaje);
    }
}