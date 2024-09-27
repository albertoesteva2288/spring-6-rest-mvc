package guru.springframework.spring6restmvc.entity;

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

import java.time.LocalDate;
import java.time.LocalDateTime;
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
    // UUID was not handled as varchar by default by Hibernate
    // so that Mysql could save it as such @JdbcTypeCode(SqlTypes.VARCHAR) was used
    @JdbcTypeCode(SqlTypes.VARCHAR)
    // Hibernate did not recognize the length of the varchar,
    // even though length = 36 was defined in @Column
    // so columnDefinition was changed to varchar(36)
    // this is because id is a UUID type
    @Column(length = 36, columnDefinition = "varchar(36)", updatable = false, nullable = false)
    private UUID id;
    @NotBlank
    @NotNull
    @Size(max = 50)
    private String customerName;
    @Version
    private Integer version;
    @CreationTimestamp
    private LocalDateTime createdDate;
    @UpdateTimestamp
    private LocalDateTime updatedDate;
    @Column(length = 255)
    private String email;
}
