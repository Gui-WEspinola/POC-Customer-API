package io.GuiWEspinola.poc1.entities.dto.response;

import lombok.Getter;
import lombok.RequiredArgsConstructor;
import lombok.Setter;

@Setter
@Getter
@RequiredArgsConstructor
public class MainAddressUpdateResponseDTO {

    private Long addressId;

    private Boolean mainAddress;
}
