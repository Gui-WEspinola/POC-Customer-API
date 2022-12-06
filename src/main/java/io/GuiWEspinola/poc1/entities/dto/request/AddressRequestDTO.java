package io.GuiWEspinola.poc1.entities.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressRequestDTO {

    private Long id;

    private String street;

    private Integer addressNumber;

    private String district;

    private String city;

    private String zipCode;

    private String state;

    private Boolean mainAddress = false;

    private CustomerRequestDTO customerRequestDTO;
}
