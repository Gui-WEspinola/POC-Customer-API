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
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new CustomerNotFoundException(id));
    }

    @Override
    @Transactional
    public Customer save(CustomerRequestDTO customerRequestDTO) {
        documentNumberFilter(customerRequestDTO);
        if (existsByEmail(customerRequestDTO.getEmail())) {
            throw new ExistingEmailException(customerRequestDTO.getEmail());
        }
        if (existsByDocumentNumber(customerRequestDTO.getDocumentNumber())) {
            throw new DocumentInUseException(customerRequestDTO.getDocumentNumber());
        }

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

    @Override
    public boolean existsByEmail(String email) {
        return customerRepository.existsByEmail(email);
    }

    @Override
    public boolean existsByDocumentNumber(String document) {
        return customerRepository.existsByDocumentNumber(document);
    }

    private static void documentNumberFilter(CustomerRequestDTO customerRequestDTO) {
        customerRequestDTO.setDocumentNumber(customerRequestDTO.getDocumentNumber()
                .replace(".", "")
                .replace("/", "")
                .replace("-", ""));
    }
}
