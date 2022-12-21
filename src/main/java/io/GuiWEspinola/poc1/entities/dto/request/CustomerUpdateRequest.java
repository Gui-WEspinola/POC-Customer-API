package io.GuiWEspinola.poc1.entities.dto.request;

import lombok.AllArgsConstructor;
import lombok.Data;

import javax.validation.constraints.Email;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Pattern;

@Data
@AllArgsConstructor
public class CustomerUpdateRequest {

    @NotBlank(message = "Name is a required field.")
    @Pattern(regexp = "^[A-Za-z]+( [A-Za-z]+)*$", message = "Full name is required.")
    private String name;

    @Email(message = "Please enter a valid e-mail.")
    private String email;

    @NotNull(message = "Mobile number is a required field.")
    private String mobileNumber;
}
