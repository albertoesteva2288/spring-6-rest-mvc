package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerDTO;
import org.springframework.data.jpa.domain.Specification;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<BeerDTO> listBeers(Specification<Beer> beerSpecification, boolean showInventory);
    Optional<BeerDTO> getBeerById(UUID id);
    BeerDTO saveBeer(BeerDTO beer);
    Optional<BeerDTO> updateBeerById(UUID beerId, BeerDTO beer);
    Boolean deleteById(UUID beerId);
    Optional<BeerDTO> updatePatchBeerById(UUID beerId, BeerDTO beer);
}
