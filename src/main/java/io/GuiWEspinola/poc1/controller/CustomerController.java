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

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "poc1/api/customers")
public class CustomerController {

    private final CustomerService customerService;

    private final ModelMapper mapper;

    @GetMapping
    @ResponseStatus(OK)
    public Page<CustomerResponseDTO> getCustomers(
            @PageableDefault(size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable,
            @RequestParam(required = false) String name) {

        if (name != null) {
            Page<Customer> page = customerService.findByCustomerName(name, pageable);
            return page.map(CustomerResponseDTO::new);
        } else {
            Page<Customer> page = customerService.findAll(pageable);
            return page.map(CustomerResponseDTO::new);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public CustomerResponseDTO getCustomerById(@PathVariable Long id) {
        return mapper.map(customerService.findById(id), CustomerResponseDTO.class);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<CustomerResponseDTO> searchCustomerByName(
            @RequestParam String name,
            @PageableDefault(size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {

        var page = customerService.findCustomerNameContaining(name, pageable);
        return page.map(customer -> mapper.map(customer, CustomerResponseDTO.class));
    }

    @GetMapping("/addresses/{id}")
    @ResponseStatus(OK)
    public List<AddressResponseDTO> getAllAddressesByCustomer(@PathVariable Long id) {
        return customerService.getAllAddresses(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CustomerResponseDTO postCustomer(@RequestBody @Valid CustomerRequestDTO customerRequestDTO) {
        return mapper.map(customerService.save(customerRequestDTO), CustomerResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public CustomerUpdateResponseDTO updateCustomer
            (@PathVariable Long id, @RequestBody @Valid CustomerUpdateRequestDTO customerRequestDTO) {
        return mapper.map(customerService.update(customerRequestDTO, id), CustomerUpdateResponseDTO.class);
    }
}
