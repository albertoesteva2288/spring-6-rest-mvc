package guru.springframework.spring6restmvc.repository;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.entity.Category;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.transaction.annotation.Transactional;

import static org.junit.jupiter.api.Assertions.*;


@SpringBootTest
class CategoryRepositoryTest {

    @Autowired
    CategoryRepository categoryRepository;

    @Autowired
    BeerRepository beerRepository;

    Beer beer;

    @BeforeEach
    void setUp() {
        beer = beerRepository.findAll().get(0);
    }

    @Transactional
    @Test
    void testCreateCategory() {
        Category category =  categoryRepository.save(Category.builder()
                        .description("Ales")
                .build());

        beer.addCategory(category);
        Beer savedBeer = beerRepository.save(beer);

        categoryRepository.flush();
        beerRepository.flush();

        assertNotNull(savedBeer);
        assertNotNull(categoryRepository.findAll().get(0));
        assertFalse(savedBeer.getCategories().isEmpty());
        assertEquals("Ales", savedBeer.getCategories().iterator().next().getDescription());
        System.out.println(savedBeer.getBeerName());
    }
}