public abstract class FlightDistance {
    @Override
    public abstract String toString(); // Fixed method signature to override Object's toString

    public abstract String[] calculateDistance(double lat1, double lon1, double lat2, double lon2);

    private static final String SYMBOLS = "+---------------------------+";
    private static final String HEADER = "| SOME IMPORTANT GUIDELINES |";
    private static final String[] INSTRUCTIONS = {
        "1. Distance between the destinations are based upon the Airports Coordinates (Latitudes && Longitudes) based in those cities.",
        "2. Actual Distance of the flight may vary from this approximation as Airlines may define their own Travel Policy that may restrict the planes to fly through specific regions.",
        "3. Flight Time depends upon several factors such as Ground Speed (GS), AirCraft Design, Flight Altitude and Weather. Ground Speed for these calculations is 450 Knots.",
        "4. Expect reaching your destination early or late from the Arrival Time. So, please keep a margin of ±1 hour.",
        "5. The departure time is the moment that your plane pushes back from the gate, not the time it takes off. The arrival time is the moment that your plane pulls into the gate, not the time it touches down on the runway."
    };

    public void displayMeasurementInstructions() {
        System.out.println("\n" + SYMBOLS);
        System.out.println(HEADER);
        System.out.println(SYMBOLS);
        for (String instruction : INSTRUCTIONS) {
            System.out.println("\n\t" + instruction);
        }
    }
}
