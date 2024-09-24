package guru.springframework.spring6restmvc.entity;

import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "beer")
public class Beer {
    @Id
    @GeneratedValue(generator = "UUID")
    //@GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    // Strategy was mark as deprecated, I modified the code of course to the next
    @UuidGenerator
    @Column(length = 36, columnDefinition = "varchar", updatable = false, nullable = false)
    private UUID id;
    @Version
    private Integer version;
    @NotBlank
    @NotNull
    // @Size: This validation occurs before attempting to persist the data, at the application layer level.
    @Size(max = 50)
    //@Column: This applies at the persistence layer (database) level.
    @Column(length = 50)
    private String beerName;
    @NotNull
    private BeerStyle beerStyle;
    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;
    private Integer quantityOnHand;
    @NotNull
    private BigDecimal price;
    private LocalDate createdDate;
    private LocalDate updatedDate;

}
