package pos_creditcard;

public abstract class Payment {
  protected double amountToPay;

  public Payment(double amountToPay) {
    this.amountToPay = amountToPay;
  }

  public abstract void print();
  //TODO: better override toString() and then write
  //   System.out.println(aPayment)
  // in place of
  //   aPayment.print()
}
