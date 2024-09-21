package guru.springframework.spring6restmvc.mapper;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beer);
    BeerDTO beerToBeerDTO(Beer beer);
}
