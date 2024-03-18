package sistema.auxiliares;

public class TestCiudadDato {
    private String codigo;
    private String nombre;

    public TestCiudadDato(String codigo) {
        this.codigo = codigo;
    }

    public TestCiudadDato(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public String getCodigo() {
        return new String(codigo);
    }

    public String getNombre() {
        return new String(nombre);
    }

    @Override
    public String toString() {
        return String.format("%s(%s)",codigo,nombre);
    }
}
