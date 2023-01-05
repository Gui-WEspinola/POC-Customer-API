package io.GuiWEspinola.poc1.entities.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

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
