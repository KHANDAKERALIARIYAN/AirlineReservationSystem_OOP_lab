import java.util.Scanner;

public class Authentication {
    private static final int MAX_ADMINS = 10;
    private static final String INVALID_CREDENTIALS_ERROR = "ERROR: Invalid credentials.";
    private static final String UNEXPECTED_PRIVILEGE_ERROR = "ERROR: Unexpected privilege level.";
    private static final String UNEXPECTED_RESPONSE_ERROR = "ERROR: Unexpected response from RolesAndPermissions.";

    private static String[][] adminCredentials = new String[MAX_ADMINS][2];
    private int adminCount = 1;

    static {
        adminCredentials[0][0] = "root";
        adminCredentials[0][1] = "root";
    }

    public void handleAdminLogin(Scanner scanner, CustomerManager customerManager, FlightManager flightManager) {
        System.out.print("\nEnter the UserName to login to the Management System: ");
        String username = scanner.next();
        System.out.print("Enter the Password to login to the Management System: ");
        String password = scanner.next();

        RolesAndPermissions roles = new RolesAndPermissions();
        int privilege = roles.isPrivilegedUserOrNot(username, password);

        switch (privilege) {
            case -1:
                System.out.println(INVALID_CREDENTIALS_ERROR);
                break;
            case 0:
                System.out.println("Logged in with limited privileges.");
                customerManager.displayCustomers(true);
                break;
            case 1:
                System.out.println("Logged in successfully as " + username);
                flightManager.handleAdminActions(scanner, customerManager);
                break;
            default:
                System.out.println(UNEXPECTED_PRIVILEGE_ERROR);
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

        if (adminCount < MAX_ADMINS) {
            adminCredentials[adminCount][0] = username;
            adminCredentials[adminCount][1] = password;
            adminCount++;
            System.out.println("Admin registered successfully.");
        } else {
            System.out.println("ERROR: Maximum number of admins reached.");
        }
    }

    public void handlePassengerLogin(Scanner scanner, FlightManager flightManager) {
        System.out.print("\nEnter the Email to Login: ");
        String email = scanner.next();
        System.out.print("Enter the Password: ");
        String password = scanner.next();

        RolesAndPermissions roles = new RolesAndPermissions();
        String[] result = roles.isPassengerRegistered(email, password).split("-");

        try {
            if (result.length > 1 && Integer.parseInt(result[0]) == 1) {
                flightManager.handlePassengerActions(scanner, result[1]);
            } else {
                System.out.println(INVALID_CREDENTIALS_ERROR);
            }
        } catch (NumberFormatException e) {
            System.out.println(UNEXPECTED_RESPONSE_ERROR);
        }
    }

    private boolean isAdminExists(String username) {
        if (username == null) return false;
        for (int i = 0; i < adminCount; i++) {
            if (username.equals(adminCredentials[i][0])) {
                return true;
            }
        }
        return false;
    }
}
