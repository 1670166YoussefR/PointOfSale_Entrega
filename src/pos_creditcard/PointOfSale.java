package pos_creditcard;

import java.util.*;


public class PointOfSale {
  private ProductCatalog productCatalog;
  private ArrayList<Sale> sales;
  private int idLastSale = 0;
  private final String FILE_NAME = "src/pos/catalog.txt";

  private CashBox cashBox;
  private ChangeMaker changeMaker;

  public PointOfSale() {
    productCatalog = new ProductCatalog(FILE_NAME);
    sales = new ArrayList<>();
    cashBox = new CashBox();
    // Esto puede ser RandomChangeMaker() o GreedyChangeMaker()
    changeMaker = new GreedyChangeMaker();

    cashBox.printContents();
  }

  public int makeNewSale() {
    idLastSale++;
    Sale newSale = new Sale(idLastSale);
    sales.add(newSale);
    return idLastSale;
  }

  public void addLineItemToSale(int idSale, String productName, int quantity) {
    ProductSpecification productSpecification = productCatalog.searchByName(productName);
    Sale sale = searchSaleById(idSale);
    sale.addLineItem(productSpecification, quantity);
    System.out.println("ordered " + quantity + " " + productName);
  }

  private Sale searchSaleById(int id) {
    for (Sale s : sales) {
      if (s.getId() == id) {
        return s;
      }
    }
    return null;
  }

  public void printReceiptOfSale(int saleId) {
    Sale sale = searchSaleById(saleId);
    sale.printReceipt();
  }

  // Actualizamos el pago en efectivo
  public void payOneSaleCash(int saleId, Map<Double, Integer> moneyHanded) {
    Sale sale = searchSaleById(saleId);
    sale.payCash(moneyHanded, cashBox, changeMaker);
  }

  public void payOneSaleCreditCard(int saleId, String ccnumber) {
    Sale sale = searchSaleById(saleId);
    sale.payCreditCard(ccnumber);
  }

  public void printPayment(int saleId) {
    Sale sale = searchSaleById(saleId);
    sale.printPayment();
  }

  public boolean isSalePaid(int id) {
    return searchSaleById(id).isPaid();
  }

  // this is for the user interface
  public ArrayList<String> getProductNames() {
    return productCatalog.getProductNames();
  }
}

