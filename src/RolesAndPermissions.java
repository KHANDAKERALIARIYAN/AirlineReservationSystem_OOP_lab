public class RolesAndPermissions extends User {
    // Assuming adminUserNameAndPassword is a 2D array of Strings
    private String[][] adminUserNameAndPassword = {
        {"admin1", "password1"},
        {"admin2", "password2"}
        // Add more admin credentials as needed
    };

    public int isPrivilegedUserOrNot(String username, String password) {
        for (int i = 0; i < adminUserNameAndPassword.length; i++) {
            String[] credentials = adminUserNameAndPassword[i];
            if (username.equals(credentials[0]) && password.equals(credentials[1])) {
                return i;
            }
        }
        return -1;
    }

    public String isPassengerRegistered(String email, String password) {
        // Ensure Customer.customerCollection is properly initialized
        if (Customer.customerCollection == null) {
            return "0";
        }
        for (Customer customer : Customer.customerCollection) {
            if (email.equals(customer.getEmail()) && password.equals(customer.getPassword())) {
                return "1-" + customer.getUserID();
            }
        }
        return "0";
    }
}