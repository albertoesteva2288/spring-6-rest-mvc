package guru.springframework.spring6restmvc.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.service.BeerService;
import guru.springframework.spring6restmvc.service.BeerServiceImpl;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.mockito.ArgumentCaptor;
import org.mockito.Captor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;


import java.time.LocalDate;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.BDDMockito.given;
import static org.mockito.Mockito.verify;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;
import static org.hamcrest.core.StringContains.containsString;

// controllers in tag stand for the controllers being tested, if no one is assigned all controller will bring for test
@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    // MockMvc It is the object used to simulate HTTP requests
    @Autowired
    MockMvc mockMvc;

    // Creates a mock object of the service that is injected into the controller.
    @MockBean
    BeerService beerService;

    @Autowired
    ObjectMapper objectMapper;

    BeerServiceImpl beerServiceImpl;

    @Captor
    ArgumentCaptor<UUID> uuidArgumentCaptor;
    @Captor
    ArgumentCaptor<Beer> beerArgumentCaptor;

    @BeforeEach
    void init(){
        beerServiceImpl = new BeerServiceImpl();
    }

    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get(BeerController.BEER_PATH_ID, testBeer.getId())
                .accept(MediaType.APPLICATION_JSON)) // Setting that the request accept a Json
                    .andExpect(status().isOk()) // Setting the response Status Ok for all responses
                    .andExpect(content().contentType(MediaType.APPLICATION_JSON))// Setting a body type Json for the ResponseEntity
                    //.andExpect(jsonPath("$.id").value(testBeer.getId().toString())) // This is a way to verify if the id of request is equals to id of testBeer
                    .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))// This other way of verify the beer id
                    .andExpect(jsonPath("$.beerName",is(testBeer.getBeerName()))); // Verify that beerName in json is equals to testBeer.getBeerName()
// According to chatgpt this is a better option to make the comparison


    }

    @Test
    void testListBeers() throws Exception {
        given(beerService.listBeers()).willReturn(beerServiceImpl.listBeers());
        mockMvc.perform(get(BeerController.BEER_PATH)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("$.length()",is(3)));// Validation of json length  of list is 3,
                // it can be set as beerServiceImpl.listBeers()beerServiceImpl.listBeers().size() instead of that to make it dynamic


    }

    @Test
    void createNewBeer() throws Exception {
        // 1 Getting a Beer just get something to return
        Beer createdBeer = beerServiceImpl.listBeers().get(0);
        // Simulation that id is null just for "save"
        Beer newBeer =  Beer.builder()
                .id(null)
                .version(null)
                .beerName(createdBeer.getBeerName())
                .beerStyle(createdBeer.getBeerStyle())
                .upc(createdBeer.getUpc())
                .price(createdBeer.getPrice())
                .quantityOnHand(createdBeer.getQuantityOnHand())
                .createdDate(createdBeer.getCreatedDate())
                .updatedDate(createdBeer.getUpdatedDate())
                .build();


        // 2 Setting the result we want

        given(beerService.saveBeer(newBeer)).willReturn(createdBeer);

        // 3 Simulating the post request to create the beer and the kind of response
        mockMvc.perform(post(BeerController.BEER_PATH)
                .accept(MediaType.APPLICATION_JSON) // The response we expect is a Json
                    .contentType(MediaType.APPLICATION_JSON) // The request has body of json format
                    .content(objectMapper.writeValueAsString(newBeer))) // Convert beer to a json and setted in body request
                .andExpect(status().isCreated()) // It's expected and HTTP Code 201 = Created in the response
                .andExpect(header().exists("Location")) // It's expected the Location header in response
                .andExpect(header().string("Location", containsString(BeerController.BEER_PATH + "/" + createdBeer.getId())));
                // chatgpt suggestion, validate that Location has the correct url

    }

    @Test
    void updateBeer()throws Exception{
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(put(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON)
                .contentType(MediaType.APPLICATION_JSON)
                .content(objectMapper.writeValueAsString(beer)))
                .andExpect(status().isNoContent()); // set a HTTP status 204 = NO CONTENT
        verify(beerService).updateBeerById(any(UUID.class), any(Beer.class));
    }

    @Test
    void deleteBeer()throws Exception{
        Beer beer = beerServiceImpl.listBeers().get(0);

        mockMvc.perform(delete(BeerController.BEER_PATH_ID, beer.getId())
                .accept(MediaType.APPLICATION_JSON))

                .andExpect(status().isNoContent());

        verify(beerService).deleteById(uuidArgumentCaptor.capture());// Verify that the deleteById() method of the beerService service was called with a UUID.
                                                                    // Capture this UUID for later inspection.
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());// Ensure that the captured UUID is equal to the ID of the Beer object attempted to delete.
    }

    @Test
    void testPatchBeer()throws Exception {
        Beer beer = beerServiceImpl.listBeers().get(0);

        Map<String, Object> beerMap = new HashMap<>();
        beerMap.put("beerName", "New Name");

        mockMvc.perform(patch(BeerController.BEER_PATH_ID, beer.getId())
                        .accept(MediaType.APPLICATION_JSON)
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(beerMap)))
                .andExpect(status().isNoContent());

        verify(beerService).updatePatchBeerById(uuidArgumentCaptor.capture(), beerArgumentCaptor.capture());
        assertThat(beer.getId()).isEqualTo(uuidArgumentCaptor.getValue());
        assertThat(beerMap.get("beerName")).isEqualTo(beerArgumentCaptor.getValue().getBeerName());
    }
}