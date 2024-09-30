package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.controller.error.NotFoundException;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.http.*;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = BEER_PATH + "/{beerId}";

    private final BeerService beerService;

    @PostMapping(value = BEER_PATH)
    public ResponseEntity<?> createNewBeer(@Validated @RequestBody BeerDTO beer) {
        BeerDTO savedBeer = beerService.saveBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + "/" + savedBeer.getId());
        return new ResponseEntity<>(headers,HttpStatus.CREATED);
    }

   @GetMapping(value = BEER_PATH)
    public Page<BeerDTO> listBeers(@RequestParam(required = false) String beerName,
                                   @RequestParam(required = false) BeerStyle beerStyle,
                                   @RequestParam(required = false) Boolean showInventory,
                                   @RequestParam(required = false) Integer pageNumber,
                                   @RequestParam(required = false) Integer pageSize) {

       return beerService.listBeers(beerName, beerStyle, showInventory, pageNumber, pageSize);
    }

    @GetMapping(value = BEER_PATH_ID)
    public BeerDTO getBeerById(@PathVariable(value = "beerId") UUID beerId) {
        log.debug("Get Beer by Id - in controller - 123456");
        return beerService.getBeerById(beerId).orElseThrow(NotFoundException::new);
    }

    @PutMapping(value = BEER_PATH_ID)
    public ResponseEntity<?> updateBeerById(@PathVariable(value = "beerId") UUID beerId,@Validated @RequestBody BeerDTO beer) {
        beerService.updateBeerById(beerId, beer).orElseThrow(NotFoundException::new);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = BEER_PATH_ID)
    public ResponseEntity<?> deleteBeerById(@PathVariable(value = "beerId") UUID beerId) {
        if(!beerService.deleteById(beerId)){
            throw new NotFoundException();
        }
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = BEER_PATH_ID)
    public ResponseEntity<?> updatePatchBeerById(@PathVariable(value = "beerId") UUID beerId, @Validated @RequestBody BeerDTO beer) {
        beerService.updatePatchBeerById(beerId, beer);
        return new ResponseEntity<>(HttpStatus.NO_CONTENT);
    }


}
