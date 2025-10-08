package pos_creditcard;

import java.util.*;

public class GreedyChangeMaker implements ChangeMaker {

    @Override
    public Map<Double, Integer> makeChange(double changeAmount, Map<Double, Integer> cashBox) {

        // Mapa para guardar el cambio a devolver
        Map<Double, Integer> changeToGive = new LinkedHashMap<>();

        //Redondeamos para evitar errores
        changeAmount = Math.round(changeAmount * 100.0) / 100.0;

        // Ordenamos las monedas/billetes de mayor a menor
        List<Double> denominations = new ArrayList<>(cashBox.keySet());
        denominations.sort(Collections.reverseOrder());

        // Recorremos las monedas/billetes
        for (Double denom : denominations) {
            int available = cashBox.get(denom);
            int used = 0;

            //Mientras quede cambio y haya monedas/billetes de ese valor
            while (changeAmount >= denom && available > 0) {
                changeAmount -= denom;
                // Volvemos a redondear para seguir evitando errores en los calculos
                changeAmount = Math.round(changeAmount * 100.0) / 100.0;
                available--;
                used++;
            }

            // Si hemos utilizado alguna moneda/billete, la añadimos al resultado
            if (used > 0) {
                changeToGive.put(denom, used);
            }
        }

        return changeToGive;
    }
}
