package guru.springframework.spring6restmvc.model;

import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Builder;
import lombok.Data;

import java.time.LocalDate;
import java.util.UUID;

@Builder
@Data
public class CustomerDTO {

    private UUID id;
    @NotBlank
    @NotNull
    private String customerName;
    private Integer version;
    private LocalDate createdDate;
    private LocalDate updatedDate;

}
