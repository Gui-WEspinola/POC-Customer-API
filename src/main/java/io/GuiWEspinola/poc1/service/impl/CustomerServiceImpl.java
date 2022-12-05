package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequestDTO;
import io.GuiWEspinola.poc1.repository.CustomerRepository;
import io.GuiWEspinola.poc1.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

    @Autowired
    private ModelMapper mapper;

    @Override
    public List<Customer> findAll() {
        return customerRepository.findAll();
    }

    @Override
    public Customer findById(Long id) {
        return customerRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("CustomerNotFoundException goes here"));

    }

    @Override
    public Customer save(CustomerRequestDTO customerRequestDTO) {
        return customerRepository.save(mapper.map(customerRequestDTO, Customer.class));
    }

    @Override
    public void delete(Long id) {
        Customer customer = findById(id);
        customerRepository.delete(customer);
    }

    @Override
    public Customer update(CustomerRequestDTO customerRequestDTO) {
        findById(customerRequestDTO.getId());
        return customerRepository.save(mapper.map(customerRequestDTO, Customer.class));
    }
}
