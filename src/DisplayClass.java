import java.util.List;

public interface DisplayClass {

        // Display all registered users for all flights
        public void displayRegisteredUsersForAllFlight();

        // Display registered users for a specific flight
        public void displayRegisteredUsersForASpecificFlight(String flightNum);

        // Display header information for users of a specific flight
        public void displayHeaderForUsers(Flight flight, List<Customer> customers);

        // Display all flights registered by a specific user
        public void displayFlightsRegisteredByOneUser(String userID);
}
