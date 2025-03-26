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
        for (Customer customer : customers) {
            customer.displayCustomerData(isLimited);
        }
    }

    public void editCustomer(String customerId) {
        Customer customer = findCustomerById(customerId);
        if (customer != null) {
            customer.editUserInfo(customerId);
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }

    public void deleteCustomer(String customerId) {
        Customer customer = findCustomerById(customerId);
        if (customer != null) {
            customers.remove(customer);
            System.out.println("Customer with ID " + customerId + " deleted successfully.");
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }

    public void searchCustomerById(String customerId) {
        Customer customer = findCustomerById(customerId);
        if (customer != null) {
            System.out.println("Customer found: " + customer);
        } else {
            System.out.println("Customer with ID " + customerId + " not found.");
        }
    }

    private Customer findCustomerById(String customerId) {
        for (Customer customer : customers) {
            if (customer.getId().equals(customerId)) {
                return customer;
            }
        }
        return null;
    }
}
