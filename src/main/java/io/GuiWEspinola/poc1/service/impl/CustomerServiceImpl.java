package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerUpdateRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponseDTO;
import io.GuiWEspinola.poc1.exception.CustomerNotFoundException;
import io.GuiWEspinola.poc1.exception.DocumentInUseException;
import io.GuiWEspinola.poc1.exception.ExistingEmailException;
import io.GuiWEspinola.poc1.repository.CustomerRepository;
import io.GuiWEspinola.poc1.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public Page<Customer> findAll(Pageable pageable) {
        return customerRepository.findAll(pageable);
    }

    @Override
    public Page<Customer> findByCustomerName(String name, Pageable pageable) {
        return customerRepository.findByNameLikeIgnoreCase(name, pageable);
    }

    @Override
    public Page<Customer> findCustomerNameContaining(String name, Pageable pageable) {
        return customerRepository.findByNameContainingIgnoreCase(name, pageable);
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    @Transactional
    public Customer save(CustomerRequestDTO customerRequestDTO) {

        checksAvailableEmail(customerRequestDTO.getEmail());
        checksDocumentNumber(customerRequestDTO.getDocumentNumber());

        return customerRepository.save(mapper.map(customerRequestDTO, Customer.class));
    }

    @Override
    @Transactional
    public void delete(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }

    @Override
    @Transactional
    public Customer update(CustomerUpdateRequestDTO customerRequestDTO, Long id) {
        Customer customer = findById(id);

        checksAvailableEmail(customerRequestDTO.getEmail());

        customer.setName(customerRequestDTO.getName());
        customer.setEmail(customerRequestDTO.getEmail());
        customer.setMobileNumber(customerRequestDTO.getMobileNumber());

        return customerRepository.save(customer);
    }

    @Override
    public List<AddressResponseDTO> getAllAddresses(Long id) {
        return findById(id).getAddress()
                .stream()
                .map(address -> mapper.map(address, AddressResponseDTO.class))
                .toList();
    }


    public void checksAvailableEmail(String email) {
        if (customerRepository.existsByEmail(email)){
            throw new ExistingEmailException(email);
        }
    }

    @Override
    public void checksDocumentNumber(String document) {
        if (customerRepository.existsByDocumentNumber(document)){
            throw new DocumentInUseException(document);
        }
    }
}
