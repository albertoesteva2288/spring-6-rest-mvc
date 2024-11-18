package guru.springframework.spring6restmvc.entity;

import guru.springframework.spring6restmvc.model.BeerStyle;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;
import lombok.*;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.JdbcTypeCode;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.annotations.UuidGenerator;
import org.hibernate.type.SqlTypes;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.HashSet;
import java.util.Set;
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
    @JdbcTypeCode(SqlTypes.VARCHAR)
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;

    @Version
    private Integer version = 0;

    @NotBlank
    @NotNull
    // @Size: This validation occurs before attempting to persist the data, at the application layer level.
    @Size(max = 50)
    //@Column: This applies at the persistence layer (database) level.
    @Column(length = 50)
    private String beerName;

    @NotNull
    @JdbcTypeCode(SqlTypes.SMALLINT)
    private BeerStyle beerStyle;

    @NotNull
    @NotBlank
    @Size(max = 255)
    private String upc;

    private Integer quantityOnHand;

    @NotNull
    private BigDecimal price;

    @OneToMany(mappedBy = "beer")
    private Set<BeerOrderLine> beerOrderLines;

    // Using this approach to make the many-to-many relationship,
    // we cannot add more fields to give more details between Beer and Category
    // The order of the JoinColumns depends on the class we are in
    @Builder.Default
    @ManyToMany
    @JoinTable(name = "beer_category",
            joinColumns = @JoinColumn(name = "beer_id"),
            inverseJoinColumns = @JoinColumn(name = "category_id"))
    private Set<Category> categories = new HashSet<>();

    public void addCategory(Category category) {
        this.getCategories().add(category);
        category.getBeers().add(this);
    }

    public void removeCategory(Category category) {
        this.getCategories().remove(category);
        category.getBeers().remove(this);
    }

    @CreationTimestamp
    @Column(updatable = false)
    private LocalDateTime createdDate;

    @UpdateTimestamp
    private LocalDateTime updatedDate;

}
