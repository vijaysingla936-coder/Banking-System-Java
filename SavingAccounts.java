import java.util.regex.Pattern;

/**
 * Banking Utilities - Helper methods for validation and operations
 * Author: Vijay Singla
 * Project: Banking-System-Java
 */
public class BankingUtils {
    
    // Constants
    public static final double MIN_BALANCE = 1000.0;
    public static final double MAX_WITHDRAWAL_PER_DAY = 50000.0;
    public static final String BANK_CODE = "SBI";
    
    /**
     * Validates account number format
     */
    public static boolean isValidAccountNumber(String accNo) {
        if (accNo == null || accNo.length() != 12) return false;
        Pattern pattern = Pattern.compile("^[0-9]{12}$");
        return pattern.matcher(accNo).matches();
    }
    
    /**
     * Validates PAN card
     */
    public static boolean isValidPAN(String pan) {
        if (pan == null) return false;
        Pattern pattern = Pattern.compile("[A-Z]{5}[0-9]{4}[A-Z]{1}");
        return pattern.matcher(pan).matches();
    }
    
    /**
     * Validates Aadhar
     */
    public static boolean isValidAadhar(String aadhar) {
        if (aadhar == null) return false;
        Pattern pattern = Pattern.compile("^[0-9]{12}$");
        return pattern.matcher(aadhar).matches();
    }
    
    /**
     * Validates phone number
     */
    public static boolean isValidPhone(String phone) {
        if (phone == null) return false;
        Pattern pattern = Pattern.compile("^[6-9][0-9]{9}$");
        return pattern.matcher(phone).matches();
    }
    
    /**
     * Validates email
     */
    public static boolean isValidEmail(String email) {
        if (email == null) return false;
        Pattern pattern = Pattern.compile("^[A-Za-z0-9+_.-]+@(.+)$");
        return pattern.matcher(email).matches();
    }
    
    // ... aur bhi methods add kar sakta hai
    // Interest calculation, Loan EMI, etc.
    
    public static double calculateEMI(double principal, double rate, int months) {
        double monthlyRate = rate / (12 * 100);
        return (principal * monthlyRate * Math.pow(1 + monthlyRate, months)) 
                / (Math.pow(1 + monthlyRate, months) - 1);
    }
    
    public static double calculateCompoundInterest(double p, double r, int t, int n) {
        return p * Math.pow(1 + (r / n), n * t) - p;
    }
    
    public static String generateTransactionId() {
        return "TXN" + System.currentTimeMillis() + (int)(Math.random() * 1000);
    }
    
    public static String generateAccountNumber() {
        return "SBIN" + (long)(Math.random() * 1000000000000L);
    }
    
    public static void printBankHeader() {
        System.out.println("========================================");
        System.out.println("      SBI - STATE BANK OF INDIA        ");
        System.out.println("========================================");
    }
    
    public static void printWelcomeMessage(String name) {
        System.out.println("\nWelcome, " + name + "!");
        System.out.println("Thank you for banking with us.");
    }
}
