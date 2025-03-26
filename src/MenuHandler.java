import java.util.Scanner;

public class MenuHandler {
    private final Menu menu = new Menu();
    private final Authentication auth = new Authentication();
    private final CustomerManager customerManager = new CustomerManager();
    private final FlightManager flightManager = new FlightManager();

    public void start() {
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
}
