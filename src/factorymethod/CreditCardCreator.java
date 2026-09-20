package factorymethod;

public class CreditCardCreator extends PaymentCreator {
    @Override
    protected PaymentMethod createPaymentMethod () {
        return new CreditCardPayment();
    }
}
