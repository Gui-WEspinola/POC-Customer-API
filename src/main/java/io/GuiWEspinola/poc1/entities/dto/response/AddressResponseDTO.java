package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;

@Data
@AllArgsConstructor
public class AddressResponseDTO {

    private Long id;

    private String street;

    private Integer addressNumber;

    private String district;

    private String city;

    private String zipCode;

    private String state;

    private Boolean mainAddress;
}
