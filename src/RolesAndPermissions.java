public class RolesAndPermissions extends User {
    // Assuming adminUserNameAndPassword is a 2D array of Strings
    private String[][] adminUserNameAndPassword = {
        {"admin1", "password1"},
        {"admin2", "password2"}
        // Add more admin credentials as needed
    };

    public int isPrivilegedUserOrNot(String username, String password) {
        if (username == null || password == null) {
            return -1; // Invalid input
        }
        for (int i = 0; i < adminUserNameAndPassword.length; i++) {
            String[] credentials = adminUserNameAndPassword[i];
            if (username.equals(credentials[0]) && password.equals(credentials[1])) {
                return i; // Privileged user found
            }
        }
        return -1; // Not a privileged user
    }

    public String isPassengerRegistered(String email, String password) {
        if (email == null || password == null || Customer.customerCollection == null) {
            return "0"; // Invalid input or uninitialized collection
        }
        for (Customer customer : Customer.customerCollection) {
            if (email.equals(customer.getEmail()) && password.equals(customer.getPassword())) {
                return "1-" + customer.getUserID(); // Registered passenger found
            }
        }
        return "0"; // Passenger not registered
    }
}