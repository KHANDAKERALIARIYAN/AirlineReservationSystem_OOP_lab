import java.util.InputMismatchException;
import java.util.Scanner;

public class MenuHandler {
    private final Menu menu = new Menu();
    private final Authentication auth = new Authentication();
    private final CustomerManager customerManager = new CustomerManager();
    private final FlightManager flightManager = new FlightManager();

    public void start() {
        menu.displayWelcomeMessage();
        try (Scanner scanner = new Scanner(System.in)) {
            int desiredOption;
            do {
                menu.displayMainMenu();
                desiredOption = getUserOption(scanner);
                handleMenuOption(desiredOption, scanner);
            } while (desiredOption != 0);
        }
    }

    private int getUserOption(Scanner scanner) {
        try {
            return scanner.nextInt();
        } catch (InputMismatchException e) {
            scanner.nextLine(); // Clear invalid input
            System.out.println("Invalid input. Please enter a valid number.");
            return -1; // Return an invalid option to prompt retry
        }
    }

    private void handleMenuOption(int option, Scanner scanner) {
        switch (option) {
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
    }
}
