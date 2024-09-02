package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();
    Optional<Beer> getBeerById(UUID id);

    Beer saveBeer(Beer beer);

    Beer updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);

    Beer updatePatchBeerById(UUID beerId, Beer beer);
}
