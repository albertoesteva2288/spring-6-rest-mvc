package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.Builder;
import lombok.Data;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class BeerDTO {

    private UUID id;
    private Integer version;
    @NotBlank
    @NotNull
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @NotNull
    @NotBlank
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    private BigDecimal price;
    private LocalDate createdDate;
    private LocalDate updatedDate;
}
