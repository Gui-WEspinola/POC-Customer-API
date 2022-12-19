package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequest;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerUpdateRequest;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponse;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public interface CustomerService {

    Page<Customer> findAll(Pageable pageable);

    Page<Customer> findByCustomerName(String name, Pageable pageable);

    Page<Customer> findCustomerNameContaining(String name, Pageable pageable);

    Customer findById(Long id);

    Customer save(CustomerRequest customerRequest);

    void delete(Long id);

    Customer update(CustomerUpdateRequest requestDTO, Long id);

    List<AddressResponse> getAllAddresses(Long id);

    void checksAvailableEmail(String email);

    void checksDocumentNumber(String document);
}
