package sistema;

import interfaz.Sistema;

public class AccionesSistema {
    public static Sistema tengoUnSistemaValido() {
        return tengoUnSistemaValido(10);
    }

    public static Sistema tengoUnSistemaValido(int ciudades) {
        Sistema s = new ImplementacionSistema();
        AuxAsserciones.checkearOk(s.inicializarSistema(ciudades), "El sistema tendria que haberse inicializado correctamente");
        return s;
    }

}
