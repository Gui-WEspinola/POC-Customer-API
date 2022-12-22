package io.GuiWEspinola.poc1.entities.dto.response;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.enums.DocumentType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CustomerResponse {

    private Long id;

    private String name;

    private String email;

    private DocumentType documentType;

    private String documentNumber;

    private String mobileNumber;

    public CustomerResponse(Customer customer) {
        this.id = customer.getId();
        this.name = customer.getName();
        this.email = customer.getEmail();
        this.documentType = customer.getDocumentType();
        this.documentNumber = customer.getDocumentType().getMask();
        this.mobileNumber = customer.getMobileNumber();
    }
}
