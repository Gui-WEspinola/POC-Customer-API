package io.GuiWEspinola.poc1.entities.dto.response;

import io.GuiWEspinola.poc1.enums.DocumentType;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class CustomerResponseDTO {

    private Long id;

    private String name;

    private String email;

    private DocumentType documentType;

    private Long mobileNumber;
}
