package factorymethod;

public class  PayPalCreator extends PaymentCreator{
    @Override
    protected PaymentMethod createPaymentMethod() {
        return new PayPalPayment();
    }
}
