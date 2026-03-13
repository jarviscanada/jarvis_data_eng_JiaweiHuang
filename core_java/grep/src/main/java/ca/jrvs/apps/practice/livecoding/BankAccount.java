package ca.jrvs.apps.practice.livecoding;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

public class BankAccount {
    private String accountNumber;
    private String ownerNmae;
    private double balance;
    public static final double threshold = 1000;
    private List<Double> transactions = new ArrayList<>();

    public BankAccount(String accountNumber, String ownerNmae, double balance) {
        if (balance < 0) {
            throw new IllegalArgumentException("Balance cannot be negative");
        }
        this.accountNumber = accountNumber;
        this.ownerNmae = ownerNmae;
        this.balance = balance;

        if(balance > 0){
            transactions.add(balance);
        }
    }

    public void deposit (double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Deposit amount cannot be negative");
        }
        balance += amount;
        transactions.add(amount);
    }

    public void withdraw (double amount){
        if (amount <= 0){
            throw new IllegalArgumentException("Withdrawn amount cannot be less than 0");
        } else if(amount > balance){
            throw new IllegalArgumentException("Withdrawn amount cannot be greater than balance");
        }
        balance -= amount;
        transactions.add(-amount);
    }

    public double getBalance(){
        return balance;
    }

    public String getAccountInfo(){
        return "Account Number: " + accountNumber + "\n" +
                "Owner Name: " + ownerNmae + "\n" +
                "Balance: " + String.format("%.2f", balance);
    }

    // add on stream api
    public double getTotalDeposited(){
        return transactions.stream().filter(t -> t > 0)
                .mapToDouble(Double::doubleValue).sum();
    }

    public double getTotalWithdrawn(){
        return transactions.stream().filter(t -> t < 0)
                .mapToDouble(Math::abs).sum();
    }

    public double getLargestTransaction(){
        return transactions.stream().mapToDouble(Math::abs).max().orElse(0.0);
    }

    public List<Double> getAllDeposits() {
        return transactions.stream()
                .filter(t -> t > 0)
                .collect(Collectors.toList());
    }
}


