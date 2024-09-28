package guru.springframework.spring6restmvc.service;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.mapper.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import guru.springframework.spring6restmvc.repository.specification.BeerSpecification;
import lombok.RequiredArgsConstructor;
import org.springframework.context.annotation.Primary;
import org.springframework.data.jpa.domain.Specification;
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
    public List<BeerDTO> listBeers(String beerName, BeerStyle beerStyle, Boolean showInventory, Integer pageNumber, Integer pageSize) {
        Specification<Beer> beerSpecification = Specification.where(BeerSpecification.hasBeerNameLike(beerName))
                .and(BeerSpecification.hasBeerStyle(beerStyle));
        return beerRepository.findAll(beerSpecification).stream().map(beer -> {
            BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
                    beerDTO.setQuantityOnHand(
                            Optional.ofNullable(showInventory)
                                    .filter(Boolean::booleanValue)
                                    .map(inventory -> beerDTO.getQuantityOnHand())
                                    .orElse(null)
                    );
            return beerDTO;})
                .collect(Collectors.toList());
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
