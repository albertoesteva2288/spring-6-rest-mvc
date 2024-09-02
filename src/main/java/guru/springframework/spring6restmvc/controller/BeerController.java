package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.service.BeerService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
public class BeerController {

    public static final String BEER_PATH = "/api/v1/beer";
    public static final String BEER_PATH_ID = "/{beerId}";

    private final BeerService beerService;

    @PostMapping(value = BEER_PATH)
    public ResponseEntity handlePost(@RequestBody Beer beer) {
        Beer savedBeer = beerService.saveBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", BEER_PATH + savedBeer.getId());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

   @GetMapping(value = BEER_PATH)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @GetMapping(value = BEER_PATH + BEER_PATH_ID)
    public Beer getBeerById(@PathVariable(value = "beerId") UUID beerId) {
        log.debug("Get Beer by Id - in controller - 123456");
        return beerService.getBeerById(beerId);
    }

    @PutMapping(value = BEER_PATH + BEER_PATH_ID)
    public ResponseEntity updateBeerById(@PathVariable(value = "beerId") UUID beerId, @RequestBody Beer beer) {
        Beer updatedBeer = beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = BEER_PATH + BEER_PATH_ID)
    public ResponseEntity deleteBeerById(@PathVariable(value = "beerId") UUID beerId) {
        beerService.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = BEER_PATH + BEER_PATH_ID)
    public ResponseEntity updaPatcheBeerById(@PathVariable(value = "beerId") UUID beerId, @RequestBody Beer beer) {
        Beer updatedBeer = beerService.updatePatchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
