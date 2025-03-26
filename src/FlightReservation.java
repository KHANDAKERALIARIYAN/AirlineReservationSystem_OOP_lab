import java.util.Iterator;
import java.util.List;
import java.util.Scanner;

public class FlightReservation implements DisplayClass {
    private Flight flight = new Flight();
    private int flightIndexInFlightList;

    void bookFlight(String flightNo, int numOfTickets, String userID) {
        Flight selectedFlight = findFlightByNumber(flightNo);
        if (selectedFlight == null) {
            System.out.println("Invalid Flight Number...! No flight with the ID \"" + flightNo + "\" was found...");
            return;
        }

        Customer customer = findCustomerByID(userID);
        if (customer == null) {
            System.out.println("Invalid User ID...! No customer with the ID \"" + userID + "\" was found...");
            return;
        }

        selectedFlight.setNoOfSeatsInTheFlight(selectedFlight.getNoOfSeats() - numOfTickets);

        if (!selectedFlight.isCustomerAlreadyAdded(selectedFlight.getListOfRegisteredCustomersInAFlight(), customer)) {
            selectedFlight.addNewCustomerToFlight(customer);
        }

        if (isFlightAlreadyAddedToCustomerList(customer.flightsRegisteredByUser, selectedFlight)) {
            addNumberOfTicketsToAlreadyBookedFlight(customer, numOfTickets);
            int flightIndex = flightIndex(flight.getFlightList(), selectedFlight); // Fixed: Use selectedFlight
            if (flightIndex != -1) {
                customer.addExistingFlightToCustomerList(flightIndex, numOfTickets);
            }
        } else {
            customer.addNewFlightToCustomerList(selectedFlight);
            addNumberOfTicketsForNewFlight(customer, numOfTickets);
        }

        System.out.printf("\n %50s You've booked %d tickets for Flight \"%5s\"...", "", numOfTickets, flightNo.toUpperCase());
    }

    void cancelFlight(String userID) {
        Customer customer = findCustomerByID(userID);
        if (customer == null) {
            System.out.println("Invalid User ID...! No customer with the ID \"" + userID + "\" was found...");
            return;
        }

        if (customer.getFlightsRegisteredByUser().isEmpty()) {
            System.out.println("No flights have been registered by you.");
            return;
        }

        displayFlightsRegisteredByOneUser(userID);

        try (Scanner read = new Scanner(System.in)) {
            System.out.print("Enter the Flight Number of the Flight you want to cancel: ");
            String flightNum = read.nextLine().trim();
            System.out.print("Enter the number of tickets to cancel: ");
            int numOfTickets = read.nextInt();

            Flight flightToCancel = findFlightInCustomerList(customer, flightNum);
            if (flightToCancel == null) {
                System.out.println("ERROR!!! Couldn't find Flight with ID \"" + flightNum.toUpperCase() + "\"...");
                return;
            }

            int index = customer.flightsRegisteredByUser.indexOf(flightToCancel);
            int numOfTicketsForFlight = customer.getNumOfTicketsBookedByUser().get(index);

            if (numOfTickets > numOfTicketsForFlight) {
                System.out.printf("ERROR!!! Number of tickets cannot be greater than %d.%n", numOfTicketsForFlight);
                return;
            }

            if (numOfTicketsForFlight == numOfTickets) {
                flightToCancel.setNoOfSeatsInTheFlight(flightToCancel.getNoOfSeats() + numOfTicketsForFlight);
                customer.numOfTicketsBookedByUser.remove(index);
                customer.flightsRegisteredByUser.remove(index);
            } else {
                flightToCancel.setNoOfSeatsInTheFlight(flightToCancel.getNoOfSeats() + numOfTickets);
                customer.numOfTicketsBookedByUser.set(index, numOfTicketsForFlight - numOfTickets);
            }

            System.out.printf("Successfully canceled %d tickets for Flight \"%s\".%n", numOfTickets, flightNum.toUpperCase());
        } catch (Exception e) {
            System.out.println("ERROR!!! Invalid input. Please try again.");
        }
    }

    private Flight findFlightByNumber(String flightNo) {
        for (Flight f : flight.getFlightList()) {
            if (flightNo.equalsIgnoreCase(f.getFlightNumber())) {
                return f;
            }
        }
        return null;
    }

    private Customer findCustomerByID(String userID) {
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                return customer;
            }
        }
        return null;
    }

    private Flight findFlightInCustomerList(Customer customer, String flightNum) {
        for (Flight flight : customer.getFlightsRegisteredByUser()) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNum)) {
                return flight;
            }
        }
        return null;
    }

    void addNumberOfTicketsToAlreadyBookedFlight(Customer customer, int numOfTickets) {
        int newNumOfTickets = customer.numOfTicketsBookedByUser.get(flightIndexInFlightList) + numOfTickets;
        customer.numOfTicketsBookedByUser.set(flightIndexInFlightList, newNumOfTickets);
    }

    void addNumberOfTicketsForNewFlight(Customer customer, int numOfTickets) {
        customer.numOfTicketsBookedByUser.add(numOfTickets);
    }

    boolean isFlightAlreadyAddedToCustomerList(List<Flight> flightList, Flight flight) {
        for (Flight flight1 : flightList) {
            if (flight1.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) {
                this.flightIndexInFlightList = flightList.indexOf(flight1);
                return true;
            }
        }
        return false;
    }

    String flightStatus(Flight flight) {
        return flight.getFlightList().stream()
                .anyMatch(f -> f.getFlightNumber().equalsIgnoreCase(flight.getFlightNumber())) ? "As Per Schedule" : "Cancelled";
    }

    @Override
    public void displayFlightsRegisteredByOneUser(String userID) {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO |  Booked Tickets  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |  FLIGHT STATUS  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
        for (Customer customer : Customer.customerCollection) {
            if (userID.equals(customer.getUserID())) {
                List<Flight> flights = customer.getFlightsRegisteredByUser();
                for (int i = 0; i < flights.size(); i++) {
                    System.out.println(toString((i + 1), flights.get(i), customer));
                    System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+-----------------+\n");
                }
            }
        }
    }

    @Override
    public void displayRegisteredUsersForAllFlight() {
        System.out.println();
        for (Flight flight : flight.getFlightList()) {
            List<Customer> customers = flight.getListOfRegisteredCustomersInAFlight();
            if (!customers.isEmpty()) {
                displayHeaderForUsers(flight, customers);
            }
        }
    }

    @Override
    public void displayRegisteredUsersForASpecificFlight(String flightNum) {
        System.out.println();
        for (Flight flight : flight.getFlightList()) {
            if (flight.getFlightNumber().equalsIgnoreCase(flightNum)) {
                displayHeaderForUsers(flight, flight.getListOfRegisteredCustomersInAFlight());
            }
        }
    }

    @Override
    public void displayHeaderForUsers(Flight flight, List<Customer> customers) {
        System.out.printf("\n%65s Displaying Registered Customers for Flight No. \"%-6s\" %s \n\n", "+++++++++++++", flight.getFlightNumber(), "+++++++++++++");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        System.out.printf("%10s| SerialNum  |   UserID   | Passenger Names                  | Age     | EmailID\t\t       | Home Address\t\t\t     | Phone Number\t       | Booked Tickets |%n", "");
        System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        for (int i = 0; i < customers.size(); i++) {
            System.out.println(toString(i, customers.get(i), flightIndex(customers.get(i).flightsRegisteredByUser, flight)));
            System.out.printf("%10s+------------+------------+----------------------------------+---------+-----------------------------+-------------------------------------+-------------------------+----------------+\n", "");
        }
    }

    int flightIndex(List<Flight> flightList, Flight flight) {
        return flightList.indexOf(flight);
    }

    public String toString(int serialNum, Flight flights, Customer customer) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9d | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  | %-10s |", serialNum, flights.getFlightSchedule(), flights.getFlightNumber(), customer.numOfTicketsBookedByUser.get(serialNum - 1), flights.getFromWhichCity(), flights.getToWhichCity(), flights.fetchArrivalTime(), flights.getFlightTime(), flights.getGate(), flightStatus(flights));
    }

    public String toString(int serialNum, Customer customer, int index) {
        return String.format("%10s| %-10d | %-10s | %-32s | %-7s | %-27s | %-35s | %-23s |       %-7s  |", "", (serialNum + 1), customer.randomIDDisplay(customer.getUserID()), customer.getName(), customer.getAge(), customer.getEmail(), customer.getAddress(), customer.getPhone(), customer.numOfTicketsBookedByUser.get(index));
    }
}