# Assignment 2 - Factory Method and Abstract Factory

## What this project is about

For this assignment I picked payment processing as my topic. I wanted something where it actually makes sense why you would need these two patterns instead of just making a new object every time.

The idea is simple: a shop needs to accept different payment methods (credit card, PayPal), and later needs to support different regions (US and EU) where the payment processor and the fraud checker rules are different for each region.

## Part A - Factory Method

Package: factorymethod

The problem I was solving here is: I don't want my code to say "new CreditCardPayment()" everywhere. If I do that and later need to add Bitcoin, I have to go find every place I wrote "new CreditCardPayment()" and add another if-statement next to it. That's messy and easy to break.

So instead I made an interface called PaymentMethod. It just says that anything that is a PaymentMethod must have a pay(double amount) method. It doesn't say how it pays, just that it can pay.

Then I made two classes that actually implement this interface: CreditCardPayment and PayPalPayment. Each one just prints a message saying how the payment happened.

The important part is PaymentCreator. It's an abstract class, which means you can't do "new PaymentCreator()" directly because it's not finished - it has one method, createPaymentMethod(), that has no body, just a signature. This is called the factory method. It's abstract because I don't want PaymentCreator to decide which payment method to use, I want that decision to be made by whoever extends it.

PaymentCreator also has a normal, finished method called processPayment(double amount). This method is written only once and never repeated:

```
public void processPayment(double amount) {
    PaymentMethod method = createPaymentMethod();
    method.pay(amount);
}
```

It asks createPaymentMethod() for an object, and it doesn't care what object it actually gets back, as long as it's some kind of PaymentMethod. Then it calls pay() on it.

CreditCardCreator and PayPalCreator are the classes that actually extend PaymentCreator and fill in the missing piece:

```
public class CreditCardCreator extends PaymentCreator {
    @Override
    protected PaymentMethod createPaymentMethod() {
        return new CreditCardPayment();
    }
}
```

These classes are very small on purpose. Their only job is to answer the question "which payment method do I create?" Everything else (the processPayment logic) they get for free from PaymentCreator because they extend it.

## Part B - Abstract Factory

Package: abstractfactory

Part A works fine when you only need to pick ONE object. But for this part I needed something more complicated: a whole region needs a payment processor AND a fraud checker, and these two need to match each other. A US processor should never end up paired with an EU fraud checker by accident.

So this time I have two interfaces instead of one:
- PaymentProcessor, which can process(double amount)
- FraudChecker, which can isSafe(double amount) and returns true or false

Then I made two versions of each, one for US and one for EU:
- USPaymentProcessor and USFraudChecker
- EUPaymentProcessor and EUFraudChecker

The US fraud checker allows amounts up to 5000, and the EU one is stricter, only up to 2000. This was on purpose so I could actually test that the pattern works differently depending on which family is used.

Then there is PaymentGatewayFactory, which is the abstract factory interface:

```
public interface PaymentGatewayFactory {
    PaymentProcessor createProcessor();
    FraudChecker createFraudChecker();
}
```

USGatewayFactory and EUGatewayFactory implement this interface, and each one can only ever build its own family of objects. USGatewayFactory can never accidentally return an EU class, because its code literally only says "return new USPaymentProcessor()" and "return new USFraudChecker()".

Last piece in this part is PaymentService, which is the client. It gets handed a PaymentGatewayFactory in its constructor and stores it in a private final field:

```
private final PaymentGatewayFactory factory;

public PaymentService(PaymentGatewayFactory factory) {
    this.factory = factory;
}
```

I made this field private because nothing outside PaymentService should be able to change which factory it's using after it's created, and final because it should never be reassigned once it's set in the constructor.

The pay() method first asks the factory for a fraud checker and checks if the amount is safe. If it's not safe, it prints "Payment blocked." and returns immediately, so the processor never even gets called. If it is safe, it asks the factory for a processor and processes the payment.

The whole point of PaymentService is that it never writes the name USFraudChecker or EUPaymentProcessor anywhere in its own code. It only knows about the three interfaces. That means I can give it a US factory or an EU factory and it will behave correctly either way without changing PaymentService itself.

## Why abstract class vs interface

I used an abstract class for PaymentCreator because I needed some methods to already have real, working code (processPayment) and one method to be left unfinished for subclasses to fill in. An interface can't really do the "some finished, some unfinished" mix the same way, so abstract class was the right tool there.

For the abstract factory side I used interfaces (PaymentGatewayFactory, PaymentProcessor, FraudChecker) because there was no shared logic to write, they are pure contracts that just say what methods must exist.

## About the modifiers I used

protected - used on createPaymentMethod() in PaymentCreator, because it needs to be visible to subclasses like CreditCardCreator so they can override it, but I don't want random outside code calling it directly.

private - used on the factory field in PaymentService, so nobody outside the class can read or change it directly.

final - used together with private on the same field, so once the constructor sets it, it can never be changed again for the life of that object.

public - used on the classes themselves and on the methods that need to be called from Main or from other packages.

## Why double instead of float

I used double for the amount everywhere. Both float and double store decimal numbers but double is bigger (64 bits vs 32 bits) and more precise. Also in Java, if you just write a number like 100.0, it's automatically treated as a double, so using double avoids having to write 100.0f everywhere.

## How the packages work

I put Part A in a folder called factorymethod and Part B in a folder called abstractfactory. In Java a package is not just a folder, every file inside it needs a line at the very top like "package factorymethod;" that matches the folder name exactly. Main.java is not inside either folder, so it has no package line, and it has to import the classes it needs from both packages.

## How to run it

Run Main.java. It should print:

```
=== Part A: Factory Method ===
Paid $100.0 using Credit Card.
Paid $50.0 using PayPal.

=== Part B: Abstract Factory ===
US fraud check for $200.0: OK
Processing $200.0 through US Visa/Mastercard network.
EU fraud check for EUR 3000.0: FLAGGED
Payment blocked.
```

The last part shows the EU payment getting blocked because 3000 is over the EU limit of 2000, while the US one goes through fine because 200 is way under its limit of 5000.

## What I would say is the main difference between the two patterns

Factory Method is for when you need to create ONE object and you want the decision of which class to use to happen in a subclass instead of a big if/else. Abstract Factory is for when you need to create SEVERAL related objects together and you need a guarantee that they all belong to the same family and never get mixed up with objects from another family.
