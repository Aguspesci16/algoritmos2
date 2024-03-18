package sistema;

import interfaz.Retorno;
import interfaz.Sistema;
import sistema.auxiliares.TestViajeroDato;

import static java.lang.String.format;

public class AccionesPasajero {

    public static void agregueUnViajero(Sistema sistema, TestViajeroDato viajero) {
        AuxAsserciones.checkearOk(agregoElViajero(sistema,viajero),
                format("El viajero '%s' deberia haberse agregado correctamente", viajero));
    }

    public static Retorno agregoElViajero(Sistema sistema,TestViajeroDato viajero){
        return sistema.registrarViajero(viajero.getCedula(), viajero.getNombre(), viajero.getEdad(), viajero.getTipoViajero());
    }



}
