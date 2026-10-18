import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;

/**
 * CreditCard.java - Banking-System-Java
 * Author: vijaysingla936-coder
 * Credit Card, Limit, Billing, EMI, Rewards
 */

class CreditCardAccount {
    String cardNumber;
    String customerName;
    String accountNumber;
    double creditLimit;
    double availableLimit;
    double outstandingAmount;
    LocalDate expiryDate;
    int cvv;
    String cardType;
    boolean isBlocked;
    
    public CreditCardAccount(String custName, String accNo, String type, double limit) {
        this.customerName = custName;
        this.accountNumber = accNo;
        this.cardType = type;
        this.creditLimit = limit;
        this.availableLimit = limit;
        this.outstandingAmount = 0;
        this.expiryDate = LocalDate.now().plusYears(5);
        this.cardNumber = generateCardNumber();
        this.cvv = 100 + new Random().nextInt(900);
        this.isBlocked = false;
    }
    
    private String generateCardNumber() {
        Random rand = new Random();
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < 16; i++) {
            sb.append(rand.nextInt(10));
            if ((i+1) % 4 == 0 && i != 15) sb.append(" ");
        }
        return sb.toString();
    }
}

class CardTransaction {
    String txnId;
    double amount;
    String merchant;
    LocalDate date;
    String type;
    
    public CardTransaction(double amt, String merch, String type) {
        this.amount = amt;
        this.merchant = merch;
        this.type = type;
        this.date = LocalDate.now();
        this.txnId = "CC" + (100000 + new Random().nextInt(900000));
    }
}

public class CreditCard {
    
    private ArrayList<CreditCardAccount> cardList;
    private ArrayList<CardTransaction> transactions;
    private Scanner sc;
    
    public CreditCard() {
        cardList = new ArrayList<>();
        transactions = new ArrayList<>();
        sc = new Scanner(System.in);
        System.out.println("Credit Card Module Initialized");
    }
    
    public void showCardTypes() {
        System.out.println("\n========== CREDIT CARD TYPES ==========");
        System.out.println("1. SILVER  - Limit Rs. 50,000  - Annual Fee Rs. 500");
        System.out.println("2. GOLD    - Limit Rs. 1,50,000 - Annual Fee Rs. 1000");
        System.out.println("3. PLATINUM- Limit Rs. 5,00,000 - Annual Fee Rs. 2500");
        System.out.println("4. INFINITE- Limit Rs.10,00,000 - Annual Fee Rs. 5000");
        System.out.println("Features: Lounge Access, Movie Offers, Fuel Surcharge Waiver");
        System.out.println("========================================");
    }
    
    public CreditCardAccount applyForCard() {
        System.out.println("\n--- APPLY FOR NEW CREDIT CARD ---");
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Savings Account No: ");
        String accNo = sc.nextLine();
        System.out.print("Enter Card Type (SILVER/GOLD/PLATINUM/INFINITE): ");
        String type = sc.nextLine().toUpperCase();
        System.out.print("Enter Annual Income: ");
        double income = sc.nextDouble();
        sc.nextLine();
        
        double limit = calculateLimit(type, income);
        System.out.println("Eligible Credit Limit: Rs. " + limit);
        
        CreditCardAccount card = new CreditCardAccount(name, accNo, type, limit);
        cardList.add(card);
        
        System.out.println("\nCard Approved!");
        System.out.println("Card Number: " + card.cardNumber);
        System.out.println("Expiry: " + card.expiryDate.getMonth() + "/" + card.expiryDate.getYear());
        System.out.println("CVV: " + card.cvv);
        System.out.println("Type: " + card.cardType + " | Limit: Rs. " + card.creditLimit);
        return card;
    }
    
    private double calculateLimit(String type, double income) {
        switch(type) {
            case "SILVER": return Math.min(50000, income * 0.5);
            case "GOLD": return Math.min(150000, income * 1.0);
            case "PLATINUM": return Math.min(500000, income * 2.0);
            case "INFINITE": return Math.min(1000000, income * 3.0);
            default: return 50000;
        }
    }
    
    public void makePayment(CreditCardAccount card) {
        System.out.println("\n--- MAKE PAYMENT WITH CREDIT CARD ---");
        System.out.print("Enter Merchant Name: ");
        String merchant = sc.nextLine();
        System.out.print("Enter Amount: ");
        double amount = sc.nextDouble();
        sc.nextLine();
        
        if (amount > card.availableLimit) {
            System.out.println("Declined! Insufficient Limit. Available: Rs. " + card.availableLimit);
            return;
        }
        
        card.availableLimit -= amount;
        card.outstandingAmount += amount;
        CardTransaction txn = new CardTransaction(amount, merchant, "PURCHASE");
        transactions.add(txn);
        
        System.out.println("Payment Successful! Txn ID: " + txn.txnId);
        System.out.println("Remaining Limit: Rs. " + card.availableLimit);
        System.out.println("Outstanding: Rs. " + card.outstandingAmount);
        
        int rewardPoints = (int)(amount / 100) * 4;
        System.out.println("Reward Points Earned: " + rewardPoints);
    }
    
    public void generateBill(CreditCardAccount card) {
        System.out.println("\n========== CREDIT CARD STATEMENT ==========");
        System.out.println("Card: " + card.cardNumber);
        System.out.println("Name: " + card.customerName);
        System.out.println("Statement Date: " + LocalDate.now());
        System.out.println("Due Date: " + LocalDate.now().plusDays(20));
        System.out.println("-------------------------------------------");
        System.out.println("Total Outstanding: Rs. " + card.outstandingAmount);
        System.out.println("Minimum Due (5%): Rs. " + (card.outstandingAmount * 0.05));
        System.out.println("Available Limit: Rs. " + card.availableLimit);
        System.out.println("-------------------------------------------");
        System.out.println("Transactions:");
        for (CardTransaction t : transactions) {
            System.out.println(t.date + " | " + t.merchant + " | Rs. " + t.amount + " | " + t.txnId);
        }
        System.out.println("-------------------------------------------");
        System.out.println("Interest if not paid full: 3.5% per month (42% per annum)");
        System.out.println("Late Payment Fee: Rs. 500");
        System.out.println("===========================================");
    }
    
    public void payBill(CreditCardAccount card) {
        System.out.print("\nEnter Bill Payment Amount: ");
        double payAmt = sc.nextDouble();
        if (payAmt > card.outstandingAmount) payAmt = card.outstandingAmount;
        card.outstandingAmount -= payAmt;
        card.availableLimit += payAmt;
        System.out.println("Payment of Rs. " + payAmt + " successful!");
        System.out.println("New Outstanding: Rs. " + card.outstandingAmount);
    }
    
    public void convertToEMI() {
        System.out.println("\n--- CONVERT TO EMI ---");
        System.out.print("Enter Transaction Amount to Convert: ");
        double amt = sc.nextDouble();
        System.out.print("Enter Tenure Months (3/6/9/12): ");
        int months = sc.nextInt();
        double interest = 12.0;
        double emi = (amt * (interest/12/100) * Math.pow(1 + interest/12/100, months)) / (Math.pow(1 + interest/12/100, months) - 1);
        System.out.printf("EMI for Rs. %.2f for %d months @ %.1f%% = Rs. %.2f per month\n", amt, months, interest, emi);
        System.out.println("EMI Conversion Fee: Rs. 199");
    }
    
    public void blockUnblockCard(CreditCardAccount card) {
        card.isBlocked = !card.isBlocked;
        System.out.println("Card " + card.cardNumber + " is now " + (card.isBlocked ? "BLOCKED" : "UNBLOCKED"));
        if (card.isBlocked) System.out.println("For security, card blocked. Call 1800-XXX to unblock.");
    }
    
    public static void main(String[] args) {
        CreditCard ccModule = new CreditCard();
        ccModule.showCardTypes();
        CreditCardAccount card = ccModule.applyForCard();
        ccModule.makePayment(card);
        ccModule.generateBill(card);
        ccModule.convertToEMI();
    }
}
