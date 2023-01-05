package io.GuiWEspinola.poc1.entities.dto.response;

import io.GuiWEspinola.poc1.entities.Address;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponse {

    private Long id;

    private String street;

    private String number;

    private String complement;

    private String district;

    private String city;

    private String zipCode;

    private String state;

    private Boolean mainAddress;
}
