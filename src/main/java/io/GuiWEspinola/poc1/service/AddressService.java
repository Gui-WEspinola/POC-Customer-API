package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface AddressService {

    Address save(AddressRequestDTO addressRequestDTO);

    Address findById(Long id);
}
