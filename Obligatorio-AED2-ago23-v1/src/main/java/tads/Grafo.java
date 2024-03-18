package tads;

import dominio.Ciudad;
import dominio.Conexion;
import excepciones.*;
import interfaz.TipoConexion;
import utilidades.VisualizadorGraphViz;

import java.util.*;

public class Grafo {

    private class Arista {
        private boolean existe;
        private ListaGenerica<Conexion> datosConexion;
        private int vOrigen;
        private int vDestino;

        public Arista(int vOrigen, int vDestino, boolean existe) {
            this.vOrigen = vOrigen;
            this.vDestino = vDestino;
            this.existe = existe;
            this.datosConexion = new ListaGenerica<>();
        }
    }

    public class ResultadoCamino {
        public ListaGenerica<Ciudad> caminoCiudades;
        public String caminoConConexiones;
        public double valorCosto;


        public ResultadoCamino(ListaGenerica<Ciudad> caminoCiudades, String caminoConConexiones, double valorCosto) {
            this.caminoCiudades = caminoCiudades;
            this.valorCosto = valorCosto;
            this.caminoConConexiones = caminoConConexiones;
        }
    }

    private int cantVertices;
    private int largo;
    private Ciudad[] vertices; //La coleccion de vertices = coleccion de ciudades
    private Arista[][] adyacencia; //Conexiones con codigoOrigen, codigo Destino

    public int getLargo() {
        return largo;
    }

    public Grafo(int cantidadCiudades) {
        this.adyacencia = new Arista[cantidadCiudades][cantidadCiudades];
        this.cantVertices = cantidadCiudades;
        this.vertices = new Ciudad[cantidadCiudades];
        for (int origen = 0; origen < cantVertices; origen++) {
            for (int destino = 0; destino < cantVertices; destino++) {
                this.adyacencia[origen][destino] = new Arista(origen, destino, false);
            }
        }
    }

    public void registroVertice(Ciudad ciudad) throws GrafoLLenoException {
        if (largo >= cantVertices)
            throw new GrafoLLenoException("Grafo lleno");
        this.vertices[largo] = ciudad;
        largo++;
    }

    public void registroArista(Ciudad origen, Ciudad destino, Conexion conexion) throws DatoRepetidoException {
        int idxOrigen = this.obtenerIdx(origen);
        int idxDestino = this.obtenerIdx(destino);
        if (this.adyacencia[idxOrigen][idxDestino].datosConexion.buscar(conexion) == null) {
            this.adyacencia[idxOrigen][idxDestino].datosConexion.agregarOrdenado(conexion);
            this.adyacencia[idxOrigen][idxDestino].existe = true;
        } else {
            throw new DatoRepetidoException("Ya está registrada esa conexión en el grafo.");
        }
    }

    public void actualizarArista(Ciudad origen, Ciudad destino, Conexion conexion) throws DatoRepetidoException {
        int idxOrigen = this.obtenerIdx(origen);
        int idxDestino = this.obtenerIdx(destino);
        Conexion encontrada = this.adyacencia[idxOrigen][idxDestino].datosConexion.buscar(conexion);
        if (encontrada != null) {
            this.adyacencia[idxOrigen][idxDestino].datosConexion.actualizar(conexion);
        } else {
            throw new DatoRepetidoException("No existe la conexión con ese identificador entre origen y destino.");
        }
    }

    public boolean existeCiudad(Ciudad c) {
        for (Ciudad vertice : vertices) {
            if (vertice != null && vertice.equals(c)) {
                return true;
            }
        }
        return false;
    }

    public Ciudad buscar(Ciudad c) {
        for (Ciudad vertice : vertices) {
            if (vertice != null && vertice.equals(c)) {
                return vertice;
            }
        }
        return null;
    }

    public String toUrl() {
        return VisualizadorGraphViz.grafoToUrl(vertices, adyacencia, a -> a.existe,
                v -> v.getNombre(), a -> a.datosConexion.listar() + "");
    }

    private int obtenerIdx(Ciudad vertice) {
        for (int i = 0; i < cantVertices; i++) {
            if (vertice != null && vertice.equals(vertices[i])) {
                return i;
            }
        }
        return -1;
    }

    //Búsqueda en anchura
    public ListaGenerica<Ciudad> bfs(Ciudad origen, int cantidad) {
        // Lista para almacenar las ciudades visitadas en orden de recorrido
        ListaGenerica<Ciudad> listaCiudades = new ListaGenerica<Ciudad>();
        // La frontera guarda los nodos pendientes a explorar (se va actualizando en cada iteración)
        ListaGenerica<Integer> frontera = new ListaGenerica<>();
        frontera.agregarAlFinal(obtenerIdx(origen));
        // Array para marcar los nodos visitados
        boolean[] visitados = new boolean[cantVertices];
        // Variable para el nivel actual (equivalente al número de transbordos desde el origen)
        int nivel = 0;
        // Mientras haya nodos pendientes en la frontera y no hayamos superado la cantidad de niveles permitidos
        while (!frontera.esVacia() && nivel <= cantidad) {
            // Cantidad de nodos que hay en la frontera en el nivel actual
            int nodosEnNivel = frontera.getCantidad();
            // Iteramos sobre cada nodo en la frontera
            for (int i = 0; i < nodosEnNivel; i++) {
                // Toma y elimina la primer ciudad de la frontera, guardándola en vActual
                int vExplorar = frontera.eliminarDelInicio();
                if (!visitados[vExplorar]) {
                    // Lo agregamos a la lista de ciudades visitadas
                    listaCiudades.agregarOrdenado(vertices[vExplorar]);
                    // Agregamos todos los nodos adyacentes no visitados a la frontera
                    for (int vAdy = 0; vAdy < cantVertices; vAdy++) {
                        if (adyacencia[vExplorar][vAdy].existe) {
                            frontera.agregarAlFinal(vAdy);
                        }
                    }
                    visitados[vExplorar] = true;
                }
            }
            nivel++;
        }
        return listaCiudades;
    }

    public boolean existeCamino(Ciudad origen, Ciudad destino) throws NoExisteOrigenException, NoExisteDestinoException {
        int origenIdx = obtenerIdx(origen);
        int destinoIdx = obtenerIdx(destino);
        if (origenIdx == -1) throw new NoExisteOrigenException("No existe ciudad de origen");
        if (destinoIdx == -1) throw new NoExisteDestinoException("No existe ciudad de destino");
        // La frontera guarda los nodos pendientes a explorar (se va actualizando en cada iteración)
        ListaGenerica<Integer> frontera = new ListaGenerica<>();
        // Agregamos la ciudad de origen a la frontera
        frontera.agregarAlFinal(origenIdx);
        // Inicializar arreglo de ciudades visitadas
        boolean[] visitados = new boolean[cantVertices];
        visitados[origenIdx] = true;
        while (!frontera.esVacia()) {
            // Toma y elimina la primer ciudad de la frontera, guardándola en vActual
            int vActual = frontera.eliminarDelInicio();
            // Si la ciudad actual es el destino, hay un camino
            if (vActual == destinoIdx) {
                return true;
            }
            // Explora ciudades adyacentes no visitadas
            for (int vAdy = 0; vAdy < cantVertices; vAdy++) {
                if (adyacencia[vActual][vAdy].existe && !visitados[vAdy]) {
                    visitados[vAdy] = true;           // Marcar ciudad como visitada
                    frontera.agregarAlFinal(vAdy);    // Agregar ciudad adyacente a la frontera
                }
            }
        }
        // Si llega aca se recorrio y no se encontro camino
        return false;
    }

    public ResultadoCamino dijkstra(Ciudad origen, Ciudad destino) {
        ListaGenerica<Ciudad> ciudades = new ListaGenerica<>();
        int vOrigen = obtenerIdx(origen);
        int vDestino = obtenerIdx(destino);
        //Frontera
        boolean[] visitados = new boolean[cantVertices];
        int[] padres = new int[cantVertices]; //para saber si un vetice es visitable y ademas para recontruir el camino. padre o anteriores
        double[] distancias = new double[cantVertices];
        for (int i = 0; i < cantVertices; i++) {
            padres[i] = -1;
            distancias[i] = Double.MAX_VALUE;
        }
        //aggaro el origen para empezar desde el origen
        distancias[vOrigen] = 0;
        padres[vOrigen] = vOrigen;
        while (!estaTodoLoAccesibleVisitado(visitados, padres)) { //lo no accesible no me interesa
            //el nuevo piso va a ser el vExplorar
            int vExplorar = obtenerVerticeNoVisitadoDeMenorDistancia(visitados, distancias);
            //conozco ahora que tengo adyacente y tengo maneras de llegar al ellas y conocer el costo
            for (int vAdy = 0; vAdy < cantVertices; vAdy++) {
                if (sonAdyacentes(vExplorar, vAdy)) {
                    //el piso actual = distancias[vExplorar] + el valor que te lleva del piso al vertice adyacente
                    double distanciaPasandoPorVExplorar = distancias[vExplorar] + obtengoElValorDeTiempoDeLaAristaQueConectaALosVertices(vExplorar, vAdy);
                    //si yo paso por este vertice es mas chico que con los que habia pasado?
                    if (distanciaPasandoPorVExplorar < distancias[vAdy]) {
                        distancias[vAdy] = distanciaPasandoPorVExplorar;
                        padres[vAdy] = vExplorar;
                        //cainoConElQueLlego[vAdy] = caminoMasChico.getTipo;
                    }
                }
            }
            visitados[vExplorar] = true;
        }
        return reconstruirCamino(padres, distancias[vDestino], vOrigen, vDestino);

    }

    public double obtengoElValorDeTiempoDeLaAristaQueConectaALosVertices(int vExplorar, int vAdy) {
        return adyacencia[vExplorar][vAdy].datosConexion.dameElMinimoUsandoDoubles(v -> v.getTiempo()).getTiempo();
    }

    private int obtenerVerticeNoVisitadoDeMenorDistancia(boolean[] visitados, double[] distancias) {
        //Busca el escalon no visitado mas chico
        double minimaDistancia = Double.MAX_VALUE;
        int idxMinimaDistancia = -1;
        for (int i = 0; i < cantVertices; i++) {
            if (!visitados[i] && distancias[i] < minimaDistancia) {
                minimaDistancia = distancias[i];
                idxMinimaDistancia = i;
            }
        }
        return idxMinimaDistancia;
    }

    private boolean estaTodoLoAccesibleVisitado(boolean[] visitados, int[] padres) {
        for (int i = 0; i < cantVertices; i++) {
            if (!visitados[i] && esAccesible(padres, i)) {
                return false;
            }
        }
        return true;
    }

    private boolean esAccesible(int[] padres, int i) {
        return padres[i] != -1;
    }

    //Nos dice si hay una arista que va de origen a destino.
    private boolean sonAdyacentes(int origen, int destino) {
        return adyacencia[origen][destino].existe;
    }

    private ResultadoCamino reconstruirCamino(int[] padres, double distancia, int vOrigen, int vDestino) {
        if (padres[vDestino] == -1) return null; //No hay camino si no era accesible
        int vActual = vDestino;
        String caminoConConexiones = "";
        ListaGenerica<Ciudad> elCamino = new ListaGenerica<>();
        while (vActual != vOrigen) {
            Ciudad ciudadActual = vertices[vActual];
            elCamino.agregarAlInicio(ciudadActual);
            int vPadreActual = padres[vActual];
            TipoConexion tipoConexion = obtengoElTipoDeConexion(vPadreActual, vActual);
            caminoConConexiones = "|" + tipoConexion + "|" + ciudadActual + caminoConConexiones;
            vActual = vPadreActual;
        }
        elCamino.agregarAlInicio(vertices[vOrigen]);
        caminoConConexiones = vertices[vOrigen] + caminoConConexiones;
        return new ResultadoCamino(elCamino, caminoConConexiones, distancia);
    }

    private TipoConexion obtengoElTipoDeConexion(int vOrigen, int vDestino) {
        Arista arista = adyacencia[vOrigen][vDestino];
        Conexion conexion = arista.datosConexion.dameElMinimoUsandoDoubles(v -> v.getTiempo());
        if (conexion != null) {
            return conexion.getTipo();
        }
        return null;
    }
}