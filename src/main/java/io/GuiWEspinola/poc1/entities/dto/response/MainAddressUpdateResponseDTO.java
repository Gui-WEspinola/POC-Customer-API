package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
public class MainAddressUpdateResponseDTO {

    private Long addressId;

    private Boolean mainAddress;
}
