
public class User extends Account {// User class
    private double balance;
    //constructor method
    public User(String username, int password) {
        super(username, password);
    }
    public double getBalance() { //getter for the user's bal
        return balance;
    }

    public void setBalance(double balance) { //setter for the user bal
        this.balance = balance;
    }
}

//    public int setPassword(int newPin) {//method to set a new password
//        return password;
//
//    }

//    public String getUsername() { //Getter for the username
//        return username;
//    }
//
//    public int getPassword() {  //getter for the password
//        return password;
//    }