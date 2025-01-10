import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;// Username for the user
    private int password;// Password to store pass
    private  List<Transfer> transactionHistory = new ArrayList<>();// List to store the  transaction's history

    //constructor method
    protected Account(String username, int password) {
        this.username = username;
        this.password = password;
    }

    protected Account() {

    }

    public String getUsername() { //Getter for the username
        return username;
    }

    public int getPassword() {  //getter for the password
        return password;
    }

    public List<Transfer> getTransactionHistory() {//getter for the transaction
        return transactionHistory;
    }

    public int setPassword(int newPin) {//method to set a new password
        return password;
    }
}
