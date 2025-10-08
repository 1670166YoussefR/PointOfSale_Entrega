package pos_creditcard;

import java.util.*;

public class CashBox {

    private final Map<Double, Integer> contents = new LinkedHashMap<>();

    public CashBox() {
        // Inicialización de la caja
        contents.put(0.01, 5);
        contents.put(0.02, 5);
        contents.put(0.05, 5);
        contents.put(0.10, 5);
        contents.put(0.20, 5);
        contents.put(0.50, 5);
        contents.put(1.0, 5);
        contents.put(2.0, 5);
        contents.put(5.0, 2);
        contents.put(10.0, 2);
        contents.put(20.0, 2);
    }

    public Map<Double, Integer> getContentsCopy() {
        return new LinkedHashMap<>(contents);
    }

    public void addMoney(Map<Double, Integer> money) {
        for (Map.Entry<Double, Integer> entry : money.entrySet()) {
            double denom = entry.getKey();
            int quantity = entry.getValue();
            contents.put(denom, contents.getOrDefault(denom, 0) + quantity);
        }
    }

    public void subtractMoney(Map<Double, Integer> money) {
        for (Map.Entry<Double, Integer> entry : money.entrySet()) {
            double denom = entry.getKey();
            int quantity = entry.getValue();
            contents.put(denom, contents.get(denom) - quantity);
        }
    }

    public void printContents() {
        System.out.println("cash box initially loaded with");
        contents.forEach((denom, quantity) ->
                System.out.printf("%d of %.2f\n", quantity, denom)
        );
        System.out.println();
    }
}
