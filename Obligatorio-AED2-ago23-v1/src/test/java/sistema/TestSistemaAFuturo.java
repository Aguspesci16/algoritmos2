package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

public class TestSistemaAFuturo {

    @Test
    public void testPruebas() {

    }

    @Test
    public void testAFuturo() {
        Sistema s = new ImplementacionSistema();
        Assertions.assertEquals(Retorno.ok(), s.inicializarSistema(500));
    }

}
