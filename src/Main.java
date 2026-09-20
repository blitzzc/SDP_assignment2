import abstractfactory.PaymentGatewayFactory;
import abstractfactory.PaymentService;
import abstractfactory.USGatewayFactory;
import abstractfactory.EUGatewayFactory;
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

        System.out.println();
        System.out.println("=====Abstract Factory======");
        PaymentGatewayFactory usFactory = new USGatewayFactory();
        PaymentGatewayFactory euFactory = new EUGatewayFactory();

        PaymentService usService = new PaymentService(usFactory);
        usService.pay(200.0);

        PaymentService euService = new PaymentService(euFactory);
        euService.pay(4000.0);
    }
}
