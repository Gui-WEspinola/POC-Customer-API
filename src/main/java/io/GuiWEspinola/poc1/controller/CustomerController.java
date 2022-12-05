package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerResponseDTO;
import io.GuiWEspinola.poc1.service.impl.CustomerServiceImpl;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(path = "poc1-api/customer")
public class CustomerController {

    @Autowired
    private CustomerServiceImpl customerService;

    @Autowired
    private ModelMapper mapper;

    @GetMapping
    public ResponseEntity<List<CustomerResponseDTO>> getAllCustomers() {
        List<Customer> responseDTOList = customerService.findAll();
        return ResponseEntity.ok().body(responseDTOList
                .stream()
                .map(customer -> mapper.map(customer, CustomerResponseDTO.class)).toList());
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(customerService.findById(id), CustomerResponseDTO.class));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> postCustomer(@RequestBody CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.status(CREATED)
                .body(mapper.map(customerService.save(customerRequestDTO), CustomerResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }
}
