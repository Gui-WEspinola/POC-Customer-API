package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerUpdateResponseDTO {

    private String name;

    private String email;

    private Long mobileNumber;
}
