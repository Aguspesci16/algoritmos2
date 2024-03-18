package sistema;

import interfaz.Sistema;
import org.junit.jupiter.api.Test;

import static sistema.AuxAsserciones.checkearError1;
import static sistema.AuxAsserciones.checkearOk;

public class TestSistema {


    @Test
    public void checkearInicializarOk(){
        Sistema s=new ImplementacionSistema();
        checkearError1(s.inicializarSistema(2),"Deberia haber dado error");
        checkearOk(s.inicializarSistema(6),"Deberia haber funcionado");
    }
}
