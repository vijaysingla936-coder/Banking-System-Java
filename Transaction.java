import java.util.ArrayList;
import java.time.LocalDateTime;

public class Transaction {
    private String type;
    private double amount;
    private LocalDateTime date;
    private double balanceAfter;

    private static ArrayList<Transaction> history = new ArrayList<>();

    public Transaction(String type, double amount, double balanceAfter) {
        this.type = type;
        this.amount = amount;
        this.balanceAfter = balanceAfter;
        this.date = LocalDateTime.now();
    }

    public static void addTransaction(String type, double amount, double balance) {
        Transaction t = new Transaction(type, amount, balance);
        history.add(t);
        System.out.println(type + " of Rs." + amount + " successful!");
    }

    public static void showHistory() {
        if(history.isEmpty()){
            System.out.println("No transactions yet.");
            return;
        }
        System.out.println("\n--- Transaction History ---");
        for(Transaction t : history){
            System.out.println(t.date + " | " + t.type + " | Amount: " + t.amount + " | Balance: " + t.balanceAfter);
        }
    }

    public static void clearHistory() {
        history.clear();
        System.out.println("History cleared.");
    }
}
