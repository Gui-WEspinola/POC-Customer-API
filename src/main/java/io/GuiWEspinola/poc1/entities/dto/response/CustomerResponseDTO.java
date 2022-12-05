package io.GuiWEspinola.poc1.entities.dto.response;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.enums.PersonType;
import lombok.*;

import java.util.ArrayList;
import java.util.List;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class CustomerResponseDTO {

    private Long id;

    private String name;

    private String email;

    private PersonType personType;

    private Integer mobileNumber;

    private List<Address> address = new ArrayList<>();
}
