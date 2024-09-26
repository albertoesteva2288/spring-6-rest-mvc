package guru.springframework.spring6restmvc.service.csv;

import guru.springframework.spring6restmvc.model.csv.BeerCSVRecord;
import org.junit.jupiter.api.Test;
import org.springframework.util.ResourceUtils;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;

class BeerCSVServiceTest {

    @Test
    void convertCSV() throws FileNotFoundException {
        BeerCSVService beerCSVService = new BeerCSVServiceImpl();
        File file = ResourceUtils.getFile("classpath:csvdata/beers.csv");
        List<BeerCSVRecord> recs = beerCSVService.convertCSV(file);
        System.out.println(recs.size());
        recs.forEach(System.out::println);
        assertThat(recs.size()).isGreaterThan(0);
    }
}