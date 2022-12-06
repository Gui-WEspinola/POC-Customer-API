package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponseDTO;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(path = "poc1-api/address")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody AddressRequestDTO addressRequestDTO) {
        var customer = customerService.findById(addressRequestDTO.getCustomerRequestDTO().getId());
        if (customer.getAddress().isEmpty()) {
            addressRequestDTO.setMainAddress(true);
        }
        return ResponseEntity.status(CREATED).body(mapper.map(
                addressService.save(addressRequestDTO), AddressResponseDTO.class));
    } // TODO Fazer a separação de customer do AddressService e do CustomerController

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(addressService.findById(id), AddressResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(HttpStatus.NO_CONTENT)
    public void deleteAddressById(@PathVariable Long id) {
        addressService.delete(id);
    }

    // TODO PUT mapping


}
