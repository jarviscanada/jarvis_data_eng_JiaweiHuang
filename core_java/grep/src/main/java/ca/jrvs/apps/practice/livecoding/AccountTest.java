package ca.jrvs.apps.practice.livecoding;

public class AccountTest {
    public static void main(String[] args) {

        BankAccount account = new BankAccount("12345", "Alice", 100.0);
        account.deposit(50.0);
        account.withdraw(30.0);
        System.out.println(account.getAccountInfo());

        System.out.println("Total Deposited: " + account.getTotalDeposited());
        System.out.println("Total Withdrawn: " + account.getTotalWithdrawn());
        System.out.println("Largest Transaction: " + account.getLargestTransaction());
        System.out.println("All Deposits: " + account.getAllDeposits());
    }
}
