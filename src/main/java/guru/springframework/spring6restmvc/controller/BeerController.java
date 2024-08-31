package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.service.BeerService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.UUID;

@Slf4j
@RequiredArgsConstructor
@RestController
@RequestMapping(value = "/api/v1/beer")
public class BeerController {

    private final BeerService beerService;

    @PostMapping
//    @RequestMapping(method = RequestMethod.POST)
    public ResponseEntity handlePost(@RequestBody Beer beer, HttpServletRequest request) {
        Beer savedBeer = beerService.saveBeer(beer);
        HttpHeaders headers = new HttpHeaders();
        headers.add("Location", request.getRequestURI() + "/" + savedBeer.getId());
        return new ResponseEntity(headers,HttpStatus.CREATED);
    }

    @RequestMapping(method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    @RequestMapping(value = "/{beerId}", method = RequestMethod.GET)
    public Beer getBeerById(@PathVariable(value = "beerId") UUID beerId) {
        log.debug("Get Beer by Id - in controller - 123456");
        return beerService.getBeerById(beerId);
    }

    @PutMapping(value = "/{beerId}")
    public ResponseEntity updateBeerById(@PathVariable(value = "beerId") UUID beerId, @RequestBody Beer beer) {
        Beer updatedBeer = beerService.updateBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @DeleteMapping(value = "/{beerId}")
    public ResponseEntity deleteBeerById(@PathVariable(value = "beerId") UUID beerId) {
        beerService.deleteById(beerId);
        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

    @PatchMapping(value = "/{beerId}")
    public ResponseEntity updaPatcheBeerById(@PathVariable(value = "beerId") UUID beerId, @RequestBody Beer beer) {
        Beer updatedBeer = beerService.updatePatchBeerById(beerId, beer);

        return new ResponseEntity(HttpStatus.NO_CONTENT);
    }

}
