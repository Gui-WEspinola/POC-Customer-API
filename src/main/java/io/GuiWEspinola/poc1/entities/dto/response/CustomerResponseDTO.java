package io.GuiWEspinola.poc1.entities.dto.response;

import io.GuiWEspinola.poc1.enums.PersonType;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

    private Long id;

    private String name;

    private String email;

    private PersonType documentType;

    private String documentNumber;

    private Integer mobileNumber;
}
