import factorymethod.PaymentCreator;
import factorymethod.CreditCardCreator;
import factorymethod.PayPalCreator;

public class Main {
    public static void main(String[] args) {
        System.out.println("=====Factory Method======");
        PaymentCreator creditcard = new CreditCardCreator();
        PaymentCreator paypal = new PayPalCreator();
        creditcard.processPayment(100.0);
        paypal.processPayment(300.0);
    }
}
