package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.exception.AddressNotFoundException;
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
        var customer = customerService.findById(addressRequestDTO.getCustomerRequestDTO().getId());
        if (customer.getAddress().isEmpty()) {
            addressRequestDTO.setMainAddress(true);
        }
        return addressRepository.save(mapper.map(addressRequestDTO, Address.class));
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    @Transactional
    public Address update(AddressRequestDTO addressRequestDTO) {
        existsById(addressRequestDTO.getId());
        return addressRepository.save(mapper.map(addressRequestDTO, Address.class));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        addressRepository.delete(findById(id));
    }

    @Override
    public void existsById(Long id) {
        if (!addressRepository.existsById(id)){
            throw new AddressNotFoundException(id);
        }
    }
}
