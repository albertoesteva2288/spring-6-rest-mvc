package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface CustomerService {

    List<CustomerDTO> listCustomers();
    Optional<CustomerDTO> getCustomerById(UUID id);
    CustomerDTO saveCustomer(CustomerDTO customer);
    Optional<CustomerDTO> updateCustomerById(UUID id, CustomerDTO customer);
    Boolean deleteById(UUID customerId);
    Optional<CustomerDTO> updatePatchCustomerById(UUID customerId, CustomerDTO customer);
}
