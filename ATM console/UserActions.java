import java.util.List;
import java.util.Scanner;

public class UserActions {


    public static User userLogin(Scanner sc, List<User> userList) {
        int attempts = 0; // Counter for login attempts

        // Allow up to 3 login attempts
        while (attempts < 3) {
            System.out.print("Enter Username: ");
            String username = sc.nextLine();
            System.out.print("Enter Password: ");
            int password = Integer.parseInt(sc.nextLine());

            // Check if the username = userlist
            for (User user : userList) {
                if (user.getUsername().equals(username) && user.getPassword() == password) { // Verify username and password
                    System.out.println("Login successful!");
                    return user; //return to the user
                }
            }

            attempts++; // Increment login attempt counter
            System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
        }

        // Maximum attempts reached, return null
        System.out.println("Maximum attempts reached.\nReturning to the main menu...");
        return null;
    }

    public static void operation(Scanner sc, User user) {
        ATMSystem atmsystem = new ATMSystem(); // Create an instance var of ATMSystem

        boolean exitMenu = false; // boolean var to control menu loop

        // Loop until user chooses to exit
        while (!exitMenu) {
            System.out.println("\nUser Menu:");
            System.out.println("1. Deposit\n2. Withdraw\n3. View Balance\n4. View Transaction History\n5. Exit");
            int choice = Integer.parseInt(sc.nextLine()); // Read and parse user choice

            switch (choice) {
                case 1: // Deposit option
                    System.out.println("Enter the amount to deposit:");
                    double amount = Double.parseDouble(sc.nextLine());
                    handleDeposit(sc, user, amount, atmsystem); //goto deposit method
                    break;

                case 2: // Withdraw option
                    handleWithdraw(sc, user, atmsystem); //goto withdraw method
                    break;

                case 3: // View balance option
                    viewBalance(user); // Display user's balance
                    break;

                case 4: // View transaction history option
                    viewTransactionHistory(user); // Display user's transaction history
                    break;

                case 5: // Exit option
                    exitMenu = true; // Exit the menu loop
                    break;

                default: // Handle invalid input
                    System.out.println("Invalid choice.");
            }
        }
    }

    // Method to handle deposit operation
    private static Object handleDeposit(Scanner sc, User user, double amount, ATMSystem atmsystem) {
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
            user.setBalance(user.getBalance() + totalAmount);
            user.addTransaction(new Transfer("Deposit", totalAmount));

            System.out.println("Deposit successful. Current Balance: " + user.getBalance());
            ATMSystem.updateAtmBalance(amount); // Update ATM balance
        } else {
            System.out.println("Your deposit amount does not match the denominations.");
            return handleDeposit(sc, user, amount, atmsystem); // Recursively call deposit handler
        }
        return null; // Return null after successful deposit
    }

    // Method to handle withdrawal operation
    private static void handleWithdraw(Scanner sc, User user, ATMSystem atmsystem) {
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
        user.setBalance(user.getBalance() - withdrawAmount); //find withdrawal amount from user's balance
        user.addTransaction(new Transfer("Withdraw", withdrawAmount)); // Record the transaction

        // Notify user of successful withdrawal
        System.out.println("Withdrawal successful. Dispensed denominations:");
        System.out.println("2000 x " + count2000);
        System.out.println("500 x " + count500);
        System.out.println("200 x " + count200);
        System.out.println("100 x " + count100);
        System.out.println("Current Balance: " + user.getBalance());
    }

    // Method to display user's current balance
    private static void viewBalance(User user) {
        System.out.println("Your balance: " + user.getBalance());
    }

    // Method to display user's transaction history
    private static void viewTransactionHistory(User user) {
        List<Transfer> transactionHistory = user.getTransactionHistory(); // view user's transaction history

        // Check if there are any transactions
        if (transactionHistory.isEmpty()) {
            System.out.println("No transaction history found."); //  display if no transactions exist
        } else {
            System.out.println("Transaction History:");
            for (Transfer transfer : transactionHistory) { // Loop through and display each transaction
                System.out.println(transfer);
            }
        }
    }
}
