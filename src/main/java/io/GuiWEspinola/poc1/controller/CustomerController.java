package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerResponseDTO;
import io.GuiWEspinola.poc1.service.impl.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;
import java.util.stream.Collectors;

@RestController
@AllArgsConstructor
@RequestMapping(path = "poc1-api/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAll() {
        List<Customer> responseDTOList = customerService.findAll();
        return ResponseEntity.ok().body(responseDTOList
                .stream()
                .map(customer -> mapper.map(customer, CustomerResponseDTO.class))
                .collect(Collectors.toList()));
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(customerService.findById(id), CustomerResponseDTO.class));
    }
}
