package abstractfactory;

public class USPaymentProcessor implements PaymentProcessor {
    @Override
    public void process(double amount) {
        System.out.println("Processing $" + amount + " through US Visa/Mastercard network");
    }
}
