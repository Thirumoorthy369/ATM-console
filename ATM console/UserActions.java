import java.util.List;
import java.util.Scanner;
import Notes.Notes;

public class UserActions {


    public static Account userLogin(Scanner sc, List<Account> userList) {
        int attempts = 0; // Counter for login attempts

        // Allow up to 3 login attempts
        while (attempts < 3) {
            //getting the name  nd pass for uer
            System.out.print("Enter Username: ");
            String username = sc.nextLine();
            System.out.print("Enter Password: ");
            int password = Integer.parseInt(sc.nextLine());

            // Check if the username = userlist
            for (Account user : userList) {//for loop to get the list of admin username
                if (user instanceof User) {
                    if (user.getUsername().equals(username) && user.getPassword() == password) { // Verify username and password
                        System.out.println("Login successful!");
                        return user; //return to the user
                    }
                }
            }

            attempts++; // Increment login attempt counter
            System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
        }

        // Maximum attempts reached, return null
        System.out.println("Maximum attempts reached.\nReturning to the main menu...");
        return null;
    }

    // Method to handle deposit operation
    static Double handleDeposit(Scanner sc, Account user, double amount, ATMSystem atmsystem,User user1) {
        //getting the denominations for deposit amount.
        System.out.println("Enter denominations and their counts for deposit:");

        System.out.print("Number of 2000 notes: ");
        int count2000 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 500 notes: ");
        int count500 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 200 notes: ");
        int count200 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 100 notes: ");
        int count100 = Integer.parseInt(sc.nextLine());

        // Calculate the total deposit amount
        int totalAmount = (2000 * count2000) + (500 * count500) + (200 * count200) + (100 * count100);

        // chech if total amount == amount
        if (totalAmount == amount) {
            // Retrieve notes by denomination from ATM system
            Notes note2000 = atmsystem.getNotesByDenomination(2000);
            Notes note500 = atmsystem.getNotesByDenomination(500);
            Notes note200 = atmsystem.getNotesByDenomination(200);
            Notes note100 = atmsystem.getNotesByDenomination(100);

            // Update the count of each denomination in the ATM system
            note2000.setNote(note2000.getNote() + count2000);
            note500.setNote(note500.getNote() + count500);
            note200.setNote(note200.getNote() + count200);
            note100.setNote(note100.getNote() + count100);

            // Update user's balance and add transaction
            user1.setBalance(user1.getBalance() + totalAmount);
            user.getTransactionHistory().add(new Transfer("Deposit","User", totalAmount));

            System.out.println("Deposit successful. Current Balance: " + user1.getBalance());
            ATMSystem.updateAtmBalance(amount); // Update ATM balance
        } else {
            System.out.println("Your deposit amount does not match the denominations.");
            return handleDeposit(sc, user, amount, atmsystem,user1); // Recursively call deposit handler
        }
        return null; // Return null after successful deposit
    }

    // Method to handle withdrawal operation
     static void handleWithdraw(Scanner sc, Account user, ATMSystem atmsystem,User user1) {
        //getting the withdrawn amount.
        System.out.print("Enter withdrawal amount: ");
        int withdrawAmount = Integer.parseInt(sc.nextLine());

        // Check if the ATM has sufficient funds
        if (withdrawAmount > ATMSystem.getAtmBalance()) {
            System.out.println("ATM has insufficient funds.");
            return; // Exit method if funds are insufficient
        }

        // Retrieve notes by denomination from ATM system
        Notes note2000 = atmsystem.getNotesByDenomination(2000);
        Notes note500 = atmsystem.getNotesByDenomination(500);
        Notes note200 = atmsystem.getNotesByDenomination(200);
        Notes note100 = atmsystem.getNotesByDenomination(100);

        int remainingAmount = withdrawAmount; // Track the amount to withdraw

        // Calculate the number of notes for each denomination to withdraw
        int count2000 = Math.min(remainingAmount / 2000, note2000.getNote());
        remainingAmount -= count2000 * 2000;

        int count500 = Math.min(remainingAmount / 500, note500.getNote());
        remainingAmount -= count500 * 500;

        int count200 = Math.min(remainingAmount / 200, note200.getNote());
        remainingAmount -= count200 * 200;

        int count100 = Math.min(remainingAmount / 100, note100.getNote());
        remainingAmount -= count100 * 100;

        // Check if the ATM can withdraw the exact amount
        if (remainingAmount > 0) {
            System.out.println("ATM cannot dispense the exact amount due to insufficient denominations.");
            return; // Exit method if exact amount cannot get withdraw
        }

        // Update ATM notes and balance
        note2000.setNote(note2000.getNote() - count2000);
        note500.setNote(note500.getNote() - count500);
        note200.setNote(note200.getNote() - count200);
        note100.setNote(note100.getNote() - count100);

        ATMSystem.updateAtmBalance(-withdrawAmount); // find withdrawal amount from ATM balance
        user1.setBalance(user1.getBalance() - withdrawAmount); //find withdrawal amount from user's balance
         user.getTransactionHistory().add(new Transfer( "Withdraw","User", withdrawAmount));


         // Notify user of successful withdrawal
        System.out.println("Withdrawal successful. Dispensed denominations:");
        System.out.println("2000 x " + count2000);
        System.out.println("500 x " + count500);
        System.out.println("200 x " + count200);
        System.out.println("100 x " + count100);
        System.out.println("Current Balance: " + user1.getBalance());
    }

    // Method to display user's current balance
    static void viewBalance(User user) {
        System.out.println("Your balance: " + user.getBalance());
    }

    // Method to display user's transaction history
    static void viewTransactionHistory(User user) {
        for(Account account: ATMSystem.getUserList()){//for loop to get the list of admin username
            if(account instanceof User){//if condition to check account is user Object
                if(account.equals(user)) {//checks the user is equal to the user.
                    List<Transfer> transactions = user.getTransactionHistory();//stores the particular user's transactions to the transactions.
                    if (transactions.isEmpty()) {//i transsactions is not empty enters into body.
                        System.out.println("No transaction history available.");
                    } else {
                        //if transaction is not empty.
                        System.out.println("\nTransaction History:");
                        for (Transfer transfer : transactions){//prints the each transaction using the loop.
                            System.out.println(transfer);
                        }
                    }
                }
            }
        }
    }
}