package abstractfactory;

public class USGatewayFactory implements PaymentGatewayFactory {
    @Override
    public PaymentProcessor createProcessor() {
        return new USPaymentProcessor();
    }

    @Override
    public FraudChecker createFraudChecker(){
        return new USFraudChecker();
    }

}
