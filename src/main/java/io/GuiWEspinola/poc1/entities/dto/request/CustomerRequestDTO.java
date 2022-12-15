package io.GuiWEspinola.poc1.entities.dto.request;

import io.GuiWEspinola.poc1.enums.DocumentType;
import io.GuiWEspinola.poc1.validation.CustomerSequenceProvider;
import io.GuiWEspinola.poc1.validation.groupValidation.CnpjGroup;
import io.GuiWEspinola.poc1.validation.groupValidation.CpfGroup;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

@Data
@NoArgsConstructor
@AllArgsConstructor
@GroupSequenceProvider(CustomerSequenceProvider.class)
public class CustomerRequestDTO {

    @NotBlank(message = "Name is a required field.")
    @Pattern(regexp = "^([a-zA-Z]*)(\\s([a-zA-Z]*))*$", message = "Full name is required.")
    private String name;

    @Email(message = "Please enter a valid e-mail.")
    @NotBlank(message = "E-mail is a required field.")
    private String email;

    @Enumerated(EnumType.STRING)
    @NotNull(message = "Enter the document Type (CPF/CNPJ)")
    private DocumentType documentType;

    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @NotBlank(message = "CPF/CNPJ is a required field.")
    @Pattern(regexp = "(^\\d{3}.\\d{3}.\\d{3}-\\d{2}$)|(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)",
            message = "Enter CPF/CNPJ with complete format")
    private String documentNumber;

    @NotNull(message = "Mobile number is a required field.")
    // TODO validation for mobile number
    private Integer mobileNumber;
}
