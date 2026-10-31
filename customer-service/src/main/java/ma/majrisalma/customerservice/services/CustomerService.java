package ma.majrisalma.customerservice.services;

import ma.majrisalma.customerservice.entities.Customer;
import ma.majrisalma.customerservice.repository.CustomerRepository;
import org.springframework.ai.mcp.annotation.McpTool;
import org.springframework.ai.mcp.annotation.McpToolParam;
import org.springframework.stereotype.Service;

import java.util.List;
@Service
public class CustomerService {
    private final CustomerRepository customerRepository;
    public CustomerService(CustomerRepository customerRepository) {
        this.customerRepository = customerRepository;
    }


    @McpTool(description = "Get all customers")
    public List<Customer> getAllCustomers(){

        return customerRepository.findAll();
    }
    @McpTool(description = "Find a customer by Id")
    public Customer findCustomerById(@McpToolParam(description = "The customer id ") Long id){
        return customerRepository.findById(id)
                .orElseThrow(()->new RuntimeException("Customer Not found"));
    }

    @McpTool(description = "save a new customer")
    public Customer saveCustomer(@McpToolParam(description = "The customer to save(name,email) ")Customer customer) {
        return customerRepository.save(customer);
    }
}