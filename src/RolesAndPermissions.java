public class RolesAndPermissions extends User {
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
        for (Customer customer : Customer.customerCollection) {
            if (email.equals(customer.getEmail()) && password.equals(customer.getPassword())) {
                return "1-" + customer.getUserID();
            }
        }
        return "0";
    }
}