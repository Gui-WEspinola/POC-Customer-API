package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponseDTO;
import io.GuiWEspinola.poc1.entities.dto.response.MainAddressUpdateResponseDTO;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.CREATED;

@RestController
@AllArgsConstructor
@RequestMapping(path = "poc1/api/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    public ResponseEntity<AddressResponseDTO> createAddress(@RequestBody AddressRequestDTO addressRequestDTO) {
        return ResponseEntity.status(CREATED).body(mapper.map(
                addressService.save(addressRequestDTO), AddressResponseDTO.class));
    }

    @GetMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> getAddressById(@PathVariable Long id) {
        return ResponseEntity.ok().body(mapper.map(addressService.findById(id), AddressResponseDTO.class));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
        return ResponseEntity.noContent().build();
    }

    @PutMapping("/{id}")
    public ResponseEntity<AddressResponseDTO> updateAddress(@PathVariable Long id,
                                                     @RequestBody AddressRequestDTO addressRequestDTO) {
        return ResponseEntity.accepted()
                .body(mapper.map(addressService.update(addressRequestDTO, id), AddressResponseDTO.class));
    }

    @PatchMapping("/main-address/{id}")
    public ResponseEntity<MainAddressUpdateResponseDTO> updateMainAddress (@PathVariable Long id) {
        return ResponseEntity.accepted()
                .body(mapper.map(addressService.updateMainAddress(id), MainAddressUpdateResponseDTO.class));
    }
}
