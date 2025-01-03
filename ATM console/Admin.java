import java.util.ArrayList;
import java.util.List;

public class Admin {
    private static List<Transfer> transactionHistory=new ArrayList<>();//static transac list to handle transac history in adminactions.java
    private  String username;//username
    private  int password;//admin pass
    private  double atmBalance;//admin deposit amt to update atmbal

    public Admin(String username, int password) {
        this.username = username;
        this.password = password;
    }

    public  String getUsername() {//getting admin- username
        return username;
    }

    public  void setUsername(String username) {
        this.username = username;
    }//setter method

    public  int getPassword() {
        return password;
    }//getter method to get pass

    public  void setPassword(int password) {
        this.password = password;
    }//setter method

    public double getAtmBalance() {
        return atmBalance;
    }//getter method tp get artmbal

    public void setAtmBalance(double atmBalance) {
        this.atmBalance = atmBalance;
    }

    public List<Transfer> getTransactionHistory() {//getter method to get transac history from admin's deposit method
        return transactionHistory;
    }

    public void setTransactionHistory(List<Transfer> transactionHistory) {//setter to set the transac history
        this.transactionHistory = transactionHistory;
    }
}
