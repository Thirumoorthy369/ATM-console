import java.util.ArrayList;
import java.util.List;

public class Account {
    private String username;// Username for the user
    private int password;// Password to store pass
    private  List<Transfer> transactionHistory = new ArrayList<>();// List to store the user's transaction history

    //constructor method
    protected Account(String username, int password) {
        this.username = username;
        this.password = password;

    }

    public String getUsername() { //Getter for the username
        return username;
    }

    public int getPassword() {  //getter for the password
        return password;
    }


    public List<Transfer> getTransactionHistory(String type, String username, double totalAmount) {//getter for the transaction
//        this.transactionHistory = new ArrayList<>();
        return transactionHistory;
    }

    public void addTransactionHistory(Transfer transfer) {//Method to add a transaction to the history
        transactionHistory.add(transfer);
    }

    public int setPassword(int newPin) {//method to set a new password
        return password;
    }
//    protected static List<Admin>adminList = new ArrayList<>();//admin list to handle admin data
//    protected static List<User> userList = new ArrayList<>();//userlist to handle user datas
}
