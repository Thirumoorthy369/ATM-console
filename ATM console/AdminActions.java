import Notes.Notes;//to handle exception in  user change pin method
import java.util.InputMismatchException;
import java.util.List;
import  java.util.Scanner;

public class AdminActions {


    public static Account adminLogin(Scanner sc, List<Account> adminList) {
        int attempts = 0;
        while (attempts < 3) {
            System.out.print("Enter Admin Username: ");
            String username = sc.nextLine();

            // Check if the username exists in the adminList
            boolean usernameExists = false;
            for (Account admin : adminList) {
                if (admin.getUsername().equals(username)) {
                    usernameExists = true;
                    break;
                }
            }

            if (!usernameExists) {//notify when username doesn't exist
                System.out.println("Invalid username, username not found");
                return null;
            }

            System.out.print("Enter Admin Password: ");
            int password = Integer.parseInt(sc.nextLine());

            //check whether the username and password are correct
            for (Account admin : adminList) {
                if (admin.getUsername().equals(username) && admin.getPassword() == password) {
                    System.out.println("Login successful!");
                    return admin;
                }
            }

            attempts++;//increament attempts count
            System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
        }

        System.out.println("Maximum attempts reached. Returning to the main menu...");
        return null;//return if 3 attempts  get failed
    }


    // Admin operations
//    public static void operation(Scanner sc, List<Account> userList) {
//
//    }

    // Method to add a new user
     static void addUser(Scanner sc, List<Account> userList,User account) {
        String username; // Variable for new username
        boolean run = true; // Loop control variable
        while (run) {
            System.out.print("Enter new username: ");
            username = sc.nextLine();

            // Check if user already exists
            boolean userExists = false;
            for (Account user : userList) {
                if (user instanceof User) {
                    if (user.getUsername().equals(username)) {
                        System.out.println("User already exists");
                        userExists = true;
                        break;
                    }
                }
            }

            if (!userExists) { // If user does not exist, proceed
                System.out.print("Enter new password: ");
                int password = Integer.parseInt(sc.nextLine());
//                System.out.print("Enter initial balance: ");
//                double balance = parseDouble(sc.nextLine());
//                initalbalanceupdate(sc, balance, atmsystem, userList); // Add balance to ATM system
                userList.add(new User(username, password)); // Add user to list
                System.out.println("User added successfully.");
                run = false; // Exit loop after adding user
            }
        }
    }

    // Method to delete an existing user
    static void deleteUser(Scanner sc, List<Account> userList) {
        System.out.print("Enter username to delete: "); // Prompt for username to delete
        String userToDelete = sc.nextLine();
        userList.removeIf(user -> user.getUsername().equals(userToDelete)); // Remove user from list
        System.out.println("User deleted successfully.");
    }

    // Method for admin deposit
     static double adminDeposit(Scanner sc, double amount, ATMSystem atmsystem,Account account) {
        System.out.println("Enter denominations and their counts for deposit:");
        System.out.print("Number of 2000 notes: ");
        int count2000 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 500 notes: ");
        int count500 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 200 notes: ");
        int count200 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 100 notes: ");
        int count100 = Integer.parseInt(sc.nextLine());

        double totalAmount = (2000 * count2000) + (500 * count500) + (200 * count200) + (100 * count100);

        if (totalAmount == amount) { // Check if total denominations match the deposit amount
            // Update ATM denominations and balance
            Notes note2000 = atmsystem.getNotesByDenomination(2000);
            Notes note500 = atmsystem.getNotesByDenomination(500);
            Notes note200 = atmsystem.getNotesByDenomination(200);
            Notes note100 = atmsystem.getNotesByDenomination(100);

            note2000.setNote(note2000.getNote() + count2000);
            note500.setNote(note500.getNote() + count500);
            note200.setNote(note200.getNote() + count200);
            note100.setNote(note100.getNote() + count100);

            ATMSystem.updateAtmBalance(totalAmount); // Update total ATM balance
            account.addTransactionHistory(new Transfer("Deposit","Admin",totalAmount));
            account.getTransactionHistory("Deposit","Admin",totalAmount);
        } else {
            System.out.println("Your deposit amount is not equal to the denomination");
            return adminDeposit(sc, amount, atmsystem,account); // Retry if mismatch
        }

        System.out.println("Deposit successful. Updated ATM Balance: " + ATMSystem.getAtmBalance());
        return 0;
    }


// Method to view the list of users
        static void viewUserList(List<Account> userList) {
        // Check if the user list is empty
        if (userList == null || userList.isEmpty()) {
            System.out.println("No users found.");
        } else {
            // Display all usernames in the list
            System.out.println("User List:");
            for (Account user : userList) {
                if (user instanceof User) {
                    System.out.println(user.getUsername());
                }
            }
        }
    }

    // Method to view all transaction histories (Admin, User, or All)
    public static void Viewalltransac(Scanner sc,Account account) {
        // Provide options for viewing different types of transaction histories
        System.out.println("\nSelect an option:");
        System.out.println("1. Admin transaction history");
        System.out.println("2. User transaction history");
        System.out.println("3. All transaction history");

        // Read the user's choice
        int choice = Integer.parseInt(sc.nextLine());

      //  List<Transfer> transac = account.getTransactionHistory("","",0);
        switch (choice) {
            case 1:
                // Display Admin transaction history
                List<Transfer> adminTransactions = account.getTransactionHistory("Deposit", "Admin", 0);
                if (adminTransactions.isEmpty()) {
                    System.out.println("No Admin transactions found.");
                } else {
                    System.out.println("Admin Transaction History:");
                    for (Transfer transfer : adminTransactions) {
                        System.out.println(transfer);
                    }
                }
                break;

            case 2:
                // Display User transaction history for a specific user
                System.out.print("Enter the username of the user: ");
                String username = sc.nextLine();

                // Retrieve the user list from the ATM system
                List<Account> userList = ATMSystem.getUserList();

                // Check if the userList is not null before proceeding
                if (userList != null) {
                    User user = null;

                    // Search for the user by username
                    for (Account u : userList) {
                        if (u instanceof User) {
                            if (u.getUsername().equals(username)) {
                                user = (User) u;
                                break;
                            }
                        }
                    }

                    // If user is found, display their transaction history
                    if (user != null) {
                        List<Transfer> userTransactions = user.getTransactionHistory("Deposit", username, 0);
                        userTransactions.addAll(user.getTransactionHistory("Withdraw", username, 0));
                        
                        if (userTransactions.isEmpty()) {
                            System.out.println("No transactions found for user: " + username);
                        } else {
                            System.out.println("Transaction History for user: " + username);
                            for (Transfer transfer : userTransactions) {
                                System.out.println(transfer);
                            }
                        }
                    } else {
                        System.out.println("User not found");
                    }
                } else {
                    System.out.println("No users found in the system");
                }
                break;

            case 3:
                // Display all transaction history
                // if (transac.isEmpty()) {
                //     System.out.println("Not found all the transaction history.");
                // } else {
                //     System.out.println("All Transaction History:");
                //     for (Transfer transfer : transac) {
                //         System.out.println(transfer);
                //     }
                // }
                // break;

            default:
                System.out.println("Invalid choice, please enter a valid choice.");
        }
    }

    // Method to change a user's PIN
    static void changeUserPin(Scanner sc, List<Account> userList) {
        Account user = null;


        System.out.println("Enter the username of the user:");
        String username = sc.nextLine();

        // Search for the user in the user list
        for (Account u : userList) {
            if (userList instanceof User) {
                if (u.getUsername().equals(username)) {//check if username == userlist
                    user = u;
                    break;
                }
            }
        }

        // If the user is not found, display an error and exit
        if (user == null) {
            System.out.println("User not found!");
            return;
        }


        System.out.println("Enter the new PIN for the user:");
        //exception handling to avoid Inputmismatchexception
        try {
            int pin = sc.nextInt();
            sc.nextLine();
            user.setPassword(pin); // Update the user's PIN
            System.out.println("PIN changed successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please enter a valid PIN.");
        }
    }

    // Method to display the current ATM balance
     static double atmbalances() {
        System.out.println("ATM Balance: " + ATMSystem.getAtmBalance());
        return 0;
    }


    // Method to update the initial balance in the ATM
//    private static double initalbalanceupdate(Scanner sc, double balance, ATMSystem atmsystem, List<Account> userList) {
//
//        System.out.println("Enter denominations and their counts for initial balance:");
//        System.out.print("Number of 2000 notes: ");
//        int count2000 = Integer.parseInt(sc.nextLine());
//        System.out.print("Number of 500 notes: ");
//        int count500 = Integer.parseInt(sc.nextLine());
//        System.out.print("Number of 200 notes: ");
//        int count200 = Integer.parseInt(sc.nextLine());
//        System.out.print("Number of 100 notes: ");
//        int count100 = Integer.parseInt(sc.nextLine());
//
//        // Calculate the total amount from denominations
//        double totalAmount = (2000 * count2000) + (500 * count500) + (200 * count200) + (100 * count100);
//
//        // Check if the total amount matches the provided balance
//        if (totalAmount == balance) {
//            // Update the ATM denominations and balance
//            Notes note2000 = atmsystem.getNotesByDenomination(2000);
//            Notes note500 = atmsystem.getNotesByDenomination(500);
//            Notes note200 = atmsystem.getNotesByDenomination(200);
//            Notes note100 = atmsystem.getNotesByDenomination(100);
//
//            note2000.setNote(note2000.getNote() + count2000);
//            note500.setNote(note500.getNote() + count500);
//            note200.setNote(note200.getNote() + count200);
//            note100.setNote(note100.getNote() + count100);
//
//            // Update user balances and add the transaction
//            for (User user : userList) {
//                user.setBalance(user.getBalance() + totalAmount);
//                user.addTransactionHistory(new Transfer("Deposit", totalAmount));
//            }
//            ATMSystem.updateAtmBalance(totalAmount);
//
//            System.out.println("Initial balance added successfully. Updated ATM Balance: " + ATMSystem.getAtmBalance());
//        } else {
//            System.out.println("Your deposit amount is not equal to the denomination\n");
//            return initalbalanceupdate(sc, balance, atmsystem, userList);// return if amount of deposit of initialbal != denom
//        }
//        return 0;
//    }

}

