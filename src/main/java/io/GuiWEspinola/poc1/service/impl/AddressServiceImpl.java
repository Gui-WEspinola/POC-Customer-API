package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.exception.AddressMaxLimitException;
import io.GuiWEspinola.poc1.exception.AddressNotFoundException;
import io.GuiWEspinola.poc1.exception.MainAddressDeleteException;
import io.GuiWEspinola.poc1.repository.AddressRepository;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

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
        var customer = customerService.findById(addressRequestDTO.getCustomer().getId());

        addressRequestDTO.setIsMainAddress(customer.getAddress().isEmpty());

        checksMaximumAddressLimit(customer);

        return addressRepository.save(mapper.map(addressRequestDTO, Address.class));
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    @Transactional
    public Address update(AddressRequestDTO addressRequestDTO, Long id) {
        Address address = findById(id);

        address.setAddressNumber(addressRequestDTO.getAddressNumber());
        address.setDistrict(addressRequestDTO.getDistrict());
        address.setState(addressRequestDTO.getState());
        address.setStreet(addressRequestDTO.getStreet());
        address.setZipCode(addressRequestDTO.getZipCode());
        address.setCity(addressRequestDTO.getCity());

        return addressRepository.save(address);
    }

    @Override
    public Address updateMainAddress(Long id) {
        Address address = findById(id);

        address.getCustomer().getAddress()
                .forEach(a -> {
                    a.setIsMainAddress(false);
                    addressRepository.save(a);});

        address.setIsMainAddress(true);
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (Boolean.TRUE.equals(findById(id).getIsMainAddress())) {
            throw new MainAddressDeleteException();
        }
        addressRepository.delete(findById(id));
    }

    public void checksMaximumAddressLimit(Customer customer) {
        if (customer.getAddress().size() >= 5) {
            throw new AddressMaxLimitException(customer.getId());
        }
    }
}
