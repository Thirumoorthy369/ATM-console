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

    public String getType() {// Getter for type of the transaction
        return type;
    }

    public String getName(){
        return name;
    }

    public double getAmount() {// Getter for type of the transaction
        return amount;
    }

    @Override
    public String toString() {// Getter for type of the transaction
        return "Type: " + type + "Done by" + name + ", Amount: " + amount;
    }
}
