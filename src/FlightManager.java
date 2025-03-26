import java.util.Scanner;

public class FlightManager {
    private Flight flight = new Flight();
    private FlightReservation reservation = new FlightReservation();

    public void handleAdminActions(Scanner scanner, CustomerManager customerManager) {
        int choice;
        do {
            displayAdminMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    customerManager.registerPassenger(scanner);
                    break;
                case 2:
                    handleSearchPassenger(scanner, customerManager);
                    break;
                case 3:
                    handleUpdatePassenger(scanner, customerManager);
                    break;
                case 4:
                    handleDeletePassenger(scanner, customerManager);
                    break;
                case 5:
                    customerManager.displayCustomers(false);
                    break;
                case 6:
                    handleDisplayFlightsByPassenger(scanner);
                    break;
                case 7:
                    flight.displayFlightSchedule();
                    break;
                case 8:
                    handleDeleteFlight(scanner);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    public void handlePassengerActions(Scanner scanner, String passengerId) {
        int choice;
        do {
            displayPassengerMenu();
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    handleBookFlight(scanner, passengerId);
                    break;
                case 2:
                    handleUpdateProfile(passengerId);
                    break;
                case 3:
                    handleDeleteAccount(scanner, passengerId);
                    break;
                case 4:
                    flight.displayFlightSchedule();
                    break;
                case 5:
                    reservation.cancelFlight(passengerId);
                    break;
                case 6:
                    reservation.displayFlightsRegisteredByOneUser(passengerId);
                    break;
                case 0:
                    System.out.println("Logging out...");
                    break;
                default:
                    System.out.println("Invalid choice.");
            }
        } while (choice != 0);
    }

    private void displayAdminMenu() {
        System.out.println("\nAdmin Menu:");
        System.out.println("1. Add Passenger");
        System.out.println("2. Search Passenger");
        System.out.println("3. Update Passenger");
        System.out.println("4. Delete Passenger");
        System.out.println("5. Display Passengers");
        System.out.println("6. Display Flights by Passenger");
        System.out.println("7. Display Passengers by Flight");
        System.out.println("8. Delete Flight");
        System.out.println("0. Logout");
        System.out.print("Enter your choice: ");
    }

    private void displayPassengerMenu() {
        System.out.println("\nPassenger Menu:");
        System.out.println("1. Book Flight");
        System.out.println("2. Update Profile");
        System.out.println("3. Delete Account");
        System.out.println("4. Display Flights");
        System.out.println("5. Cancel Flight");
        System.out.println("6. Display My Flights");
        System.out.println("0. Logout");
        System.out.print("Enter your choice: ");
    }

    private void handleSearchPassenger(Scanner scanner, CustomerManager customerManager) {
        System.out.print("Enter Customer ID to search: ");
        String searchId = scanner.next();
        customerManager.searchCustomerById(searchId);
    }

    private void handleUpdatePassenger(Scanner scanner, CustomerManager customerManager) {
        System.out.print("Enter Customer ID to update: ");
        String updateId = scanner.next();
        customerManager.editCustomer(updateId);
    }

    private void handleDeletePassenger(Scanner scanner, CustomerManager customerManager) {
        System.out.print("Enter Customer ID to delete: ");
        String deleteId = scanner.next();
        customerManager.deleteCustomer(deleteId);
    }

    private void handleDisplayFlightsByPassenger(Scanner scanner) {
        System.out.print("Enter Customer ID to display flights: ");
        String customerId = scanner.next();
        reservation.displayFlightsRegisteredByOneUser(customerId);
    }

    private void handleDeleteFlight(Scanner scanner) {
        System.out.print("Enter Flight Number to delete: ");
        String flightNum = scanner.next();
        flight.deleteFlight(flightNum);
    }

    private void handleBookFlight(Scanner scanner, String passengerId) {
        flight.displayFlightSchedule();
        System.out.print("Enter Flight Number to book: ");
        String flightNum = scanner.next();
        System.out.print("Enter number of tickets: ");
        int tickets = scanner.nextInt();
        reservation.bookFlight(flightNum, tickets, passengerId);
    }

    private void handleUpdateProfile(String passengerId) {
        Customer customer = new Customer();
        customer.editUserInfo(passengerId);
    }

    private void handleDeleteAccount(Scanner scanner, String passengerId) {
        System.out.print("Are you sure to delete your account? (Y/N): ");
        char confirm = scanner.next().charAt(0);
        if (confirm == 'Y' || confirm == 'y') {
            CustomerManager customerManager = new CustomerManager();
            customerManager.deleteCustomer(passengerId);
            System.out.println("Account deleted successfully.");
        }
    }
}
