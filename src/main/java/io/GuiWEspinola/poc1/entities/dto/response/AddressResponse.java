package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
