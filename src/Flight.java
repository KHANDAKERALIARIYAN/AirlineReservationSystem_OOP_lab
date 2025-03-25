import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.*;

public class Flight extends FlightDistance {
    private final String flightSchedule;
    private final String flightNumber;
    private final String fromWhichCity;
    private final String gate;
    private final String toWhichCity;
    private final double distanceInMiles;
    private final double distanceInKm;
    private final String flightTime;
    private int numOfSeatsInTheFlight;
    private final List<Customer> listOfRegisteredCustomersInAFlight;
    private int customerIndex;
    private static int nextFlightDay = 0;
    private static final List<Flight> flightList = new ArrayList<>();

    public Flight() {
        this.flightSchedule = null;
        this.flightNumber = null;
        this.numOfSeatsInTheFlight = 0;
        this.toWhichCity = null;
        this.fromWhichCity = null;
        this.gate = null;
        this.distanceInMiles = 0;
        this.distanceInKm = 0;
        this.flightTime = null;
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
    }

    public Flight(String flightSchedule, String flightNumber, int numOfSeatsInTheFlight, String[][] chosenDestinations, String[] distanceBetweenTheCities, String gate) {
        this.flightSchedule = flightSchedule;
        this.flightNumber = flightNumber;
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
        this.fromWhichCity = chosenDestinations[0][0];
        this.toWhichCity = chosenDestinations[1][0];
        this.distanceInMiles = Double.parseDouble(distanceBetweenTheCities[0]);
        this.distanceInKm = Double.parseDouble(distanceBetweenTheCities[1]);
        this.flightTime = calculateFlightTime(this.distanceInMiles);
        this.listOfRegisteredCustomersInAFlight = new ArrayList<>();
        this.gate = gate;
    }

    public void flightScheduler() {
        int numOfFlights = 15; // Number of unique flights to include in the scheduler
        RandomGenerator randomGenerator = new RandomGenerator();
        for (int i = 0; i < numOfFlights; i++) {
            String[][] chosenDestinations = randomGenerator.randomDestinations();
            String[] distanceBetweenTheCities = calculateDistance(
                Double.parseDouble(chosenDestinations[0][1]),
                Double.parseDouble(chosenDestinations[0][2]),
                Double.parseDouble(chosenDestinations[1][1]),
                Double.parseDouble(chosenDestinations[1][2])
            );
            String flightSchedule = createNewFlightsAndTime();
            String flightNumber = randomGenerator.randomFlightNumbGen(2, 1).toUpperCase();
            int numOfSeatsInTheFlight = randomGenerator.randomNumOfSeats();
            String gate = randomGenerator.randomFlightNumbGen(1, 30).toUpperCase();
            flightList.add(new Flight(flightSchedule, flightNumber, numOfSeatsInTheFlight, chosenDestinations, distanceBetweenTheCities, gate));
        }
    }

    public void addNewCustomerToFlight(Customer customer) {
        this.listOfRegisteredCustomersInAFlight.add(customer);
    }

    public void addTicketsToExistingCustomer(Customer customer, int numOfTickets) {
        customer.addExistingFlightToCustomerList(customerIndex, numOfTickets);
    }

    public boolean isCustomerAlreadyAdded(List<Customer> customersList, Customer customer) {
        for (Customer existingCustomer : customersList) {
            if (existingCustomer.getUserID().equals(customer.getUserID())) {
                customerIndex = customersList.indexOf(existingCustomer);
                return true;
            }
        }
        return false;
    }

    public String calculateFlightTime(double distanceBetweenTheCities) {
        double groundSpeed = 450; // Average ground speed in miles per hour
        double time = distanceBetweenTheCities / groundSpeed;
        int hours = (int) time;
        int minutes = (int) ((time - hours) * 60);
        minutes = roundToNearestFive(minutes);
        if (minutes >= 60) {
            minutes -= 60;
            hours++;
        }
        return String.format("%02d:%02d", hours, minutes);
    }

    private int roundToNearestFive(int minutes) {
        int remainder = minutes % 5;
        return remainder < 3 ? minutes - remainder : minutes + (5 - remainder);
    }

    public String fetchArrivalTime() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a ");
        LocalDateTime departureDateTime = LocalDateTime.parse(flightSchedule, formatter);
        String[] flightTimeParts = flightTime.split(":");
        int hours = Integer.parseInt(flightTimeParts[0]);
        int minutes = Integer.parseInt(flightTimeParts[1]);
        LocalDateTime arrivalTime = departureDateTime.plusHours(hours).plusMinutes(minutes);
        return arrivalTime.format(DateTimeFormatter.ofPattern("EE, dd-MM-yyyy HH:mm a"));
    }

    public void deleteFlight(String flightNumber) {
        boolean isFound = flightList.removeIf(flight -> flight.getFlightNumber().equalsIgnoreCase(flightNumber));
        if (!isFound) {
            System.out.println("Flight with given Number not found...");
        }
        displayFlightSchedule();
    }

    @Override
    public String[] calculateDistance(double lat1, double lon1, double lat2, double lon2) {
        double theta = lon1 - lon2;
        double distance = Math.sin(degreeToRadian(lat1)) * Math.sin(degreeToRadian(lat2)) + Math.cos(degreeToRadian(lat1)) * Math.cos(degreeToRadian(lat2)) * Math.cos(degreeToRadian(theta));
        distance = Math.acos(distance);
        distance = radianToDegree(distance);
        distance = distance * 60 * 1.1515;
        return new String[]{
            String.format("%.2f", distance * 0.8684),
            String.format("%.2f", distance * 1.609344),
            String.format("%.2f", distance)
        };
    }

    private double degreeToRadian(double deg) {
        return deg * Math.PI / 180.0;
    }

    private double radianToDegree(double rad) {
        return rad * 180.0 / Math.PI;
    }

    public void displayFlightSchedule() {
        System.out.println();
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        System.out.printf("| Num  | FLIGHT SCHEDULE\t\t\t   | FLIGHT NO | Available Seats  | \tFROM ====>>       | \t====>> TO\t   | \t    ARRIVAL TIME       | FLIGHT TIME |  GATE  |   DISTANCE(MILES/KMS)  |%n");
        System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        int i = 0;
        for (Flight flight : flightList) {
            i++;
            System.out.println(flight.toString(i));
            System.out.print("+------+-------------------------------------------+-----------+------------------+-----------------------+------------------------+---------------------------+-------------+--------+------------------------+\n");
        }
    }

    @Override
    public String toString(int i) {
        return String.format("| %-5d| %-41s | %-9s | \t%-9s | %-21s | %-22s | %-10s  |   %-6sHrs |  %-4s  |  %-8s / %-11s|",
                i, flightSchedule, flightNumber, numOfSeatsInTheFlight, fromWhichCity, toWhichCity, fetchArrivalTime(), flightTime, gate, distanceInMiles, distanceInKm);
    }

    public String createNewFlightsAndTime() {
        Calendar c = Calendar.getInstance();
        nextFlightDay += Math.random() * 7;
        c.add(Calendar.DATE, nextFlightDay);
        c.add(Calendar.HOUR, nextFlightDay);
        c.set(Calendar.MINUTE, ((c.get(Calendar.MINUTE) * 3) - (int) (Math.random() * 45)));
        Date myDateObj = c.getTime();
        LocalDateTime date = Instant.ofEpochMilli(myDateObj.getTime()).atZone(ZoneId.systemDefault()).toLocalDateTime();
        date = getNearestHourQuarter(date);
        return date.format(DateTimeFormatter.ofPattern("EEEE, dd MMMM yyyy, HH:mm a "));
    }

    public LocalDateTime getNearestHourQuarter(LocalDateTime datetime) {
        int minutes = datetime.getMinute();
        int mod = minutes % 15;
        LocalDateTime newDatetime;
        if (mod < 8) {
            newDatetime = datetime.minusMinutes(mod);
        } else {
            newDatetime = datetime.plusMinutes(15 - mod);
        }
        newDatetime = newDatetime.truncatedTo(ChronoUnit.MINUTES);
        return newDatetime;
    }

    public int getNoOfSeats() {
        return numOfSeatsInTheFlight;
    }

    public String getFlightNumber() {
        return flightNumber;
    }

    public void setNoOfSeatsInTheFlight(int numOfSeatsInTheFlight) {
        this.numOfSeatsInTheFlight = numOfSeatsInTheFlight;
    }

    public String getFlightTime() {
        return flightTime;
    }

    public List<Flight> getFlightList() {
        return flightList;
    }

    public List<Customer> getListOfRegisteredCustomersInAFlight() {
        return listOfRegisteredCustomersInAFlight;
    }

    public String getFlightSchedule() {
        return flightSchedule;
    }

    public String getFromWhichCity() {
        return fromWhichCity;
    }

    public String getGate() {
        return gate;
    }

    public String getToWhichCity() {
        return toWhichCity;
    }

}