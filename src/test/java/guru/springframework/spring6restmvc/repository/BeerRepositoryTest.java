package guru.springframework.spring6restmvc.repository;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.validation.ConstraintViolationException;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;

import java.math.BigDecimal;

import static org.assertj.core.api.Assertions.assertThat;

import static org.junit.jupiter.api.Assertions.*;

@DataJpaTest
class BeerRepositoryTest {

    @Autowired
    BeerRepository beerRepository;

    @Test
    void testSaveBeerNameTooLong(){
        assertThrows(ConstraintViolationException.class, () -> {
            beerRepository.saveAndFlush(Beer.builder()
                    .beerName("My Beer 01234567890123456789012345678901234567890123456789")
                    .beerStyle(BeerStyle.ALE)
                    .upc("123456789")
                    .price(BigDecimal.valueOf(11.99))
                    .build());
        } );
    }

    @Test
    void testSaveBeer(){
                Beer beer = beerRepository.save(Beer.builder()
                    .beerName("My Beer")
                    .beerStyle(BeerStyle.ALE)
                    .upc("123456789")
                    .price(BigDecimal.valueOf(11.99))
                    .build());
            // If your entity Beer has database-level constraints (e.g., non-null fields, unique constraints),
            // they won't be enforced until the SQL INSERT statement is actually sent to the database.
            // If you don't call flush(), Hibernate will batch the SQL statements and execute them only
            // when the transaction commits, which may be after your test assertions.
            beerRepository.flush();
            assertThat(beer).isNotNull();
            assertThat(beer.getId()).isNotNull();



    }
}