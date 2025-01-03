import java.util.ArrayList;
import java.util.List;

public class User {// User class
    private String username;// Username for the user
    private int password;// Password to store pass
    private double balance;
    private List<Transfer> transactionHistory;// List to store the user's transaction history

    //constructor method
    public User(String username, int password, double balance) {
        this.username = username;
        this.password = password;
        this.balance = balance;
        this.transactionHistory = new ArrayList<>();
    }

    public String getUsername() { //Getter for the username
        return username;
    }

    public int getPassword() {  //getter for the password
        return password;
    }

    public double getBalance() { //getter for the user's bal
        return balance;
    }

    public void setBalance(double balance) { //setter for the user bal
        this.balance = balance;
    }

    public List<Transfer> getTransactionHistory() { //getter for the transaction
        return transactionHistory;
    }

    public void addTransaction(Transfer transfer) {//Method to add a transaction to the history
        transactionHistory.add(transfer);
    }

    public int setPassword(int newPin) {//method to set a new password
        return password;

    }
}
