package guru.springframework.spring6restmvc.mapper;

import guru.springframework.spring6restmvc.entity.Customer;
import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.mapstruct.*;

@Mapper
public interface CustomerMapper {

    Customer customerDTOToCustomer(CustomerDTO customer);
    CustomerDTO customerToCustomerDTO(Customer customer);
    void updateCustomerFromCustomerDTO(CustomerDTO customerDTO, @MappingTarget Customer customer);
    void updateCustomerDTOFromCustomer(Customer customer, @MappingTarget CustomerDTO customerDTO);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerFromCustomerDTOIgnoringNulls(CustomerDTO customerDTO, @MappingTarget Customer customer);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateCustomerDTOFromCustomerIgnoringNulls(Customer customer, @MappingTarget CustomerDTO customerDTO);

}
