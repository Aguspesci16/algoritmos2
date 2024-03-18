package interfaz;

import java.util.Arrays;
import java.util.Objects;

public enum TipoConexion {
    RUTA_NACIONAL(0, "Ruta nacional"),
    RUTA_MARITIMA(0, "Ruta maritima"),
    RUTA_AEREA(0, "Ruta aerea"),
    RUTA_FERROVIARIA(0, "Ruta ferroviaria"),
    OTRA_RUTA(0, "Otra ruta");

    private final int indice;
    private final String texto;

    TipoConexion(int indice, String texto) {
        this.indice = indice;
        this.texto = texto;
    }

    public int getIndice() {
        return indice;
    }

    public String getTexto() {
        return texto;
    }

    public static TipoConexion fromTexto(String codigo) {
        return Arrays.stream(TipoConexion.values())
                .filter(a -> Objects.equals(a.texto, codigo))
                .findFirst()
                .orElse(null);
    }

}
