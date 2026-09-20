package factorymethod;

public class CreditCardPayment implements PaymentMethod {
    @Override
    public void pay(double amount) {
        System.out.println("Paid $" + amount + " using credit card");
    }
}
