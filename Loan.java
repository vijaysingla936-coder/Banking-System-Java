public class Loan {
    private String loanType;
    private double loanAmount;
    private double interestRate;
    private int durationYears;
    private boolean isApproved;

    public Loan(String loanType, double loanAmount, int durationYears) {
        this.loanType = loanType;
        this.loanAmount = loanAmount;
        this.durationYears = durationYears;
        this.isApproved = false;
        setInterestRate();
    }

    private void setInterestRate() {
        switch (loanType.toLowerCase()) {
            case "home":
                interestRate = 8.5;
                break;
            case "car":
                interestRate = 9.2;
                break;
            case "personal":
                interestRate = 11.5;
                break;
            case "education":
                interestRate = 7.5;
                break;
            default:
                interestRate = 10.0;
        }
    }

    public void checkEligibility(double accountBalance, double monthlyIncome) {
        System.out.println("\n--- Checking Loan Eligibility ---");
        if (accountBalance < 5000) {
            System.out.println("Not Eligible: Minimum balance 5000 required.");
            return;
        }
        if (monthlyIncome < 15000) {
            System.out.println("Not Eligible: Minimum income 15000 required.");
            return;
        }
        if (loanAmount > accountBalance * 10) {
            System.out.println("Not Eligible: Loan amount too high for your balance.");
            return;
        }
        isApproved = true;
        System.out.println("Congratulations! You are eligible for " + loanType + " loan.");
    }

    public void applyLoan() {
        if (!isApproved) {
            System.out.println("Please check eligibility first.");
            return;
        }
        System.out.println("\nLoan Applied Successfully!");
        System.out.println("Type: " + loanType);
        System.out.println("Amount: Rs." + loanAmount);
        System.out.println("Duration: " + durationYears + " years");
        System.out.println("Interest Rate: " + interestRate + "%");
        calculateEMI();
    }

    public void calculateEMI() {
        double monthlyRate = interestRate / (12 * 100);
        int months = durationYears * 12;
        double emi = (loanAmount * monthlyRate * Math.pow(1 + monthlyRate, months)) / (Math.pow(1 + monthlyRate, months) - 1);
        System.out.println("Estimated Monthly EMI: Rs." + String.format("%.2f", emi));
        System.out.println("Total Payable: Rs." + String.format("%.2f", emi * months));
    }

    public void showLoanDetails() {
        System.out.println("\n--- Loan Details ---");
        System.out.println("Type: " + loanType + " | Amount: " + loanAmount + " | Approved: " + isApproved);
    }
}
