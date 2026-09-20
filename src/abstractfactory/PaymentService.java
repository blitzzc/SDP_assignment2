package abstractfactory;

public class PaymentService {
    private final PaymentGatewayFactory factory;
    public PaymentService(PaymentGatewayFactory factory) {
        this.factory = factory;
    }

    public void pay(double amount) {
        FraudChecker checker = factory.createFraudChecker();
        if (!checker.isSafe(amount)) {
            System.out.println("Payment blocked");
            return;
        }
        PaymentProcessor processor = factory.createProcessor();
        processor.process(amount);
    }
}
