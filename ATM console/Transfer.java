public class Transfer { // Class to handle individual transactions
    private String type;
    private double amount;
    private String name;

    // Constructor to initialize the type and amount of the transaction
    public Transfer(String type, String name,double amount) {
        this.type = type;
        this.name = name;
        this.amount = amount;
    }

    public String getType() {// Getter method to get the type of the transaction
        return type;
    }

    public String getName(){ //Getter method to get the name
        return name;
    }

    public double getAmount() {// Getter method to get the amount
        return amount;
    }

    @Override
    public String toString() {// Override the tostring method to get user-friendly transaction
        return "Type: " + type + " Done by " + name + ", Amount: " + amount;
    }
}