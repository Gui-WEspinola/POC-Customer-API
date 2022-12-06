package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.repository.AddressRepository;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Optional;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @Override
    @Transactional
    public Address save(AddressRequestDTO addressRequestDTO) {
        customerService.findById(addressRequestDTO.getCustomerRequestDTO().getId()); // Customer ID validation
        return addressRepository.save(mapper.map(addressRequestDTO, Address.class));
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("AddressNotFoundException goes here"));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Address address = findById(id);
        addressRepository.delete(address);
    }
}
