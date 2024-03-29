package io.GuiWEspinola.poc1.controller;

import io.GuiWEspinola.poc1.entities.dto.request.AddressRequest;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponse;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import lombok.AllArgsConstructor;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

import static org.springframework.http.HttpStatus.*;

@RestController
@AllArgsConstructor
@RequestMapping(path = "api/poc1/addresses")
public class AddressController {

    @Autowired
    private AddressService addressService;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    @PostMapping
    @ResponseStatus(CREATED)
    public AddressResponse createAddress(@RequestBody @Valid AddressRequest addressRequest) {
        return mapper.map(addressService.save(addressRequest), AddressResponse.class);
    }

    @GetMapping("/{id}")
    @ResponseStatus(OK)
    public AddressResponse getAddressById(@PathVariable Long id) {
        return mapper.map(addressService.findById(id), AddressResponse.class);
    }

    @DeleteMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public void deleteAddress(@PathVariable Long id) {
        addressService.delete(id);
    }

    @PutMapping("/{id}")
    @ResponseStatus(NO_CONTENT)
    public AddressResponse updateAddress(@PathVariable Long id,
                                         @RequestBody @Valid AddressRequest addressRequest) {
        return mapper.map(addressService.update(addressRequest, id), AddressResponse.class);
    }

    @PatchMapping("/main-address/{id}")
    @ResponseStatus(NO_CONTENT)
    public AddressResponse updateMainAddress(@PathVariable Long id) {
        return mapper.map(addressService.updateMainAddress(id), AddressResponse.class);
    }
}
