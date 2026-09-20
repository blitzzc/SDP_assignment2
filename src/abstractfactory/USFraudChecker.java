package abstractfactory;

public class USFraudChecker implements FraudChecker {
    @Override
    public boolean isSafe(double amount) {
        boolean safe = amount <= 5000;
        System.out.println("US fraud check for $" + amount + ": " + (safe ? "OK" : "FLAGGED"));
        return safe;
    }
}
