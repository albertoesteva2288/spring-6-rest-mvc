package guru.springframework.spring6restmvc.service.csv;
import guru.springframework.spring6restmvc.model.csv.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.util.List;

public interface BeerCSVService {
    List<BeerCSVRecord> convertCSV(File file) throws FileNotFoundException;

}
