// Bank.java - Manages all accounts in bank
import java.util.ArrayList;

public class Bank {
    private String bankName;
    private String branch;
    private ArrayList<Account> accounts;

    // Constructor
    public Bank(String bankName, String branch) {
        this.bankName = bankName;
        this.branch = branch;
        this.accounts = new ArrayList<>();
    }

    // Add new account to bank
    public void addAccount(Account acc) {
        accounts.add(acc);
        System.out.println("Account added successfully!");
        System.out.println("Account No: " + acc.getAccountNumber());
    }

    // Remove account
    public void removeAccount(String accNumber) {
        for (int i = 0; i < accounts.size(); i++) {
            if (accounts.get(i).getAccountNumber().equals(accNumber)) {
                accounts.remove(i);
                System.out.println("Account removed: " + accNumber);
                return;
            }
        }
        System.out.println("Account not found: " + accNumber);
    }

    // Find account
    public Account findAccount(String accNumber) {
        for (Account acc : accounts) {
            if (acc.getAccountNumber().equals(accNumber)) {
                return acc;
            }
        }
        System.out.println("Account not found");
        return null;
    }

    // Show all accounts
    public void showAllAccounts() {
        System.out.println("--- Bank: " + bankName + " | Branch: " + branch + " ---");
        System.out.println("Total Accounts: " + accounts.size());
        for (Account acc : accounts) {
            acc.displayInfo();
            System.out.println("--------------------");
        }
    }

    // Get total balance in bank
    public void totalBankBalance() {
        double total = 0;
        for (Account acc : accounts) {
            total = total + acc.getBalance();
        }
        System.out.println("Total Money in Bank: Rs. " + total);
    }
}
