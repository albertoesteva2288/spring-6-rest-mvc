package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.controller.error.NotFoundException;
import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.mapper.BeerMapper;
import guru.springframework.spring6restmvc.model.BeerDTO;
import guru.springframework.spring6restmvc.model.BeerStyle;
import guru.springframework.spring6restmvc.repository.BeerRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.annotation.Rollback;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.setup.MockMvcBuilders;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.math.BigDecimal;
import java.util.*;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.patch;
import static org.hamcrest.core.Is.is;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
class BeerControllerIntegrationTest {
    @Autowired
    BeerController beerController;

    @Autowired
    BeerRepository beerRepository;

    @Autowired
    BeerMapper beerMapper;

    //WebApplicationContext: It is used to load the entire context of the web application,
    // which includes all the beans in the Spring context (controllers,
    // services, repositories, etc.), plus any web-tier-specific configurations.
    // It is useful for integration testing where you want to test
    // how multiple layers of the application interact.
    @Autowired
    WebApplicationContext wac;

    MockMvc mockMvc;

    @Autowired
    ObjectMapper objectMapper;

    @BeforeEach
    void setUp(){
    mockMvc = MockMvcBuilders.webAppContextSetup(wac).build();
    }

    @Test
    void deleteByIdNotFound(){
        assertThrows(NotFoundException.class, () -> beerController.deleteBeerById(UUID.randomUUID()));
    }

    @Rollback
    @Transactional
    @Test
    void deleteByIdFound(){
        Beer beer = beerRepository.findAll().get(0);
        ResponseEntity<?> responseEntity = beerController.deleteBeerById(beer.getId());
        Optional<Beer> existingBeer = beerRepository.findById(beer.getId());
        assertThat(existingBeer.isPresent()).isFalse();
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);
    }

    @Rollback
    @Transactional
    @Test
    void updateExistingBeer(){
        Beer beer = beerRepository.findAll().get(0);
        BeerDTO beerDTO = beerMapper.beerToBeerDTO(beer);
        beerDTO.setVersion(2);
        final String updatedName = "Beer Updated";
        beerDTO.setBeerName(updatedName);

        ResponseEntity<?> responseEntity= beerController.updateBeerById(beer.getId(), beerDTO);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.NO_CONTENT);

        beerRepository.findById(beer.getId()).ifPresent(
                updatedBeer -> assertThat(updatedBeer.getBeerName()).isEqualTo(updatedName)
        );

    }

    @Test
    void testUpdateNotFound(){
        assertThrows(NotFoundException.class, () -> beerController.updateBeerById(UUID.randomUUID(), BeerDTO.builder().build()));
    }

    @Rollback
    @Transactional
    @Test
    void testNewBeer() {
        BeerDTO newBeer = BeerDTO.builder()
                .beerName("New Beer")
                .build();

        ResponseEntity<?> responseEntity = beerController.createNewBeer(newBeer);
        assertThat(responseEntity.getStatusCode()).isEqualTo(HttpStatus.CREATED);
        assertThat(responseEntity.getHeaders().getLocation()).isNotNull();
        String[] location = responseEntity.getHeaders().getLocation().getPath().split("/");
        UUID beerId = UUID.fromString(location[location.length-1]);

        beerRepository.findById(beerId).ifPresentOrElse(
                beerUpdated -> assertThat(beerUpdated).isNotNull(),
                () -> fail("Beer not found!")
        );
    }
    @Test
    void testGetByIdNotFound(){
        NotFoundException exception = assertThrows(NotFoundException.class, () -> beerController.getBeerById(UUID.randomUUID()));
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
        List<BeerDTO> beerDTOs = beerController.listBeers(null, null, true);
        //assertThat(beerDTOs.size()).isEqualTo(3);
        assertThat(beerDTOs.size()).isEqualTo(beerRepository.count());

    }

    @Rollback
    @Transactional
    @Test
    void testEmptyListBeers() {
        beerRepository.deleteAll();
        List<BeerDTO> beerDTOs = beerController.listBeers(null, null, true);
        assertThat(beerDTOs.size()).isEqualTo(0);
        assertThat(beerDTOs.size()).isEqualTo(beerRepository.count());

    }

    @Test
    void testPatchBeer()throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");
        beerMap.put("beerStyle", BeerStyle.PALE_ALE);
        beerMap.put("price", BigDecimal.valueOf(150));
        beerMap.put("upc",12);

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());
    }

    @Test
    void testPatchBeerBadName()throws Exception {
        Beer beer = beerRepository.findAll().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name01234567890123456789012345678901234567890123456789");
        beerMap.put("beerStyle", BeerStyle.PALE_ALE);
        beerMap.put("price", BigDecimal.valueOf(150));
        beerMap.put("upc",12);

        MvcResult result = mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isBadRequest())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()", is(1))).andReturn();

        System.out.println(result.getResponse().getContentAsString());

    }

    @Test
    void testListBeerByName() throws Exception {
        mockMvc.perform(get(BeerController.BEER_PATH)
                .queryParam("beerName", "IPA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.size()", is(336)));
    }
}