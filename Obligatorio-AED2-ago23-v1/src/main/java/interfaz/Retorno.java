package interfaz;

import java.util.Objects;


public class Retorno {
    public enum Resultado {
        OK, ERROR_1, ERROR_2, ERROR_3, ERROR_4, ERROR_5, ERROR_6, ERROR_7, NO_IMPLEMENTADA
    }
    private final Resultado resultado;
    private final Integer valorInteger;
    private final String valorString;

    private Retorno(Resultado resultado, Integer valorInteger, String valorString) {
        this.resultado = resultado;
        this.valorInteger = valorInteger;
        this.valorString = valorString;
    }

    public Resultado getResultado() {
        return resultado;
    }

    public Integer getValorInteger() {
        return valorInteger;
    }

    public String getValorString() {
        return valorString;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o == null || getClass() != o.getClass()) {
            return false;
        }
        Retorno retorno = (Retorno) o;
        return Objects.equals(valorInteger, retorno.valorInteger)
                && resultado == retorno.resultado && Objects.equals(valorString, retorno.valorString);
    }

    @Override
    public int hashCode() {
        return Objects.hash(resultado, valorInteger, valorString);
    }

    public static Retorno ok() {
        return new Retorno(Resultado.OK, null, null);
    }

    public static Retorno ok(String valorString) {
        return new Retorno(Resultado.OK, null, valorString);
    }

    public static Retorno ok(int valorInteger) {
        return ok(valorInteger, null);
    }

    public static Retorno ok(int valorInteger, String valorString) {
        return new Retorno(Resultado.OK, valorInteger, valorString);
    }

    public static Retorno error(Resultado error, String msg) {
        return new Retorno(error, 0, msg);
    }

    public static Retorno error1(String mensaje) {
        return error(Resultado.ERROR_1, mensaje);
    }

    public static Retorno error2(String mensaje) {
        return error(Resultado.ERROR_2, mensaje);
    }

    public static Retorno error3(String mensaje) {
        return error(Resultado.ERROR_3, mensaje);
    }

    public static Retorno error4(String mensaje) {
        return error(Resultado.ERROR_4, mensaje);
    }

    public static Retorno error5(String mensaje) {
        return error(Resultado.ERROR_5, mensaje);
    }

    public static Retorno error6(String mensaje) {
        return error(Resultado.ERROR_6, mensaje);
    }

    public static Retorno error7(String mensaje) {
        return error(Resultado.ERROR_7, mensaje);
    }

    public static Retorno noImplementada() {
        return new Retorno(Resultado.NO_IMPLEMENTADA, 0, "");
    }

    public boolean isOk() {
        return this.resultado == Resultado.OK;
    }

    @Override
    public String toString() {
        if (this.resultado == Resultado.OK) {
            if (this.valorString != null && valorInteger != null) {
                return String.format("OK[%s,'%s']", valorInteger, valorString);
            } else if (valorString != null) {
                return String.format("OK['%s']", valorString);
            } else if (valorInteger != null) {
                return String.format("OK[%s]", valorInteger);
            } else {
                return "OK";
            }
        } else if (resultado == Resultado.NO_IMPLEMENTADA) {
            return resultado.name();
        }
        return String.format("%s['%s']", resultado.name(), valorString);
    }
}
