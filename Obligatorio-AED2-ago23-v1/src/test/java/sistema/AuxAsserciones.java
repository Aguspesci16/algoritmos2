package sistema;

import interfaz.Retorno;
import org.junit.jupiter.api.Assertions;

public class AuxAsserciones {
    public static Retorno checkearOk(Retorno retorno, String mensajeError) {
        Assertions.assertEquals(Retorno.Resultado.OK, retorno.getResultado(), mensajeError);
        return retorno;
    }

    public static Retorno checkearOk(Retorno retorno, String texto, String mensajeError) {
        Assertions.assertEquals(Retorno.Resultado.OK, retorno.getResultado(), mensajeError);
        Assertions.assertEquals(texto, retorno.getValorString(), mensajeError);

        return retorno;
    }

    public static Retorno checkearOk(Retorno retorno, int valor, String texto, String mensajeError) {
        Assertions.assertEquals(Retorno.Resultado.OK, retorno.getResultado(), mensajeError);
        Assertions.assertEquals(texto, retorno.getValorString(), mensajeError);
        Assertions.assertEquals(valor, retorno.getValorInteger(), mensajeError);

        return retorno;
    }

    public static Retorno checkearError(Retorno.Resultado resultado, Retorno retorno, String textoAImprimir) {
        Assertions.assertEquals(resultado, retorno.getResultado(), textoAImprimir);
        return retorno;

    }

    public static Retorno checkearError(Retorno.Resultado resultado, Retorno retorno) {
        Assertions.assertEquals(resultado, retorno.getResultado());
        return retorno;
    }


    public static Retorno checkearError1(Retorno retorno, String textoAImprimir) {
        return checkearError(Retorno.Resultado.ERROR_1, retorno, textoAImprimir);
    }

    public static Retorno checkearError2(Retorno retorno, String textoAImprimir) {
        return checkearError(Retorno.Resultado.ERROR_2, retorno, textoAImprimir);
    }

    public static Retorno checkearError3(Retorno retorno, String textoAImprimir) {
        return checkearError(Retorno.Resultado.ERROR_3, retorno, textoAImprimir);
    }

    public static Retorno checkearError4(Retorno retorno, String textoAImprimir) {
        return checkearError(Retorno.Resultado.ERROR_4, retorno, textoAImprimir);
    }

    public static Retorno checkearError5(Retorno retorno, String textoAImprimir) {
        return checkearError(Retorno.Resultado.ERROR_5, retorno, textoAImprimir);
    }

    public static Retorno checkearError6(Retorno retorno, String textoAImprimir) {
        return checkearError(Retorno.Resultado.ERROR_6, retorno, textoAImprimir);
    }
}
