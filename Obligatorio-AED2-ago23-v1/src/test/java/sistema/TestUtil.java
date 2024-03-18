package sistema;

import java.util.Arrays;
import java.util.Comparator;
import java.util.Random;

public class TestUtil {
    /**
     * No les va a servir, la burbuja tiene O(n^2).Ordena in place con burbuja.
     */
    public static <T> T[] ordenar(T[] original, Comparator<T> comparator) {
        if (original == null) {
            return null;
        }
        T[] ordenar = Arrays.copyOf(original, original.length);
        for (int i = 0; i < ordenar.length; i++) {
            for (int j = 0; j < (ordenar.length - i - 1); j++) {
                if (comparator.compare(ordenar[j], ordenar[j + 1]) > 0) {
                    T aux = ordenar[j];
                    ordenar[j] = ordenar[j + 1];
                    ordenar[j + 1] = aux;
                }
            }
        }
        return ordenar;
    }

    public static <T extends Comparable<T>> T[] ordenar(T[] ordenar) {
        return ordenar(ordenar, Comparator.naturalOrder());
    }

    public static <T> T[] shuffle(T[] original, Random r) {

        if (original == null) {
            return null;
        }
        T[] shuffle = Arrays.copyOf(original, original.length);
        int shuffleQty = Math.max(0, Math.min(original.length, (int) (r.nextDouble() * original.length / 4 + original.length / 2)));
        for (int i = 0; i < shuffleQty; i++) {
            int start = Math.min(original.length - 1, (int) (r.nextDouble() * original.length));
            int end = Math.min(original.length - 1, (int) (r.nextDouble() * original.length));
            T aux = shuffle[start];
            shuffle[start] = shuffle[end];
            shuffle[end] = aux;
        }
        return shuffle;

    }

    public static <T> String arrToString(T[] valores) {
        if (valores == null) {
            return "";
        }
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < valores.length; i++) {
            if (sb.length() > 0) {
                sb.append("|");
            }
            sb.append(valores[i]);
        }
        return sb.toString();
    }

    public static void main(String[] args) {

        System.out.println(Arrays.toString(ordenar(new Integer[]{5, 2, 1, 8, 23, 1, 11, 32, -1, -5})));

    }
}
