package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entity.Customer;
import guru.springframework.spring6restmvc.mapper.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Test
    void deleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> customerController.deleteCustomerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound(){
        Customer customer = customerRepository.findAll().get(0);
        ResponseEntity<?> response = customerController.deleteCustomerById(customer.getId());
        Optional<Customer> existingCustomer = customerRepository.findById(customer.getId());
        assertThat(existingCustomer.isPresent()).isFalse();
        assertThat(response.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingCustomer(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerMapper.customerToCustomerDTO(customer);
        customerDTO.setVersion(2);
        final String customerName = "Customer Updated";
        customerDTO.setCustomerName(customerName);

        ResponseEntity<?> responseEntity = customerController.updateCustomerById(customer.getId(), customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        customerRepository.findById(customer.getId()).ifPresent(
                updatedBeer -> assertThat(updatedBeer.getCustomerName()).isEqualTo(customerName)
        );
    }

    @Test
    void testUpdateNotFound(){
        assertThrows(NotFoundException.class, () -> customerController.updateCustomerById(UUID.randomUUID(), CustomerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void testNewCustomer() {
        CustomerDTO customerDTO = CustomerDTO.builder()
                .customerName("New Beer")
                .build();

        ResponseEntity<?> responseEntity = customerController.createNewCustomer(customerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);// Codigo 201
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID customerId = UUID.fromString(location[location.length-1]);

        customerRepository.findById(customerId).ifPresentOrElse(
                customerUpdated -> assertThat(customerUpdated).isNotNull(),
                () -> fail("Beer not found!")
        );

//        Customer customerUpdated = customerRepository.findById(customerId)
//                .map(customer -> {
//                    assertThat(customer).isNotNull();
//                    return customer;
//                }).orElseThrow(() -> new NoSuchElementException("Customer not found!"));
//

    }

    @Test
    void testGetByIdNotFound(){
        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
        assertEquals("Value Not Found", exception.getMessage());
    }

    @Test
    void testGetById(){
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();

    }

    @Test
    void listCustomers() {
        List<CustomerDTO> customersDTO = customerController.listCustomers();
        assertThat(customersDTO.size()).isEqualTo(3);
    }

    @Rollback
    @Transactional
    @Test
    void testListEmptyCustomers() {
        customerRepository.deleteAll();
        List<CustomerDTO> customersDTO = customerController.listCustomers();
        assertThat(customersDTO.size()).isEqualTo(0);
    }

    @Test
    void getByIdNotFound() {
        NotFoundException exception = assertThrows(NotFoundException.class, () -> customerController.getCustomerById(UUID.randomUUID()));
        assertEquals("Value Not Found", exception.getMessage());

    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = customerController.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }
}