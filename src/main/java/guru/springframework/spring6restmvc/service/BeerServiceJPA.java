package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.mapper.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.NoSuchElementException;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Service
@Primary
@RequiredArgsConstructor
public class BeerServiceJPA implements BeerService {

    private final BeerRepository beerRepository;
    private final BeerMapper beerMapper;

    @Override
    public List<BeerDTO> listBeers() {
        return beerRepository.findAll().stream().map(beerMapper::beerToBeerDTO).collect(Collectors.toList());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        //return Optional.ofNullable(beerMapper.beerToBeerDTO(beerRepository.findById(id).orElse(null)));
        return beerRepository.findById(id).map(beerMapper::beerToBeerDTO);
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.findById(beerId)
                .map(existingBeer -> {
                    beerMapper.updateBeerFromBeerDTO(beerDTO, existingBeer);
                    return beerRepository.save(existingBeer);
                })
                .orElseThrow(() -> new NoSuchElementException("Beer not found with ID: " + beerId)));
    }

    @Override
    public void deleteById(UUID beerId) {

    }

    @Override
    public BeerDTO updatePatchBeerById(UUID beerId, BeerDTO beer) {
        return null;
    }
}
