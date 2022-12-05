package io.GuiWEspinola.poc1.entities.dto.request;

import io.GuiWEspinola.poc1.enums.PersonType;
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Getter
@Setter
@RequiredArgsConstructor
public class CustomerRequestDTO {

    private String name;

    private String email;

    private PersonType documentType;

    private String documentNumber;

    private Integer mobileNumber;
}
