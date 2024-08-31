package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.model.Beer;

import java.util.List;
import java.util.UUID;

public interface BeerService {

    List<Beer> listBeers();
    Beer getBeerById(UUID id);

    Beer saveBeer(Beer beer);

    Beer updateBeerById(UUID beerId, Beer beer);

    void deleteById(UUID beerId);
}
