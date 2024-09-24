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
        return beerRepository.findById(id).map(beerMapper::beerToBeerDTO);
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        return beerMapper.beerToBeerDTO(beerRepository.save(beerMapper.beerDTOToBeer(beerDTO)));
    }

    @Override
    public Optional<BeerDTO> updateBeerById(UUID beerId,BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
                .map(existingBeer -> {
                    beerMapper.updateBeerFromBeerDTO(beerDTO, existingBeer);
                    return beerMapper.beerToBeerDTO(beerRepository.save(existingBeer));
                });
    }

    @Override
    public Boolean deleteById(UUID beerId) {
        if(beerRepository.existsById(beerId)){
            beerRepository.deleteById(beerId);
            return true;
        }
        return false;
    }

    @Override
    public Optional<BeerDTO> updatePatchBeerById(UUID beerId, BeerDTO beerDTO) {
        return beerRepository.findById(beerId)
            .map(existingBeer -> {
               beerMapper.updateBeerFromBeerDTOIgnoringNulls(beerDTO, existingBeer);
               return beerMapper.beerToBeerDTO(beerRepository.save(existingBeer));
            });
    }
}
