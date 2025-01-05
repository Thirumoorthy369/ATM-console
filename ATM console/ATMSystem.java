import ListofNotes.*;
import Notes.Notes;
import static java.lang.Double.parseDouble;
import java.util.*;

public class ATMSystem {

    private static double atmbalance ;//variable to handle balance
    private static List<Account>accounts = new ArrayList<>();
    private  ArrayList<Notes>notesArrayList = new ArrayList<>(Arrays.asList(new Twoo(2000,0,0),
            new ListofNotes.Five(500,0,0),new ListofNotes.Two(200,0,0),new ListofNotes.One(100,0,0)));//array list for notes

    //private static List<Transfer> transactionHistory =new ArrayList<>();//array list to handle transactions
     static Account account = new Account("",0) ;

    public static void start()  { //start method
        Scanner sc = new Scanner(System.in);
        accounts.add(new Admin("admin", 1234));//admin's new admin name and password
        accounts.add(new Admin("superadmin",12345));//scanner usage

        Account acc = new User("",0);


        while (true) { //while loop
            System.out.println("\nATM System Menu:");
            System.out.println("1. Admin Login \n2. User Login \n3. Exit \nEnter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());//choice for switch

            switch (choice) {
                case 1:
                    Account admin = AdminActions.adminLogin(sc,accounts);//admin logic func
                    if (admin != null) {
                        ATMSystem atmsystem = new ATMSystem();//instance of ATMSystem
                        boolean exit = false;

                        //loop to display menu option
                        while (!exit) {
                            System.out.println("\nAdmin Menu:");
                            System.out.println("1. Add User\n2. Delete User\n3. Admin Deposit\n4. View ATM Balance\n5. View User List\n6. View all transactions\n7.Change user pin \n8.Exit");
                            int choice1 = Integer.parseInt(sc.nextLine());

                            switch (choice1) {
                                case 1:
                                   AdminActions.addUser(sc,accounts,(User) acc);//add user option
                                    break;
                                case 2:
                                    AdminActions.deleteUser(sc, accounts);//delete user option
                                    break;
                                case 3:
                                    System.out.print("Enter the amount to be deposit:");
                                    double amount = parseDouble(sc.nextLine());
                                    AdminActions.adminDeposit(sc, amount, atmsystem,account);//admin deposit option
                                    break;
                                case 4:
                                    AdminActions.atmbalances(); //view ATM Bal
                                    break;
                                case 5:
                                    AdminActions.viewUserList(accounts); //view user list
                                    break;
                                case 6:
                                    AdminActions.Viewalltransac(sc,account); //view all trasac his
                                    break;
                                case 7:
                                    AdminActions.changeUserPin(sc, accounts); //change user pin
                                    break;
                                case 8:
                                    exit = true;
                                    break;//exit the menu
                                default:
                                    System.out.println("Invalid choice.");
                            }
                        }
//                        AdminActions.operation(sc,accounts);//admin op
                    }
                    break;
                case 2:
                    Account user = UserActions.userLogin(sc,accounts); //user login func
                    if (user != null) {
                        ATMSystem atmsystem = new ATMSystem(); // Create an instance var of ATMSystem

                        boolean exitMenu = false; // boolean var to control menu loop

                        // Loop until user chooses to exit
                        while (!exitMenu) {
                            System.out.println("\nUser Menu:");
                            System.out.println("1. Deposit\n2. Withdraw\n3. View Balance\n4. View Transaction History\n5. Exit");
                            int choice2 = Integer.parseInt(sc.nextLine()); // Read and parse user choice

                            switch (choice2) {
                                case 1: // Deposit option
                                    System.out.println("Enter the amount to deposit:");
                                    double amount = Double.parseDouble(sc.nextLine());
                                    UserActions.handleDeposit(sc, user, amount, atmsystem,(User) acc); //goto deposit method
                                    break;

                                case 2: // Withdraw option
                                    UserActions.handleWithdraw(sc, user, atmsystem, (User) acc); //goto withdraw method
                                    break;

                                case 3: // View balance option
                                    UserActions.viewBalance((User)acc); // Display user's balance
                                    break;

                                case 4: // View transaction history option
                                   UserActions.viewTransactionHistory((User)acc); // Display user's transaction history
                                    break;

                                case 5: // Exit option
                                    exitMenu = true; // Exit the menu loop
                                    break;

                                default: // Handle invalid input
                                    System.out.println("Invalid choice.");
                            }
                        }
                        //UserActions.operation(sc, user,); //user op
                    }
                    break;
                case 3:
                    System.out.println("Exiting the ATM system...");
                    return;
                default:
                    System.out.println("Invalid choice. Please try again.");
            }
        }
    }


    public static double setAtmBalance(double amount){//set the atm balance to get the correct bal
        return atmbalance;
    }

    public static void updateAtmBalance(double amount) { // updating bal to get the correct balance
        atmbalance += amount;
    }
    public static double getAtmBalance() { //getting atmbal
        return atmbalance;
    }
    public static  List<Account> getUserList() {//getting user list to get the users details
            return accounts;
    }


    public Notes getNotesByDenomination(int denomination) {//getting denomination of the Atm amount
        for (Notes note : notesArrayList) {
            if (note.getDenomination() == denomination) {
                return note;
            }
        }
        return null; // Return null if no matching denomination is found
    }

}
