package tads;

import excepciones.DatoRepetidoException;

public class ListaGenerica<TipoDatoListar extends Comparable<TipoDatoListar>> {

    protected NodoLista<TipoDatoListar> inicio;
    protected NodoLista<TipoDatoListar> ultimo;
    protected int cantidad;

    public ListaGenerica() {
        this.inicio = null;
        this.ultimo = null;
        this.cantidad = 0;
    }

    public int getCantidad() {
        return cantidad;
    }

    private static class NodoLista<TipoDatoListar> {
        protected TipoDatoListar dato;
        protected NodoLista<TipoDatoListar> sig;

        public NodoLista(TipoDatoListar dato, NodoLista<TipoDatoListar> sig) {
            this.dato = dato;
            this.sig = sig;
        }

        public NodoLista(TipoDatoListar dato) {
            this.dato = dato;
            this.sig = null;
        }

        public NodoLista<TipoDatoListar> getSig() {
            return sig;
        }

        public TipoDatoListar getDato() {
            return dato;
        }
    }

    private NodoLista<TipoDatoListar> getInicio() {
        return inicio;
    }

    public boolean esVacia() {
        return this.inicio == null;
    }

    public void agregarOrdenado(TipoDatoListar objeto) {
        NodoLista<TipoDatoListar> nodoAInsertar = new NodoLista<>(objeto);
        if (this.esVacia() || objeto.compareTo(this.inicio.getDato()) < 0) {
            agregarAlInicio(objeto);
            return;
        }
        NodoLista<TipoDatoListar> actual = this.inicio;
        while (actual.sig != null && objeto.compareTo(actual.sig.getDato()) >= 0) {
            actual = actual.sig;
        }
        nodoAInsertar.sig = actual.sig;
        actual.sig = nodoAInsertar;
        if (nodoAInsertar.sig == null) {
            this.ultimo = nodoAInsertar;
        }
        this.cantidad++;
    }

    public void actualizar(TipoDatoListar aActualizar) {
        NodoLista<TipoDatoListar> nodoActualizar = new NodoLista<>(aActualizar);
        NodoLista<TipoDatoListar> actual = this.inicio;
        boolean encontrado = false;
        if (actual.dato.equals(nodoActualizar.dato)) {
            NodoLista<TipoDatoListar> aux = actual.sig;
            this.inicio = nodoActualizar;
            nodoActualizar.sig = aux;
            if (nodoActualizar.sig == null) this.ultimo = nodoActualizar;
            encontrado = true;
        }
        while (!encontrado && actual.sig != null) {
            if (actual.sig.dato.equals(nodoActualizar.dato)) {
                NodoLista<TipoDatoListar> aux = actual.sig.sig;
                actual.sig = nodoActualizar;
                nodoActualizar.sig = aux;
                encontrado = true;
            }
            actual = actual.getSig();
        }
        if (actual.getSig() == null) {
            this.ultimo = actual;
        }
    }

    public void agregarAlFinal(TipoDatoListar dato) {
        NodoLista<TipoDatoListar> nodoInsertar = new NodoLista<>(dato);
        if (this.esVacia()) {
            agregarAlInicio(dato);
        } else {
            this.ultimo.sig = nodoInsertar;
            this.ultimo = nodoInsertar;
        }
        this.cantidad++;
    }

    public TipoDatoListar eliminarDelInicio() {
        TipoDatoListar retorno = this.inicio.getDato();

        if (!this.esVacia()) {
            this.inicio = this.inicio.sig;
            if (this.inicio == null) this.ultimo = null;
            this.cantidad--;

        }
        return retorno;
    }

    public TipoDatoListar buscar(TipoDatoListar dato) {
        NodoLista<TipoDatoListar> actual = getInicio();
        while (actual != null) {
            if (actual.getDato().equals(dato)) {
                return actual.getDato();
            }
            actual = actual.getSig();
        }
        return null;
    }

    public void agregarAlInicio(TipoDatoListar dato) {
        NodoLista<TipoDatoListar> nodo = new NodoLista<>(dato, inicio);
        if (this.inicio == null) {
            this.inicio = nodo;
            this.ultimo = nodo;
        } else {
            NodoLista<TipoDatoListar> aux = this.inicio;
            this.inicio = nodo;
            nodo.sig = aux;
        }
    }

    public String listar() {
        return listarGenerico(this.inicio);
    }

    public String listarGenerico(NodoLista<TipoDatoListar> nodo) {
        if (nodo == null) {
            return "";
        } else if (nodo.sig == null) {
            return nodo.getDato().toString();
        } else {
            return nodo.getDato().toString() + "|" + listarGenerico(nodo.getSig());
        }
    }

    public interface DameDouble<TipoDatoListar> {
        double getDouble(TipoDatoListar dato);
    }

    public TipoDatoListar dameElMinimoUsandoDoubles(DameDouble<TipoDatoListar> dameValor) {
        TipoDatoListar elementoMininimo = null;
        NodoLista<TipoDatoListar> actual = this.inicio;
        if (actual != null) {
            //Hago un primer seteo para valorMinimo con el valor inicial de la lista
            double valorMinimo = dameValor.getDouble(actual.dato);
            elementoMininimo = actual.dato;
            actual = actual.sig;
            while (actual != null) {
                double valorDato = dameValor.getDouble(actual.dato);
                if (valorDato < valorMinimo) {
                    valorMinimo = valorDato;
                    elementoMininimo = actual.dato;
                }
                actual = actual.sig;
            }
        }
        return elementoMininimo;
    }
}
