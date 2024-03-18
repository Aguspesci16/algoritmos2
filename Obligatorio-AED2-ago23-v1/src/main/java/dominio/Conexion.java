package dominio;
import excepciones.DatoNullOVacioException;
import excepciones.DatosMenoresACeroException;
import interfaz.*;

import java.util.Objects;


public class Conexion implements Comparable<Conexion> {
    private String codigoCiudadOrigen;
    private String codigoCiudadDestino;
    private int identificadorConexion;
    private double costo;
    private double tiempo;
    private TipoConexion tipo;

    public Conexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        this.codigoCiudadOrigen = codigoCiudadOrigen;
        this.codigoCiudadDestino = codigoCiudadDestino;
        this.identificadorConexion = identificadorConexion;
        this.costo = costo;
        this.tiempo = tiempo;
        this.tipo = tipo;
    }

    public TipoConexion getTipo() {
        return tipo;
    }

    public double getTiempo() {
        return tiempo;
    }

    public void validar() throws DatosMenoresACeroException, DatoNullOVacioException {
        validarError1();
        validarError2();
    }

    private void validarError1() throws DatosMenoresACeroException {
        if(this.costo <= 0 || this.tiempo <= 0) throw new DatosMenoresACeroException("El costo o tiempo no pueden ser menores o iguales a 0");
    }

    private void validarError2() throws DatoNullOVacioException {
        if(this.codigoCiudadDestino == null || this.codigoCiudadDestino.isEmpty() || this.codigoCiudadOrigen == null || this.codigoCiudadOrigen.isEmpty() || this.tipo == null)
            throw new DatoNullOVacioException("Alguno de los datos son null o vacios");
    }

    @Override
    public int compareTo(Conexion o) {
        return Integer.compare(this.identificadorConexion, o.identificadorConexion);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Conexion conexion = (Conexion) o;
        return identificadorConexion == conexion.identificadorConexion;
    }


    @Override
    public String toString() {
        return "costo: " + costo + "tiempo: " + tiempo ;

    }
}
