package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import interfaz.TipoConexion;
import sistema.auxiliares.TestCiudadDato;
import sistema.auxiliares.TestConexionDato;

import static java.lang.String.format;

public class AccionesMapa {
    public static TestCiudadDato agregueUnaCiudadCorrectamente(Sistema s,
                                                               String codigo,
                                                               String nombre) {

        AuxAsserciones.checkearOk(s.registrarCiudad(codigo, nombre),
                format("La  ciudad '%s' ('%s') deberia de haberse agregado correctamente",
                        nombre, codigo));
        return new TestCiudadDato(codigo, nombre);
    }

    public static TestCiudadDato agregueUnaCiudadCorrectamente(Sistema s,
                                                               TestCiudadDato ciudad) {
        return agregueUnaCiudadCorrectamente(s, ciudad.getCodigo(), ciudad.getNombre());
    }

    public static Retorno agregoUnaCiudad(Sistema s, TestCiudadDato c) {
        return agregoUnaCiudad(s, c.getCodigo(), c.getNombre());
    }

    public static Retorno agregoUnaCiudad(Sistema s, String codigo, String nombre) {
        return s.registrarCiudad(codigo, nombre);
    }


    public static TestConexionDato agregueUnaConexionCorrectamente(Sistema s, TestConexionDato conexionDato) {
        AuxAsserciones.checkearOk(agregoUnaConexion(s, conexionDato), format("No pude agregar la conexion entre '%s' y '%s'",
                conexionDato.getCiudadOrigen(), conexionDato.getCiudadDestino()));
        return conexionDato;
    }

    public static Retorno agregoUnaConexion(Sistema s, TestCiudadDato origen, TestCiudadDato destino,
                                            int id, double costo, double tiempo, TipoConexion tipo) {
        return agregoUnaConexion(s, new TestConexionDato(origen, destino, id, costo, tiempo, tipo));
    }

    public static Retorno agregoUnaConexion(Sistema s, TestConexionDato conexionDato) {
        return s.registrarConexion(conexionDato.getCiudadOrigen().getCodigo(),
                conexionDato.getCiudadDestino().getCodigo(), conexionDato.getIdentificadorConexion(), conexionDato.getCosto(),
                conexionDato.getTiempo(), conexionDato.getTipo());
    }

    public static TestConexionDato agregueUnaConexionCorrectamente(Sistema s, TestCiudadDato origen, TestCiudadDato destino,
                                                                   int id, double costo, double tiempo, TipoConexion tipo) {
        return agregueUnaConexionCorrectamente(s, new TestConexionDato(origen, destino, id, costo, tiempo, tipo));

    }

    public static TestConexionDato agregueUnaConexionCorrectamente(Sistema s, String codigoOrigen, String codigoDestino,
                                                                   int id, double costo, double tiempo, TipoConexion tipo) {
        return agregueUnaConexionCorrectamente(s, new TestCiudadDato(codigoOrigen), new TestCiudadDato(codigoDestino),
                id, costo, tiempo, tipo);
    }


    public static TestConexionDato actualiceUnaConexionCorrectamente(Sistema s, TestConexionDato con) {
        AuxAsserciones.checkearOk(actualizoUnaConexion(s, con.getCiudadOrigen(), con.getCiudadDestino(), con.getIdentificadorConexion(), con.getCosto(), con.getTiempo(), con.getTipo()),
                "La conexion debia haberse actualizado correctamente");
        return con;
    }

    public static Retorno actualizoUnaConexion(Sistema s, TestCiudadDato origen, TestCiudadDato destino, int id, double costo, double tiempo, TipoConexion tipo) {
        return s.actualizarConexion(origen.getCodigo(), destino.getCodigo(), id, costo, tiempo, tipo);
    }
}
