import Notes.Notes;
import java.util.List;
import  java.util.Scanner;

public class AdminActions {  //class to handle admin - specific actions

    //Method for admin login
    public static Account adminLogin(Scanner sc, List<Account> adminList) {
        int attempts = 0;//counter for admin login attempts
        while (attempts < 3) { //while loop to check the admin login credentials
            System.out.print("Enter Admin Username: ");
            String username = sc.nextLine();

            // Check if the username exists in the adminList
            boolean usernameExists = false;
            for (Account admin : adminList) {//for loop to get the list of admin username
                if(admin instanceof Admin) { //if condition check this one is Admin/User object
                    if (admin.getUsername().equals(username)) { //checks the username == getusername
                        usernameExists = true; //it makes bool var = true to get the password
                        break;
                    }
                }
            }

            if (!usernameExists) {//notify when username doesn't exist
                System.out.println("Invalid username, username not found");
                return null; //return to the previous one
            }
            //getting the adminpass.
            System.out.print("Enter Admin Password: ");
            int password = Integer.parseInt(sc.nextLine());

            //check whether the username and password are correct
            for (Account admin : adminList) { //for loop to get the list of admin username
                if (admin instanceof Admin) { //if condition to check this one is Admin Object
                    if (admin.getUsername().equals(username) && admin.getPassword() == password) {//checking the usrname and the password given is matching not
                        System.out.println("Login successful!");
                        return admin; //return admin to the previous one
                    }
                }
            }
            attempts++;//increment attempts count
            System.out.println("Invalid credentials. Attempts left: " + (3 - attempts));
        }
        System.out.println("Maximum attempts reached. Returning to the main menu...");
        return null;//return if 3 attempts get failed
    }

    // Method to add a new user
     static void addUser(Scanner sc, List<Account> userList) {
        String username; // Variable for new username
        boolean run = true; // Loop control variable
        while (run) {// if true enters into the body
            //getting the username.
            System.out.print("Enter new username: ");
            username = sc.nextLine();

            // Check if user already exists
            boolean userExists = false;
            for (Account user : userList) {//for loop to get the list of admin username
                if (user instanceof User) {//if condition to check this one is Admin Object
                    if (user.getUsername().equals(username)) {//checking the username is equal or not.
                        System.out.println("User already exists");
                        userExists = true;//if username is exist then reassign the userexists as true.
                        break;
                    }
                }
            }
            if (!userExists) { // If user exist, proceed
                //getting the pass
                System.out.print("Enter new password: ");
                int password = Integer.parseInt(sc.nextLine());
                // adding the user object to the userlist
                userList.add(new User(username, password)); // Add user to list
                System.out.println("User added successfully.");
                run = false; // Exit loop after adding user
            }
        }
    }

    // Method to delete an existing user
    static void deleteUser(Scanner sc, List<Account> userList) {//method to delete the user
        System.out.print("Enter username to delete: "); // Prompt for username to delete
        String userToDelete = sc.nextLine();//getting the username to delete
        //remove the user from the uswer arraylist.
        userList.removeIf(user -> user.getUsername().equals(userToDelete)); // Remove user from list
        System.out.println("User deleted successfully.");
    }

    // Method for admin deposit
     static double adminDeposit(Scanner sc, double amount, ATMSystem atmsystem,Admin admin) {

        //getting the denominations.
        System.out.println("Enter denominations and their counts for deposit:");
        System.out.print("Number of 2000 notes: ");
        int count2000 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 500 notes: ");
        int count500 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 200 notes: ");
        int count200 = Integer.parseInt(sc.nextLine());
        System.out.print("Number of 100 notes: ");
        int count100 = Integer.parseInt(sc.nextLine());

        //calculating the total amount using the count and denomimations
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
            addTransactionHistory(admin ,new Transfer("Deposit","Admin",totalAmount));
            admin.getTransactionHistory();//calling the transactions methood using the admin object.
        } else {
            System.out.println("Your deposit amount is not equal to the denomination");
            return adminDeposit(sc, amount, atmsystem,admin); // Retry if mismatch
        }

        System.out.println("Deposit successful. Updated ATM Balance: " + ATMSystem.getAtmBalance());//printing the balance after deposit
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
            for (Account user : userList) {//for loop to get the list of user username
                if (user instanceof User) {//if condition to check this one is user Object
                    System.out.println(user.getUsername());//printing the user list
                }
            }
        }
    }

    // Method to view all transaction histories (Admin, User, or All)
    public static void Viewalltransac(Scanner sc) {
        // Provide options for viewing different types of transaction histories
        System.out.println("\nSelect an option:");
        System.out.println("1. Admin transaction history");
        System.out.println("2. User transaction history");

        // Read the user's choice
        int choice = Integer.parseInt(sc.nextLine());
        switch (choice) {//switch case for transactions.
            case 1://admin transaction
                for(Account account1 : ATMSystem.getUserList()) {//for loop to get the list of admin username
                    if(account1 instanceof Admin) {//if condition to check this one is user Object
                        if (account1.getTransactionHistory().isEmpty()) {//getting the transaction histry of Admin
                            System.out.println("No Admin transactions found.");
                        } else {
                            System.out.println("Admin Transaction History:");
                            for (Transfer transfer : account1.getTransactionHistory()) {//getting the transaction using the account1(admin) object.
                                System.out.println(transfer);//printing the transfer.
                            }
                        }
                    }
                }
                break;

            case 2:
                // Display User transaction history for a specific user
                System.out.print("Enter the username of the user: ");
                String username = sc.nextLine();

                // Check if the userList is not null before proceeding
                if (ATMSystem.getUserList() != null) {//checking the userlist isempty or not.
                    User user = null;

                    // Search for the user by username
                    for (Account u : ATMSystem.getUserList()) {//for loop to get the list of admin username
                        if (u instanceof User) {//if condition to check this one is user Object
                            if (u.getUsername().equals(username)) {//checks is the username is equal or not.
                                user = (User) u;//reassigning u to the user(in User type).
                                break;
                            }
                        }
                    }

                    // If user is found, display their transaction history
                    if (user != null) {
                        UserActions.viewTransactionHistory(user);//calling the transaction history method.
                    } else {
                        System.out.println("User not found");
                    }
                }else {//if no users found.
                    System.out.println("No users found in the system");
                }
                break;
            default:
                System.out.println("Invalid choice, please enter a valid choice.");
        }
    }


    // Method to display the current ATM balance
     static double atmbalances() {
        System.out.println("ATM Balance: " + ATMSystem.getAtmBalance());
        return 0;
    }

    public static void addTransactionHistory(Account account,Transfer transfer) {//Method to add a transaction to the history
        account.getTransactionHistory().add(transfer);
    }

}