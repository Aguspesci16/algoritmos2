package dominio;

import java.util.Objects;
import excepciones.CodigoCiudadException;
import excepciones.DatoNullOVacioException;
import excepciones.DatosFaltantesException;

public class Ciudad implements ObjetoOrdenable<Ciudad>, Comparable<Ciudad> {
    private String codigo;
    private String nombre;

    public Ciudad(String codigo, String nombre) {
        this.codigo = codigo;
        this.nombre = nombre;
    }

    public Ciudad(String codigo) {
        this.codigo = codigo;
    }

    public void validarDatos() throws CodigoCiudadException, DatoNullOVacioException {
        validarNombreVacioONull();
        validarCodigoVacioONull();
        validarCodigo();
    }

    @Override
    public String toString() {
        return this.codigo + ";" + this.nombre;
    }

    @Override
    public int compareTo(Ciudad o) {
        return this.codigo.compareTo(o.codigo);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;
        Ciudad ciudad = (Ciudad) o;
        return Objects.equals(codigo, ciudad.codigo);
    }

    //TODO: Para que es esto?
    @Override
    public boolean esMayor(Ciudad obj) {
        return codigo.compareTo(obj.codigo) > 0;
    }

    public String getNombre() {
        return nombre;
    }

    private void validarNombreVacioONull() throws DatoNullOVacioException {
        if(this.nombre == null || this.nombre.isEmpty()) {
            throw new DatoNullOVacioException("El nombre es null o vacio");
        }
    }

    public void validarCodigoVacioONull() throws DatoNullOVacioException {
        if(this.codigo == null || this.codigo.isEmpty()) {
            throw new DatoNullOVacioException("El codigo es null o vacio");
        }
    }

    public void validarCodigo() throws CodigoCiudadException {
        if( this.codigo.length() < 5) {
            throw new CodigoCiudadException("el codigo debe tener almenos 5 caracteres y todo en mayusculas");
        }
        if (!this.codigo.matches("^[A-Z0-9]+$")) {
            throw new CodigoCiudadException("El código debe ser alfanumérico y estar en mayúsculas.");
        }
    }
}
