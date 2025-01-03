import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Scanner;

public class ATMSystem {

    private static double atmbalance ;//variable to handle balance
    private static List<Admin>adminList = new ArrayList<>();//admin list to handle admin data
    private static List<User> userList = new ArrayList<>();//userlist to handle user datas
    private  ArrayList<Notes>notesArrayList = new ArrayList<>(Arrays.asList(new Twoo(2000,0,0),
            new Five(500,0,0),new Two(200,0,0),new One(100,0,0)));//array list for notes
     public static List<Transfer> transactionHistory =new ArrayList<>();//array list to handle transactions


    public static void start()  { //start method
        Scanner sc = new Scanner(System.in);//scanner usage
        adminList.add(new Admin("admin", 1234));//admin's new admin name and password
        adminList.add(new Admin("superadmin",12345));
//        adminList.add(new Admin("",0));


        while (true) { //while loop
            System.out.println("\nATM System Menu:");
            System.out.println("1. Admin Login \n2. User Login \n3. Exit \nEnter your choice: ");
            int choice = Integer.parseInt(sc.nextLine());//choice for switch

            switch (choice) {
                case 1:
                    Admin admin = AdminActions.adminLogin(sc,adminList);//admin logic func
                    if (admin != null) {
                        AdminActions.operation(sc, userList,transactionHistory);//admin op
                    }
                    break;
                case 2:
                    User user = UserActions.userLogin(sc, userList); //user login func
                    if (user != null) {
                        UserActions.operation(sc, user); //user op
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
    public static List<User> getUserList() { //getting user list to get the users details
        return userList;
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
