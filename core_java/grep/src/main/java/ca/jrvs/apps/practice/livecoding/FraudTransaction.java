package ca.jrvs.apps.practice.livecoding;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

public class FraudTransaction {
    public List<Integer> detectFraud(List<Integer> transactions, int threshold) {

        List<Integer> suspicious = new ArrayList<>();

        for (Integer amount : transactions) {
            if (amount > BankAccount.threshold) {
                suspicious.add(amount);
            }
        }
        return suspicious;
    }

    public void main(String[] args) {

        List<Integer> transactions = Arrays.asList(20, 40, 5000, 30, 3000);
        int threshold = 1000;

        List<Integer> result = detectFraud(transactions, threshold);
        System.out.println(result);
    }
}
