package guru.springframework.spring6restmvc.model;

import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {

    private UUID id;
    private String customerName;
    private Integer version;
    private LocalDate createdDate;
    private LocalDate updatedDate;

}
