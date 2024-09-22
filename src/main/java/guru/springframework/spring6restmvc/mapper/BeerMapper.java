package guru.springframework.spring6restmvc.mapper;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.mapstruct.*;

@Mapper
public interface BeerMapper {

    Beer beerDTOToBeer(BeerDTO beer);
    BeerDTO beerToBeerDTO(Beer beer);
    void updateBeerFromBeerDTO(BeerDTO beerDTO, @MappingTarget Beer beer);
    void updateBeerDTOFromBeer(Beer beer, @MappingTarget BeerDTO beerDTO);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBeerFromBeerDTOIgnoringNulls(BeerDTO beerDTO, @MappingTarget Beer beer);
    @BeanMapping(nullValuePropertyMappingStrategy = NullValuePropertyMappingStrategy.IGNORE)
    void updateBeerDTOFromBeerIgnoringNulls(Beer beer, @MappingTarget BeerDTO beerDTO);
}
