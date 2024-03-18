package sistema.auxiliares;

import interfaz.TipoViajero;

import java.util.Arrays;
import java.util.Objects;
import java.util.logging.Logger;

public class TestViajeroDato {
    private String cedula;
    private String nombre;
    private int edad;
    private TipoViajero tipoViajero;

    public TestViajeroDato(String cedula, String nombre, int edad, TipoViajero tipoViajero) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.tipoViajero = tipoViajero;
    }

    public String getCedula() {
        return cedula;
    }

    public String getNombre() {
        return nombre;
    }

    public int getEdad() {
        return edad;
    }

    public TipoViajero getTipoViajero() {
        return tipoViajero;
    }

    @Override
    public String toString() {
        return "{" +
                "cedula='" + cedula + '\'' +
                ", nombre='" + nombre + '\'' +
                ", edad=" + edad +
                ", tipoViajero=" + tipoViajero +
                '}';
    }

    public static TestViajeroDato fromString(String resultado){
        try {
            String[] partes = resultado.split(";");
            return new TestViajeroDato(partes[0],partes[1],Integer.parseInt(partes[2]),obtenerTipoViajero(partes[3]));
        }catch (Exception e){
            Logger.getLogger(TestViajeroDato.class.getCanonicalName()).warning("No pude leer el viajero: '"+resultado+"'");
            return null;
        }
    }

    private static TipoViajero obtenerTipoViajero(String texto) {
        return Arrays.stream(TipoViajero.values()).filter(v->v.getTexto().equalsIgnoreCase(texto)).findFirst().orElse(null);
    }

    public TestViajeroDato copia() {
        return new TestViajeroDato(new String(cedula),new String(nombre),edad,tipoViajero);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        TestViajeroDato that = (TestViajeroDato) o;
        return edad == that.edad && Objects.equals(cedula, that.cedula) && Objects.equals(nombre, that.nombre) && tipoViajero == that.tipoViajero;
    }

    @Override
    public int hashCode() {
        return Objects.hash(cedula, nombre, edad, tipoViajero);
    }
}
