package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.mapper.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertThrows;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;


    @Rollback
    @Transactional
    @Test
    void updateExistingBeer(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setVersion(2);
        final String updatedName = "Beer Updated";
        beerDTO.setBeerName(updatedName);
        ResponseEntity responseEntity= beerController.updateBeerById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);//Codigo 204

        Optional<Beer> updatedBeer = beerRepository.findById(beer.getId());
        if(updatedBeer.isPresent()){
            Beer beerUpdated = updatedBeer.get();
            assertEquals(updatedName, beerUpdated.getBeerName());
        }

    }

    @Rollback
    @Transactional
    @Test
    void testNewBeer() {
        BeerDTO newBeer = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity responseEntity = beerController.createNewBeer(newBeer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);// Codigo 201
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID beerId = UUID.fromString(location[location.length-1]);

        Optional<Beer> beer = beerRepository.findById(beerId);
        if(beer.isPresent()){
            Beer beerUpdated = beer.get();
            assertThat(beer).isNotNull();
        }



    }
    @Test
    void testGetByIdNotFound(){
        NotFoundException exception = assertThrows(NotFoundException.class, () -> {
            beerController.getBeerById(UUID.randomUUID());
        });
        assertEquals("Value Not Found", exception.getMessage());
    }

    @Test
    void testGetById(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerController.getBeerById(beer.getId());
        assertThat(beerDTO).isNotNull();

    }

    @Test
    void testListBeers() {
        List<BeerDTO> beerDTOs = beerController.listBeers();
        assertThat(beerDTOs.size()).isEqualTo(3);
        assertThat(beerDTOs.size()).isEqualTo(beerRepository.count());

    }

    @Rollback
    @Transactional
    @Test
    void testEmptyListBeers() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOs = beerController.listBeers();
        assertThat(beerDTOs.size()).isEqualTo(0);
        assertThat(beerDTOs.size()).isEqualTo(beerRepository.count());

    }

}