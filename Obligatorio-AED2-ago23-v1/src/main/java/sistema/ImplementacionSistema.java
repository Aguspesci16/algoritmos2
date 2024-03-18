package sistema;

import dominio.Conexion;
import tads.ABBGenerico;
import dominio.Ciudad;
import tads.Grafo;
import tads.ListaGenerica;
import dominio.Viajero;
import interfaz.*;
import excepciones.*;

public class ImplementacionSistema implements Sistema {

    private int cantidadMaxima;
    private ABBGenerico<Viajero> abbViajeros;
    private ListaGenerica<Viajero> listaCasual;
    private ListaGenerica<Viajero> listaPremium;
    private ListaGenerica<Viajero> listaEstandar;
    private Grafo grafoConexiones;

    @Override
    public Retorno inicializarSistema(int maxCiudades) {
        try {
            if (maxCiudades <= 5) throw new ValorInvalidoException("El valor debe ser mayor a 5.");
            cantidadMaxima = maxCiudades;
            abbViajeros = new ABBGenerico<>();
            listaPremium = new ListaGenerica<>();
            listaEstandar = new ListaGenerica<>();
            listaCasual = new ListaGenerica<>();
            grafoConexiones = new Grafo(maxCiudades);
            return Retorno.ok();
        } catch (ValorInvalidoException ex) {
            return Retorno.error1(ex.getMessage());
        }
    }

    @Override
    public Retorno registrarViajero(String cedula, String nombre, int edad, TipoViajero tipo) {
        try {
            Viajero viajero = new Viajero(cedula, nombre, edad, tipo);
            viajero.validarDatos();
            abbViajeros.insertar(viajero);
            switch (viajero.getTipo()) {
                case CASUAL -> listaCasual.agregarOrdenado(viajero);
                case ESTANDAR -> listaEstandar.agregarOrdenado(viajero);
                case PREMIUM -> listaPremium.agregarOrdenado(viajero);
            }
            return Retorno.ok("Se registró el viajero correctamente");
        } catch (DatosFaltantesException ex) {
            return Retorno.error1(ex.getMessage());
        } catch (FormatoInvalidoException ex) {
            return Retorno.error2(ex.getMessage());
        } catch (DatoRepetidoException ex) {
            return Retorno.error3("Ya existe un viajero con la misma cédula.");
        }
    }

    @Override
    public Retorno buscarViajero(String cedula) {
        try {
            Viajero aux = new Viajero(cedula);
            aux.validarCedula();
            ABBGenerico<Viajero>.ResultadoBusqueda resultado = abbViajeros.buscarElemento(aux);
            return Retorno.ok(resultado.cantidadDeBusquedas, resultado.datos.toString());
        } catch (FormatoInvalidoException ex) {
            return Retorno.error1(ex.getMessage());
        } catch (NoEncontradoException ex) {
            return Retorno.error2(ex.getMessage());
        }
    }

    @Override
    public Retorno listarViajerosAscendente() {
        return Retorno.ok(abbViajeros.inOrder().listar());
    }

    @Override
    public Retorno listarViajerosDescendente() {
        return Retorno.ok(abbViajeros.inOrderInverso().listar());
    }

    @Override
    public Retorno listarViajerosPorTipo(TipoViajero tipo) {
        try {
            if (tipo == null) throw new Exception("El tipo no es válido.");
            return switch (tipo) {
                case CASUAL -> Retorno.ok(listaCasual.listar());
                case ESTANDAR -> Retorno.ok(listaEstandar.listar());
                case PREMIUM -> Retorno.ok(listaPremium.listar());
            };
        } catch (Exception ex) {
            return Retorno.error1(ex.getMessage());
        }
    }

    @Override
    public Retorno registrarCiudad(String codigo, String nombre) {
        try {
            if (grafoConexiones.getLargo() >= cantidadMaxima)
                throw new GrafoLLenoException("Ya se llegó a límite de ciudades");
            Ciudad ciudad = new Ciudad(codigo, nombre);
            ciudad.validarDatos();
            if (grafoConexiones.existeCiudad(ciudad)) {
                throw new DatoRepetidoException("ya existe la ciudad");
            }
            grafoConexiones.registroVertice(ciudad);
            return Retorno.ok("La ciudad se agregó correctamente.");
        } catch (GrafoLLenoException e) {
            return Retorno.error1(e.getMessage());
        } catch (DatoNullOVacioException e) {
            return Retorno.error2(e.getMessage());
        } catch (CodigoCiudadException e) {
            return Retorno.error3(e.getMessage());
        } catch (DatoRepetidoException e) {
            return Retorno.error4(e.getMessage());
        }
    }

    @Override
    public Retorno registrarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        try {
            Conexion conexion = new Conexion(codigoCiudadOrigen, codigoCiudadDestino, identificadorConexion, costo, tiempo, tipo);
            conexion.validar();
            Ciudad ciudadOri = new Ciudad(codigoCiudadOrigen);
            Ciudad ciudadDes = new Ciudad(codigoCiudadDestino);
            ciudadOri.validarCodigo();
            ciudadDes.validarCodigo();
            if (!grafoConexiones.existeCiudad(ciudadOri)) {
                throw new NoExisteOrigenException("No existe el origen.");
            }
            if (!grafoConexiones.existeCiudad(ciudadDes)) {
                throw new NoExisteDestinoException("No existe el destino.");
            }
            grafoConexiones.registroArista(grafoConexiones.buscar(ciudadOri), grafoConexiones.buscar(ciudadDes), conexion);
            return Retorno.ok();
        } catch (DatosMenoresACeroException e) {
            return Retorno.error1(e.getMessage());
        } catch (DatoNullOVacioException e) {
            return Retorno.error2(e.getMessage());
        } catch (CodigoCiudadException e) {
            return Retorno.error3(e.getMessage());
        } catch (NoExisteOrigenException e) {
            return Retorno.error4(e.getMessage());
        } catch (NoExisteDestinoException e) {
            return Retorno.error5(e.getMessage());
        } catch (DatoRepetidoException e) {
            return Retorno.error6(e.getMessage());
        }
    }

    @Override
    public Retorno actualizarConexion(String codigoCiudadOrigen, String codigoCiudadDestino, int identificadorConexion, double costo, double tiempo, TipoConexion tipo) {
        try {
            Conexion conexion = new Conexion(codigoCiudadOrigen, codigoCiudadDestino, identificadorConexion, costo, tiempo, tipo);
            conexion.validar();
            Ciudad ciudadOri = new Ciudad(codigoCiudadOrigen);
            Ciudad ciudadDes = new Ciudad(codigoCiudadDestino);
            ciudadOri.validarCodigo();
            ciudadDes.validarCodigo();
            if (!grafoConexiones.existeCiudad(ciudadOri)) {
                throw new NoExisteOrigenException("No existe el origen.");
            }
            if (!grafoConexiones.existeCiudad(ciudadDes)) {
                throw new NoExisteDestinoException("No existe el destino.");
            }
            grafoConexiones.actualizarArista(grafoConexiones.buscar(ciudadOri), grafoConexiones.buscar(ciudadDes), conexion);
            return Retorno.ok();
        } catch (DatosMenoresACeroException e) {
            return Retorno.error1(e.getMessage());
        } catch (DatoNullOVacioException e) {
            return Retorno.error2(e.getMessage());
        } catch (CodigoCiudadException e) {
            return Retorno.error3(e.getMessage());
        } catch (NoExisteOrigenException e) {
            return Retorno.error4(e.getMessage());
        } catch (NoExisteDestinoException e) {
            return Retorno.error5(e.getMessage());
        } catch (DatoRepetidoException e) {
            return Retorno.error6(e.getMessage());
        }
    }

    @Override
    public Retorno listadoCiudadesCantTrasbordos(String codigo, int cantidad) {
        try {
            if (cantidad < 0) throw new DatosMenoresACeroException("La cantidad es menor que 0");
            if (codigo == null || codigo.isEmpty()) throw new DatoNullOVacioException("El codigo es nulo");
            Ciudad ciudad = new Ciudad(codigo);
            ciudad.validarCodigo();
            if (!grafoConexiones.existeCiudad(ciudad)) throw new CiudadNoRegistradaException("Ciudad no registrada");
            return Retorno.ok(grafoConexiones.bfs(ciudad, cantidad).listar());
        } catch (DatosMenoresACeroException e) {
            return Retorno.error1(e.getMessage());
        } catch (DatoNullOVacioException e) {
            return Retorno.error2(e.getMessage());
        } catch (CodigoCiudadException e) {
            return Retorno.error3(e.getMessage());
        } catch (CiudadNoRegistradaException e) {
            return Retorno.error4(e.getMessage());
        }
    }

    @Override
    public Retorno viajeCostoMinimo(String codigoCiudadOrigen, String codigoCiudadDestino) {
        try {
            Ciudad ciudadOri = new Ciudad(codigoCiudadOrigen);
            Ciudad ciudadDes = new Ciudad(codigoCiudadDestino);
            ciudadOri.validarCodigoVacioONull();
            ciudadDes.validarCodigoVacioONull();
            ciudadOri.validarCodigo();
            ciudadDes.validarCodigo();
            if(!grafoConexiones.existeCamino(ciudadOri, ciudadDes)) {
                throw new NoHayCaminoException("No existe un camino entre origen y destino.");
            }
            Grafo.ResultadoCamino datosMenorCamino = grafoConexiones.dijkstra(ciudadOri,ciudadDes);
            return Retorno.ok((int)datosMenorCamino.valorCosto, datosMenorCamino.caminoConConexiones);
        } catch (DatoNullOVacioException e) {
            return Retorno.error1(e.getMessage());
        } catch (CodigoCiudadException e) {
            return Retorno.error2(e.getMessage());
        } catch (NoHayCaminoException e) {
            return Retorno.error3(e.getMessage());
        } catch (NoExisteOrigenException e) {
            return Retorno.error4(e.getMessage());
        } catch (NoExisteDestinoException e) {
            return Retorno.error5(e.getMessage());
        }
    }
}
