public class Customer {
    private String customerId;
    private String name;
    private String email;
    private String phone;
    private String address;
    private String panCard;
    private String accountType;

    private static int customerCount = 0;

    public Customer(String name, String email, String phone, String address, String panCard, String accountType) {
        this.customerId = generateCustomerId();
        this.name = name;
        this.email = email;
        this.phone = phone;
        this.address = address;
        this.panCard = panCard;
        this.accountType = accountType;
        customerCount++;
    }

    private String generateCustomerId() {
        return "CUST" + (1000 + customerCount);
    }

    public void showCustomerDetails() {
        System.out.println("\n--- Customer Details ---");
        System.out.println("Customer ID: " + customerId);
        System.out.println("Name: " + name);
        System.out.println("Email: " + email);
        System.out.println("Phone: " + phone);
        System.out.println("Address: " + address);
        System.out.println("PAN: " + panCard);
        System.out.println("Account Type: " + accountType);
    }

    public void updateEmail(String newEmail) {
        if(newEmail.contains("@")){
            this.email = newEmail;
            System.out.println("Email updated successfully to: " + newEmail);
        } else {
            System.out.println("Invalid email format!");
        }
    }

    public void updatePhone(String newPhone) {
        if(newPhone.length() == 10){
            this.phone = newPhone;
            System.out.println("Phone updated successfully to: " + newPhone);
        } else {
            System.out.println("Phone number must be 10 digits!");
        }
    }

    public void updateAddress(String newAddress) {
        this.address = newAddress;
        System.out.println("Address updated successfully.");
    }

    public static void totalCustomers() {
        System.out.println("Total Customers in Bank: " + customerCount);
    }

    public String getCustomerId() {
        return customerId;
    }

    public String getName() {
        return name;
    }

    public void verifyKYC() {
        System.out.println("\nVerifying KYC for " + name + "...");
        if(panCard != null && phone.length() == 10){
            System.out.println("KYC Verified Successfully!");
        } else {
            System.out.println("KYC Failed. Please provide valid documents.");
        }
    }
}
