package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import interfaz.TipoViajero;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import sistema.auxiliares.TestViajeroDato;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.Stream;

import static java.lang.String.format;
import static sistema.AccionesPasajero.agregoElViajero;
import static sistema.AccionesPasajero.agregueUnViajero;
import static sistema.AccionesSistema.tengoUnSistemaValido;
import static sistema.AuxAsserciones.*;

public class TestViajeros {

    private static final TestViajeroDato[] VIAJEROS_VALIDOS_ORDENADOS = new TestViajeroDato[]{
            new TestViajeroDato("132.322-2", "Romina", 23, TipoViajero.CASUAL),//1
            new TestViajeroDato("152.122-8", "Raul", 23, TipoViajero.CASUAL),//2
            new TestViajeroDato("832.322-2", "Jorgen", 23, TipoViajero.PREMIUM),//0
            new TestViajeroDato("1.523.221-2", "Sofia", 23, TipoViajero.CASUAL),//3
            new TestViajeroDato("1.832.322-2", "Felipe", 23, TipoViajero.PREMIUM),//4
            new TestViajeroDato("2.242.322-2", "Florencia", 23, TipoViajero.CASUAL),//5
            new TestViajeroDato("3.532.322-2", "Lucia", 23, TipoViajero.CASUAL),//6
            new TestViajeroDato("4.212.112-2", "Julio", 23, TipoViajero.PREMIUM),//7
            new TestViajeroDato("8.342.352-2", "Pedro", 23, TipoViajero.CASUAL),//8
            new TestViajeroDato("9.255.322-2", "Marina", 23, TipoViajero.CASUAL)//9
    };
    private static final TestViajeroDato[] VIAJEROS_VALIDOS_ORDENADOS_AL_REVES;

    static {
        VIAJEROS_VALIDOS_ORDENADOS_AL_REVES = new TestViajeroDato[VIAJEROS_VALIDOS_ORDENADOS.length];
        for (int i = 0; i < VIAJEROS_VALIDOS_ORDENADOS.length; i++) {
            VIAJEROS_VALIDOS_ORDENADOS_AL_REVES[i] = VIAJEROS_VALIDOS_ORDENADOS[VIAJEROS_VALIDOS_ORDENADOS.length - i - 1];
        }
    }

    @Test
    public void testAgregarOk() {
        //dado que
        Sistema s = tengoUnSistemaValido();
        for (int i = 0; i < VIAJEROS_VALIDOS_ORDENADOS.length; i++) {

            //cuando
            TestViajeroDato viajeroValido = VIAJEROS_VALIDOS_ORDENADOS[i];
            Retorno resultado = agregoElViajero(s, viajeroValido.copia());
            //entonces
            checkearOk(resultado, format("El viajero '%s' deberia haberse agregado correctamente", viajeroValido));
        }
    }

    @Test
    public void testAgregarError1() {
        //dado que
        Sistema s = tengoUnSistemaValido();
        TestViajeroDato viajeroValido = new TestViajeroDato("3.211.322-3", "Algun nombre", 23, TipoViajero.CASUAL);
        agregueUnViajero(s, viajeroValido);
        //cuando
        TestViajeroDato viajeroNombreNullo = new TestViajeroDato("3.222.333-2", null, 23, TipoViajero.CASUAL);
        Retorno resultado = agregoElViajero(s, viajeroNombreNullo);
        elPasajeroNoTendriaQueExistirEnElSistema(s, viajeroNombreNullo.getCedula());
        //entonces
        checkearError1(resultado, "El nombre era nulo");

        //cuando
        TestViajeroDato viajeroNombreVacio = new TestViajeroDato("3.222.211-2", new String(""), 23, TipoViajero.ESTANDAR);
        resultado = agregoElViajero(s, viajeroNombreVacio);
        //entonces
        checkearError1(resultado, "El nombre era vacio");
        elPasajeroNoTendriaQueExistirEnElSistema(s, viajeroNombreVacio.getCedula());
        //cuando
        TestViajeroDato viajeroCedulaVacia = new TestViajeroDato(new String(""), "el nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaVacia);
        //entonces
        checkearError1(resultado, "La cedula es vacia");
        //cuando
        TestViajeroDato viajeroTipoNulo = new TestViajeroDato("4.223.222-3", "Un nombre", 24, null);
        resultado = agregoElViajero(s, viajeroTipoNulo);
        elPasajeroNoTendriaQueExistirEnElSistema(s, viajeroTipoNulo.getCedula());
        //entonces
        checkearError1(resultado, "El tipo era nulo");

    }

    private void elPasajeroNoTendriaQueExistirEnElSistema(Sistema s, String cedula) {
        checkearError2(s.buscarViajero(cedula), format("El pasajero con cedula '%s' no deberia de estar registrado", cedula));
    }

    @Test
    public void testAgregarError2() {

        //dado que
        Sistema s = tengoUnSistemaValido();
        TestViajeroDato viajeroValido = new TestViajeroDato("3.211.322-3", "Algun nombre", 23, TipoViajero.CASUAL);
        agregueUnViajero(s, viajeroValido);
        //cuando
        TestViajeroDato viajeroCedulaInvalida = new TestViajeroDato("algoquenosepareceaunacedula", "Un nombre", 23, TipoViajero.CASUAL);
        Retorno resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("2.323222-3", "Un nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("2#323#222-3", "Un nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "En las regexp el . significa cualquier caracter");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("40.232.222-2", "Un nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("0.232.222-2", "Un nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("332.222-", "Algun nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("2.3.2.1.2", "Otro nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("ba2.342.233-2", "Otro nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("2.342.233-2222", "El nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");

        //cuando
        viajeroCedulaInvalida = new TestViajeroDato("2.3\422.233-2222", "Nombre", 23, TipoViajero.CASUAL);
        resultado = agregoElViajero(s, viajeroCedulaInvalida);
        //entonces
        checkearError2(resultado, "LA cedula no tiene el formato valido");
    }


    @Test
    public void testAgregarError3() {

        //dado que
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);

        for (int i = 0; i < VIAJEROS_VALIDOS_ORDENADOS.length; i++) {

            //cuando
            TestViajeroDato viajeroValido = VIAJEROS_VALIDOS_ORDENADOS[i].copia();
            Retorno resultado = agregoElViajero(s, viajeroValido);
            //entonces
            checkearError3(resultado, format("El pasajero con la cedula '%s' esta repetido[%d]", viajeroValido.getCedula(), i));
        }
    }

    @Test
    public void testBuscarPasajerosOk1() {

        //dado que
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        for (TestViajeroDato viajeroABuscar : VIAJEROS_VALIDOS_ORDENADOS) {
            //entonces cuando
            Retorno resultado = s.buscarViajero(new String(viajeroABuscar.getCedula()));
            //deberia
            checkearOk(resultado, format("El viajero con cedula '%s' se reigstro, y debería de haberse agregado", viajeroABuscar.getCedula()));
            Assertions.assertEquals(viajeroABuscar, TestViajeroDato.fromString(resultado.getValorString()));
        }

    }

    @Test
    public void testBuscarPasajerosOk2() {

        //dado que
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        for (TestViajeroDato viajeroABuscar : VIAJEROS_VALIDOS_ORDENADOS) {
            //entonces cuando
            Retorno resultado = s.buscarViajero(new String(viajeroABuscar.getCedula()));
            //deberia
            checkearOk(resultado, format("El viajero con cedula '%s' se reigstro, y debería de haberse agregado", viajeroABuscar.getCedula()));
            Assertions.assertEquals(viajeroABuscar, TestViajeroDato.fromString(resultado.getValorString()));
        }

    }

    @Test
    public void testBuscarPasajerosOkIteraciones() {
        Sistema s = tengoUnSistemaValido();

        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);

        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[2], 0);//raiz
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[3], 1);//derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[1], 1);//izquierda
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[6], 2);//derecha, derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[4], 3);//derecha, derecha, izquierda
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[5], 4);// derecha, derecha,izquierda,derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[9], 3);// derecha, derecha,derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[7], 4);// derecha, derecha,derecha,izquieda
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[8], 5);// derecha, derecha,derecha,izquieda,derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[0], 2);//izquierda,izquierda

    }

    @Test
    public void testConReinicio() {
        Sistema s = tengoUnSistemaValido();
        for (TestViajeroDato v : VIAJEROS_VALIDOS_ORDENADOS) {
            agregueUnViajero(s, v);
        }
        //dado que tengo un sistema con los pasajeros agregados en orden ascendente
        //chequeamos que el arbol haya quedado degenerado en una lista

        for (int i = 0; i < VIAJEROS_VALIDOS_ORDENADOS.length; i++) {

            chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[i], i);
        }
        //chequeamos que todas las consultas funcionen correctamente.
        AuxAsserciones.checkearOk(s.listarViajerosAscendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS), "Deberian estar todos");
        AuxAsserciones.checkearOk(s.listarViajerosDescendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS_AL_REVES), "Deberian estar todos");
        AuxAsserciones.checkearOk(s.listarViajerosPorTipo(TipoViajero.PREMIUM), viajerosToString(Arrays.stream(VIAJEROS_VALIDOS_ORDENADOS)
                .filter(v -> v.getTipoViajero() == TipoViajero.PREMIUM)), "Deberian estar todos");
        //reinicializamos el sistema chequeamos que todo se haya limpiado
        s.inicializarSistema(20);

        for (int i = 0; i < VIAJEROS_VALIDOS_ORDENADOS.length; i++) {
            AuxAsserciones.checkearError2(s.buscarViajero(VIAJEROS_VALIDOS_ORDENADOS[i].getCedula()), "Al reinicializar no deberia haber memoria del sistema anterior");
        }
        AuxAsserciones.checkearOk(s.listarViajerosAscendente(),"","Tiene que ser vacio");
        AuxAsserciones.checkearOk(s.listarViajerosDescendente(),"","Tiene que ser vacio");
        AuxAsserciones.checkearOk(s.listarViajerosPorTipo(TipoViajero.PREMIUM),"","Tiene que ser vacio");
        AuxAsserciones.checkearOk(s.listarViajerosPorTipo(TipoViajero.ESTANDAR),"","Tiene que ser vacio");

        //chequeamos que podamos reagregar a los viajeros.

        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);

        //chequeamos que todas las consultas funcionen correctamente.
        AuxAsserciones.checkearOk(s.listarViajerosAscendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS), "Deberian estar todos");
        AuxAsserciones.checkearOk(s.listarViajerosDescendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS_AL_REVES), "Deberian estar todos");
        AuxAsserciones.checkearOk(s.listarViajerosPorTipo(TipoViajero.PREMIUM), viajerosToString(Arrays.stream(VIAJEROS_VALIDOS_ORDENADOS)
                .filter(v -> v.getTipoViajero() == TipoViajero.PREMIUM)), "Deberian estar todos");
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[0], 0);//raiz
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[2], 1);//derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[8], 2);//derecha,derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[4], 3);//derecha, derecha,izquierda
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[3], 4);//derecha, derecha, izquierda,izquierda
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[1], 2);//derecha,izquieda
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[5], 4);// derecha, derecha,izquieda,derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[9], 3);// derecha, derecha,derecha,
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[6], 5);// derecha, derecha,izquieda,derecha,derecha
        chequearBusquedaCorrecta(s, VIAJEROS_VALIDOS_ORDENADOS[7], 6);// derecha, derecha,izquieda,derecha,derecha,dercha

    }

    private void chequearBusquedaCorrecta(Sistema s, TestViajeroDato dato, int cantidadIteraciones) {

        AuxAsserciones.checkearOk(s.buscarViajero(dato.getCedula()), cantidadIteraciones, viajeroToString(dato), "La busqueda dio resultados invalidos");
    }

    @Test
    public void testBuscarError1() {

        //dado que
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);

        //cuando
        Retorno resultado = s.buscarViajero(null);
        //entonces
        checkearError1(resultado, "La cedula es nula");

        //cuando
        resultado = s.buscarViajero(new String(""));
        //entonces
        checkearError1(resultado, "La cedula es vacia");
        //cuando
        resultado = s.buscarViajero(new String("3.233.222-2a"));
        //entonces
        checkearError1(resultado, "La cedula es invalida");
        //cuando
        resultado = s.buscarViajero(new String("abssds"));
        //entonces
        checkearError1(resultado, "La cedula es invalida");
        //cuando
        resultado = s.buscarViajero(new String("3@212.322-2"));
        //entonces
        checkearError1(resultado, "La cedula es invalida");
    }

    @Test
    public void testBuscarError2() {

        //dado que
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        //cuando
        Retorno resultado = s.buscarViajero(VIAJEROS_VALIDOS_ORDENADOS[0].getCedula());
        //entonces
        checkearError2(resultado, "El viajero no se registro nunca");
        //cuando
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        //entonces
        checkearOk(s.buscarViajero(VIAJEROS_VALIDOS_ORDENADOS[0].getCedula()), "Despues de agregado si se debe encontrar");
        //cuando reinicializo el sistema
        s.inicializarSistema(20);

        checkearError2(s.buscarViajero(VIAJEROS_VALIDOS_ORDENADOS[0].getCedula()), "El sistema fue reinicializado, no deberia estar mas");

    }


    @Test
    public void testListarOrdenadosOk1() {
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        checkearOk(s.listarViajerosAscendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS), "La lista esta mal");
        checkearOk(s.listarViajerosDescendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS_AL_REVES), "La lista esta mal");
    }

    private static String viajerosToString(TestViajeroDato[] viajerosValidosOrdenados) {
        return viajerosToString(Arrays.stream(viajerosValidosOrdenados));
    }

    private static String viajerosToString(Stream<TestViajeroDato> viajeros) {
        return viajeros.map(v -> viajeroToString(v)).collect(Collectors.joining("|"));
    }

    private static String viajeroToString(TestViajeroDato v) {
        return format("%s;%s;%s;%s",
                v.getCedula(), v.getNombre(), v.getEdad(), v.getTipoViajero());
    }

    @Test
    public void testListarOrdenadosOk2() {
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        checkearOk(s.listarViajerosAscendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS), "La lista esta mal");
        checkearOk(s.listarViajerosDescendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS_AL_REVES), "La lista esta mal");
    }

    @Test
    public void testListarOrdenadosOk3() {
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        checkearOk(s.listarViajerosAscendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS), "La lista esta mal");
        checkearOk(s.listarViajerosDescendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS_AL_REVES), "La lista esta mal");
    }

    @Test
    public void testListarOrdenadosOk4() {

        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        checkearOk(s.listarViajerosAscendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS), "La lista esta mal");
        checkearOk(s.listarViajerosDescendente(), viajerosToString(VIAJEROS_VALIDOS_ORDENADOS_AL_REVES), "La lista esta mal");
        s.inicializarSistema(20);
        checkearOk(s.listarViajerosAscendente(), "", "El sistema se reinicializo, deberia dar vacio");
        checkearOk(s.listarViajerosDescendente(), "", "El sistema se reinicializo, deberia dar vacio");

    }

    @Test
    public void testListarViajerosPorTipoVacio() {

        Sistema s = tengoUnSistemaValido();
        checkearOk(s.listarViajerosPorTipo(TipoViajero.PREMIUM), "", "Deberia ser vacio");
    }

    @Test
    public void testListarViajerosPorTipoVacio2() {

        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        checkearOk(s.listarViajerosPorTipo(TipoViajero.ESTANDAR), "", "Deberia ser vacio");
    }

    @Test
    public void testListarViajerosPorTipoVacio3() {

        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        s.inicializarSistema(23);
        checkearOk(s.listarViajerosPorTipo(TipoViajero.CASUAL), "", "Deberia ser vacio");
    }

    @Test
    public void testListarViajerosPorTipoError() {
        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        checkearError1(s.listarViajerosPorTipo(null), "El tipo es vacio");
    }

    @Test
    public void testListarViajerosPorTipoOk() {

        Sistema s = tengoUnSistemaValido();
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[0]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[5]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[4]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[2]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[9]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[8]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[7]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[3]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[1]);
        agregueUnViajero(s, VIAJEROS_VALIDOS_ORDENADOS[6]);
        checkearOk(s.listarViajerosPorTipo(TipoViajero.CASUAL), viajerosToString(Arrays.stream(VIAJEROS_VALIDOS_ORDENADOS)
                .filter(v -> v.getTipoViajero() == TipoViajero.CASUAL)), "Deberia ser vacio");

        checkearOk(s.listarViajerosPorTipo(TipoViajero.PREMIUM), viajerosToString(Arrays.stream(VIAJEROS_VALIDOS_ORDENADOS)
                .filter(v -> v.getTipoViajero() == TipoViajero.PREMIUM)), "Deberia ser vacio");
    }


}
