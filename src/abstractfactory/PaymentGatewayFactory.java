package abstractfactory;

public interface PaymentGatewayFactory {
    PaymentProcessor createProcessor();
    FraudChecker createFraudChecker();
}
