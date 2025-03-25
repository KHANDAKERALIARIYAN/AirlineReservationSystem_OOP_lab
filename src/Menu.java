import java.util.Scanner;

public class Menu {
    public void displayWelcomeMessage() {
        System.out.println("\n\t\t\t\t\t+++++++++++++ Welcome to BAV AirLines +++++++++++++\n");
        System.out.println("\n***** Default Username && Password is root-root ***** Using Default Credentials will restrict you to just view the list of Passengers....\n");
    }

    public void displayMainMenu() {
        System.out.println("\n\n\t\t(a) Press 0 to Exit.");
        System.out.println("\t\t(b) Press 1 to Login as admin.");
        System.out.println("\t\t(c) Press 2 to Register as admin.");
        System.out.println("\t\t(d) Press 3 to Login as Passenger.");
        System.out.println("\t\t(e) Press 4 to Register as Passenger.");
        System.out.println("\t\t(f) Press 5 to Display the User Manual.");
        System.out.print("\t\tEnter the desired option:    ");
    }

    public void displayManualInstructions(Scanner scanner) {
        System.out.println("\n\n\t\t(a) Press 1 to display Admin Manual.");
        System.out.println("\t\t(b) Press 2 to display User Manual.");
        System.out.print("\nEnter the desired option :    ");
        
        try {
            int choice = scanner.nextInt();

            if (choice == 1) {
                System.out.println("\nAdmin Manual: ...");
            } else if (choice == 2) {
                System.out.println("\nUser Manual: ...");
            } else {
                System.out.println("Invalid choice.");
            }
        } catch (Exception e) {
            System.out.println("Invalid input. Please enter a number.");
            scanner.nextLine(); // Consume the invalid input
        }
    }
}
