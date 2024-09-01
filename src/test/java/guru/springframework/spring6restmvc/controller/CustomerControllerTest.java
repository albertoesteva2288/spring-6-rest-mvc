package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.service.CustomerService;
import guru.springframework.spring6restmvc.service.CustomerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;

import java.time.LocalDate;
import java.util.List;
import java.util.UUID;

import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;
import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(CustomerController.class)
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerService customerService;

    CustomerServiceImpl customerServiceImpl;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void Init(){
        customerServiceImpl = new CustomerServiceImpl();
    }

    @Test
    void getCustomerById() throws Exception {
        Customer customer = customerServiceImpl.listCustomers().get(0);
        given(customerService.getCustomerById(customer.getId())).willReturn(customer);

        mockMvc.perform(get("/api/v1/customer/" + customer.getId())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.customerName", is(customer.getCustomerName())))
                .andExpect(jsonPath("$.version", is(customer.getVersion())))
        ;

    }

    @Test
    void getListCustomers() throws Exception {
        // Preparin the expected response
        given(customerService.listCustomers()).willReturn(customerServiceImpl.listCustomers());
        // simulating the http get request and its reponse
        mockMvc.perform(get("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(customerServiceImpl.listCustomers().size())))
        ;


    }

    @Test
    void createNewCustomer() throws Exception {
        // 1 Getting a Customer just get something to return
        Customer createdCustomer = customerServiceImpl.listCustomers().get(0);
        // Simulation that id is null just for "save"
        Customer newCustomer = Customer.builder()
                .id(null)
                .customerName(createdCustomer.getCustomerName())
                .version(createdCustomer.getVersion())
                .createdDate(createdCustomer.getCreatedDate())
                .lastModifiedDate(createdCustomer.getLastModifiedDate())
                .build();

        // 2 Setting the result we want
        given(customerService.saveCustomer(newCustomer)).willReturn(createdCustomer);

        // 3 Simulating the post request to create the beer and the kind of response
        mockMvc.perform(post("/api/v1/customer")
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(newCustomer)))
                .andExpect(status().isCreated())
                .andExpect(header().exists("Location"))
                .andExpect(header().string("Location", containsString("/api/v1/customer/" + createdCustomer.getId())));

    }

}