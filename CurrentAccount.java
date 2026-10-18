import java.time.LocalDate;
import java.util.ArrayList;
import java.util.List;

/**
 * Current Account Class - For Business & High Transaction Users
 * Features: Overdraft, High Transaction Limit, Business Tools
 * Author: Vijay Singla
 * Project: Banking-System-Java
 * Version: 1.0
 */
public class CurrentAccount extends Account {
    
    // Constants for Current Account
    private static final double DEFAULT_OVERDRAFT_LIMIT = 100000.0;
    private static final double TRANSACTION_FEE = 20.0;
    private static final int FREE_TRANSACTIONS_PER_MONTH = 50;
    private static final double MIN_BALANCE_CURRENT = 5000.0;
    
    // Fields
    private double overdraftLimit;
    private double overdraftUsed;
    private int transactionCount;
    private String businessName;
    private String gstNumber;
    private List<String> authorizedSignatories;
    private boolean isOverdraftEnabled;
    private LocalDate accountOpeningDate;
    private double monthlyMaintenanceFee;
    
    /**
     * Constructor for Current Account with Business Details
     * @param accountNumber - 12 digit account number
     * @param customer - Customer object
     * @param initialBalance - Initial deposit
     * @param businessName - Business/Company name
     */
    public CurrentAccount(String accountNumber, Customer customer, 
                         double initialBalance, String businessName) {
        super(accountNumber, customer, initialBalance);
        this.overdraftLimit = DEFAULT_OVERDRAFT_LIMIT;
        this.overdraftUsed = 0.0;
        this.transactionCount = 0;
        this.businessName = businessName;
        this.authorizedSignatories = new ArrayList<>();
        this.isOverdraftEnabled = true;
        this.accountOpeningDate = LocalDate.now();
        this.monthlyMaintenanceFee = 500.0;
        this.gstNumber = "";
    }
    
    /**
     * Constructor without business name
     */
    public CurrentAccount(String accountNumber, Customer customer, double initialBalance) {
        this(accountNumber, customer, initialBalance, "Individual Current Account");
    }
    
    /**
     * Overridden withdraw method with overdraft support
     * Allows withdrawal beyond balance up to overdraft limit
     */
    @Override
    public boolean withdraw(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid amount! Amount must be greater than 0.");
            return false;
        }
        
        double availableBalance = getBalance() + (overdraftLimit - overdraftUsed);
        
        if (amount > availableBalance) {
            System.out.println("Insufficient funds! Available (with overdraft): Rs. " + availableBalance);
            System.out.println("Requested: Rs. " + amount);
            return false;
        }
        
        // Check if we need to use overdraft
        if (amount > getBalance()) {
            double overdraftNeeded = amount - getBalance();
            overdraftUsed += overdraftNeeded;
            System.out.println("Overdraft used: Rs. " + overdraftNeeded);
            System.out.println("Total overdraft used: Rs. " + overdraftUsed + " / " + overdraftLimit);
            // Withdraw remaining balance first
            super.withdraw(getBalance());
        } else {
            super.withdraw(amount);
        }
        
        transactionCount++;
        checkTransactionFee();
        return true;
    }
    
    /**
     * Deposit method - First repays overdraft if any
     */
    @Override
    public void deposit(double amount) {
        if (amount <= 0) {
            System.out.println("Invalid deposit amount!");
            return;
        }
        
        // If overdraft is used, repay it first
        if (overdraftUsed > 0) {
            if (amount <= overdraftUsed) {
                overdraftUsed -= amount;
                System.out.println("Overdraft repaid: Rs. " + amount);
                System.out.println("Remaining overdraft: Rs. " + overdraftUsed);
                return;
            } else {
                double remaining = amount - overdraftUsed;
                System.out.println("Overdraft fully repaid: Rs. " + overdraftUsed);
                overdraftUsed = 0;
                super.deposit(remaining);
                System.out.println("Remaining Rs. " + remaining + " added to balance");
            }
        } else {
            super.deposit(amount);
        }
        
        transactionCount++;
    }
    
    /**
     * Check and apply transaction fee after free limit
     */
    private void checkTransactionFee() {
        if (transactionCount > FREE_TRANSACTIONS_PER_MONTH) {
            System.out.println("Transaction fee applied: Rs. " + TRANSACTION_FEE);
            System.out.println("Free transactions: " + FREE_TRANSACTIONS_PER_MONTH + 
                             ", Used: " + transactionCount);
            // Fee is deducted from balance
            super.withdraw(TRANSACTION_FEE);
        }
    }
    
    /**
     * Add authorized signatory for business account
     */
    public void addAuthorizedSignatory(String name) {
        if (name != null && !name.trim().isEmpty()) {
            authorizedSignatories.add(name);
            System.out.println("Authorized signatory added: " + name);
        }
    }
    
    /**
     * Remove authorized signatory
     */
    public boolean removeAuthorizedSignatory(String name) {
        boolean removed = authorizedSignatories.remove(name);
        if (removed) {
            System.out.println("Removed signatory: " + name);
        } else {
            System.out.println("Signatory not found: " + name);
        }
        return removed;
    }
    
    /**
     * Display all signatories
     */
    public void displaySignatories() {
        System.out.println("\n--- Authorized Signatories for " + businessName + " ---");
        if (authorizedSignatories.isEmpty()) {
            System.out.println("No authorized signatories added yet.");
        } else {
            for (int i = 0; i < authorizedSignatories.size(); i++) {
                System.out.println((i+1) + ". " + authorizedSignatories.get(i));
            }
        }
    }
    
    /**
     * Get overdraft status
     */
    public void displayOverdraftStatus() {
        System.out.println("\n--- Overdraft Status ---");
        System.out.println("Overdraft Limit: Rs. " + overdraftLimit);
        System.out.println("Overdraft Used: Rs. " + overdraftUsed);
        System.out.println("Overdraft Available: Rs. " + (overdraftLimit - overdraftUsed));
        System.out.println("Overdraft Enabled: " + (isOverdraftEnabled ? "Yes" : "No"));
    }
    
    /**
     * Display complete current account info
     */
    public void displayCurrentAccountInfo() {
        displayAccountInfo();
        System.out.println("\n--- Current Account Details ---");
        System.out.println("Business Name: " + businessName);
        System.out.println("GST Number: " + (gstNumber.isEmpty() ? "Not Provided" : gstNumber));
        System.out.println("Account Opening Date: " + accountOpeningDate);
        System.out.println("Monthly Maintenance Fee: Rs. " + monthlyMaintenanceFee);
        System.out.println("Free Transactions/Month: " + FREE_TRANSACTIONS_PER_MONTH);
        System.out.println("Transactions This Month: " + transactionCount);
        displayOverdraftStatus();
        displaySignatories();
    }
    
    // Getters and Setters with documentation
    
    public double getOverdraftLimit() { return overdraftLimit; }
    
    public void setOverdraftLimit(double limit) {
        if (limit >= 0) {
            this.overdraftLimit = limit;
            System.out.println("Overdraft limit updated to: Rs. " + limit);
        }
    }
    
    public double getOverdraftUsed() { return overdraftUsed; }
    public int getTransactionCount() { return transactionCount; }
    public String getBusinessName() { return businessName; }
    
    public void setBusinessName(String businessName) {
        this.businessName = businessName;
    }
    
    public String getGstNumber() { return gstNumber; }
    
    public void setGstNumber(String gstNumber) {
        this.gstNumber = gstNumber;
    }
    
    public boolean isOverdraftEnabled() { return isOverdraftEnabled; }
    
    public void setOverdraftEnabled(boolean enabled) {
        this.isOverdraftEnabled = enabled;
    }
    
    public LocalDate getAccountOpeningDate() { return accountOpeningDate; }
}
