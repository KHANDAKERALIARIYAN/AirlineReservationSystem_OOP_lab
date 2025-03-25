import java.util.*;

public class Customer {
    private final String userID;
    private String email;
    private String name;
    private String phone;
    private final String password;
    private String address;
    private int age;
    private final List<Flight> flightsRegisteredByUser;
    private final List<Integer> numOfTicketsBookedByUser;
    public static final List<Customer> customerCollection = User.getCustomersCollection();

    // Constructor
    public Customer(String name, String email, String password, String phone, String address, int age) {
        this.userID = generateRandomID();
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.flightsRegisteredByUser = new ArrayList<>();
        this.numOfTicketsBookedByUser = new ArrayList<>();
    }

    // Utility method to generate random ID
    private String generateRandomID() {
        Random random = new Random();
        return String.valueOf(random.nextInt(100000)); // Example random ID generation
    }

    // Add a new customer
    public static void addNewCustomer() {
        System.out.println("\n++++++ Welcome to the Customer Registration Portal ++++++");
        try (Scanner scanner = new Scanner(System.in)) {
            System.out.print("Enter your name: ");
            String name = scanner.nextLine();
            System.out.print("Enter your email address: ");
            String email = scanner.nextLine();
            while (isEmailTaken(email)) {
                System.out.println("ERROR: Email already exists. Please use a different email.");
                System.out.print("Enter your email address: ");
                email = scanner.nextLine();
            }
            System.out.print("Enter your password: ");
            String password = scanner.nextLine();
            System.out.print("Enter your phone number: ");
            String phone = scanner.nextLine();
            System.out.print("Enter your address: ");
            String address = scanner.nextLine();
            System.out.print("Enter your age: ");
            int age = scanner.nextInt();

            customerCollection.add(new Customer(name, email, password, phone, address, age));
            System.out.println("Customer registered successfully!");
        } catch (InputMismatchException e) {
            System.out.println("Invalid input. Please try again.");
        }
    }

    // Check if email is already taken
    private static boolean isEmailTaken(String email) {
        return customerCollection.stream().anyMatch(c -> c.getEmail().equals(email));
    }

    // Search for a customer by ID
    public static void searchUser(String userID) {
        Customer customer = customerCollection.stream()
                .filter(c -> c.getUserID().equals(userID))
                .findFirst()
                .orElse(null);

        if (customer != null) {
            System.out.println("Customer Found:");
            displayHeader();
            System.out.println(customer.formatCustomerDetails(1));
        } else {
            System.out.printf("No customer found with ID: %s%n", userID);
        }
    }

    // Format customer details for display
    private String formatCustomerDetails(int serialNum) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |",
                "", serialNum, userID, name, age, email, address, phone);
    }

    // Display header for customer table
    private static void displayHeader() {
        System.out.println("+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+");
        System.out.println("| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID                     | Home Address                      | Phone Number            |");
        System.out.println("+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+");
    }

    // Getters and Setters
    public String getUserID() {
        return userID;
    }

    public String getEmail() {
        return email;
    }

    public String getName() {
        return name;
    }

    public String getPhone() {
        return phone;
    }

    public String getAddress() {
        return address;
    }

    public int getAge() {
        return age;
    }

    public void setName(String name) {
        this.name = name;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public void setPhone(String phone) {
        this.phone = phone;
    }

    public void setAddress(String address) {
        this.address = address;
    }

    public void setAge(int age) {
        this.age = age;
    }
}