package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
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
