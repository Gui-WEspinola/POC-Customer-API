package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
public class CustomerUpdateResponse {

    private String name;

    private String email;

    private String mobileNumber;
}

