package utilidades;

import java.util.function.Function;
import java.util.function.Predicate;

public final class VisualizadorGraphViz {

    /**
     * Muestra un arbol en formato graphviz. UN ejemplo de llamada es:<br />
     * System.out.println(VisualizarArbolesGraphViz.arbolBinToString(arbol.raiz,n->n.dato,n->n.izq,n->n.der));<br />
     * Para visualizar pueden ir a {@see <a href='https://dreampuf.github.io/GraphvizOnline/' >GraphViz</a>}
     * @param raiz    LA raiz del arbol
     * @param getDato Una lambda que devuelve el dato del nodo
     * @param getIzq  Una lambda que devuelve la izquierda
     * @param getDer  Una lambda que devuelve la derecha
     * @param <T>     La clase del nodo
     * @return El Arbol en String
     */
    //System.out.println(VisualizarArbolesGraphViz.arbolBinToString(arbol.raiz,n->n.dato,n->n.izq,n->n.der));
    public static <T> String arbolBinToString(
            T raiz,
            Function<T, Object> getDato,
            Function<T, T> getIzq,
            Function<T, T> getDer,
            boolean mostrarNulos) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph G{\n");
        arbolBinToString(raiz, "R", getDato, getIzq, getDer, mostrarNulos, sb);
        sb.append("\n}");
        return sb.toString();
        //return toUrl(sb.toString());


    }
    /**
     * Muestra un arbol en formato graphviz. UN ejemplo de llamada es:<br />
     * System.out.println(VisualizarArbolesGraphViz.arbolBinToString(arbol.raiz,n->n.dato,n->n.izq,n->n.der));<br />
     * Para visualizar pueden ir a {@see <a href='https://dreampuf.github.io/GraphvizOnline/' >GraphViz</a>}
     * @param raiz    LA raiz del arbol
     * @param getDato Una lambda que devuelve el dato del nodo
     * @param getIzq  Una lambda que devuelve la izquierda
     * @param getDer  Una lambda que devuelve la derecha
     * @param <T>     La clase del nodo
     * @return El Arbol en String
     */
    //System.out.println(VisualizarArbolesGraphViz.arbolBinToString(arbol.raiz,n->n.dato,n->n.izq,n->n.der));
    public static <T> String arbolBinToUrl(
            T raiz,
            Function<T, Object> getDato,
            Function<T, T> getIzq,
            Function<T, T> getDer) {
        return toUrl(arbolBinToString(raiz,getDato,getIzq,getDer,true));
    }

    private static String toUrl(String graphviz){
        return String.format("https://dreampuf.github.io/GraphvizOnline/#%s",
                graphviz.replace("\n","%0A")
                        .replace(" ","%20")
                        .replace("#","%232")
                        .replace("\"",	"%22")
                        .replace("{","%7B")
                        .replace("}","%7D")
                        .replace("[","%5B")
                        .replace("]","%5D")
                        .replace("=","%3D")
                        .replace("-","%2D")
                        .replace("_","%5F")
                        .replace(">","%3E")
                        .replace(";","%3B"));
    }

    private static <T> void arbolBinToString(
            T raizSubArbol,
            String nombreNodo,
            Function<T, Object> getDato,
            Function<T, T> getIzq,
            Function<T, T> getDer,
            boolean mostrarNulos,
            StringBuilder sb) {

        if (raizSubArbol == null) {
            return;
        }
        sb.append(String.format("%s[label=\"%s\"];\n", nombreNodo, getDato.apply(raizSubArbol)));
        T izq = getIzq.apply(raizSubArbol);
        T der = getDer.apply(raizSubArbol);
        String nombreIzq = nombreNodo + "_I";
        String nombreDer = nombreNodo + "_D";
        if (izq != null) {
            sb.append(String.format("%s -- %s;\n", nombreNodo, nombreIzq));
            arbolBinToString(izq, nombreIzq, getDato, getIzq, getDer,mostrarNulos, sb);
        }else if(der!=null && mostrarNulos){
            sb.append(String.format("%s -- %s;\n",nombreNodo,nombreIzq));
            sb.append(String.format("%s[label=\"null\"];\n",nombreIzq));
        }
        if (der != null) {
            sb.append(String.format("%s -- %s;\n", nombreNodo, nombreDer));
            arbolBinToString(der, nombreDer, getDato, getIzq, getDer,mostrarNulos, sb);
        }else if(izq!=null && mostrarNulos){
            sb.append(String.format("%s -- %s;\n",nombreNodo,nombreDer));
            sb.append(String.format("%s[label=\"null\"];\n",nombreDer));
        }
    }
    //System.out.println(VisualizarArbolesGraphViz.arbolGeneralAString(arbol.raiz,n->n.dato,n->new NodoAB[]{n.izq,n.der}));
    public static <T> String arbolGeneralAString(
            T raiz,
            Function<T, Object> getDato,
            Function<T, T[]> getHijos) {
        StringBuilder sb = new StringBuilder();
        sb.append("graph G{\n");
        arbolGeneralToString(raiz, "R", getDato, getHijos, sb);
        sb.append("\n}");
        return sb.toString();

    }

    private static <T> void arbolGeneralToString(
            T raizSubArbol,
            String nombreNodo,
            Function<T, Object> getDato,
            Function<T, T[]> getHijos,
            StringBuilder sb) {
        if (raizSubArbol == null) {
            return;
        }
        sb.append(String.format("%s[label=\"%s\"];\n", nombreNodo, getDato.apply(raizSubArbol)));
        T[] hijos = getHijos.apply(raizSubArbol);
        if (hijos != null) {
            for (int i = 0; i < hijos.length; i++) {
                if(hijos[i]!=null){
                    sb.append(String.format("%s -- %s_%s;\n",nombreNodo,nombreNodo,i));
                    arbolGeneralToString(hijos[i],nombreNodo+"_"+i,getDato,getHijos,sb);
                }
            }
        }

    }

    public static <V,A> String grafoToString(V[] vertices, A[][] aristas,
                                             Predicate<A> existeONo,
                                             Function<V,String> verticeToString,
                                             Function<A,String> aristaToString){
        StringBuilder sb=new StringBuilder();
        sb.append("digraph G{\n");
        for (int i = 0; i < vertices.length; i++) {
            if(vertices[i]!=null){
                sb.append(String.format("V_%s[label=\"%s\"];\n",i,verticeToString.apply(vertices[i])));
            }
        }

        for (int i = 0; i < vertices.length; i++) {
            for (int j = 0; j < vertices.length; j++) {
                if(existeONo.test(aristas[i][j]))
                    sb.append(String.format("V_%s->V_%s[label=\"%s\"];\n",i,j,aristaToString.apply(aristas[i][j])));

            }
        }

        sb.append("}\n");
        return sb.toString();

    }
    public static <V,A> String grafoToUrl(V[] vertices, A[][] aristas,Predicate<A> existeONo, Function<V,String> verticeToString,Function<A,String> aristaToString) {
        return toUrl(grafoToString(vertices,aristas,existeONo,verticeToString,aristaToString));
    }
}