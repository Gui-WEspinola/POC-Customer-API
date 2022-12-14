package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class CustomerUpdateResponseDTO {

    private String name;

    private String email;

    private String mobileNumber;
}
