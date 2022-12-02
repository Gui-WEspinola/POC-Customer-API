package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Customer;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<Customer> findAll();

    Customer findById(Long id);

    void create(Customer customer);
}
