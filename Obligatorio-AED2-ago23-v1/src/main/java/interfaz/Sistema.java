package interfaz;

/**
 * Provee una interfaz para interactuar con el sistema
 */
public interface Sistema {

    Retorno inicializarSistema(int maxCiudades);

    Retorno registrarViajero(String cedula, String nombre, int edad, TipoViajero tipo);

    Retorno buscarViajero(String cedula);

    Retorno listarViajerosAscendente();

    Retorno listarViajerosDescendente();

    Retorno listarViajerosPorTipo(TipoViajero tipo);

    Retorno registrarCiudad(String codigo, String nombre);

    Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion,
                              double costo, double tiempo, TipoConexion tipo);

    Retorno actualizarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion,
                             double costo, double tiempo, TipoConexion tipo);

    Retorno listadoCiudadesCantTrasbordos(String codigo, int cantidad);


    Retorno viajeCostoMinimo(String codigoCiudadOrigen, String codigoCiudadDestino);


}
