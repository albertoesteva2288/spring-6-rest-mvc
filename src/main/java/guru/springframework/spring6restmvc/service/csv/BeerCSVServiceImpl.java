package guru.springframework.spring6restmvc.service.csv;

import com.opencsv.bean.CsvToBeanBuilder;
import guru.springframework.spring6restmvc.model.csv.BeerCSVRecord;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.util.List;

public class BeerCSVServiceImpl implements BeerCSVService {
    @Override
    public List<BeerCSVRecord> convertCSV(File file) throws FileNotFoundException {
        try {
            List<BeerCSVRecord> records = new CsvToBeanBuilder<BeerCSVRecord>(new FileReader(file))
                    .withType(BeerCSVRecord.class)
                    .build()
                    .parse();
            return records;
        } catch (FileNotFoundException e) {
            throw new RuntimeException(e);
        }

    }
}