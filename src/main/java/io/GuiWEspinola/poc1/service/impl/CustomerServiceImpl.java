package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.repository.CustomerRepository;
import io.GuiWEspinola.poc1.service.CustomerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class CustomerServiceImpl implements CustomerService {

    @Autowired
    private CustomerRepository customerRepository;

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
    public void create(Customer customer) {
        findById(customer.getId());
        customerRepository.save(customer);
    }
}
