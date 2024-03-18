package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import interfaz.TipoConexion;
import org.junit.jupiter.api.Test;
import sistema.auxiliares.TestCiudadDato;
import sistema.auxiliares.TestConexionDato;

import static java.lang.String.format;
import static sistema.AccionesMapa.*;
import static sistema.AccionesSistema.tengoUnSistemaValido;

public class TestRegistrosMapa {
    private static final TestCiudadDato MVD = new TestCiudadDato("MVD001", "Montevideo");
    private static final TestCiudadDato RIO = new TestCiudadDato("RIO01", "Rio de janeiro");
    private static final TestCiudadDato PARIS = new TestCiudadDato("PAR01", "Charles de Gaulle");
    private static final TestCiudadDato PARIS_2 = new TestCiudadDato("PAR002", "Orly");
    private static final TestCiudadDato MADRID = new TestCiudadDato("MADRID", "Madrid");
    private static final TestCiudadDato BERLIN = new TestCiudadDato("0001BER", "Berlin");
    private static final TestCiudadDato SANTIAGO = new TestCiudadDato("SAN232", "Santiago de Chile");
    private static final TestCiudadDato NY = new TestCiudadDato("NYNYNY", "New york");
    private static final TestCiudadDato[] CIUDADES = new TestCiudadDato[]{
            MVD,
            RIO,
            PARIS,
            PARIS_2,
            MADRID,
            BERLIN,
            SANTIAGO,
            NY
    };

    @Test
    public void registrarCiudadOk1() {
        Sistema s = tengoUnSistemaValido(10);
        //cuando
        Retorno resultado = agregoUnaCiudad(s, PARIS);
        //entonces
        AuxAsserciones.checkearOk(resultado, "La ciudad de paris deberia de haberse agregado correctamente");
        //cuando
        resultado = agregoUnaCiudad(s, BERLIN);
        //entonces
        AuxAsserciones.checkearOk(resultado, "La ciudad de Berlin deberia de haberse agregado correctamente");
    }

    @Test
    public void registrarCiudadOkTodasLasCiudades() {
        Sistema s = tengoUnSistemaValido(10);
        for (TestCiudadDato c : CIUDADES) {
            //cuando
            Retorno resultado = agregoUnaCiudad(s, c);
            //entonces
            AuxAsserciones.checkearOk(resultado, format("La ciudad de '%s' deberia de haberse agregado correctamente", c));
        }
    }

    @Test
    public void registrarCiudadOk2() {
        //probamos varios codigos
        Sistema s = tengoUnSistemaValido(100);
        //cuando
        Retorno resultado = agregoUnaCiudad(s, "23222222", "Un nombre");
        //entonces
        AuxAsserciones.checkearOk(resultado, "La ciudad deberia haberse agregado correctamente");
        //cuando
        resultado = agregoUnaCiudad(s, "AAAAAA", "nombre");
        //entonces
        AuxAsserciones.checkearOk(resultado, "La ciudad deberia haberse agregado correctamente");
        //cuando
        resultado = agregoUnaCiudad(s, "12345", "nombre");
        //entonces
        AuxAsserciones.checkearOk(resultado, "La ciudad deberia haberse agregado correctamente");
        //cuando
        resultado = agregoUnaCiudad(s, "1A3B5", "nombre");
        //entonces
        AuxAsserciones.checkearOk(resultado, "La ciudad deberia haberse agregado correctamente");
    }

    @Test
    public void registrarCiudadError1() {
        //chequeamos que se pueda insertar hasta el largo.
        Sistema s = tengoUnSistemaValido(6);
        agregueUnaCiudadCorrectamente(s, PARIS);//1
        agregueUnaCiudadCorrectamente(s, MVD);//2
        agregueUnaCiudadCorrectamente(s, PARIS_2);//3
        agregueUnaCiudadCorrectamente(s, BERLIN);//4
        agregueUnaCiudadCorrectamente(s, MADRID);//5
        agregueUnaCiudadCorrectamente(s, SANTIAGO);//6
        //cuando
        Retorno resultado = agregoUnaCiudad(s, NY);
        //entonces
        AuxAsserciones.checkearError1(resultado, "Se usaron todas las ciudades disponibles");
    }

    @Test
    public void registrarCiudadError2() {

        Sistema s = tengoUnSistemaValido(6);

        Retorno resultado = agregoUnaCiudad(s, null, "un nombre");
        AuxAsserciones.checkearError2(resultado, "El codigo es nulo");

        resultado = agregoUnaCiudad(s, "ASSD2322", null);
        AuxAsserciones.checkearError2(resultado, "El nombre es nulo");
        resultado = agregoUnaCiudad(s, "ASSD2322", new String());
        AuxAsserciones.checkearError2(resultado, "El nombre es vacio");
        resultado = agregoUnaCiudad(s, new String(""), "Test nombre");
        AuxAsserciones.checkearError2(resultado, "El codigo es vacio");
    }

    @Test
    public void registrarCiudadError3() {

        Sistema s = tengoUnSistemaValido(6);

        Retorno resultado = agregoUnaCiudad(s, "OOP", "un nombre");
        AuxAsserciones.checkearError3(resultado, "El codigo corto");

        resultado = agregoUnaCiudad(s, "aBCD2322", "un nombre");
        AuxAsserciones.checkearError3(resultado, "Hay una minuscula");

        resultado = agregoUnaCiudad(s, "BCD2322a", "un nombre");
        AuxAsserciones.checkearError3(resultado, "Hay una minuscula");

        resultado = agregoUnaCiudad(s, "BCD$2322", "un nombre");
        AuxAsserciones.checkearError3(resultado, "Hay un simbolo");

        resultado = agregoUnaCiudad(s, "abc232", "un nombre");
        AuxAsserciones.checkearError3(resultado, "minusculas");

        resultado = agregoUnaCiudad(s, "1234", "un nombre");
        AuxAsserciones.checkearError3(resultado, "Codigo corto");

    }

    @Test
    public void registrarCiudadError4() {
        Sistema s = tengoUnSistemaValido(10);
        //dado que
        agregueUnaCiudadCorrectamente(s, NY);
        agregueUnaCiudadCorrectamente(s, MVD);
        agregueUnaCiudadCorrectamente(s, MADRID);
        agregueUnaCiudadCorrectamente(s, PARIS);
        //cuando
        Retorno resultado = agregoUnaCiudad(s, PARIS);
        AuxAsserciones.checkearError4(resultado, "Ya se agrego al final");

        resultado = agregoUnaCiudad(s, NY);
        AuxAsserciones.checkearError4(resultado, "Ya se agrego al principio");

        resultado = agregoUnaCiudad(s, MADRID);
        AuxAsserciones.checkearError4(resultado, "Ya se agrego al principio");
    }

    private Sistema tengoUnSistemaValidoConTodasLasCiudades() {
        Sistema s = tengoUnSistemaValido(20);
        for (TestCiudadDato c : CIUDADES) {
            agregueUnaCiudadCorrectamente(s, c);
        }
        return s;
    }

    @Test
    public void testRegistrarConexionOk1() {
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        //cuando agrego
        Retorno resultado = agregoUnaConexion(s, NY, PARIS, 1, 23, 22, TipoConexion.RUTA_AEREA);
        AuxAsserciones.checkearOk(resultado, "La conexion es valida");
    }

    @Test
    public void testRegistrarConexionOkTodasLasConexiones() {
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        for (int i = 0; i < CIUDADES.length; i++) {
            for (int j = 0; j < CIUDADES.length; j++) {

                TestCiudadDato orig = CIUDADES[i];
                TestCiudadDato destino = CIUDADES[j];

                if (i != j) {
                    //cuando agrego
                    Retorno resultado = agregoUnaConexion(s, orig, destino, 1 + i + j, i * j + 1, Math.abs(i - j) + 1, TipoConexion.RUTA_AEREA);
                    AuxAsserciones.checkearOk(resultado, format("La conexion entre '%s' y '%s' es valida", orig, destino));
                }
            }
        }
    }

    @Test
    public void testRegistrarConexionError1() {

        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        //cuando
        Retorno resultado = agregoUnaConexion(s, PARIS, MVD, 1, 0, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El costo es 0");

        //cuando
        resultado = agregoUnaConexion(s, PARIS, MVD, 1, -12, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El costo es negativo");

        //cuando
        resultado = agregoUnaConexion(s, PARIS, MVD, 1, 12, 0, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El tiempo es 0");

        //cuando
        resultado = agregoUnaConexion(s, MADRID, PARIS_2, 1, 12, -42, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El tiempo es negativo");

        //cuando
        resultado = agregoUnaConexion(s, MADRID, PARIS_2, 1, 12, -42, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El tiempo es negativo");

    }

    @Test
    public void testRegistrarConexionError2() {

        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        //cuando
        Retorno resultado = agregoUnaConexion(s, PARIS, MVD, 1, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El tipo  es nulo");
        //cuando
        resultado = s.registrarConexion(PARIS.getCodigo(), null, 1, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de destino es nulo");
        //cuando
        resultado = s.registrarConexion(null, NY.getCodigo(), 1, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de origen es nulo");
        //cuando
        resultado = s.registrarConexion(new String(), NY.getCodigo(), 1, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de origen es vacio");
        //cuando
        resultado = s.registrarConexion(NY.getCodigo(), new String(), 1, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de destino es vacio");
    }


    @Test
    public void testRegistrarConexionError3() {

        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        //cuando
        Retorno resultado = s.registrarConexion(PARIS.getCodigo(), "1234", 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo destino no tiene el largo esperado");
        //cuando
        resultado = s.registrarConexion("aAAAA322", NY.getCodigo(), 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo de origen tiene una minuscula");
        //cuando
        resultado = s.registrarConexion("A$@#@DDSSS", NY.getCodigo(), 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo de origen es invalido");
        //cuando
        resultado = s.registrarConexion(NY.getCodigo(), "ABAEEEEe", 1, 23, 22, TipoConexion.RUTA_MARITIMA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo de destino tiene una minuscula");
    }


    @Test
    public void testRegistrarConexionError4() {
        TestCiudadDato ciudadNoAgregada = new TestCiudadDato("ALGUNCODIGO", "Con nombre");
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();


        //cuando
        Retorno resultado = agregoUnaConexion(s, ciudadNoAgregada, PARIS, 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError4(resultado, "El origen no fue agregado");

        //dado que ahora
        agregueUnaCiudadCorrectamente(s, ciudadNoAgregada);
        resultado = agregoUnaConexion(s, ciudadNoAgregada, PARIS, 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearOk(resultado, "Si se agrego deberia de funcionar");

    }

    @Test
    public void testRegistrarConexionError5() {
        TestCiudadDato ciudadNoAgregada = new TestCiudadDato("ALGUNCODIGO", "Con nombre");
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();


        //cuando
        Retorno resultado = agregoUnaConexion(s, PARIS, ciudadNoAgregada, 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError5(resultado, "El destino no fue agregado");

        //dado que ahora
        agregueUnaCiudadCorrectamente(s, ciudadNoAgregada);
        resultado = agregoUnaConexion(s, PARIS, ciudadNoAgregada, 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearOk(resultado, "Si se agrego deberia de funcionar");

    }

    @Test
    public void testRegistrarConexionError6() {
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, PARIS, MVD, 1, 23, 22, TipoConexion.RUTA_MARITIMA);

        //cuando
        Retorno resultado = agregoUnaConexion(s, PARIS, MVD, 1, 52, 11, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError6(resultado, "El id esta repetido");

        //Cuidado que el siguiente ejemplo es valido, porque la orientacion de la arista cambia.
        resultado = agregoUnaConexion(s, MVD, PARIS, 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearOk(resultado, "El id solo funciona dentro de una conexion");

        //dado que ahora agrego una ciudad correctamente
        agregueUnaCiudadCorrectamente(s, new TestCiudadDato("AASSSA222", "nombre"));
        //nada deberia cambiar con PARIS MVD y ademas MVD PARIS deberia dar repetida
        resultado = agregoUnaConexion(s, PARIS, MVD, 1, 52, 11, TipoConexion.RUTA_AEREA);
        AuxAsserciones.checkearError6(resultado, "El id esta repetido");
        resultado = agregoUnaConexion(s, MVD, PARIS, 1, 23, 22, TipoConexion.RUTA_AEREA);
        AuxAsserciones.checkearError6(resultado, "El id esta repetido");

        resultado = agregoUnaConexion(s, PARIS, MVD, 2, 52, 11, TipoConexion.RUTA_AEREA);

        AuxAsserciones.checkearOk(resultado, "Recuerden que al ser un multigrafo pueden haber varias conexiones" +
                "que van entre el mismo par de vertices, solo que las diferenciamos por su id");
    }


    @Test
    public void testActualizarConexionOk1() {
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agreguePorLoMenosUnaConexionEntreCadaParDeCiudades(s);
        //cuando
        Retorno resultado = actualizoUnaConexion(s, PARIS, MVD, 1, 50, 10, TipoConexion.RUTA_FERROVIARIA);
        //entonces
        AuxAsserciones.checkearOk(resultado, "La conexion estaba registrada");
        //cuando
        resultado = actualizoUnaConexion(s, MVD, MADRID, 1, 1, 1, TipoConexion.RUTA_MARITIMA);
        //entonces
        AuxAsserciones.checkearOk(resultado, "La conexion estaba registrada");
        //cuando
        TestCiudadDato nuevaCiudad = new TestCiudadDato("AEW223", "Un nombre");
        agregueUnaCiudadCorrectamente(s, nuevaCiudad);
        TestConexionDato nuevaConexion = agregueUnaConexionCorrectamente(s, PARIS, nuevaCiudad, 12, 23, 42, TipoConexion.RUTA_AEREA);
        resultado = actualizoUnaConexion(s, PARIS, nuevaCiudad, 12, 422, 121, TipoConexion.RUTA_MARITIMA);
        AuxAsserciones.checkearOk(resultado, "La conexion era valida");


    }

    @Test
    public void testActualizarConexionError1() {

        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        //cuando
        Retorno resultado = actualizoUnaConexion(s, PARIS, MVD, 1, 0, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El costo es 0");

        //cuando
        resultado = actualizoUnaConexion(s, PARIS, MVD, 1, -12, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El costo es negativo");

        //cuando
        resultado = actualizoUnaConexion(s, PARIS, MVD, 1, 12, 0, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El tiempo es 0");

        //cuando
        resultado = actualizoUnaConexion(s, MADRID, PARIS_2, 1, 12, -42, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El tiempo es negativo");

        //cuando
        resultado = actualizoUnaConexion(s, MADRID, PARIS_2, 1, 12, -42, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError1(resultado, "El tiempo es negativo");

    }


    @Test
    public void testActualizarConexionError2() {

        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, PARIS, MVD, 100, 25, 11, TipoConexion.OTRA_RUTA);

        //cuando
        Retorno resultado = actualizoUnaConexion(s, PARIS, MVD, 100, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El tipo  es nulo");
        //cuando
        resultado = s.actualizarConexion(PARIS.getCodigo(), null, 100, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de destino es nulo");
        //cuando
        resultado = s.actualizarConexion(null, NY.getCodigo(), 100, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de origen es nulo");
        //cuando
        resultado = s.actualizarConexion(new String(), NY.getCodigo(), 100, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de origen es vacio");
        //cuando
        resultado = s.actualizarConexion(NY.getCodigo(), new String(), 100, 23, 22, null);
        //entonces
        AuxAsserciones.checkearError2(resultado, "El codigo de destino es vacio");
    }


    @Test
    public void testActualizarConexionError3() {

        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agreguePorLoMenosUnaConexionEntreCadaParDeCiudades(s);

        //cuando
        Retorno resultado = s.actualizarConexion(PARIS.getCodigo(), "1234", 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo destino no tiene el largo esperado");
        //cuando
        resultado = s.actualizarConexion("aAAAA322", NY.getCodigo(), 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo de origen tiene una minuscula");
        //cuando
        resultado = s.actualizarConexion("A$@#@DDSSS", NY.getCodigo(), 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo de origen es invalido");
        //cuando
        resultado = s.actualizarConexion(NY.getCodigo(), "ABAEEEEe", 1, 23, 22, TipoConexion.RUTA_MARITIMA);
        //entonces
        AuxAsserciones.checkearError3(resultado, "El codigo de destino tiene una minuscula");
    }


    @Test
    public void testActualizarConexionError4() {
        TestCiudadDato ciudadNoAgregada = new TestCiudadDato("ALGUNCODIGO", "Con nombre");
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        //cuando
        Retorno resultado = actualizoUnaConexion(s, ciudadNoAgregada, PARIS, 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError4(resultado, "El origen no fue agregado");
    }

    @Test
    public void testActualizarConexionError5() {
        TestCiudadDato ciudadNoAgregada = new TestCiudadDato("ALGUNCODIGO", "Con nombre");
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        //cuando
        Retorno resultado = actualizoUnaConexion(s, PARIS, ciudadNoAgregada, 1, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError5(resultado, "El destino no fue agregado");

    }

    @Test
    public void testActualizarConexionError6() {
        //dado que
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agreguePorLoMenosUnaConexionEntreCadaParDeCiudades(s);

        TestCiudadDato ciudadSinConexiones = new TestCiudadDato("AAAAAAA", "Nombre");
        TestCiudadDato ciudadSinConexiones2 = new TestCiudadDato("BBBBBB", "Nombre");
        agregueUnaCiudadCorrectamente(s,ciudadSinConexiones);
        agregueUnaCiudadCorrectamente(s,ciudadSinConexiones2);
        //cuando
        Retorno resultado = actualizoUnaConexion(s, PARIS, MVD, 211, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError6(resultado, "La conexion no existe por el id");

        //cuando
         resultado = actualizoUnaConexion(s, PARIS,ciudadSinConexiones, 211, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError6(resultado, "No hay ninguna conexion de paris a la ciudad nueva");

        //cuando
        resultado = actualizoUnaConexion(s, ciudadSinConexiones2,ciudadSinConexiones, 211, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearError6(resultado, "No hay conexion entre las ciudades");
        //dado que
        agregueUnaConexionCorrectamente(s,ciudadSinConexiones2,ciudadSinConexiones,211,222,22,TipoConexion.RUTA_AEREA);
        //cuando
        resultado = actualizoUnaConexion(s, ciudadSinConexiones2,ciudadSinConexiones, 211, 23, 22, TipoConexion.RUTA_AEREA);
        //entonces
        AuxAsserciones.checkearOk(resultado, "Una vez registrada la operacion debe funcjonar");
    }


    private void agreguePorLoMenosUnaConexionEntreCadaParDeCiudades(Sistema s) {
        for (int i = 0; i < CIUDADES.length; i++) {
            for (int j = 0; j < CIUDADES.length; j++) {
                if (i != j) {
                    agregueUnaConexionCorrectamente(s, CIUDADES[i], CIUDADES[j], 1, 23, 42, TipoConexion.RUTA_AEREA);
                }
            }
        }
    }

}
