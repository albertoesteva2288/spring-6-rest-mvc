package guru.springframework.spring6restmvc.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.Id;
import jakarta.persistence.Version;
import lombok.*;

import java.time.LocalDate;
import java.util.UUID;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
@Entity

public class Customer {
    @Id
    private UUID id;
    private String customerName;
    @Version
    private Integer version;
    private LocalDate createdDate;
    private LocalDate lastModifiedDate;
}
