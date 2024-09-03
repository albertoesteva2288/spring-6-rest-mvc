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
        CustomerDTO customerDTO1 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Jhon")
                .version(1)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        CustomerDTO customerDTO2 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Sarah")
                .version(1)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();

        CustomerDTO customerDTO3 = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName("Richard")
                .version(1)
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();


        customerMap.put(customerDTO1.getId(), customerDTO1);
        customerMap.put(customerDTO2.getId(), customerDTO2);
        customerMap.put(customerDTO3.getId(), customerDTO3);
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
    public CustomerDTO saveCustomer(CustomerDTO customerDTO) {
        CustomerDTO savedCustomerDTO = CustomerDTO.builder()
                .id(UUID.randomUUID())
                .customerName(customerDTO.getCustomerName())
                .version(customerDTO.getVersion())
                .createdDate(LocalDate.now())
                .lastModifiedDate(LocalDate.now())
                .build();
        customerMap.put(savedCustomerDTO.getId(), savedCustomerDTO);
        return savedCustomerDTO;
    }

    @Override
    public CustomerDTO updateCustomerById(UUID id, CustomerDTO customerDTO) {
        CustomerDTO existingCustomerDTO = customerMap.get(id);
        existingCustomerDTO.setCustomerName(customerDTO.getCustomerName());
        existingCustomerDTO.setVersion(customerDTO.getVersion());
        existingCustomerDTO.setLastModifiedDate(LocalDate.now());
        return existingCustomerDTO;
    }

    @Override
    public void deleteById(UUID customerId) {
        customerMap.remove(customerId);
    }

    @Override
    public CustomerDTO updatePatchCustomerById(UUID customerId, CustomerDTO customerDTO) {
        CustomerDTO existingCustomerDTO = customerMap.get(customerId);
        if(StringUtils.hasText(customerDTO.getCustomerName())) {
            existingCustomerDTO.setCustomerName(customerDTO.getCustomerName());
        }
        if(customerDTO.getVersion() != null){
            existingCustomerDTO.setVersion(customerDTO.getVersion());
        }
        existingCustomerDTO.setLastModifiedDate(LocalDate.now());
        return existingCustomerDTO;
    }
}
