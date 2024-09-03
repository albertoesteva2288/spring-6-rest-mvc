package guru.springframework.spring6restmvc.mapper;

import guru.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.Mapper;

@Mapper
public interface BeerMapper {

    guru.springframework.spring6restmvc.entity.Beer beerDTOToBeer(BeerDTO beer);
    BeerDTO beerToBeerDTO(guru.springframework.spring6restmvc.entity.Beer beer);
}
