package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.time.LocalDate;
import java.util.*;

@Service
public class CustomerServiceImpl implements CustomerService {

    private Map<UUID, CustomerDTO> customerMap;

    public CustomerServiceImpl() {
        customerMap = new HashMap<>();
        CustomerDTO customer1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Jhon")
                .version(1)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        CustomerDTO customer2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Sarah")
                .version(1)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        CustomerDTO customer3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Richard")
                .version(1)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();


        customerMap.put(customer1.getId(), customer1);
        customerMap.put(customer2.getId(), customer2);
        customerMap.put(customer3.getId(), customer3);
    }

    @Override
    public List<CustomerDTO> listCustomers() {
        return new ArrayList<>(customerMap.values());
    }

    @Override
    public Optional<CustomerDTO> getCustomerById(UUID id) {
        return Optional.of(customerMap.get(id));
    }

    @Override
    public CustomerDTO saveCustomer(CustomerDTO customer) {
        CustomerDTO savedCustomer = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customer.getCustomerName())
                .version(customer.getVersion())
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();
        customerMap.put(savedCustomer.getId(), savedCustomer);
        return savedCustomer;
    }

    @Override
    public CustomerDTO updateCustomerById(UUID id, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(id);
        existingCustomer.setCustomerName(customer.getCustomerName());
        existingCustomer.setVersion(customer.getVersion());
        existingCustomer.setLastModifiedDate(LocalDate.now());
        return existingCustomer;
    }

    @Override
    public void deleteById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public CustomerDTO updatePatchCustomerById(UUID customerId, CustomerDTO customer) {
        CustomerDTO existingCustomer = customerMap.get(customerId);
        if(StringUtils.hasText(customer.getCustomerName())) {
            existingCustomer.setCustomerName(customer.getCustomerName());
        }
        if(customer.getVersion() != null){
            existingCustomer.setVersion(customer.getVersion());
        }
        existingCustomer.setLastModifiedDate(LocalDate.now());
        return existingCustomer;
    }
}
