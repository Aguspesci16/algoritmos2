package tads;

import excepciones.DatoRepetidoException;
import excepciones.NoEncontradoException;


public class ABBGenerico<T extends Comparable<T>> {

    private NodoAbb<T> raiz;

    public ABBGenerico() {
        raiz = null;
    }

    private class NodoAbb<T> {
        private T dato;
        private NodoAbb<T> izq, der;

        public NodoAbb(T dato) {
            this.dato = dato;
            this.izq = null;
            this.der = null;
        }
    }

    public class ResultadoBusqueda {
        public int cantidadDeBusquedas;
        public T datos;

        public ResultadoBusqueda(int cantidadDeBusquedas, T datos) {
            this.cantidadDeBusquedas = cantidadDeBusquedas;
            this.datos = datos;
        }
    }

    public void insertar(T dato) throws DatoRepetidoException {
        raiz = insertarRec(raiz, dato);
    }

    private NodoAbb<T> insertarRec(NodoAbb<T> nodo, T dato) throws DatoRepetidoException {
        if (nodo == null) {
            return new NodoAbb<T>(dato);
        }
        if (dato.compareTo(nodo.dato) < 0) {
            nodo.izq = insertarRec(nodo.izq, dato);
        } else if (dato.compareTo(nodo.dato) > 0) {
            nodo.der = insertarRec(nodo.der, dato);
        } else {
            throw new DatoRepetidoException("Ya existe ese elemento en el árbol.");
        }
        return nodo;
    }

    public ResultadoBusqueda buscarElemento(T dato) throws NoEncontradoException {
        ResultadoBusqueda resultado = busquedaRecursiva(this.raiz, dato, 0);
        if (resultado == null) throw new NoEncontradoException("No se encontro el elemento buscado");
        return resultado;
    }

    private ResultadoBusqueda busquedaRecursiva(NodoAbb<T> nodo, T dato, int profundidad) {
        if (nodo == null) {
            return null;
        }
        if (dato.compareTo(nodo.dato) < 0) {
            return busquedaRecursiva(nodo.izq, dato, profundidad + 1);
        } else if (dato.compareTo(nodo.dato) > 0) {
            return busquedaRecursiva(nodo.der, dato, profundidad + 1);
        } else {
            return new ResultadoBusqueda(profundidad, nodo.dato);
        }
    }

    public ListaGenerica<T> inOrderInverso() {
        ListaGenerica<T> listaRetorno = new ListaGenerica<>();
        listadoInOrderInverso(this.raiz, listaRetorno);
        return listaRetorno;
    }

    public void listadoInOrderInverso(NodoAbb<T> nodo, ListaGenerica<T> lista) {
        if (nodo == null) {
            return;
        }
        listadoInOrderInverso(nodo.izq, lista);
        lista.agregarAlInicio(nodo.dato);
        listadoInOrderInverso(nodo.der, lista);
    }

    public ListaGenerica<T> inOrder() {
        ListaGenerica<T> listaRetorno = new ListaGenerica<>();
        listadoInOrder(this.raiz, listaRetorno);
        return listaRetorno;
    }

    private void listadoInOrder(NodoAbb<T> nodo, ListaGenerica<T> lista) {
        if (nodo == null) {
            return;
        }
        listadoInOrder(nodo.izq, lista);
        lista.agregarAlFinal(nodo.dato);
        listadoInOrder(nodo.der, lista);
    }
}

