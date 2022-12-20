package io.GuiWEspinola.poc1.entities.dto.request;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Pattern;
import lombok.*;

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
