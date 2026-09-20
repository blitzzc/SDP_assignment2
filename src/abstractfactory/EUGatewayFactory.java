package abstractfactory;

public class EUGatewayFactory implements PaymentGatewayFactory {
    @Override
    public PaymentProcessor createProcessor() {
        return new EUPaymentProcessor();
    }

    @Override
    public FraudChecker createFraudChecker() {
        return new EUFraudChecker();
    }
}
