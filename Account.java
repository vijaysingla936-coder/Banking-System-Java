// Account.java - Bank Account details
public class Account {
    private String accountNumber;
    private String accountHolder;
    private double balance;
    private String accountType;

    // Constructor
    public Account(String accountNumber, String accountHolder, double balance, String accountType) {
        this.accountNumber = accountNumber;
        this.accountHolder = accountHolder;
        this.balance = balance;
        this.accountType = accountType;
    }

    // Deposit money
    public void deposit(double amount) {
        if (amount > 0) {
            balance = balance + amount;
            System.out.println("Deposited: " + amount);
            System.out.println("New Balance: " + balance);
        } else {
            System.out.println("Invalid amount");
        }
    }

    // Withdraw money
    public void withdraw(double amount) {
        if (amount > 0 && amount <= balance) {
            balance = balance - amount;
            System.out.println("Withdrawn: " + amount);
            System.out.println("Remaining Balance: " + balance);
        } else {
            System.out.println("Insufficient balance or invalid amount");
        }
    }

    // Check balance
    public void checkBalance() {
        System.out.println("Account Number: " + accountNumber);
        System.out.println("Holder Name: " + accountHolder);
        System.out.println("Current Balance: " + balance);
    }

    // Display account info
    public void displayInfo() {
        System.out.println("--- Account Details ---");
        System.out.println("Account No: " + accountNumber);
        System.out.println("Name: " + accountHolder);
        System.out.println("Type: " + accountType);
        System.out.println("Balance: Rs. " + balance);
    }

    // Getters
    public String getAccountNumber() {
        return accountNumber;
    }

    public double getBalance() {
        return balance;
    }
}
