package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Customer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<Customer> listCustomers();
    Optional<Customer> getCustomerById(UUID id);
    Customer saveCustomer(Customer customer);
    Customer updateCustomerById(UUID id, Customer customer);

    void deleteById(UUID customerId);

    Customer updatePatchCustomerById(UUID customerId, Customer customer);
}
