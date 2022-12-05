package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.repository.AddressRepository;
import io.GuiWEspinola.poc1.repository.CustomerRepository;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Address save(AddressRequestDTO addressRequestDTO) {
        customerService.findById(addressRequestDTO.getCustomerRequestDTO().getId()); // Validação de ID do Customer
        return addressRepository.save(mapper.map(addressRequestDTO, Address.class));
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AddressNotFoundException goes here"));
    }
}
