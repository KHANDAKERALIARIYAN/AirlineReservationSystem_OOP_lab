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

    // Default constructor
    public Customer() {
        this(null, null, null, null, null, 0);
    }

    // Parameterized constructor
    public Customer(String name, String email, String password, String phone, String address, int age) {
        RandomGenerator random = new RandomGenerator();
        random.randomIDGen();
        this.userID = random.getRandomNumber();
        this.name = name;
        this.email = email;
        this.password = password;
        this.phone = phone;
        this.address = address;
        this.age = age;
        this.flightsRegisteredByUser = new ArrayList<>();
        this.numOfTicketsBookedByUser = new ArrayList<>();
    }

    // Add a new customer
    public void addNewCustomer() {
        System.out.printf("\n\n\n%60s ++++++++++++++ Welcome to the Customer Registration Portal ++++++++++++++", "");
        try (Scanner read = new Scanner(System.in)) {
            String name = promptInput(read, "Enter your name :\t");
            String email = promptUniqueEmail(read);
            String password = promptInput(read, "Enter your Password :\t");
            String phone = promptInput(read, "Enter your Phone number :\t");
            String address = promptInput(read, "Enter your address :\t");
            int age = promptIntInput(read, "Enter your age :\t");
            customerCollection.add(new Customer(name, email, password, phone, address, age));
        }
    }

    // Search for a customer by ID
    public void searchUser(String ID) {
        Customer customer = findCustomerByID(ID);
        if (customer != null) {
            System.out.printf("%-50sCustomer Found...!!!Here is the Full Record...!!!\n\n\n", " ");
            displayHeader();
            System.out.println(customer.toString(1));
            printFooter();
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    // Edit customer information
    public void editUserInfo(String ID) {
        Customer customer = findCustomerByID(ID);
        if (customer != null) {
            try (Scanner read = new Scanner(System.in)) {
                customer.setName(promptInput(read, "Enter the new name of the Passenger:\t"));
                customer.setEmail(promptInput(read, "Enter the new email address:\t"));
                customer.setPhone(promptInput(read, "Enter the new Phone number:\t"));
                customer.setAddress(promptInput(read, "Enter the new address:\t"));
                customer.setAge(promptIntInput(read, "Enter the new age:\t"));
                displayCustomersData(false);
            }
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    // Delete a customer by ID
    public void deleteUser(String ID) {
        Customer customer = findCustomerByID(ID);
        if (customer != null) {
            customerCollection.remove(customer);
            System.out.printf("\n%-50sPrinting all Customer's Data after deleting Customer with the ID %s.....!!!!\n", "", ID);
            displayCustomersData(false);
        } else {
            System.out.printf("%-50sNo Customer with the ID %s Found...!!!\n", " ", ID);
        }
    }

    // Display all customers
    public void displayCustomersData(boolean showHeader) {
        if (showHeader) displayHeader();
        int i = 0;
        for (Customer c : customerCollection) {
            i++;
            System.out.println(c.toString(i));
            printFooter();
        }
    }

    // Helper methods
    private String promptInput(Scanner read, String message) {
        System.out.print(message);
        return read.nextLine();
    }

    private int promptIntInput(Scanner read, String message) {
        System.out.print(message);
        return read.nextInt();
    }

    private String promptUniqueEmail(Scanner read) {
        String email;
        do {
            email = promptInput(read, "Enter your email address :\t");
            if (!isUniqueData(email)) { // Fix: Negate the second call to isUniqueData
                System.out.println("ERROR!!! User with the same email already exists. Use a new email or login.");
            }
        } while (!isUniqueData(email)); // Fix: Negate the condition here
        return email;
    }

    private Customer findCustomerByID(String ID) {
        for (Customer c : customerCollection) {
            if (ID.equals(c.getUserID())) {
                return c;
            }
        }
        return null;
    }

    private boolean isUniqueData(String emailID) {
        return customerCollection.stream().anyMatch(c -> emailID.equals(c.getEmail()));
    }

    private void displayHeader() {
        System.out.println();
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.printf("%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID\t\t       | Home Address\t\t\t     | Phone Number\t       |\n", "");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
        System.out.println();
    }

    private void printFooter() {
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+\n", "");
    }

    @Override
    public String toString(int i) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |", "", i, randomIDDisplay(userID), name, age, email, address, phone);
    }

    private String randomIDDisplay(String randomID) {
        StringBuilder newString = new StringBuilder();
        for (int i = 0; i < randomID.length(); i++) {
            if (i == 3) {
                newString.append(" ").append(randomID.charAt(i));
            } else {
                newString.append(randomID.charAt(i));
            }
        }
        return newString.toString();
    }

    // Getters and setters
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

    public List<Flight> getFlightsRegisteredByUser() {
        return flightsRegisteredByUser;
    }

    public List<Integer> getNumOfTicketsBookedByUser() {
        return numOfTicketsBookedByUser;
    }

    public void addExistingFlightToCustomerList(int customerIndex, int numOfTickets) {
        if (customerIndex < 0 || customerIndex >= customerCollection.size()) {
            System.out.println("Invalid customer index.");
            return;
        }

        Customer customer = customerCollection.get(customerIndex);
        try (Scanner read = new Scanner(System.in)) {
            System.out.print("Enter the Flight ID to register: ");
            String flightID = read.nextLine();

            // Assuming Flight class and a method to find a flight by ID exist
            Flight flight = Flight.findFlightByID(flightID);
            if (flight != null) {
                customer.getFlightsRegisteredByUser().add(flight);
                customer.getNumOfTicketsBookedByUser().add(numOfTickets);
                System.out.printf("Flight with ID %s successfully added for customer %s.\n", flightID, customer.getName());
            } else {
                System.out.printf("No flight found with ID %s.\n", flightID);
            }
        }
    }
}