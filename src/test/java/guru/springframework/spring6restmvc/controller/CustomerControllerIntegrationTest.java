package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.controller.error.NotFoundException;
import guru.springframework.spring6restmvc.entity.Customer;
import guru.springframework.spring6restmvc.mapper.CustomerMapper;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.hamcrest.core.Is.is;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class CustomerControllerIntegrationTest {
    @Autowired
    CustomerController customerController;

    @Autowired
    CustomerRepository customerRepository;

    @Autowired
    CustomerMapper customerMapper;

    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
        mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

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
    void testListCustomers() {
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
    void testPatchCustomer()throws Exception {
        Customer customer = customerRepository.findAll().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name");


        mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPatchCustomerBadName()throws Exception {
        Customer customer = customerRepository.findAll().get(0);

        Map<String, Object> customerMap = new HashMap<>();
        customerMap.put("customerName", "New Name01234567890123456789012345678901234567890123456789");


        MvcResult result = mockMvc.perform(patch(CustomerController.CUSTOMER_PATH_ID, customer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(customerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1))).andReturn();

        System.out.println(result.getResponse().getContentAsString());
    }

}