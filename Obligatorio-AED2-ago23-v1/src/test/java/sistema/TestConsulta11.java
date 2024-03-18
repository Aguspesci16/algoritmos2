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

public class TestConsulta11 {


    private static final TestCiudadDato MVD = new TestCiudadDato("MVD001", "Montevideo");
    private static final TestCiudadDato RIO = new TestCiudadDato("RIO01", "Rio de janeiro");
    private static final TestCiudadDato PARIS = new TestCiudadDato("PAR01", "Charles de Gaulle");
    private static final TestCiudadDato PARIS_2 = new TestCiudadDato("PAR002", "Orly");
    private static final TestCiudadDato MADRID = new TestCiudadDato("MADRID", "Madrid");
    private static final TestCiudadDato BERLIN = new TestCiudadDato("0001BER", "Berlin");
    private static final TestCiudadDato SANTIAGO = new TestCiudadDato("SAN232", "Santiago de Chile");
    private static final TestCiudadDato NY = new TestCiudadDato("NYNYNY", "New york");
    private static final TestCiudadDato WASHINGTON = new TestCiudadDato("WAHS11", "Washington");
    private static final TestCiudadDato TOKYO = new TestCiudadDato("TOKYO", "Tokio");
    private static final TestCiudadDato[] CIUDADES = new TestCiudadDato[]{
            BERLIN,
            MADRID,
            TOKYO,
            SANTIAGO,
            WASHINGTON,
            PARIS,
            MVD,
            PARIS_2,
            RIO,
            NY
    };

    @Test
    public void testearCaminosMinimos1() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        //grafo inicial: https://dreampuf.github.io/GraphvizOnline/#graph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_2--V_0%5Blabel%3D%222.0%22%5D%3B%0AV_3--V_0%5Blabel%3D%225.0%22%5D%3B%0AV_3--V_1%5Blabel%3D%2243.0%22%5D%3B%0AV_4--V_1%5Blabel%3D%2260.0%22%5D%3B%0AV_4--V_2%5Blabel%3D%22211.0%22%5D%3B%0AV_5--V_0%5Blabel%3D%2215.0%22%5D%3B%0AV_6--V_2%5Blabel%3D%2211.0%22%5D%3B%0AV_7--V_1%5Blabel%3D%2243.0%22%5D%3B%0AV_7--V_5%5Blabel%3D%2221.0%22%5D%3B%0AV_7--V_6%5Blabel%3D%2215.0%22%5D%3B%0AV_8--V_3%5Blabel%3D%2243.0%22%5D%3B%0AV_8--V_6%5Blabel%3D%2221.0%22%5D%3B%0AV_9--V_4%5Blabel%3D%22264.0%22%5D%3B%0AV_9--V_5%5Blabel%3D%2221.0%22%5D%3B%0AV_9--V_8%5Blabel%3D%2256.0%22%5D%3B%0A%7D%0A
        TestConexionDato berlin_paris = agregueUnaBidireccionalConexionCorrectamente(s, BERLIN, PARIS, 1, 5, 15, TipoConexion.RUTA_AEREA);
        TestConexionDato berlin_tokyo = agregueUnaBidireccionalConexionCorrectamente(s, BERLIN, TOKYO, 1, 2, 2, TipoConexion.RUTA_AEREA);
        TestConexionDato berlin_santiago = agregueUnaBidireccionalConexionCorrectamente(s, BERLIN, SANTIAGO, 1, 3, 5, TipoConexion.RUTA_AEREA);

        TestConexionDato madrid_washington = agregueUnaBidireccionalConexionCorrectamente(s, MADRID, WASHINGTON, 1, 11, 60, TipoConexion.RUTA_AEREA);
        TestConexionDato madrid_paris_2 = agregueUnaBidireccionalConexionCorrectamente(s, MADRID, PARIS_2, 1, 9, 43, TipoConexion.RUTA_AEREA);
        TestConexionDato madrid_santiago = agregueUnaBidireccionalConexionCorrectamente(s, MADRID, SANTIAGO, 1, 9, 43, TipoConexion.RUTA_AEREA);

        TestConexionDato santiago_rio = agregueUnaBidireccionalConexionCorrectamente(s, SANTIAGO, RIO, 1, 5, 43, TipoConexion.RUTA_AEREA);
        TestConexionDato washington_tokyo = agregueUnaBidireccionalConexionCorrectamente(s, WASHINGTON, TOKYO, 1, 22, 211, TipoConexion.RUTA_AEREA);

        TestConexionDato mvd_tokyo = agregueUnaBidireccionalConexionCorrectamente(s, MVD, TOKYO, 1, 9, 11, TipoConexion.RUTA_AEREA);
        TestConexionDato washington_ny = agregueUnaBidireccionalConexionCorrectamente(s, WASHINGTON, NY, 1, 1, 264, TipoConexion.RUTA_AEREA);

        TestConexionDato paris_paris_2 = agregueUnaBidireccionalConexionCorrectamente(s, PARIS, PARIS_2, 1, 6, 21, TipoConexion.RUTA_AEREA);
        TestConexionDato paris_ny = agregueUnaBidireccionalConexionCorrectamente(s, PARIS, NY, 1, 4, 21, TipoConexion.RUTA_AEREA);
        TestConexionDato rio_ny = agregueUnaBidireccionalConexionCorrectamente(s, RIO, NY, 1, 12, 56, TipoConexion.RUTA_AEREA);
        TestConexionDato mvd_rio = agregueUnaBidireccionalConexionCorrectamente(s, MVD, RIO, 1, 15, 21, TipoConexion.RUTA_AEREA);
        TestConexionDato paris_2_mvd = agregueUnaBidireccionalConexionCorrectamente(s, PARIS_2, MVD, 1, 8, 15, TipoConexion.RUTA_AEREA);
        chequearCaminoMasCortoEsCorrecto(s, BERLIN, PARIS, berlin_paris);
        chequearCaminoMasCortoEsCorrecto(s, BERLIN, NY, berlin_paris, paris_ny);

        actualizoUnaConexion(s, PARIS, NY, 1, 100, 1023, TipoConexion.RUTA_MARITIMA);
        chequearCaminoMasCortoEsCorrecto(s, BERLIN, NY, berlin_tokyo, mvd_tokyo.invertir(), mvd_rio, rio_ny);
    }

    @Test
    public void testearCaminosMinimosConValoresAltos() {

        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(PARIS.getCodigo(), MVD.getCodigo()), "No existe la ciudad");

        double base = 2e305; // un numero excesivamente alto
        TestConexionDato paris_berlin = agregueUnaConexionCorrectamente(s, PARIS, BERLIN, 1, 40, 2 * base, TipoConexion.RUTA_AEREA);
        TestConexionDato paris_madrid = agregueUnaConexionCorrectamente(s, PARIS, MADRID, 1, 10, 5 * base, TipoConexion.RUTA_AEREA);
        TestConexionDato madrid_rio = agregueUnaConexionCorrectamente(s, MADRID, RIO, 1, 10, 10 * base, TipoConexion.RUTA_AEREA);
        TestConexionDato rio_mvd = agregueUnaConexionCorrectamente(s, RIO, MVD, 1, 10, 11 * base, TipoConexion.RUTA_AEREA);
        TestConexionDato mvd_madrid = agregueUnaConexionCorrectamente(s, MVD, MADRID, 1, 10, 3 * base, TipoConexion.RUTA_AEREA);
        chequearCaminoMasCortoEsCorrecto(s, PARIS, MVD, paris_madrid, madrid_rio, rio_mvd);


    }

    @Test
    public void testearCaminosMinimosError1() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        AuxAsserciones.checkearError1(s.viajeCostoMinimo(new String(), "ASAAAAAA"), "Codigo vacio");
        AuxAsserciones.checkearError1(s.viajeCostoMinimo("ASAAAAAA", new String()), "Codigo vacio");
        AuxAsserciones.checkearError1(s.viajeCostoMinimo("ASAAAAA", null), "Codigo nulo");
        AuxAsserciones.checkearError1(s.viajeCostoMinimo(null, "ASAAAAA"), "Codigo nulo");
    }

    @Test
    public void testearCaminosMinimosError2() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        AuxAsserciones.checkearError2(s.viajeCostoMinimo("A", "ASAAAAAA"), "Codigo corto");
        AuxAsserciones.checkearError2(s.viajeCostoMinimo("ASAAAAAA", "AAAAAAAAa"), "Codigo invalido");
        AuxAsserciones.checkearError2(s.viajeCostoMinimo("ASAaAA322AA", "123"), "Codigo invalido");
        AuxAsserciones.checkearError2(s.viajeCostoMinimo("123455", "AS#AAAA%A"), "Codigo invalido");
    }

    @Test
    public void testearCaminosMinimosError3() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(PARIS.getCodigo(), MVD.getCodigo()), "No existe la ciudad");
        agregoUnaConexion(s, PARIS, BERLIN, 1, 10, 100, TipoConexion.RUTA_AEREA);
        agregoUnaConexion(s, PARIS, MADRID, 1, 10, 100, TipoConexion.RUTA_AEREA);
        agregoUnaConexion(s, MADRID, RIO, 1, 10, 100, TipoConexion.RUTA_AEREA);
        agregoUnaConexion(s, RIO, MVD, 1, 10, 100, TipoConexion.RUTA_AEREA);
        agregoUnaConexion(s, MVD, MADRID, 1, 10, 100, TipoConexion.RUTA_AEREA);

        //grafo
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_8%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_0%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_8-%3EV_6%5Blabel%3D%22100.0%22%5D%3B%0A%7D%0A
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(MVD.getCodigo(), PARIS.getCodigo()), "El camino inverso existe");
        AuxAsserciones.checkearOk(s.viajeCostoMinimo(PARIS.getCodigo(), MVD.getCodigo()), "El camino inverso existe");
    }

    @Test
    public void testearCaminosMinimosConComponentesConexas() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(PARIS.getCodigo(), MVD.getCodigo()), "No existe la ciudad");
        TestConexionDato paris_berlin = agregueUnaConexionCorrectamente(s, PARIS, BERLIN, 1, 10, 100, TipoConexion.RUTA_AEREA);
        TestConexionDato paris_madrid = agregueUnaConexionCorrectamente(s, PARIS, MADRID, 1, 10, 100, TipoConexion.RUTA_AEREA);
        TestConexionDato madrid_rio = agregueUnaConexionCorrectamente(s, MADRID, RIO, 1, 10, 100, TipoConexion.RUTA_AEREA);
        TestConexionDato rio_mvd = agregueUnaConexionCorrectamente(s, RIO, MVD, 1, 10, 100, TipoConexion.RUTA_AEREA);
        TestConexionDato mvd_madrid = agregueUnaConexionCorrectamente(s, MVD, MADRID, 1, 10, 100, TipoConexion.RUTA_AEREA);

        TestConexionDato washington_ny = agregueUnaConexionCorrectamente(s, WASHINGTON, NY, 1, 23, 24, TipoConexion.RUTA_MARITIMA);
        TestConexionDato ny_tokyo = agregueUnaConexionCorrectamente(s, NY, TOKYO, 1, 23, 24, TipoConexion.RUTA_MARITIMA);
        TestConexionDato tokyo_ny = agregueUnaConexionCorrectamente(s, TOKYO, NY, 1, 23, 24, TipoConexion.RUTA_MARITIMA);
        TestConexionDato paris_2_ny = agregueUnaConexionCorrectamente(s, PARIS_2, NY, 1, 23, 24, TipoConexion.RUTA_MARITIMA);

        //grafo
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_8%5Blabel%3D%22100.0%22%5D%3B%0AV_2-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_4-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_5-%3EV_0%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_7-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_8-%3EV_6%5Blabel%3D%22100.0%22%5D%3B%0AV_9-%3EV_2%5Blabel%3D%2224.0%22%5D%3B%0A%7D%0A
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(MVD.getCodigo(), PARIS.getCodigo()), "El camino inverso existe");
        AuxAsserciones.checkearOk(s.viajeCostoMinimo(PARIS.getCodigo(), MVD.getCodigo()), "El camino inverso existe");
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(PARIS.getCodigo(), WASHINGTON.getCodigo()), "El camino no exista esta en otra componente conexa");

        TestConexionDato madrid_santiago = agregueUnaConexionCorrectamente(s, MADRID, SANTIAGO, 1, 10, 100, TipoConexion.RUTA_AEREA);
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_3%5Blabel%3D%22100.0%22%5D%3B%0AV_1-%3EV_8%5Blabel%3D%22100.0%22%5D%3B%0AV_2-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_4-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_5-%3EV_0%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_7-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_8-%3EV_6%5Blabel%3D%22100.0%22%5D%3B%0AV_9-%3EV_2%5Blabel%3D%2224.0%22%5D%3B%0A%7D%0A
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(PARIS.getCodigo(), WASHINGTON.getCodigo()), "El camino no exista esta en otra componente conexa");
        //ahora conectamos santiago con la otra componente conexa
        TestConexionDato santiago_tokyo = agregueUnaConexionCorrectamente(s, SANTIAGO, TOKYO, 1, 10, 100, TipoConexion.RUTA_AEREA);
        AuxAsserciones.checkearError3(s.viajeCostoMinimo(PARIS.getCodigo(), WASHINGTON.getCodigo()), "Las componentes estan unidas, pero no hay camino por las orientaciones");
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_3%5Blabel%3D%22100.0%22%5D%3B%0AV_1-%3EV_8%5Blabel%3D%22100.0%22%5D%3B%0AV_2-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22100.0%22%5D%3B%0AV_4-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_5-%3EV_0%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_7-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_8-%3EV_6%5Blabel%3D%22100.0%22%5D%3B%0AV_9-%3EV_2%5Blabel%3D%2224.0%22%5D%3B%0A%7D%0A
        TestConexionDato ny_paris_2 = agregueUnaConexionCorrectamente(s, NY, PARIS_2, 1, 10, 100, TipoConexion.RUTA_AEREA);
        TestConexionDato paris_2_washington = agregueUnaConexionCorrectamente(s, PARIS_2, WASHINGTON, 1, 10, 100, TipoConexion.RUTA_AEREA);
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_3%5Blabel%3D%22100.0%22%5D%3B%0AV_1-%3EV_8%5Blabel%3D%22100.0%22%5D%3B%0AV_2-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22100.0%22%5D%3B%0AV_4-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_5-%3EV_0%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_7-%3EV_4%5Blabel%3D%22100.0%22%5D%3B%0AV_7-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_8-%3EV_6%5Blabel%3D%22100.0%22%5D%3B%0AV_9-%3EV_2%5Blabel%3D%2224.0%22%5D%3B%0AV_9-%3EV_7%5Blabel%3D%22100.0%22%5D%3B%0A%7D%0A
        chequearCaminoMasCortoEsCorrecto(s, PARIS, WASHINGTON, paris_madrid, madrid_santiago, santiago_tokyo, tokyo_ny, ny_paris_2, paris_2_washington);

        TestConexionDato a_1_mvd_paris_2 = agregueUnaConexionCorrectamente(s, MVD, PARIS_2, 1, 10, 600, TipoConexion.RUTA_AEREA);
        TestConexionDato a_2_mvd_paris_2 = agregueUnaConexionCorrectamente(s, MVD, PARIS_2, 2, 10, 200, TipoConexion.RUTA_AEREA);
        TestConexionDato a_3_mvd_paris_2 = agregueUnaConexionCorrectamente(s, MVD, PARIS_2, 3, 10, 550, TipoConexion.RUTA_AEREA);
        TestConexionDato a_4_mvd_paris_2 = agregueUnaConexionCorrectamente(s, MVD, PARIS_2, 4, 10, 400, TipoConexion.RUTA_AEREA);
        //no cambia nada, la aristass agregadas tiene un costo altisimo
        chequearCaminoMasCortoEsCorrecto(s, PARIS, WASHINGTON, paris_madrid, madrid_santiago, santiago_tokyo, tokyo_ny, ny_paris_2, paris_2_washington);
        //Obligamos a pasar ahora si por a_3
        a_3_mvd_paris_2 = actualiceUnaConexionCorrectamente(s, a_3_mvd_paris_2.withTiempo(1).withTipo(TipoConexion.OTRA_RUTA));
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_3%5Blabel%3D%22100.0%22%5D%3B%0AV_1-%3EV_8%5Blabel%3D%22100.0%22%5D%3B%0AV_2-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22100.0%22%5D%3B%0AV_4-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_5-%3EV_0%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22400.0%2C1.0%2C200.0%2C600.0%22%5D%3B%0AV_7-%3EV_4%5Blabel%3D%22100.0%22%5D%3B%0AV_7-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_8-%3EV_6%5Blabel%3D%22100.0%22%5D%3B%0AV_9-%3EV_2%5Blabel%3D%2224.0%22%5D%3B%0AV_9-%3EV_7%5Blabel%3D%22100.0%22%5D%3B%0A%7D%0A
        chequearCaminoMasCortoEsCorrecto(s, PARIS, WASHINGTON, paris_madrid, madrid_rio, rio_mvd, a_3_mvd_paris_2, paris_2_washington);
        a_3_mvd_paris_2 = actualiceUnaConexionCorrectamente(s, a_3_mvd_paris_2.withTiempo(1200));
        //al volverla deberia volver a dar el mismo camino
        chequearCaminoMasCortoEsCorrecto(s, PARIS, WASHINGTON, paris_madrid, madrid_santiago, santiago_tokyo, tokyo_ny, ny_paris_2, paris_2_washington);

        TestConexionDato paris_washington = agregueUnaConexionCorrectamente(s, PARIS, WASHINGTON, 1, 20, 300, TipoConexion.RUTA_FERROVIARIA);
        chequearCaminoMasCortoEsCorrecto(s, PARIS, TOKYO, paris_madrid, madrid_santiago, santiago_tokyo);
        //no voy en tren voy en avion
        TestConexionDato paris_washington_2 = agregueUnaConexionCorrectamente(s, PARIS, WASHINGTON, 2, 20, 10, TipoConexion.RUTA_AEREA);
        //https://dreampuf.github.io/GraphvizOnline/#digraph%20G%7B%0AV_0%5Blabel%3D%220001BER%3BBerlin%22%5D%3B%0AV_1%5Blabel%3D%22MADRID%3BMadrid%22%5D%3B%0AV_2%5Blabel%3D%22TOKYO%3BTokio%22%5D%3B%0AV_3%5Blabel%3D%22SAN232%3BSantiago%20de%20Chile%22%5D%3B%0AV_4%5Blabel%3D%22WAHS11%3BWashington%22%5D%3B%0AV_5%5Blabel%3D%22PAR01%3BCharles%20de%20Gaulle%22%5D%3B%0AV_6%5Blabel%3D%22MVD001%3BMontevideo%22%5D%3B%0AV_7%5Blabel%3D%22PAR002%3BOrly%22%5D%3B%0AV_8%5Blabel%3D%22RIO01%3BRio%20de%20janeiro%22%5D%3B%0AV_9%5Blabel%3D%22NYNYNY%3BNew%20york%22%5D%3B%0AV_1-%3EV_3%5Blabel%3D%22100.0%22%5D%3B%0AV_1-%3EV_8%5Blabel%3D%22100.0%22%5D%3B%0AV_2-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_3-%3EV_2%5Blabel%3D%22100.0%22%5D%3B%0AV_4-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_5-%3EV_0%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_5-%3EV_4%5Blabel%3D%2210.0%2C300.0%22%5D%3B%0AV_6-%3EV_1%5Blabel%3D%22100.0%22%5D%3B%0AV_6-%3EV_7%5Blabel%3D%22400.0%2C1200.0%2C200.0%2C600.0%22%5D%3B%0AV_7-%3EV_4%5Blabel%3D%22100.0%22%5D%3B%0AV_7-%3EV_9%5Blabel%3D%2224.0%22%5D%3B%0AV_8-%3EV_6%5Blabel%3D%22100.0%22%5D%3B%0AV_9-%3EV_2%5Blabel%3D%2224.0%22%5D%3B%0AV_9-%3EV_7%5Blabel%3D%22100.0%22%5D%3B%0A%7D%0A
        chequearCaminoMasCortoEsCorrecto(s, PARIS, TOKYO, paris_washington_2, washington_ny, ny_tokyo);
        TestConexionDato berlin_paris_2 = agregueUnaConexionCorrectamente(s, BERLIN, PARIS_2, 1, 10, 10, TipoConexion.OTRA_RUTA);
        TestConexionDato paris_2_santiago = agregueUnaConexionCorrectamente(s, PARIS_2, SANTIAGO, 1, 10, 10, TipoConexion.OTRA_RUTA);
        chequearCaminoMasCortoEsCorrecto(s, PARIS, TOKYO, paris_washington_2, washington_ny, ny_tokyo);
        TestConexionDato a2_paris_berlin = agregueUnaBidireccionalConexionCorrectamente(s, PARIS, BERLIN, 3, 10, 5, TipoConexion.OTRA_RUTA);
        TestConexionDato a3_paris_berlin = agregueUnaBidireccionalConexionCorrectamente(s, PARIS, BERLIN, 4, 10, 1, TipoConexion.OTRA_RUTA);
        chequearCaminoMasCortoEsCorrecto(s, PARIS, TOKYO, paris_washington_2, washington_ny, ny_tokyo);
        santiago_tokyo = actualiceUnaConexionCorrectamente(s, santiago_tokyo.withTiempo(10));
        chequearCaminoMasCortoEsCorrecto(s, PARIS, TOKYO, a3_paris_berlin, berlin_paris_2, paris_2_santiago, santiago_tokyo);
    }

    @Test
    public void testearCaminosMinimosError4() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        AuxAsserciones.checkearError4(s.viajeCostoMinimo("OTRACIUDADQUENOEXISTE", MVD.getCodigo()), "No existe la ciudad");
    }

    @Test
    public void testearCaminosMinimosError5() {
        Sistema s = tengoUnSistemaValidoConTodasLasCiudades();
        AuxAsserciones.checkearError5(s.viajeCostoMinimo(MVD.getCodigo(), "OTRACIUDADQUENOEXISTE"), "No existe la ciudad");
    }


    private void chequearCaminoMasCortoEsCorrecto(Sistema s, TestCiudadDato origen, TestCiudadDato destino, TestConexionDato... conexionesAUsar) {

        double largoTotal = 0;
        StringBuilder sb = new StringBuilder(format("%s;%s", origen.getCodigo(), origen.getNombre()));
        for (TestConexionDato con : conexionesAUsar) {
            largoTotal += con.getTiempo();
            sb.append("|");
            sb.append(con.getTipo());
            sb.append("|");
            sb.append(format("%s;%s", con.getCiudadDestino().getCodigo(), con.getCiudadDestino().getNombre()));

        }
        Retorno resultado = s.viajeCostoMinimo(origen.getCodigo(), destino.getCodigo());
        AuxAsserciones.checkearOk(resultado, (int) largoTotal, sb.toString(),
                format("El viaje de cost minimo no es el correcto (costo devuelto: %d, costo esperado: %d )", resultado.getValorInteger(), (int) largoTotal));

    }

    private TestConexionDato agregueUnaBidireccionalConexionCorrectamente(Sistema s, TestCiudadDato origen, TestCiudadDato destino, int id, int costo, int tiempo, TipoConexion tipoConexion) {
        agregueUnaConexionCorrectamente(s, destino, origen, id, costo, tiempo, tipoConexion);
        return agregueUnaConexionCorrectamente(s, origen, destino, id, costo, tiempo, tipoConexion);
    }


    private Sistema tengoUnSistemaValidoConTodasLasCiudades() {
        Sistema s = tengoUnSistemaValido(20);
        for (TestCiudadDato c : CIUDADES) {
            agregueUnaCiudadCorrectamente(s, c);
        }
        return s;
    }
}

