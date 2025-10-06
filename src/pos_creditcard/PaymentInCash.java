package pos_creditcard;

import java.util.*;

public class PaymentInCash extends Payment {
  double amountHanded;
  private final CashBox cashBox;
  private final ChangeMaker changeMaker;

  private Map<Double, Integer> moneyHanded = new LinkedHashMap<>();
  private Map<Double, Integer> changeGiven = new LinkedHashMap<>();


  public PaymentInCash(double amountHanded, double amountToPay, CashBox cashbox, ChangeMaker changeMaker) {
    super(amountToPay);
    assert amountHanded >= amountToPay;
    this.amountHanded = amountHanded;
    this.cashBox = cashbox;
    this.changeMaker = changeMaker;
  }

    public void processPayment(Map<Double, Integer> moneyHanded) {
        this.moneyHanded = moneyHanded;

        // Calcula el total entregado y el cambio total
        double totalHanded = totalHanded();
        double changeAmount = Math.round((totalHanded - amountToPay) * 100.0) / 100.0;

        // Salta Exception si el dinero entregado no es suficiente
        if (totalHanded < amountToPay) {
            throw new IllegalStateException("El dinero entregado no es suficiente para pagar la compra.");
        }

        // ✅ Aquí estaba el fallo: usa cashBox, no cashbox
        cashBox.addMoney(moneyHanded);

        // Calculamos el cambio exacto usando el algoritmo
        changeGiven = changeMaker.makeChange(changeAmount, cashBox.getContentsCopy());

        // Restamos las monedas/billetes entregados como cambio de la caja
        cashBox.subtractMoney(changeGiven);
    }


    private double totalHanded() {
      double total = 0.0;
      for (Map.Entry<Double, Integer> entry : moneyHanded.entrySet()) {
          total += entry.getValue() * entry.getValue();
      }
      return Math.round(total * 100.0) / 100.0;
  }

  private double change() {
    double change = amountHanded - amountToPay;
    assert change >= 0;
    return change;
  }

  @Override
  public void print() {
    System.out.printf("\nAmount handed : %.2f\nChange : %.2f\n", amountHanded, change());
  }
}
