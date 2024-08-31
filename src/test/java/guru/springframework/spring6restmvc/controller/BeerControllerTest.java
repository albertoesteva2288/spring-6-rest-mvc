package guru.springframework.spring6restmvc.controller;

import guru.springframework.spring6restmvc.service.BeerService;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import org.springframework.test.web.servlet.result.MockMvcResultMatchers;

import java.util.UUID;

import static org.junit.jupiter.api.Assertions.*;
// controllers in tag stand for the controllers being tested, if no one is assigned all controller will bring for test
@WebMvcTest(controllers = BeerController.class)
class BeerControllerTest {

    // MockMvc It is the object used to simulate HTTP requests
    @Autowired
    MockMvc mockMvc;

    // Creates a mock object of the service that is injected into the controller.
    @MockBean
    BeerService beerService;
    @Test
    void getBeerById() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.get("/api/v1/beer/"+UUID.randomUUID())
                .accept(MediaType.APPLICATION_JSON))
                .andExpect(MockMvcResultMatchers.status().isOk())
        ;

    }
}