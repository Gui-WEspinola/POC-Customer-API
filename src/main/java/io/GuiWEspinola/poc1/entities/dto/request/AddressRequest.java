package io.GuiWEspinola.poc1.entities.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressRequest {

    @NotBlank
    private String number;

    @Pattern(regexp = "^\\d{5}-?\\d{3}$", message = "Enter a valid zip code.")
    private String zipCode;

    private String complement;

    @JsonIgnore
    private Boolean mainAddress;

    @NotNull(message = "Enter a valid Customer ID.")
    private Long customerId;
}
