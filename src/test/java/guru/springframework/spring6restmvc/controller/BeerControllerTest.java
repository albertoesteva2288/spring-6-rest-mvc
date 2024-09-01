package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.model.Beer;
import guru.springframework.spring6restmvc.service.BeerService;
import guru.springframework.spring6restmvc.service.BeerServiceImpl;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;
import org.mockito.BDDMockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.hamcrest.core.Is.is;
// controllers in tag stand for the controllers being tested, if no one is assigned all controller will bring for test
@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    // MockMvc It is the object used to simulate HTTP requests
    @Autowired
    MockMvc mockMvc;

    // Creates a mock object of the service that is injected into the controller.
    @MockBean
    BeerService beerService;

    BeerServiceImpl beerServiceImpl = new BeerServiceImpl();

    @Test
    void getBeerById() throws Exception {
        Beer testBeer = beerServiceImpl.listBeers().get(0);
        given(beerService.getBeerById(testBeer.getId())).willReturn(testBeer);

        mockMvc.perform(get("/api/v1/beer/" + testBeer.getId())
                .accept(MediaType.APPLICATION_JSON)) // Setting that the request accept a Json
                .andExpect(status().isOk()) // Setting the response Status Ok for all responses
                .andExpect(content().contentType(MediaType.APPLICATION_JSON))// Setting a body type Json for the ResponseEntity
                //.andExpect(jsonPath("$.id").value(testBeer.getId().toString())) // This is a way to verify if the id of request is equals to id of testBeer
                .andExpect(jsonPath("$.id", is(testBeer.getId().toString())))// This other way of verify the beer id
                .andExpect(jsonPath("$.beerName",is(testBeer.getBeerName()))) // Verify that beerName in json is equals to testBeer.getBeerName()                                                                                              //According to chatgpt this is a better option to make the comparison
        ;

    }
}