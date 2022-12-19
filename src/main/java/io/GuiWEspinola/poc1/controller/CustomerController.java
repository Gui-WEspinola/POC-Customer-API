package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequest;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerUpdateRequest;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponse;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerResponse;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerUpdateResponse;
import io.GuiWEspinola.poc1.service.CustomerService;
import jakarta.validation.Valid;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
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
    public Page<CustomerResponse> getCustomers(
            @PageableDefault(size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable,
            @RequestParam(required = false) String name) {

        if (name != null) {
            Page<Customer> page = customerService.findByCustomerName(name, pageable);
            return page.map(CustomerResponse::new);
        } else {
            Page<Customer> page = customerService.findAll(pageable);
            return page.map(CustomerResponse::new);
        }
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public CustomerResponse getCustomerById(@PathVariable Long id) {
        return mapper.map(customerService.findById(id), CustomerResponse.class);
    }

    @GetMapping("/search")
    @ResponseStatus(OK)
    public Page<CustomerResponse> searchCustomerByName(
            @RequestParam String name,
            @PageableDefault(size = 10, direction = Sort.Direction.ASC, sort = "id") Pageable pageable) {

        var page = customerService.findCustomerNameContaining(name, pageable);
        return page.map(customer -> mapper.map(customer, CustomerResponse.class));
    }

    @GetMapping("/addresses/{id}")
    @ResponseStatus(OK)
    public List<AddressResponse> getAllAddressesByCustomer(@PathVariable Long id) {
        return customerService.getAllAddresses(id);
    }

    @PostMapping
    @ResponseStatus(CREATED)
    public CustomerResponse postCustomer(@RequestBody @Valid CustomerRequest customerRequest) {
        return mapper.map(customerService.save(customerRequest), CustomerResponse.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteCustomerById(@PathVariable Long id) {
        customerService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(OK)
    public CustomerUpdateResponse updateCustomer
            (@PathVariable Long id, @RequestBody @Valid CustomerUpdateRequest customerRequestDTO) {
        return mapper.map(customerService.update(customerRequestDTO, id), CustomerUpdateResponse.class);
    }
}
