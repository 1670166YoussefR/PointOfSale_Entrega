package pos_creditcard;

import java.util.*;

public class PaymentInCash extends Payment {
  private final CashBox cashBox;
  private final ChangeMaker changeMaker;

  private Map<Double, Integer> moneyHanded = new LinkedHashMap<>();
  private Map<Double, Integer> changeGiven = new LinkedHashMap<>();


  public PaymentInCash(double amountToPay, CashBox cashBox, ChangeMaker changeMaker) {
    super(amountToPay);
    this.cashBox = cashBox;
    this.changeMaker = changeMaker;
  }

  public void processPayment(Map<Double, Integer> moneyHanded) {
    this.moneyHanded = moneyHanded;

    double totalHanded = totalHanded();
    double changeAmount = Math.round((totalHanded - amountToPay) * 100.0) / 100.0;

    // Salta Exception si el dinero entregado no es suficiente
    if (totalHanded < amountToPay) {
        throw new IllegalStateException("El dinero entregado no es suficiente para pagar la compra.");
    }

    cashBox.addMoney(moneyHanded);

    // Calculamos el cambio exacto usando el algoritmo
    changeGiven = changeMaker.makeChange(changeAmount, cashBox.getContentsCopy());

    // Restamos las monedas/billetes entregados como cambio de la caja
    cashBox.subtractMoney(changeGiven);
  }


  private double totalHanded() {
    double total = 0.0;
    for (Map.Entry<Double, Integer> entry : moneyHanded.entrySet()) {
        total += entry.getKey() * entry.getValue(); // 💡 denominación × cantidad
    }
    return Math.round(total * 100.0) / 100.0;
  }

  private double change() {
    double change = totalHanded() - amountToPay;
    assert change >= 0;
    return change;
  }

  @Override
  public void print() {
    double totalHanded = totalHanded();
    double changeAmount = Math.round((totalHanded - amountToPay) * 100.0) / 100.0;

    System.out.println("added payment to cash box");
    for (Map.Entry<Double, Integer> entry : moneyHanded.entrySet()) {
      double denom = entry.getKey();
      int qty = entry.getValue();
      System.out.printf("%d of %.1f\n", qty, denom);
    }
    System.out.println();

    System.out.printf("total to pay %.1f, change to give %.1f\n", amountToPay, changeAmount);

    System.out.println("the change is");
    for (Map.Entry<Double, Integer> entry : changeGiven.entrySet()) {
      double denom = entry.getKey();
      int quantity = entry.getValue();
      System.out.printf("%d of %.1f\n", quantity, denom);
    }
    System.out.println();

    System.out.println("after payment and giving change the cash box has");
    Map<Double, Integer> contents = cashBox.getContentsCopy();
    for (Map.Entry<Double, Integer> entry : contents.entrySet()) {
      double denom = entry.getKey();
      int quantity = entry.getValue();
      System.out.printf("%d of %.2f\n", quantity, denom);
    }
  }
}
