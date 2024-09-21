package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entity.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController controller;
    @Autowired
    CustomerRepository customerRepository;
    @Autowired
    private CustomerController customerController;

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
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            customerController.getCustomerById(UUID.randomUUID());
        });
        assertEquals("Value Not Found", exception.getMessage());

    }

    @Test
    void getCustomerById() {
        Customer customer = customerRepository.findAll().get(0);
        CustomerDTO customerDTO = controller.getCustomerById(customer.getId());
        assertThat(customerDTO).isNotNull();
    }
}