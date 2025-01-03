public class Transfer { // Class to handle individual transactions
    private String type;
    private double amount;

    // Constructor to initialize the type and amount of the transaction
    public Transfer(String type, double amount) {
        this.type = type;
        this.amount = amount;
    }

    public String getType() {// Getter for type of the transaction
        return type;
    }

    public double getAmount() {// Getter for type of the transaction
        return amount;
    }

    @Override
    public String toString() {// Getter for type of the transaction
        return "Type: " + type + ", Amount: " + amount;
    }
}
