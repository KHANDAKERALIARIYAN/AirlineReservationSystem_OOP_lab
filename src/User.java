import java.util.List;
import java.util.Scanner;

public class User {
    public static void main(String[] args) {
        Menu menu = new Menu();
        Authentication auth = new Authentication();
        CustomerManager customerManager = new CustomerManager();
        FlightManager flightManager = new FlightManager();

        menu.displayWelcomeMessage();
        int desiredOption;

        try (Scanner scanner = new Scanner(System.in)) {
            do {
                menu.displayMainMenu();
                desiredOption = scanner.nextInt();

                switch (desiredOption) {
                    case 1:
                        auth.handleAdminLogin(scanner, customerManager, flightManager);
                        break;
                    case 2:
                        auth.registerAdmin(scanner);
                        break;
                    case 3:
                        auth.handlePassengerLogin(scanner, flightManager);
                        break;
                    case 4:
                        customerManager.registerPassenger(scanner);
                        break;
                    case 5:
                        menu.displayManualInstructions(scanner);
                        break;
                    case 0:
                        System.out.println("Thanks for Using BAV Airlines Ticketing System...!!!");
                        break;
                    default:
                        System.out.println("Invalid Option. Please try again.");
                }
            } while (desiredOption != 0);
        }
    }

    public static List<Customer> getCustomersCollection() {
        throw new UnsupportedOperationException("Unimplemented method 'getCustomersCollection'");
    }
}

class Menu {
    void displayWelcomeMessage() {
        System.out.println(
                "\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++\n\nTo Further Proceed, Please enter a value.");
        System.out.println(
                "\n***** Default Username && Password is root-root ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
    }

    void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option:    ");
    }

    void displayManualInstructions(Scanner read) {
        System.out.printf("%n%n%50s %s Welcome to BAV Airlines User Manual %s", "", "+++++++++++++++++",
                "+++++++++++++++++");
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option :    ");
        int choice = read.nextInt();
        while (choice < 1 || choice > 2) {
            System.out.print("ERROR!!! Invalid entry...Please enter a value either 1 or 2....Enter again....");
            choice = read.nextInt();
        }
        if (choice == 1) {
            System.out.println(
                    "\n\n(1) Admin have the access to all users data...Admin can delete, update, add and can perform search for any customer...\n");
            System.out.println(
                    "(2) In order to access the admin module, you've to get yourself register by pressing 2, when the main menu gets displayed...\n");
            System.out.println(
                    "(3) Provide the required details i.e., name, email, id...Once you've registered yourself, press 1 to login as an admin... \n");
            System.out.println(
                    "(4) Once you've logged in, 2nd layer menu will be displayed on the screen...From here on, you can select from variety of options...\n");
            System.out.println(
                    "(5) Pressing \"1\" will add a new Passenger, provide the program with required details to add the passenger...\n");
            System.out.println(
                    "(6) Pressing \"2\" will search for any passenger, given the admin(you) provides the ID from the table printing above....  \n");
            System.out.println(
                    "(7) Pressing \"3\" will let you update any passengers data given the user ID provided to program...\n");
            System.out.println("(8) Pressing \"4\" will let you delete any passenger given its ID provided...\n");
            System.out.println("(9) Pressing \"5\" will let you display all registered passenger...\n");
            System.out.println(
                    "(10) Pressing \"6\" will let you display all registered passengers...After selecting, program will ask, if you want to display passengers for all flights(Y/y) or a specific flight(N/n)\n");
            System.out.println(
                    "(11) Pressing \"7\" will let you delete any flight given its flight number provided...\n");
            System.out.println(
                    "(11) Pressing \"0\" will make you logged out of the program...You can login again any time you want during the program execution....\n");
        } else {
            System.out.println(
                    "\n\n(1) Local user has the access to its data only...He/She won't be able to change/update other users data...\n");
            System.out.println(
                    "(2) In order to access local users benefits, you've to get yourself register by pressing 4, when the main menu gets displayed...\n");
            System.out.println(
                    "(3) Provide the details asked by the program to add you to the users list...Once you've registered yourself, press \"3\" to login as a passenger...\n");
            System.out.println(
                    "(4) Once you've logged in, 3rd layer menu will be displayed...From here on, you embarked on the journey to fly with us...\n");
            System.out.println(
                    "(5) Pressing \"1\" will display available/scheduled list of flights...To get yourself booked for a flight, enter the flight number and number of tickets for the flight...Max num of tickets at a time is 10 ...\n");
            System.out.println(
                    "(7) Pressing \"2\" will let you update your own data...You won't be able to update other's data... \n");
            System.out.println("(8) Pressing \"3\" will delete your account... \n");
            System.out
                    .println("(9) Pressing \"4\" will display randomly designed flight schedule for this runtime...\n");
            System.out.println("(10) Pressing \"5\" will let you cancel any flight registered by you...\n");
            System.out.println("(11) Pressing \"6\" will display all flights registered by you...\n");
            System.out.println(
                    "(12) Pressing \"0\" will make you logout of the program...You can login back at anytime with your credentials...for this particular run-time... \n");
        }
    }
}

class Authentication {
    static String[][] adminUserNameAndPassword = new String[10][2];
    private int countNumOfUsers = 1;

    void handleAdminLogin(Scanner scanner, CustomerManager customerManager, FlightManager flightManager) {
        RolesAndPermissions r1 = new RolesAndPermissions();
        adminUserNameAndPassword[0][0] = "root";
        adminUserNameAndPassword[0][1] = "root";

        System.out.print("\nEnter the UserName to login to the Management System :     ");
        String username = scanner.next();
        System.out.print("Enter the Password to login to the Management System :    ");
        String password = scanner.next();
        System.out.println();

        if (r1.isPrivilegedUserOrNot(username, password) == -1) {
            System.out.printf(
                    "\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n",
                    "");
        } else if (r1.isPrivilegedUserOrNot(username, password) == 0) {
            System.out.println(
                    "You've standard/default privileges to access the data... You can just view customers data...Can't perform any actions on them....");
            customerManager.displayCustomersData(true);
        } else {
            System.out.printf(
                    "%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", username);

            int desiredOption;
            do {
                System.out.printf("\n\n%-60s+++++++++ 2nd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "",
                        "", username);
                System.out.printf("%-30s (a) Enter 1 to add new Passenger....\n", "");
                System.out.printf("%-30s (b) Enter 2 to search a Passenger....\n", "");
                System.out.printf("%-30s (c) Enter 3 to update the Data of the Passenger....\n", "");
                System.out.printf("%-30s (d) Enter 4 to delete a Passenger....\n", "");
                System.out.printf("%-30s (e) Enter 5 to Display all Passengers....\n", "");
                System.out.printf("%-30s (f) Enter 6 to Display all flights registered by a Passenger...\n",
                        "");
                System.out.printf("%-30s (g) Enter 7 to Display all registered Passengers in a Flight....\n",
                        "");
                System.out.printf("%-30s (h) Enter 8 to Delete a Flight....\n", "");
                System.out.printf("%-30s (i) Enter 0 to Go back to the Main Menu/Logout....\n", "");
                System.out.print("Enter the desired Choice :   ");
                desiredOption = scanner.nextInt();

                switch (desiredOption) {
                    case 1:
                        customerManager.addNewCustomer();
                        break;
                    case 2:
                        customerManager.searchCustomer(scanner);
                        break;
                    case 3:
                        customerManager.updateCustomer(scanner);
                        break;
                    case 4:
                        customerManager.deleteCustomer(scanner);
                        break;
                    case 5:
                        customerManager.displayCustomersData(false);
                        break;
                    case 6:
                        flightManager.displayFlightsRegisteredByUser(scanner);
                        break;
                    case 7:
                        flightManager.displayRegisteredPassengers(scanner);
                        break;
                    case 8:
                        flightManager.deleteFlight(scanner);
                        break;
                    case 0:
                        System.out.println("Thanks for Using BAV Airlines Ticketing System...!!!");
                        break;
                    default:
                        System.out.println(
                                "Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                        desiredOption = 0;
                }
            } while (desiredOption != 0);
        }
    }

    void registerAdmin(Scanner scanner) {
        RolesAndPermissions r1 = new RolesAndPermissions();
        System.out.print("\nEnter the UserName to Register :    ");
        String username = scanner.next();
        System.out.print("Enter the Password to Register :     ");
        String password = scanner.next();
        while (r1.isPrivilegedUserOrNot(username, password) != -1) {
            System.out.print("ERROR!!! Admin with same UserName already exist. Enter new UserName:   ");
            username = scanner.next();
            System.out.print("Enter the Password Again:   ");
            password = scanner.next();
        }

        adminUserNameAndPassword[countNumOfUsers][0] = username;
        adminUserNameAndPassword[countNumOfUsers][1] = password;
        countNumOfUsers++;
    }

    void handlePassengerLogin(Scanner scanner, FlightManager flightManager) {
        RolesAndPermissions r1 = new RolesAndPermissions();
        System.out.print("\n\nEnter the Email to Login : \t");
        String userName = scanner.next();
        System.out.print("Enter the Password : \t");
        String password = scanner.next();
        String[] result = r1.isPassengerRegistered(userName, password).split("-");

        if (Integer.parseInt(result[0]) == 1) {
            int desiredChoice;
            System.out.printf(
                    "\n\n%-20sLogged in Successfully as \"%s\"..... For further Proceedings, enter a value from below....",
                    "", userName);
            do {
                System.out.printf("\n\n%-60s+++++++++ 3rd Layer Menu +++++++++%50sLogged in as \"%s\"\n", "",
                        "", userName);
                System.out.printf("%-40s (a) Enter 1 to Book a flight....\n", "");
                System.out.printf("%-40s (b) Enter 2 to update your Data....\n", "");
                System.out.printf("%-40s (c) Enter 3 to delete your account....\n", "");
                System.out.printf("%-40s (d) Enter 4 to Display Flight Schedule....\n", "");
                System.out.printf("%-40s (e) Enter 5 to Cancel a Flight....\n", "");
                System.out.printf("%-40s (f) Enter 6 to Display all flights registered by \"%s\"....\n", "",
                        userName);
                System.out.printf("%-40s (g) Enter 0 to Go back to the Main Menu/Logout....\n", "");
                System.out.print("Enter the desired Choice :   ");
                desiredChoice = scanner.nextInt();

                switch (desiredChoice) {
                    case 1:
                        flightManager.bookFlight(scanner, result[1]);
                        break;
                    case 2:
                        CustomerManager.editUserInfo(result[1]);
                        break;
                    case 3:
                        CustomerManager.deleteUserAccount(scanner, result[1], userName);
                        break;
                    case 4:
                        flightManager.displayFlightSchedule();
                        break;
                    case 5:
                        flightManager.cancelFlight(result[1]);
                        break;
                    case 6:
                        flightManager.displayFlightsRegisteredByOneUser(result[1]);
                        break;
                    case 0:
                        System.out.println("Thanks for Using BAV Airlines Ticketing System...!!!");
                        break;
                    default:
                        System.out.println(
                                "Invalid Choice...Looks like you're Robot...Entering values randomly...You've Have to login again...");
                        desiredChoice = 0;
                }
            } while (desiredChoice != 0);
        } else {
            System.out.printf(
                    "\n%20sERROR!!! Unable to login Cannot find user with the entered credentials.... Try Creating New Credentials or get yourself register by pressing 4....\n",
                    "");
        }
    }
}

class CustomerManager {
    private static List<Customer> customersCollection = new ArrayList<>();

    void addNewCustomer() {
        Customer c1 = new Customer();
        c1.addNewCustomer();
    }

    void searchCustomer(Scanner scanner) {
        Customer c1 = new Customer();
        displayCustomersData(false);
        System.out.print("Enter the CustomerID to Search :\t");
        String customerID = scanner.next();
        System.out.println();
        c1.searchUser(customerID);
    }

    void updateCustomer(Scanner scanner) {
        Customer c1 = new Customer();
        displayCustomersData(false);
        System.out.print("Enter the CustomerID to Update its Data :\t");
        String customerID = scanner.next();
        if (customersCollection.size() > 0) {
            c1.editUserInfo(customerID);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
        }
    }

    void deleteCustomer(Scanner scanner) {
        Customer c1 = new Customer();
        displayCustomersData(false);
        System.out.print("Enter the CustomerID to Delete its Data :\t");
        String customerID = scanner.next();
        if (customersCollection.size() > 0) {
            c1.deleteUser(customerID);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", customerID);
        }
    }

    void displayCustomersData(boolean isDefault) {
        Customer c1 = new Customer();
        c1.displayCustomersData(isDefault);
    }

    static void editUserInfo(String customerID) {
        Customer c1 = new Customer();
        c1.editUserInfo(customerID);
    }

    static void deleteUserAccount(Scanner scanner, String customerID, String userName) {
        Customer c1 = new Customer();
        System.out.print(
                "Are you sure to delete your account...It's an irreversible action...Enter Y/y to confirm...");
        char confirmationChar = scanner.next().charAt(0);
        if (confirmationChar == 'Y' || confirmationChar == 'y') {
            c1.deleteUser(customerID);
            System.out.printf("User %s's account deleted Successfully...!!!", userName);
        } else {
            System.out.println("Action has been cancelled...");
        }
    }

    public static List<Customer> getCustomersCollection() {
        return customersCollection;
    }

    void registerPassenger(Scanner scanner) {
        addNewCustomer();
    }
}

class FlightManager {
    Flight f1 = new Flight();
    FlightReservation bookingAndReserving = new FlightReservation();

    void displayFlightSchedule() {
        if (f1.getFlightList().isEmpty()) {
            f1.flightScheduler(); // Create flights only if the list is empty
        }
        f1.displayFlightSchedule(); // Display the flight schedule
    }

    void bookFlight(Scanner scanner, String userID) {
        f1.displayFlightSchedule();
        System.out.print("\nEnter the desired flight number to book :\t ");
        String flightToBeBooked = scanner.next();
        System.out.print("Enter the Number of tickets for " + flightToBeBooked + " flight :   ");
        int numOfTickets = scanner.nextInt();
        while (numOfTickets > 10) {
            System.out.print(
                    "ERROR!! You can't book more than 10 tickets at a time for single flight....Enter number of tickets again : ");
            numOfTickets = scanner.nextInt();
        }
        bookingAndReserving.bookFlight(flightToBeBooked, numOfTickets, userID);
    }

    void cancelFlight(String userID) {
        bookingAndReserving.cancelFlight(userID);
    }

    void displayFlightsRegisteredByOneUser(String userID) {
        bookingAndReserving.displayFlightsRegisteredByOneUser(userID);
    }

    void displayFlightsRegisteredByUser(Scanner scanner) {
        CustomerManager customerManager = new CustomerManager();
        customerManager.displayCustomersData(false);
        System.out.print(
                "\n\nEnter the ID of the user to display all flights registered by that user...");
        String id = scanner.next();
        bookingAndReserving.displayFlightsRegisteredByOneUser(id);
    }

    void displayRegisteredPassengers(Scanner scanner) {
        System.out.print(
                "Do you want to display Passengers of all flights or a specific flight.... 'Y/y' for displaying all flights and 'N/n' to look for a"
                        +
                        " specific flight.... ");
        char choice = scanner.next().charAt(0);
        if ('y' == choice || 'Y' == choice) {
            bookingAndReserving.displayRegisteredUsersForAllFlight();
        } else if ('n' == choice || 'N' == choice) {
            f1.displayFlightSchedule();
            System.out.print(
                    "Enter the Flight Number to display the list of passengers registered in that flight... ");
            String flightNum = scanner.next();
            bookingAndReserving.displayRegisteredUsersForASpecificFlight(flightNum);
        } else {
            System.out.println("Invalid Choice...No Response...!");
        }
    }

    void deleteFlight(Scanner scanner) {
        f1.displayFlightSchedule();
        System.out.print("Enter the Flight Number to delete the flight : ");
        String flightNum = scanner.next();
        f1.deleteFlight(flightNum);
    }
}