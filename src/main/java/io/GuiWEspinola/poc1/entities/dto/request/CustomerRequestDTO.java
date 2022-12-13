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
import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.hibernate.validator.constraints.br.CNPJ;
import org.hibernate.validator.constraints.br.CPF;
import org.hibernate.validator.group.GroupSequenceProvider;

@Getter
@Setter
@RequiredArgsConstructor
@GroupSequenceProvider(CustomerSequenceProvider.class)
public class CustomerRequestDTO {

    @NotBlank(message = "Name is a required field.")
    private String name;

    @Email(message = "Please enter a valid e-mail.")
    @NotBlank(message = "E-mail is a required field.")
    private String email;

    @Enumerated(EnumType.STRING)
    private DocumentType documentType;

    @CPF(groups = CpfGroup.class)
    @CNPJ(groups = CnpjGroup.class)
    @NotBlank(message = "CPF/CNPJ is a required field.")
    private String documentNumber;

    @NotNull(message = "Mobile number is a required field.")
    private Integer mobileNumber;
}
