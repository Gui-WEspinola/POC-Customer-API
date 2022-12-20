package io.GuiWEspinola.poc1.service;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequest;
import org.springframework.stereotype.Service;

@Service
public interface AddressService {

    Address save(AddressRequest addressRequest);

    Address findById(Long id);

    void delete(Long id);

    Address update(AddressRequest addressRequest, Long id);

    Address updateMainAddress(Long id);

    void checksMaximumAddressLimit(Customer customer);

}
