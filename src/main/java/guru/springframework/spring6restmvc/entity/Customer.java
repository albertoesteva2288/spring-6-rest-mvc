package guru.springframework.spring6restmvc.entity;

import jakarta.persistence.*;
import lombok.*;
import org.hibernate.annotations.UuidGenerator;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity
@Table(name = "customer")
public class Customer {
    @Id
    @GeneratedValue(generator = "UUID")
    //@GenericGenerator(name = "UUID" , strategy = "org.hibernate.id.UUIDGenerator")
    // Strategy was mark as deprecated, I modified the code of course to the next
    @UuidGenerator
    @Column(length = 36, columnDefinition = "BINARY(16)", updatable = false, nullable = false)
    private UUID id;
    private String customerName;
    @Version
    private Integer version;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
