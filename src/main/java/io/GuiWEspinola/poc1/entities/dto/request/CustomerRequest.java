package io.GuiWEspinola.poc1.entities.dto.request;

import io.GuiWEspinola.poc1.enums.DocumentType;
import io.GuiWEspinola.poc1.validation.CustomerSequenceProvider;
import io.GuiWEspinola.poc1.validation.groupValidation.CnpjGroup;
import io.GuiWEspinola.poc1.validation.groupValidation.CpfGroup;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;
import javax.validation.groups.Default;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@GroupSequenceProvider(CustomerSequenceProvider.class)
public class CustomerRequest {

    @NotBlank(message = "Name is a required field.")
    @Pattern(regexp = "^[A-Za-z]+( [A-Za-z]+)*$", message = "Full name is required.")
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
    @Pattern(regexp = "(^\\d{3}.\\d{3}.\\d{3}-\\d{2}$)|(^\\d{11}$)", message = "Invalid CPF format", groups = CpfGroup.class)
    @Pattern(regexp = "(^\\d{2}.\\d{3}.\\d{3}/\\d{4}-\\d{2}$)|(^\\d{14}$)", message = "Invalid CNPJ format", groups = CnpjGroup.class)
    private String documentNumber;

    @NotNull(message = "Mobile number is a required field.")
    private String mobileNumber;
}
