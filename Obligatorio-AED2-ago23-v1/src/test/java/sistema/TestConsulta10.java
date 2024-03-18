package sistema;

import interfaz.Sistema;
import interfaz.TipoConexion;
import org.junit.jupiter.api.Test;
import sistema.auxiliares.TestCiudadDato;

import java.util.Arrays;
import java.util.Comparator;
import java.util.stream.Collectors;

import static java.lang.String.format;
import static sistema.AccionesMapa.agregueUnaCiudadCorrectamente;
import static sistema.AccionesSistema.tengoUnSistemaValido;

public class TestConsulta10 {

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
    public void testearSoloOrigenOk() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, PARIS, 0, PARIS);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, PARIS, 100, PARIS);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, PARIS, 100000, PARIS);
    }

    @Test
    public void testearSoloAdyacentes() {
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_1%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_2%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_3%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_4%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_5%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_6%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_7%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_4%5Blabel%3D%22%22%5D%3B%0A%7D%0A
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        //RIO no puede aparecer porque desde paris no hay una arista que los conecta.
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, PARIS, 1, PARIS, MADRID, PARIS_2, MVD);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, PARIS, 10, PARIS, MADRID, PARIS_2, MVD);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 1, RIO, MVD, PARIS);
    }

    @Test
    public void testearDosSaltos() {
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_1%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_2%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_3%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_4%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_5%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_6%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_7%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_6%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_4%5Blabel%3D%22%22%5D%3B%0A%7D%0A
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 2,
                RIO,//con 0 saltos
                MVD, PARIS, SANTIAGO,//con 1 salto
                PARIS_2, MADRID);
    }

    @Test
    public void testearTresSaltos() {
        //grafo
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_1%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_2%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_3%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_4%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_5%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_6%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_7%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_6%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_1%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_4%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_5%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%22%22%5D%3B%0A%7D%0A
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        agregueUnaConexionCorrectamente(s, MADRID, BERLIN);
        agregueUnaConexionCorrectamente(s, MADRID, MVD);
        agregueUnaConexionCorrectamente(s, MADRID, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, RIO);
        agregueUnaConexionCorrectamente(s, MADRID, NY);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 0,
                RIO//con 0 saltos
        );
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 1,
                RIO,//con 0 saltos
                MVD, PARIS, SANTIAGO//con 1 salto
        );
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 2,
                RIO,//con 0 saltos
                MVD, PARIS, SANTIAGO,//con 1 salto
                PARIS_2, MADRID//con 2 saltos
        );
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 3,
                RIO,//con 0 saltos
                MVD, PARIS, SANTIAGO,//con 1 salto
                PARIS_2, MADRID,//con 2 saltos
                NY, BERLIN);//con 3
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 300000,//cuidado si lo hiceron recursivo
                RIO,//con 0 saltos
                MVD, PARIS, SANTIAGO,//con 1 salto
                PARIS_2, MADRID,//con 2 saltos
                NY, BERLIN);//con 3
    }

    @Test
    public void testearNodoSinAdyacentesDesde() {
        //grafo
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_1%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_2%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_3%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_4%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_5%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_6%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_7%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_6%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_1%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_4%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_5%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%22%22%5D%3B%0A%7D%0A
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        agregueUnaConexionCorrectamente(s, MADRID, BERLIN);
        agregueUnaConexionCorrectamente(s, MADRID, MVD);
        agregueUnaConexionCorrectamente(s, MADRID, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, RIO);
        agregueUnaConexionCorrectamente(s, MADRID, NY);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, MVD, 1110,
                MVD
        );
    }

    @Test
    public void testearAgregoUnCaminoLuegoDeEjecutarLaConsulta() {
        //grafo
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_1%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_2%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_3%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_4%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_5%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_6%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_7%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_6%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_1%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_4%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_5%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%22%22%5D%3B%0A%7D%0A
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        agregueUnaConexionCorrectamente(s, MADRID, BERLIN);
        agregueUnaConexionCorrectamente(s, MADRID, MVD);
        agregueUnaConexionCorrectamente(s, MADRID, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, RIO);
        agregueUnaConexionCorrectamente(s, MADRID, NY);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, MVD, 1110,
                MVD
        );
        agregueUnaConexionCorrectamente(s, MVD, PARIS);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, MVD, 2,
                MVD,//0 saltos
                PARIS,//1 salto
                MADRID, RIO,PARIS_2//2 saltos
        );


    }

    @Test
    public void testearReinicio() {
        //grafo
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_1%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_2%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_3%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_4%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_5%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_6%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_7%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_1-%3EV_6%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_1%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_3%5Blabel%3D%22%22%5D%3B%0AV_2-%3EV_4%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_0%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_2%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_5%5Blabel%3D%22%22%5D%3B%0AV_4-%3EV_7%5Blabel%3D%22%22%5D%3B%0A%7D%0A
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        agregueUnaConexionCorrectamente(s, MADRID, BERLIN);
        agregueUnaConexionCorrectamente(s, MADRID, MVD);
        agregueUnaConexionCorrectamente(s, MADRID, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, RIO);
        agregueUnaConexionCorrectamente(s, MADRID, NY);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 3,
                RIO,//con 0 saltos
                MVD, PARIS, SANTIAGO,//con 1 salto
                PARIS_2, MADRID,//con 2 saltos
                NY, BERLIN);//con 3
        s.inicializarSistema(100);
        agregueUnaCiudadCorrectamente(s, RIO);
        agregueUnaCiudadCorrectamente(s, BERLIN);
        agregueUnaCiudadCorrectamente(s, PARIS);
        agregueUnaCiudadCorrectamente(s, PARIS_2);
        checkearConsultaTransbordosTieneLasSiguientesCiudades(s, RIO, 3,
                RIO);//Se reinicializo todo

        AuxAsserciones.checkearError4(s.listadoCiudadesCantTrasbordos(MADRID.getCodigo(), 1), "Al reinicializar se perdio la ciudad");
    }

    @Test
    public void testError1() {

        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        AuxAsserciones.checkearError1(s.listadoCiudadesCantTrasbordos(RIO.getCodigo(), -1), "La cantidad es negativa");
        AuxAsserciones.checkearError1(s.listadoCiudadesCantTrasbordos(RIO.getCodigo(), -23), "La cantidad es negativa");
    }

    @Test
    public void testError2() {

        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        AuxAsserciones.checkearError2(s.listadoCiudadesCantTrasbordos(null, 1), "El codigo es nulo");
        AuxAsserciones.checkearError2(s.listadoCiudadesCantTrasbordos(new String(), 23), "El codigo es vacio");
    }

    @Test
    public void testError3() {

        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        AuxAsserciones.checkearError3(s.listadoCiudadesCantTrasbordos("aASAAAAAAS", 1), "El codigo es invalido");
        AuxAsserciones.checkearError3(s.listadoCiudadesCantTrasbordos("AAA", 23), "El codigo es invalido");
    }

    @Test
    public void testError4() {

        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();

        agregueUnaConexionCorrectamente(s, RIO, PARIS);
        agregueUnaConexionCorrectamente(s, PARIS, MADRID);
        agregueUnaConexionCorrectamente(s, PARIS, MVD);
        agregueUnaConexionCorrectamente(s, PARIS, PARIS_2);
        agregueUnaConexionCorrectamente(s, RIO, MVD);
        agregueUnaConexionCorrectamente(s, RIO, SANTIAGO);
        AuxAsserciones.checkearError4(s.listadoCiudadesCantTrasbordos("CIUDADINEXISTENTE", 1), "El origen no existe");
    }

    private void agregueUnaConexionCorrectamente(Sistema s, TestCiudadDato origen, TestCiudadDato destino) {
        AccionesMapa.agregueUnaConexionCorrectamente(s, origen, destino, 1, 23, 22, TipoConexion.RUTA_AEREA);
    }

    private void checkearConsultaTransbordosTieneLasSiguientesCiudades(Sistema s, TestCiudadDato origen, int cantidad, TestCiudadDato... ciudadesQueDebenAparecer) {
        AuxAsserciones.checkearOk(s.listadoCiudadesCantTrasbordos(origen.getCodigo(), cantidad),
                Arrays.stream(ciudadesQueDebenAparecer)//prohibido de usar en su obligatorio
                        .sorted(Comparator.comparing(TestCiudadDato::getCodigo))
                        .map(ciudad -> format("%s;%s", ciudad.getCodigo(), ciudad.getNombre()))
                        .collect(Collectors.joining("|")), "El resultado no es el esperado");
    }


    private Sistema tengoUnSistemaValidoConTodasLasCiudades() {
        Sistema s = tengoUnSistemaValido(20);
        for (TestCiudadDato c : CIUDADES) {
            agregueUnaCiudadCorrectamente(s, c);
        }
        return s;
    }
}
