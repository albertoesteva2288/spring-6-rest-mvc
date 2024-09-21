package guru.springframework.spring6restmvc.bootstrap;

import guru.springframework.spring6restmvc.entity.Beer;
import guru.springframework.spring6restmvc.entity.Customer;
import guru.springframework.spring6restmvc.model.BeerStyle;

import guru.springframework.spring6restmvc.repository.BeerRepository;
import guru.springframework.spring6restmvc.repository.CustomerRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.Arrays;

@Component
@RequiredArgsConstructor
public class BootStrapData implements CommandLineRunner {

    private final BeerRepository beerRepository;
    private final CustomerRepository customerRepository;

    @Override
    public void run(String... args) throws Exception {
        loadBeerData();
        loadCustomerData();
    }

    private void loadBeerData() {
       if(beerRepository.count() == 0) {
           Beer beer1 = Beer.builder()

                   .beerName("Galaxy Cat")
                   .beerStyle(BeerStyle.PALE_ALE)
                   .upc("12356")
                   .price(new BigDecimal("12.99"))
                   .quantityOnHand(122)
                   .createdDate(LocalDate.now())
                   .updatedDate(LocalDate.now())
                   .version(1)
                   .build();

           Beer beer2 = Beer.builder()

                   .beerName("Crank")
                   .beerStyle(BeerStyle.PALE_ALE)
                   .upc("12356222")
                   .price(new BigDecimal("11.99"))
                   .quantityOnHand(392)
                   .createdDate(LocalDate.now())
                   .updatedDate(LocalDate.now())
                   .version(1)
                   .build();

           Beer beer3 = Beer.builder()

                   .beerName("Sunshine City")
                   .beerStyle(BeerStyle.IPA)
                   .upc("12356")
                   .price(new BigDecimal("13.99"))
                   .quantityOnHand(144)
                   .createdDate(LocalDate.now())
                   .updatedDate(LocalDate.now())
                   .version(1)
                   .build();
           beerRepository.saveAll(Arrays.asList(beer1,beer2,beer3));
       }


    }
    private void loadCustomerData() {
        if(customerRepository.count() == 0) {
            Customer customer1 = Customer.builder()

                    .customerName("Jhon")
                    .createdDate(LocalDate.now())
                    .lastModifiedDate(LocalDate.now())
                    .version(1)
                    .build();

            Customer customer2 = Customer.builder()

                    .customerName("Sarah")
                    .createdDate(LocalDate.now())
                    .lastModifiedDate(LocalDate.now())
                    .version(1)
                    .build();

            Customer customer3 = Customer.builder()

                    .customerName("Richard")
                    .createdDate(LocalDate.now())
                    .lastModifiedDate(LocalDate.now())
                    .version(1)
                    .build();

            customerRepository.save(customer1);
            customerRepository.save(customer2);
            customerRepository.save(customer3);
        }
    }
}
