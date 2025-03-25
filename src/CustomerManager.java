import java.util.ArrayList;
import java.util.List;
import java.util.Scanner;

public class CustomerManager {
    private static List<Customer> customers = new ArrayList<>();

    public void registerPassenger(Scanner scanner) {
        Customer customer = new Customer();
        customer.addNewCustomer();
        customers.add(customer);
    }

    public void displayCustomers(boolean isLimited) {
        Customer customer = new Customer();
        customer.displayCustomersData(isLimited);
    }

    public void editCustomer(String customerId) {
        Customer customer = new Customer();
        customer.editUserInfo(customerId);
    }

    public void deleteCustomer(String customerId) {
        Customer customer = new Customer();
        customer.deleteUser(customerId);
    }
}
