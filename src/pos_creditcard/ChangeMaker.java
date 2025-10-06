package pos_creditcard;

import java.util.Map;
/**
 * Interfaz que define el comportamiento común
 * de cualquier algoritmo de cálculo de cambio.
 */
public interface ChangeMaker {
    Map<Double, Integer> makeChange(double changeAmount, Map<Double, Integer> cashBox);
}
