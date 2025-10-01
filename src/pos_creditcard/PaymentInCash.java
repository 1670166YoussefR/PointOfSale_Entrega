package pos_creditcard;

public class PaymentInCash extends Payment {
  double amountHanded;

  public PaymentInCash(double amountHanded, double amountToPay) {
    super(amountToPay);
    assert amountHanded >= amountToPay;
    this.amountHanded = amountHanded;
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
