package main.java;

import java.util.HashMap;
import java.util.Map;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * A banking service implementation.
 * .
 */
public class Test13 {
    private final Map<String, Account> accounts;

    public Test13() {
        this.accounts = new HashMap<>();
    }

    /**
     * Creates a new account with the specified ID and initial balance
     */
    public void createAccount(String accountId, double initialBalance) {
        if (accounts.containsKey(accountId)) {
            throw new IllegalArgumentException("Account already exists: " + accountId);
        }

        Account account = new Account(accountId, initialBalance);
        accounts.put(accountId, account);
        System.out.println("CREATE: " + accountId + " - $" + initialBalance);
    }

    /**
     * Transfers money between two accounts
     */
    public boolean transferMoney(String fromAccountId, String toAccountId, double amount) {
        Account fromAccount = accounts.get(fromAccountId);
        Account toAccount = accounts.get(toAccountId);

        if (fromAccount == null || toAccount == null || amount <= 0) {
            return false;
        }

        fromAccount.lock.lock();
        try {
            toAccount.lock.lock();
            try {
                if (fromAccount.balance < amount) {
                    return false;
                }

                fromAccount.balance -= amount;

                toAccount.balance += amount;

                System.out.println("TRANSFER: " + fromAccountId + "->" + toAccountId + " - $" + amount);
                return true;
            } finally {
                toAccount.lock.unlock();
            }
        } finally {
            fromAccount.lock.unlock();
        }
    }

    /**
     * Deposits money into an account
     */
    public boolean deposit(String accountId, double amount) {
        Account account = accounts.get(accountId);

        if (account == null || amount <= 0) {
            return false;
        }

        account.balance += amount;
        System.out.println("DEPOSIT: " + accountId + " - $" + amount);
        return true;
    }

    /**
     * Withdraws money from an account
     */
    public boolean withdraw(String accountId, double amount) {
        Account account = accounts.get(accountId);

        if (account == null || amount <= 0) {
            return false;
        }

        account.lock.lock();
        try {
            if (account.balance < amount) {
                return false;
            }

            account.balance -= amount;
            System.out.println("WITHDRAW: " + accountId + " - $" + amount);
            return true;
        } finally {
            account.lock.unlock();
        }
    }

    /**
     * Gets the balance of an account
     */
    public double getBalance(String accountId) {
        Account account = accounts.get(accountId);

        if (account == null) {
            throw new IllegalArgumentException("Account does not exist: " + accountId);
        }

        return account.balance;
    }

    /**
     * Applies interest to all accounts
     */
    public void applyInterest(double rate) {

        for (Account account : accounts.values()) {

            double interest = account.balance * rate;
            account.balance += interest;
            System.out.println("INTEREST: " + account.id + " - $" + interest);
        }
    }

    /**
     * Internal Account class
     */
    private static class Account {
        private final String id;
        private double balance;
        private final Lock lock;

        public Account(String id, double initialBalance) {
            this.id = id;
            this.balance = initialBalance;
            this.lock = new ReentrantLock();
        }
    }

    /**
     * Demonstrates a problematic concurrent usage of the banking service
     */
    public static void main(String[] args) {
        final Test13 bank = new Test13();
        bank.createAccount("A", 1000);
        bank.createAccount("B", 1000);

        ExecutorService executor = Executors.newFixedThreadPool(5);

        try {
            // Submit transfer and deposit tasks
            for (int i = 0; i < 10; i++) {
                final int iteration = i;
                executor.submit(() -> {
                    if (iteration % 2 == 0) {
                        bank.transferMoney("A", "B", 100);
                    } else {
                        bank.deposit(iteration % 3 == 0 ? "A" : "B", 50);
                    }
                });
            }

            // Submit interest application tasks
            executor.submit(() -> bank.applyInterest(0.01));

            executor.shutdown();

            executor.awaitTermination(5, TimeUnit.SECONDS);

            System.out.println("Final balance A: " + bank.getBalance("A"));
            System.out.println("Final balance B: " + bank.getBalance("B"));

        } catch (InterruptedException e) {

            System.out.println("Operation interrupted");
        }
    }
}