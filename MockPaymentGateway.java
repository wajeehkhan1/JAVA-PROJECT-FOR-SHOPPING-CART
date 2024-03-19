package shoppingsystem.classes;

import java.util.Scanner;
import shoppingsystem.Interfaces.PaymentGateway;

public class MockPaymentGateway implements PaymentGateway {
    private Scanner scanner;

    public MockPaymentGateway() {
        this.scanner = new Scanner(System.in);
    }
    @Override
    public boolean processPayment(double amount) {
        System.out.println("Payment processing...");
        System.out.println("Choose your payment method:");
        System.out.println("1. Credit Card");
        System.out.println("2. Debit Card");
        System.out.println("3. PayPal");
        System.out.print("Enter your choice: ");

        int choice = scanner.nextInt();
        scanner.nextLine(); // Consume newline

        switch (choice) {
            case 1:
                System.out.println("Processing credit card payment...");
                break;
            case 2:
                System.out.println("Processing debit card payment...");
                break;
            case 3:
                System.out.println("Processing PayPal payment...");
                break;
            default:
                System.out.println("Invalid choice. Payment failed.");
                return false;
        }

     
        System.out.println("Payment successful! Amount: $" + amount);
        return true;
    }
}
