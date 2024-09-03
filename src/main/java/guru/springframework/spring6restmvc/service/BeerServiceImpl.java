package guru.springframework.spring6restmvc.service;


import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;

@Slf4j
@Service
public class BeerServiceImpl implements BeerService {

    private Map<UUID, BeerDTO> beerMap;

    public BeerServiceImpl() {
        beerMap = new HashMap<>();

        BeerDTO beerDTO1 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Galaxy Cat")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356")
                .price(new BigDecimal("12.99"))
                .quantityOnHand(122)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        BeerDTO beerDTO2 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Crank")
                .beerStyle(BeerStyle.PALE_ALE)
                .upc("12356222")
                .price(new BigDecimal("11.99"))
                .quantityOnHand(392)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        BeerDTO beerDTO3 = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(1)
                .beerName("Sunshine City")
                .beerStyle(BeerStyle.IPA)
                .upc("12356")
                .price(new BigDecimal("13.99"))
                .quantityOnHand(144)
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();

        beerMap.put(beerDTO1.getId(), beerDTO1);
        beerMap.put(beerDTO2.getId(), beerDTO2);
        beerMap.put(beerDTO3.getId(), beerDTO3);
    }

    @Override
    public List<BeerDTO> listBeers() {
        return new ArrayList<>(beerMap.values());
    }

    @Override
    public Optional<BeerDTO> getBeerById(UUID id) {
        log.debug("Get Beer by Id was called");
        return Optional.of(beerMap.get(id));
    }

    @Override
    public BeerDTO saveBeer(BeerDTO beerDTO) {
        BeerDTO mockBeerDTO = BeerDTO.builder()
                .id(UUID.randomUUID())
                .version(beerDTO.getVersion())
                .beerName(beerDTO.getBeerName())
                .beerStyle(beerDTO.getBeerStyle())
                .upc(beerDTO.getUpc())
                .price(beerDTO.getPrice())
                .quantityOnHand(beerDTO.getQuantityOnHand())
                .createdDate(LocalDate.now())
                .updatedDate(LocalDate.now())
                .build();
        beerMap.put(mockBeerDTO.getId(), mockBeerDTO);
        return mockBeerDTO;
    }

    @Override
    public BeerDTO updateBeerById(UUID beerId, BeerDTO beerDTO) {
    BeerDTO savedBeerDTO = beerMap.get(beerId);
    savedBeerDTO.setVersion(beerDTO.getVersion());
    savedBeerDTO.setBeerName(beerDTO.getBeerName());
    savedBeerDTO.setBeerStyle(beerDTO.getBeerStyle());
    savedBeerDTO.setUpc(beerDTO.getUpc());
    savedBeerDTO.setQuantityOnHand(beerDTO.getQuantityOnHand());
    savedBeerDTO.setPrice(beerDTO.getPrice());

    savedBeerDTO.setUpdatedDate(LocalDate.now());

    return savedBeerDTO;

    }

    @Override
    public void deleteById(UUID beerId) {
        beerMap.remove(beerId);
    }

    @Override
    public BeerDTO updatePatchBeerById(UUID beerId, BeerDTO beerDTO) {
        BeerDTO existing = beerMap.get(beerId);

        if(beerDTO.getVersion() != null){
            existing.setVersion(beerDTO.getVersion());
        }

        if(StringUtils.hasText(beerDTO.getBeerName())){
            existing.setBeerName(beerDTO.getBeerName());
        }
        if(beerDTO.getBeerStyle() != null){
            existing.setBeerStyle(beerDTO.getBeerStyle());
        }
        if(StringUtils.hasText(beerDTO.getUpc())){
            existing.setUpc(beerDTO.getUpc());
        }

        if(beerDTO.getQuantityOnHand() != null){
            existing.setQuantityOnHand(beerDTO.getQuantityOnHand());
        }

        if(beerDTO.getPrice() != null){
            existing.setPrice(beerDTO.getPrice());
        }

        existing.setUpdatedDate(LocalDate.now());
        return existing;
    }
}
