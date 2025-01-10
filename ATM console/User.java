
public class User extends Account {// User class
    private double balance;
    //constructor method
    public User(String username, int password) {
        super(username, password);//super keyword to access the superclass
    }

    public User() {
        super();
    }

    public double getBalance() { //getter for the user's bal
        return balance;
    }

    public void setBalance(double balance) { //setter for the user bal
        this.balance = balance;
    }
}