package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.dto.request.AddressRequestDTO;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponseDTO;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import static org.springframework.http.HttpStatus.*;

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
    @ResponseStatus(CREATED)
    public AddressResponseDTO createAddress(@RequestBody AddressRequestDTO addressRequestDTO) {
        return mapper.map(addressService.save(addressRequestDTO), AddressResponseDTO.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AddressResponseDTO getAddressById(@PathVariable Long id) {
        return mapper.map(addressService.findById(id), AddressResponseDTO.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(ACCEPTED)
    public AddressResponseDTO updateAddress(@PathVariable Long id,
                                            @RequestBody AddressRequestDTO addressRequestDTO) {
        return mapper.map(addressService.update(addressRequestDTO, id), AddressResponseDTO.class);
    }

    @PatchMapping("/main-address/{id}")
    @ResponseStatus(ACCEPTED)
    public AddressResponseDTO updateMainAddress(@PathVariable Long id) {
        return mapper.map(addressService.updateMainAddress(id), AddressResponseDTO.class);
    }
}
