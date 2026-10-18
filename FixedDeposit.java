import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;
import java.util.Random;

/**
 * FixedDeposit.java - Banking-System-Java
 * Author: vijaysingla936-coder
 * Handles FD Accounts, Interest Calculation, Maturity
 */

class FDAccount {
    String fdId;
    String customerName;
    String accountNumber;
    double principalAmount;
    double interestRate;
    int durationMonths;
    LocalDate startDate;
    LocalDate maturityDate;
    double maturityAmount;
    boolean isActive;
    
    public FDAccount(String custName, String accNo, double amount, double rate, int months) {
        this.customerName = custName;
        this.accountNumber = accNo;
        this.principalAmount = amount;
        this.interestRate = rate;
        this.durationMonths = months;
        this.startDate = LocalDate.now();
        this.maturityDate = startDate.plusMonths(months);
        this.isActive = true;
        this.fdId = generateFDId();
        this.maturityAmount = calculateMaturityAmount();
    }
    
    private String generateFDId() {
        Random rand = new Random();
        return "FD" + (100000 + rand.nextInt(900000));
    }
    
    public double calculateMaturityAmount() {
        // Compound Interest Formula: A = P(1 + r/100)^t
        double years = durationMonths / 12.0;
        double amount = principalAmount * Math.pow((1 + interestRate / 100), years);
        return amount;
    }
    
    public double calculateInterest() {
        return maturityAmount - principalAmount;
    }
}

public class FixedDeposit {
    
    private Scanner sc;
    
    public FixedDeposit() {
        sc = new Scanner(System.in);
        System.out.println("Fixed Deposit Module Initialized");
    }
    
    public void showFDPlans() {
        System.out.println("\n========== FD INTEREST RATE PLANS ==========");
        System.out.println("Duration       | General | Senior Citizen");
        System.out.println("-------------------------------------------");
        System.out.println("7-14 Days      | 3.00%   | 3.50%");
        System.out.println("15-29 Days     | 3.25%   | 3.75%");
        System.out.println("30-90 Days     | 4.50%   | 5.00%");
        System.out.println("91-180 Days    | 5.25%   | 5.75%");
        System.out.println("6 Months       | 5.75%   | 6.25%");
        System.out.println("9 Months       | 6.00%   | 6.50%");
        System.out.println("1 Year         | 6.80%   | 7.30%");
        System.out.println("2 Years        | 7.00%   | 7.50%");
        System.out.println("3 Years        | 7.10%   | 7.60%");
        System.out.println("5 Years (Tax)  | 6.50%   | 7.00% + Tax Benefit");
        System.out.println("============================================");
    }
    
    public FDAccount createFD() {
        System.out.println("\n--- CREATE NEW FIXED DEPOSIT ---");
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Linked Savings Account No: ");
        String accNo = sc.nextLine();
        System.out.print("Enter FD Amount (Min Rs.1000): ");
        double amount = sc.nextDouble();
        System.out.print("Enter Duration in Months (e.g. 12): ");
        int months = sc.nextInt();
        System.out.print("Is Senior Citizen? (true/false): ");
        boolean isSenior = sc.nextBoolean();
        
        double rate = getInterestRate(months, isSenior);
        System.out.println("Applicable Interest Rate: " + rate + "%");
        
        FDAccount fd = new FDAccount(name, accNo, amount, rate, months);
        System.out.println("\nFD Created Successfully!");
        System.out.println("FD ID: " + fd.fdId);
        System.out.println("Principal: Rs. " + fd.principalAmount);
        System.out.println("Maturity Amount: Rs. " + String.format("%.2f", fd.maturityAmount));
        System.out.println("Interest Earned: Rs. " + String.format("%.2f", fd.calculateInterest()));
        System.out.println("Maturity Date: " + fd.maturityDate);
        return fd;
    }
    
    private double getInterestRate(int months, boolean isSenior) {
        double baseRate = 6.8;
        if (months <= 1) baseRate = 4.5;
        else if (months <= 3) baseRate = 5.25;
        else if (months <= 6) baseRate = 5.75;
        else if (months <= 12) baseRate = 6.8;
        else if (months <= 24) baseRate = 7.0;
        else baseRate = 7.1;
        
        if (isSenior) baseRate += 0.5;
        return baseRate;
    }
    
    public void calculatePrematureWithdrawal(FDAccount fd) {
        System.out.println("\n--- PREMATURE WITHDRAWAL CALCULATION ---");
        long daysPassed = ChronoUnit.DAYS.between(fd.startDate, LocalDate.now());
        System.out.println("Days Completed: " + daysPassed);
        System.out.println("Original Rate: " + fd.interestRate + "%");
        double penaltyRate = 1.0;
        double newRate = fd.interestRate - penaltyRate;
        System.out.println("After 1% Penalty, Rate: " + newRate + "%");
        double years = daysPassed / 365.0;
        double prematureAmount = fd.principalAmount * Math.pow((1 + newRate/100), years);
        System.out.printf("Premature Amount Payable: Rs. %.2f\n", prematureAmount);
        System.out.println("Penalty: 1% interest + No extra benefits");
    }
    
    public void fdCalculator() {
        System.out.println("\n--- FD CALCULATOR ---");
        System.out.print("Enter Amount: ");
        double p = sc.nextDouble();
        System.out.print("Enter Rate %: ");
        double r = sc.nextDouble();
        System.out.print("Enter Years: ");
        double t = sc.nextDouble();
        double maturity = p * Math.pow((1 + r/100), t);
        System.out.printf("Maturity: Rs. %.2f | Interest: Rs. %.2f\n", maturity, (maturity - p));
    }
    
    public void showTaxBenefitFD() {
        System.out.println("\n========== TAX SAVING FD (5 Years) ==========");
        System.out.println("1. Deduction under 80C up to Rs. 1.5 Lakh");
        System.out.println("2. Lock-in period 5 years");
        System.out.println("3. Interest Taxable");
        System.out.println("4. Rate: 6.5% General, 7.0% Senior");
        System.out.println("5. Auto-renewal option available");
        System.out.println("=============================================");
    }
    
    public void fdRenewalProcess(FDAccount fd) {
        System.out.println("\n--- FD RENEWAL ---");
        System.out.println("FD ID " + fd.fdId + " matured on " + fd.maturityDate);
        System.out.println("Maturity Amount: Rs. " + fd.maturityAmount);
        System.out.println("Options: 1. Renew Principal Only 2. Renew with Interest 3. Close & Transfer to Savings");
        System.out.println("Renewal done for next " + fd.durationMonths + " months at rate " + fd.interestRate + "%");
    }
    
    public static void main(String[] args) {
        FixedDeposit fdModule = new FixedDeposit();
        fdModule.showFDPlans();
        FDAccount fd = fdModule.createFD();
        fdModule.fdCalculator();
        fdModule.showTaxBenefitFD();
        fdModule.fdRenewalProcess(fd);
    }
}
