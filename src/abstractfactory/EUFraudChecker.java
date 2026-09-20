package abstractfactory;

public class EUFraudChecker implements FraudChecker {
    @Override
    public boolean isSafe(double amount) {
        boolean safe = amount <= 3000;
        System.out.println("EU fraud check for EUR" + amount + ": " + (safe ? "OK" : "FLAGGED"));
        return safe;
    }
}
