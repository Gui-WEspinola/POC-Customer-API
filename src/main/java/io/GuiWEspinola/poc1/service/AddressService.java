package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {

    Address save(AddressRequestDTO addressRequestDTO);

    Address findById(Long id);

    void delete(Long id);

}
