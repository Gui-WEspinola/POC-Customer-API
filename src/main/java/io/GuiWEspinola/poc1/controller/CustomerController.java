package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerUpdateRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponseDTO;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerResponseDTO;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerUpdateResponseDTO;
import io.GuiWEspinola.poc1.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(path = "poc1/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final ModelMapper mapper;

    @GetMapping
    public ResponseEntity<Page<CustomerResponseDTO>> getAllCustomers(
            @PageableDefault(size = 10, direction = Sort.Direction.ASC, sort = {"name"}) Pageable pageable,
            @RequestParam(required = false) String name) {

        if (name != null) {
            Page<Customer> page = customerService.findByName(name, pageable);
            return ResponseEntity.ok().body(page.map(customer -> mapper.map(customer, CustomerResponseDTO.class)));
        } else {
            Page<Customer> page = customerService.findAll(pageable);
            return ResponseEntity.ok().body(page.map(customer -> mapper.map(customer, CustomerResponseDTO.class)));
        }
    }

    @GetMapping("/{id}")
    public ResponseEntity<CustomerResponseDTO> getCustomerById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(customerService.findById(id), CustomerResponseDTO.class));
    }

    @GetMapping("/addresses/{id}")
    public ResponseEntity<List<AddressResponseDTO>> getAllAddresses(@PathVariable Long id) {
        return ResponseEntity.ok().body(customerService.getAllAddresses(id));
    }

    @PostMapping
    public ResponseEntity<CustomerResponseDTO> postCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return ResponseEntity.status(CREATED)
                .body(mapper.map(customerService.save(customerRequestDTO), CustomerResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteCustomerById(@PathVariable Long id) {
        customerService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<CustomerUpdateResponseDTO> updateCustomer
            (@PathVariable Long id, @RequestBody @Valid CustomerUpdateRequestDTO customerRequestDTO) {

        return ResponseEntity.accepted()
                .body(mapper.map(customerService.update(customerRequestDTO, id), CustomerUpdateResponseDTO.class));
    }
}
