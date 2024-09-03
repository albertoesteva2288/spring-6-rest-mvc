package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.mapper.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return List.of();
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        return Optional.empty();
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beer) {
        return null;
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO beer) {
        return null;
    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public BeerDTO updatePatchBeerById(UUID beerId, BeerDTO beer) {
        return null;
    }
}
