package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Customer;
import guru.springframework.spring6restmvc.service.CustomerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
@RequestMapping("/api/v1/customer")
public class CustomerController {

    private final CustomerService customerService;

    @RequestMapping(method = RequestMethod.GET)
    public List<Customer> listCustomers() {
        return customerService.listCustomers();
    }

    @RequestMapping(value = "/{customerId}", method = RequestMethod.GET)
    public Customer getCustomerById(@PathVariable(value = "customerId") UUID customerId) {
        return customerService.getCustomerById(customerId);
    }

    @PostMapping
    public ResponseEntity handlePost(@RequestBody Customer customer, HttpServletRequest request) {
       Customer customerSaved = customerService.saveCustomer(customer);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, request.getRequestURI()+ "/" + customerSaved.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }
    @PutMapping(value = "/{customerId}")
    public ResponseEntity updateCustomerById(@PathVariable(value = "customerId") UUID customerId, @RequestBody Customer customer) {
        customerService.updateCustomerById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{customerId}")
    public ResponseEntity deleteCustomerById(@PathVariable(value = "customerId") UUID customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{customerId}")
    public ResponseEntity updatePatchCustomerById(@PathVariable(value = "customerId") UUID customerId, @RequestBody Customer customer) {
        customerService.updatePatchCustomerById(customerId, customer);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }
}
