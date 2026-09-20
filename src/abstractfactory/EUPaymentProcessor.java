package abstractfactory;

public class EUPaymentProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing EUR" + amount + " through EU SEPA network");
    }
}
