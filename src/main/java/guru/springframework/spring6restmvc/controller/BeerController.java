package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.service.BeerService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.UUID;

@Slf4j
@AllArgsConstructor
@RestController
@RequestMapping(value = "/api/v1")
public class BeerController {

    private final BeerService beerService;

    @RequestMapping(value = "/beer", method = RequestMethod.GET)
    public List<Beer> listBeers() {
        return beerService.listBeers();
    }

    public Beer getBeerById(UUID id) {
        log.debug("Get Beer by Id - in controller");
        return beerService.getBeerById(id);
    }
}
