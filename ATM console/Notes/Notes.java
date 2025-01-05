package Notes;

public class Notes {// Notes class represents each denomination of ATM notes and their quantities
    protected int denomination;
    protected double amount;
    protected int note;

    // Constructor to initialize denomination, amount, and quantity
    public Notes(int denomination, double amount, int quantity) {
        this.denomination = denomination;
        this.amount = amount;
        this.note = quantity;
    }

    public int getNote() {// Getter for the quantity of the denomination
        return note;
    }
    public void setNote(int a){// Setter to update the quantity of the denomination
        this.note=a;
    }


    @Override
    public String toString() {// Override toString to represent the denomination in a user-friendly format

        return denomination + " * " + note + " = " + amount;
    }

    public int getDenomination() {// Getter for the denomination
        return denomination;
    }

}