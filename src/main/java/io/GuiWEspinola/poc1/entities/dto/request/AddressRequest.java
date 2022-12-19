package io.GuiWEspinola.poc1.entities.dto.request;

import com.fasterxml.jackson.annotation.JsonIgnore;
import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressRequest {

    private String number;

    private String zipCode;

    private String complement;

    @JsonIgnore
    private Boolean mainAddress;

    private Long customerId;
}
