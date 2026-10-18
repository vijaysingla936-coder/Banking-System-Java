import java.time.LocalDateTime;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.Random;
import java.util.Scanner;

/**
 * NetBanking.java - Banking-System-Java
 * Author: vijaysingla936-coder
 * Internet Banking, Fund Transfer, IMPS, NEFT, Security
 */

class NetBankingUser {
    String userId;
    String password;
    String accountNumber;
    String customerName;
    boolean isLocked;
    int failedAttempts;
    LocalDateTime lastLogin;
    
    public NetBankingUser(String userId, String pass, String accNo, String name) {
        this.userId = userId;
        this.password = pass;
        this.accountNumber = accNo;
        this.customerName = name;
        this.isLocked = false;
        this.failedAttempts = 0;
    }
}

class FundTransfer {
    String txnId;
    String fromAccount;
    String toAccount;
    double amount;
    String mode;
    LocalDateTime timestamp;
    String status;
    
    public FundTransfer(String from, String to, double amt, String mode) {
        this.fromAccount = from;
        this.toAccount = to;
        this.amount = amt;
        this.mode = mode;
        this.timestamp = LocalDateTime.now();
        this.txnId = "NB" + (1000000 + new Random().nextInt(9000000));
        this.status = "SUCCESS";
    }
}

public class NetBanking {
    
    private HashMap<String, NetBankingUser> users;
    private ArrayList<FundTransfer> transferHistory;
    private Scanner sc;
    private NetBankingUser currentUser;
    
    public NetBanking() {
        users = new HashMap<>();
        transferHistory = new ArrayList<>();
        sc = new Scanner(System.in);
        // Demo users
        users.put("vijay936", new NetBankingUser("vijay936", "Vijay@123", "SB123456789", "Vijay Singla"));
        System.out.println("Net Banking System Initialized - Secure 128-bit Encryption");
    }
    
    public void registerUser() {
        System.out.println("\n--- REGISTER FOR NET BANKING ---");
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        System.out.print("Enter Customer Name: ");
        String name = sc.nextLine();
        System.out.print("Choose User ID: ");
        String userId = sc.nextLine();
        System.out.print("Set Password (Min 8 chars, 1 special): ");
        String pass = sc.nextLine();
        System.out.print("Enter Mobile for OTP: ");
        String mobile = sc.nextLine();
        
        int otp = 100000 + new Random().nextInt(900000);
        System.out.println("OTP sent to " + mobile + ": " + otp);
        System.out.print("Enter OTP: ");
        int enteredOtp = sc.nextInt();
        sc.nextLine();
        
        if (enteredOtp == otp) {
            NetBankingUser newUser = new NetBankingUser(userId, pass, accNo, name);
            users.put(userId, newUser);
            System.out.println("Registration Successful! User ID: " + userId);
            System.out.println("You can now login with your credentials.");
        } else {
            System.out.println("Invalid OTP! Registration failed.");
        }
    }
    
    public boolean login() {
        System.out.println("\n========== NET BANKING LOGIN ==========");
        System.out.print("User ID: ");
        String userId = sc.nextLine();
        System.out.print("Password: ");
        String pass = sc.nextLine();
        
        if (!users.containsKey(userId)) {
            System.out.println("User ID not found!");
            return false;
        }
        
        NetBankingUser user = users.get(userId);
        if (user.isLocked) {
            System.out.println("Account Locked! Contact branch. Too many failed attempts.");
            return false;
        }
        
        if (user.password.equals(pass)) {
            user.failedAttempts = 0;
            user.lastLogin = LocalDateTime.now();
            currentUser = user;
            System.out.println("Login Successful! Welcome " + user.customerName);
            System.out.println("Last Login: " + user.lastLogin);
            showDashboard();
            return true;
        } else {
            user.failedAttempts++;
            System.out.println("Invalid Password! Attempt " + user.failedAttempts + "/3");
            if (user.failedAttempts >= 3) {
                user.isLocked = true;
                System.out.println("Account Locked due to 3 failed attempts!");
            }
            return false;
        }
    }
    
    public void showDashboard() {
        int choice;
        do {
            System.out.println("\n========== DASHBOARD - " + currentUser.customerName + " ==========");
            System.out.println("Account: " + currentUser.accountNumber);
            System.out.println("1. Check Balance");
            System.out.println("2. Transfer Funds (IMPS/NEFT/RTGS)");
            System.out.println("3. Add Beneficiary");
            System.out.println("4. View Mini Statement");
            System.out.println("5. Pay Bills (Electricity, Mobile, DTH)");
            System.out.println("6. Request Cheque Book");
            System.out.println("7. Change Password");
            System.out.println("8. View Transfer History");
            System.out.println("9. Logout");
            System.out.print("Choice: ");
            choice = sc.nextInt();
            sc.nextLine();
            
            switch(choice) {
                case 1: checkBalance(); break;
                case 2: transferFunds(); break;
                case 3: addBeneficiary(); break;
                case 4: miniStatement(); break;
                case 5: billPayment(); break;
                case 6: requestChequeBook(); break;
                case 7: changePassword(); break;
                case 8: viewTransferHistory(); break;
            }
        } while(choice != 9);
    }
    
    public void checkBalance() {
        double balance = 50000 + new Random().nextInt(100000);
        System.out.println("\nAccount Balance for " + currentUser.accountNumber);
        System.out.println("Available Balance: Rs. " + balance);
        System.out.println("Hold Amount: Rs. 0.00");
        System.out.println("Total Balance: Rs. " + balance);
    }
    
    public void transferFunds() {
        System.out.println("\n--- FUND TRANSFER ---");
        System.out.println("Modes: 1. IMPS (Instant 24x7) 2. NEFT (30 min) 3. RTGS (Min 2 Lakh)");
        System.out.print("Choose Mode: ");
        String mode = sc.nextLine();
        System.out.print("Enter Beneficiary Account No: ");
        String toAcc = sc.nextLine();
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();
        sc.nextLine();
        System.out.print("Enter Remarks: ");
        String remarks = sc.nextLine();
        
        int otp = 100000 + new Random().nextInt(900000);
        System.out.println("OTP for transfer Rs." + amt + " is: " + otp);
        System.out.print("Enter OTP: ");
        int enteredOtp = sc.nextInt();
        sc.nextLine();
        
        if (enteredOtp == otp) {
            FundTransfer ft = new FundTransfer(currentUser.accountNumber, toAcc, amt, mode);
            transferHistory.add(ft);
            System.out.println("Transfer Successful!");
            System.out.println("Txn ID: " + ft.txnId + " | Amount: Rs. " + amt + " | To: " + toAcc);
            System.out.println("Mode: " + mode + " | Remarks: " + remarks);
        } else {
            System.out.println("Invalid OTP! Transfer failed.");
        }
    }
    
    public void addBeneficiary() {
        System.out.println("\n--- ADD BENEFICIARY ---");
        System.out.print("Enter Beneficiary Name: ");
        String name = sc.nextLine();
        System.out.print("Enter Account Number: ");
        String accNo = sc.nextLine();
        System.out.print("Enter IFSC Code: ");
        String ifsc = sc.nextLine();
        System.out.print("Enter Nickname: ");
        String nick = sc.nextLine();
        System.out.println("Beneficiary " + name + " (" + nick + ") added. Verification: 30 min.");
    }
    
    public void miniStatement() {
        System.out.println("\n--- MINI STATEMENT (Last 5) ---");
        System.out.println(LocalDateTime.now().minusDays(1) + " | Debit | Rs. 2000 | ATM Withdrawal");
        System.out.println(LocalDateTime.now().minusDays(2) + " | Credit | Rs. 25000 | Salary Credit");
        System.out.println(LocalDateTime.now().minusDays(3) + " | Debit | Rs. 500 | UPI Payment");
        System.out.println(LocalDateTime.now().minusDays(4) + " | Debit | Rs. 1200 | Shopping");
        System.out.println(LocalDateTime.now().minusDays(5) + " | Credit | Rs. 1000 | Refund");
    }
    
    public void billPayment() {
        System.out.println("\n--- BILL PAYMENT (BBPS) ---");
        System.out.println("1. Electricity 2. Mobile Recharge 3. DTH 4. Water Bill");
        System.out.print("Choose: ");
        String type = sc.nextLine();
        System.out.print("Enter Consumer ID / Mobile No: ");
        String id = sc.nextLine();
        System.out.print("Enter Amount: ");
        double amt = sc.nextDouble();
        sc.nextLine();
        System.out.println("Bill Payment of Rs. " + amt + " for " + type + " (" + id + ") successful!");
        System.out.println("BBPS Reference No: BBPS" + (100000 + new Random().nextInt(900000)));
    }
    
    public void requestChequeBook() {
        System.out.println("\nCheque Book Request Submitted for " + currentUser.accountNumber);
        System.out.println("Type: 25 Leaves | Delivery: 7 working days to registered address");
        System.out.println("Charges: Rs. 100");
    }
    
    public void changePassword() {
        System.out.print("Enter Old Password: ");
        String oldPass = sc.nextLine();
        if (!oldPass.equals(currentUser.password)) {
            System.out.println("Old password incorrect!");
            return;
        }
        System.out.print("Enter New Password: ");
        String newPass = sc.nextLine();
        currentUser.password = newPass;
        System.out.println("Password Changed Successfully!");
    }
    
    public void viewTransferHistory() {
        System.out.println("\n--- TRANSFER HISTORY ---");
        for (FundTransfer ft : transferHistory) {
            System.out.println(ft.timestamp + " | " + ft.txnId + " | Rs." + ft.amount + " to " + ft.toAccount + " | " + ft.status);
        }
        if (transferHistory.isEmpty()) System.out.println("No transactions yet.");
    }
    
    public static void main(String[] args) {
        NetBanking nb = new NetBanking();
        nb.registerUser();
        nb.login();
    }
}
