package io.GuiWEspinola.poc1.entities.dto.response;

import io.GuiWEspinola.poc1.entities.Customer;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class AddressResponseDTO {

    private Long id;

    private String street;

    private Integer addressNumber;

    private String district;

    private String city;

    private String zipCode;

    private String state;
}
