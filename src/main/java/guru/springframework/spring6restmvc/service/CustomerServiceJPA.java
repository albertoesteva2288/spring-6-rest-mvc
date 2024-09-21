package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.mapper.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class CustomerServiceJPA implements CustomerService {

    private final CustomerRepository customerRepository;
    private final CustomerMapper customerMapper;

    @Override
    public List<CustomerDTO> listCustomers() {
        return customerRepository.findAll().stream().map(customerMapper::customerToCustomerDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        //return Optional.ofNullable(customerMapper.customerToCustomerDTO(customerRepository.findById(id).orElse(null)));
        return customerRepository.findById(id).map(customerMapper::customerToCustomerDTO);
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        return null;
    }

    @Override
    public CustomerDTO updateCustomerById(UUID id, CustomerDTO customer) {
        return null;
    }

    @Override
    public void deleteById(UUID customerId) {

    }

    @Override
    public CustomerDTO updatePatchCustomerById(UUID customerId, CustomerDTO customer) {
        return null;
    }
}
