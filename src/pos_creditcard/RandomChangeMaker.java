package pos_creditcard;

import java.util.*;

/**
 * Implementación aleatoria del cálculo del cambio.
 * Selecciona monedas/billetes al azar hasta completar el importe.
 */
public class RandomChangeMaker implements ChangeMaker {

    private final Random random = new Random();

    @Override
    public Map<Double, Integer> makeChange(double changeAmount, Map<Double, Integer> cashBox) {
        // Mapa para guardar el cambio a devolver
        Map<Double, Integer> changeToGive = new LinkedHashMap<>();

        //Redondeamos para evitar errores
        changeAmount = Math.round(changeAmount * 100.0) / 100.0;

        // Ordenamos las monedas/billetes de mayor a menor
        List<Double> denominations = new ArrayList<>(cashBox.keySet());
        denominations.sort(Collections.reverseOrder());

        //Mientras quede cambio por devolver
        while (changeAmount > 0) {
            //Filtramos solo las monedas/billetes que esten disponibles
            List<Double> usable = new ArrayList<>();
            for (double d : denominations) {
                if (cashBox.get(d) > 0 && d <= changeAmount + 0.0001) {
                    usable.add(d);
                }
            }

            //Si no hay monedas/billetes utiles, lanzamos Exception
            if (usable.isEmpty()) {
                throw new IllegalStateException("No hay suficiente dinero en la caja para devolver el cambio.");
            }

            // Elegimos una denominación aleatoria entre las posibles
            double denom = usable.get(random.nextInt(usable.size()));

            // Restamos del cambio y actualizamos cantidades
            changeAmount -= denom;
            // Volvemos a redondear para seguir evitando errores en los calculos
            changeAmount = Math.round(changeAmount * 100.0) / 100.0;

            //Restamos una unidad de la disponibilidad
            cashBox.put(denom, cashBox.get(denom) - 1);
            //Sumamos esa unidad al cambio a devolver
            changeToGive.put(denom, changeToGive.getOrDefault(denom, 0) + 1);
        }
        return changeToGive;
    }
}
