import java.util.Scanner;

public class Authentication {
    private static String[][] adminCredentials = new String[10][2];
    private int adminCount = 1;

    public void handleAdminLogin(Scanner scanner, CustomerManager customerManager, FlightManager flightManager) {
        adminCredentials[0][0] = "root";
        adminCredentials[0][1] = "root";

        System.out.print("\nEnter the UserName to login to the Management System: ");
        String username = scanner.next();
        System.out.print("Enter the Password to login to the Management System: ");
        String password = scanner.next();

        RolesAndPermissions roles = new RolesAndPermissions();
        int privilege = roles.isPrivilegedUserOrNot(username, password);

        if (privilege == -1) {
            System.out.println("ERROR: Invalid credentials.");
        } else if (privilege == 0) {
            System.out.println("Logged in with limited privileges.");
            customerManager.displayCustomers(true);
        } else {
            System.out.println("Logged in successfully as " + username);
            flightManager.handleAdminActions(scanner, customerManager);
        }
    }

    public void registerAdmin(Scanner scanner) {
        System.out.print("\nEnter the UserName to Register: ");
        String username = scanner.next();
        System.out.print("Enter the Password to Register: ");
        String password = scanner.next();

        while (isAdminExists(username)) {
            System.out.print("ERROR: Admin with same UserName already exists. Enter new UserName: ");
            username = scanner.next();
        }

        adminCredentials[adminCount][0] = username;
        adminCredentials[adminCount][1] = password;
        adminCount++;
        System.out.println("Admin registered successfully.");
    }

    public void handlePassengerLogin(Scanner scanner, FlightManager flightManager) {
        System.out.print("\nEnter the Email to Login: ");
        String email = scanner.next();
        System.out.print("Enter the Password: ");
        String password = scanner.next();

        RolesAndPermissions roles = new RolesAndPermissions();
        String[] result = roles.isPassengerRegistered(email, password).split("-");

        if (Integer.parseInt(result[0]) == 1) {
            flightManager.handlePassengerActions(scanner, result[1]);
        } else {
            System.out.println("ERROR: Invalid credentials.");
        }
    }

    private boolean isAdminExists(String username) {
        for (int i = 0; i < adminCount; i++) {
            if (adminCredentials[i][0].equals(username)) {
                return true;
            }
        }
        return false;
    }
}
