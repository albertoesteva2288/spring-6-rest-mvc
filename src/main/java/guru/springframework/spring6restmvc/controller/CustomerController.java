package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import guru.springframework.spring6restmvc.service.CustomerService;
import lombok.AllArgsConstructor;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@AllArgsConstructor
@RestController
public class CustomerController {

    public static final String CUSTOMER_PATH = "/api/v1/customer";
    public static final String CUSTOMER_PATH_ID = CUSTOMER_PATH + "/{customerId}";

    private final CustomerService customerService;

    @GetMapping(value = CUSTOMER_PATH)
    public List<CustomerDTO> listCustomers() {
        return customerService.listCustomers();
    }

   @GetMapping(value = CUSTOMER_PATH_ID)
    public CustomerDTO getCustomerById(@PathVariable(value = "customerId") UUID customerId) {
        return customerService.getCustomerById(customerId).orElseThrow(NotFoundException::new);
    }

    @PostMapping(value= CUSTOMER_PATH)
    public ResponseEntity createNewCustomer(@RequestBody CustomerDTO customerDTO) {
       CustomerDTO customerDTOSaved = customerService.saveCustomer(customerDTO);
        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.LOCATION, CUSTOMER_PATH + "/" + customerDTOSaved.getId());
        return new ResponseEntity(headers, HttpStatus.CREATED);
    }

    @PutMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity updateCustomerById(@PathVariable(value = "customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {
        customerService.updateCustomerById(customerId, customerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = CUSTOMER_PATH_ID )
    public ResponseEntity deleteCustomerById(@PathVariable(value = "customerId") UUID customerId) {
        customerService.deleteById(customerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = CUSTOMER_PATH_ID)
    public ResponseEntity updatePatchCustomerById(@PathVariable(value = "customerId") UUID customerId, @RequestBody CustomerDTO customerDTO) {
        customerService.updatePatchCustomerById(customerId, customerDTO);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
