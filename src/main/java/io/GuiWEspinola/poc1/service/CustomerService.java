package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequestDTO;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    List<Customer> findAll();

    Customer findById(Long id);

    Customer save(CustomerRequestDTO customerRequestDTO);

    void delete (Long id);

    Customer update(CustomerRequestDTO customerRequestDTO);

    List<Address> getAllAddresses(Long id);
}
