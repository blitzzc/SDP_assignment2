package factorymethod;

public abstract class PaymentCreator {
    protected abstract PaymentMethod createPaymentMethod();

    public void processPayment(double amount) {
        PaymentMethod method = createPaymentMethod();
        method.pay(amount);
    }
}
