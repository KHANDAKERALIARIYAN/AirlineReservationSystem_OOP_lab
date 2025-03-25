import java.util.Scanner;

public class FlightManager {
    private Flight flight = new Flight();
    private FlightReservation reservation = new FlightReservation();

    public void handleAdminActions(Scanner scanner, CustomerManager customerManager) {
        int choice;
        do {
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
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    customerManager.registerPassenger(scanner);
                    break;
                case 2:
                    System.out.print("Enter Customer ID to search: ");
                    String searchId = scanner.next();
                    customerManager.searchCustomerById(searchId); // Updated to use searchId
                    break;
                case 3:
                    System.out.print("Enter Customer ID to update: ");
                    String updateId = scanner.next();
                    customerManager.editCustomer(updateId);
                    break;
                case 4:
                    System.out.print("Enter Customer ID to delete: ");
                    String deleteId = scanner.next();
                    customerManager.deleteCustomer(deleteId);
                    break;
                case 5:
                    customerManager.displayCustomers(false);
                    break;
                case 6:
                    System.out.print("Enter Customer ID to display flights: ");
                    String customerId = scanner.next();
                    reservation.displayFlightsRegisteredByOneUser(customerId);
                    break;
                case 7:
                    flight.displayFlightSchedule();
                    break;
                case 8:
                    System.out.print("Enter Flight Number to delete: ");
                    String flightNum = scanner.next();
                    flight.deleteFlight(flightNum);
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
            System.out.println("\nPassenger Menu:");
            System.out.println("1. Book Flight");
            System.out.println("2. Update Profile");
            System.out.println("3. Delete Account");
            System.out.println("4. Display Flights");
            System.out.println("5. Cancel Flight");
            System.out.println("6. Display My Flights");
            System.out.println("0. Logout");
            System.out.print("Enter your choice: ");
            choice = scanner.nextInt();

            switch (choice) {
                case 1:
                    flight.displayFlightSchedule();
                    System.out.print("Enter Flight Number to book: ");
                    String flightNum = scanner.next();
                    System.out.print("Enter number of tickets: ");
                    int tickets = scanner.nextInt();
                    reservation.bookFlight(flightNum, tickets, passengerId);
                    break;
                case 2:
                    Customer customer = new Customer();
                    customer.editUserInfo(passengerId);
                    break;
                case 3:
                    System.out.print("Are you sure to delete your account? (Y/N): ");
                    char confirm = scanner.next().charAt(0);
                    if (confirm == 'Y' || confirm == 'y') {
                        CustomerManager customerManager = new CustomerManager();
                        customerManager.deleteCustomer(passengerId);
                        System.out.println("Account deleted successfully.");
                        choice = 0;
                    }
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
}
