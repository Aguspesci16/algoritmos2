package dominio;

import java.util.Objects;
import java.util.regex.*;

import excepciones.DatosFaltantesException;
import excepciones.FormatoInvalidoException;
import interfaz.*;

public class Viajero implements Comparable<Viajero> {
    private String cedula;
    private int cedulaFormateada;
    private String nombre;
    private int edad;
    private TipoViajero tipo;

    public TipoViajero getTipo() {
        return tipo;
    }

    public Viajero(String cedula, String nombre, int edad, TipoViajero tipo) {
        this.cedula = cedula;
        this.nombre = nombre;
        this.edad = edad;
        this.tipo = tipo;
    }

    public Viajero(String cedula) {
        this.cedula = cedula;
        formatearCedula();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Viajero viajero = (Viajero) o;
        return Objects.equals(cedula, viajero.cedula);
    }

    @Override
    public int compareTo(Viajero o) {
        return Integer.compare(this.cedulaFormateada, o.cedulaFormateada);
    }

    @Override
    public String toString() {
        return cedula + ';' + nombre + ';' + edad + ';' + tipo;
    }

    public void validarCedula() throws FormatoInvalidoException {
        if (!esCedulaValida(this.cedula)) throw new FormatoInvalidoException("El formato de la cédula no es correcto.");
    }

    private void validarCampos() throws DatosFaltantesException {
        if (cedula == null || cedula.isEmpty()) {
            throw new DatosFaltantesException("Cédula no válida.");
        }
        if (nombre == null || nombre.isEmpty()) {
            throw new DatosFaltantesException("Nombre no válido.");
        }
        if (edad < 0) {
            throw new DatosFaltantesException("Edad no válida.");
        }
        if (tipo == null) {
            throw new DatosFaltantesException("Tipo no válido.");
        }
    }

    public void validarDatos() throws DatosFaltantesException, FormatoInvalidoException {
        validarCampos();
        validarCedula();
        formatearCedula();
    }

    private boolean esCedulaValida(String cedula) {
        if (cedula == null || cedula.isEmpty()) {
            return false;
        } else {
            // String formato1 = "^[1-9]{1}\\.\\d{3}\\.\\d{3}-\\d{1}$";
            // String formato2 = "^[1-9]{1}\\d{2}\\.\\d{3}-\\d{1}$";
            String formato = "^[1-9]([.][0-9])?[0-9]{2}[.][0-9]{3}-[0-9]$";
            return cedula.matches(formato);
        }
    }

    private void formatearCedula() {
        if (this.cedula != null) {
            String cedulaFormateada = cedula.replaceAll("[^0-9]", "");
            if(!cedulaFormateada.isEmpty()) {
                this.cedulaFormateada = Integer.parseInt(cedulaFormateada.substring(0, cedulaFormateada.length() - 1));
            }
        }
    }
}