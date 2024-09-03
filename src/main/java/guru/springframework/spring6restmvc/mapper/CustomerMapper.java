package guru.springframework.spring6restmvc.mapper;

import guru.springframework.spring6restmvc.model.CustomerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface CustomerMapper {

    guru.springframework.spring6restmvc.entity.Customer customerDTOToCustomer(CustomerDTO customer);
    CustomerDTO customerToCustomerDTO(guru.springframework.spring6restmvc.entity.Customer customer);
}
