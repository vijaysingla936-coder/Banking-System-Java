import java.util.*;

class BankAccount {
    String accNo, name;
    double balance;
    BankAccount(String accNo, String name, double balance){
        this.accNo = accNo; this.name = name; this.balance = balance;
    }
    void deposit(double amt){ balance+=amt; System.out.println(amt+" Deposited!"); }
    void withdraw(double amt){
        if(amt <= balance){ balance-=amt; System.out.println(amt+" Withdrawn!"); }
        else System.out.println("Low Balance!");
    }
    void display(){ System.out.println("\nAcc No: "+accNo+"\nName: "+name+"\nBalance: "+balance); }
}

public class Main {
    public static void main(String[] args){
        Scanner sc = new Scanner(System.in);
        ArrayList<BankAccount> list = new ArrayList<>();
        int ch;
        do{
            System.out.println("\n--- Vijay's Bank ---\n1.Create 2.Deposit 3.Withdraw 4.Display 5.Exit");
            ch = sc.nextInt();
            if(ch==1){
                System.out.print("Acc No: "); String no=sc.next();
                System.out.print("Name: "); String n=sc.next();
                System.out.print("Balance: "); double b=sc.nextDouble();
                list.add(new BankAccount(no,n,b));
            } else if(ch>=2 && ch<=4){
                System.out.print("Enter Acc No: "); String no=sc.next();
                for(BankAccount a: list){
                    if(a.accNo.equals(no)){
                        if(ch==2){ System.out.print("Amount: "); a.deposit(sc.nextDouble()); }
                        if(ch==3){ System.out.print("Amount: "); a.withdraw(sc.nextDouble()); }
                        if(ch==4){ a.display(); }
                    }
                }
            }
        }while(ch!=5);
    }
}
