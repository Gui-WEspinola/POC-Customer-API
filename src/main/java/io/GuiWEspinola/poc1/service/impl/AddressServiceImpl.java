package io.GuiWEspinola.poc1.service.impl;

import com.google.gson.Gson;
import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.AddressRequest;
import io.GuiWEspinola.poc1.entities.dto.response.ViaCepResponse;
import io.GuiWEspinola.poc1.exception.AddressMaxLimitException;
import io.GuiWEspinola.poc1.exception.AddressNotFoundException;
import io.GuiWEspinola.poc1.exception.MainAddressDeleteException;
import io.GuiWEspinola.poc1.exception.ZipCodeNotFoundException;
import io.GuiWEspinola.poc1.repository.AddressRepository;
import io.GuiWEspinola.poc1.service.AddressService;
import io.GuiWEspinola.poc1.service.CustomerService;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.web.client.RestTemplateBuilder;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.client.RestTemplate;

@Service
public class AddressServiceImpl implements AddressService {

    @Autowired
    private AddressRepository addressRepository;

    @Autowired
    private CustomerService customerService;

    @Autowired
    private ModelMapper mapper;

    private static final String VIACEP_URL = "https://viacep.com.br/ws/{zipcode}/json/";

    private final RestTemplate restTemplate;

    private final Gson gson;

    @Autowired
    public AddressServiceImpl (RestTemplateBuilder restTemplateBuilder) {
        this.restTemplate = restTemplateBuilder.build();
        this.gson = new Gson();
    }

    @Override
    @Transactional
    public Address save(AddressRequest addressRequest) {
        var customer = customerService.findById(addressRequest.getCustomerId());
        Address address = mapper.map(addressRequest, Address.class);

        ResponseEntity<String> response = restTemplate.getForEntity(VIACEP_URL, String.class, addressRequest.getZipCode());

            if (response.getStatusCode().is2xxSuccessful()) {
                String responseBody = response.getBody();

                ViaCepResponse viaCepResponse = gson.fromJson(responseBody, ViaCepResponse.class);

                address.setStreet(viaCepResponse.getLogradouro());
                address.setDistrict(viaCepResponse.getBairro());
                address.setCity(viaCepResponse.getLocalidade());
                address.setState(viaCepResponse.getUf());
                address.setMainAddress(customer.getAddress().isEmpty());

                checksMaximumAddressLimit(customer);


            } else if (response.getStatusCode().is4xxClientError()){
                throw new ZipCodeNotFoundException(addressRequest.getZipCode());
            }

        return addressRepository.save(address);
    }

    @Override
    public Address findById(Long id) {
        return addressRepository.findById(id).orElseThrow(() -> new AddressNotFoundException(id));
    }

    @Override
    @Transactional
    public Address update(AddressRequest addressRequest, Long id) {
        Address address = findById(id);

//        address.setAddressNumber(addressRequest.getAddressNumber());
//        address.setDistrict(addressRequest.getDistrict());
//        address.setState(addressRequest.getState());
//        address.setStreet(addressRequest.getStreet());
//        address.setZipCode(addressRequest.getZipCode());
//        address.setCity(addressRequest.getCity());

        return addressRepository.save(address);
    }

    @Override
    public Address updateMainAddress(Long id) {
        Address address = findById(id);

        address.getCustomer().getAddress()
                .forEach(a -> {
                    a.setMainAddress(false);
                    addressRepository.save(a);
                });

        address.setMainAddress(true);
        return addressRepository.save(address);
    }

    @Override
    @Transactional
    public void delete(Long id) {
        if (Boolean.TRUE.equals(findById(id).getMainAddress())) {
            throw new MainAddressDeleteException();
        }
        addressRepository.delete(findById(id));
    }

    public void checksMaximumAddressLimit(Customer customer) {
        if (customer.getAddress().size() >= 5) {
            throw new AddressMaxLimitException(customer.getId());
        }
    }
}
