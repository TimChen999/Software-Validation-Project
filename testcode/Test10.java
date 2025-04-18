import java.util.concurrent.*;

public class BankSystem {
    private int balance;

    public BankSystem(int initialBalance) {
        this.balance = initialBalance;
    }

    public void deposit(int amount) {
        balance += amount;
    }

    public void withdraw(int amount) {
        if (balance >= amount) {
            balance -= amount;
        }
    }

    public int getBalance() {
        return balance;
    }

    public static void main(String[] args) throws InterruptedException {
        BankSystem account = new BankSystem(1000);

        Runnable depositTask = () -> {
            for (int i = 0; i < 1000; i++) {
                account.deposit(1);
            }
        };

        Runnable withdrawTask = () -> {
            for (int i = 0; i < 1000; i++) {
                account.withdraw(1);
            }
        };

        Thread t1 = new Thread(depositTask);
        Thread t2 = new Thread(withdrawTask);
        t1.start();
        t2.start();
        t1.join();
        t2.join();

        System.out.println("Final Balance: " + account.getBalance());
    }
}
