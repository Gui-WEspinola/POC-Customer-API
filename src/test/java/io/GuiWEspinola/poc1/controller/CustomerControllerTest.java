package io.GuiWEspinola.poc1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequest;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerUpdateRequest;
import io.GuiWEspinola.poc1.entities.dto.response.AddressResponse;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerResponse;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerUpdateResponse;
import io.GuiWEspinola.poc1.enums.DocumentType;
import io.GuiWEspinola.poc1.exception.CustomerNotFoundException;
import io.GuiWEspinola.poc1.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.BDDMockito.given;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.jsonPath;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;

@ExtendWith(SpringExtension.class)
@WebMvcTest(controllers = CustomerController.class)
@AutoConfigureMockMvc
class CustomerControllerTest {

    @Autowired
    MockMvc mockMvc;

    @MockBean
    CustomerServiceImpl customerService;

    @MockBean
    ModelMapper mapper;

    static String CUSTOMER_API = "/api/poc1/customers";

    Customer savedCustomer = Customer.builder()
            .id(1L)
            .name("guilherme")
            .email("gui@email.com")
            .documentType(DocumentType.CPF)
            .documentNumber("097.169.134-78")
            .mobileNumber("83993347877")
            .address(new ArrayList<>()).build();

    CustomerResponse expectedResponse = CustomerResponse.builder()
            .id(1L)
            .name("guilherme")
            .email("gui@email.com")
            .documentType(DocumentType.CPF)
            .documentNumber("097.169.134-78")
            .mobileNumber("83993347877").build();

    CustomerRequest customerRequestCPF = CustomerRequest.builder()
            .name("guilherme")
            .email("gui@email.com")
            .documentType(DocumentType.CPF)
            .documentNumber("097.169.134-78")
            .mobileNumber("83993347877").build();

    CustomerRequest customerRequestCNPJ = CustomerRequest.builder()
            .name("guilherme")
            .email("gui@email.com")
            .documentType(DocumentType.CNPJ)
            .documentNumber("25.229.847/0001-73")
            .mobileNumber("83993347877").build();

    private static final Long ID = 1L;

    @Test
    @DisplayName("Should successfully create a customer with a valid CPF")
    void testPostCustomerCPF() throws Exception {
        // Set up mock behavior
        given(customerService.save(any(CustomerRequest.class))).willReturn(savedCustomer);
        given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);

        // Convert test data to JSON
        String json = new ObjectMapper().writeValueAsString(customerRequestCPF);

        // Send POST request to the controller
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .post(CUSTOMER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        // Convert response to an object
        CustomerResponse actualResponse =
                new ObjectMapper().readValue(result.getResponse().getContentAsString(), CustomerResponse.class);

        // Verify that the response is correct
        assertThat(actualResponse.getId()).isEqualTo(expectedResponse.getId());
        assertThat(actualResponse.getName()).isEqualTo(expectedResponse.getName());
        assertThat(actualResponse.getEmail()).isEqualTo(expectedResponse.getEmail());
        assertThat(actualResponse.getDocumentNumber()).isEqualTo(expectedResponse.getDocumentNumber());
        assertThat(actualResponse.getDocumentType()).isEqualTo(expectedResponse.getDocumentType());
        assertThat(actualResponse.getMobileNumber()).isEqualTo(expectedResponse.getMobileNumber());
    }

    @Test
    @DisplayName("Should successfully create a customer with a valid CNPJ")
    void testPostCustomerCNPJ() throws Exception {
        // Set up mock behavior
        given(customerService.save(any(CustomerRequest.class))).willReturn(savedCustomer);
        given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);
        // Convert test data to JSON
        String json = new ObjectMapper().writeValueAsString(customerRequestCNPJ);
        // Send POST request to the controller
        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("$.documentType").value(expectedResponse.getDocumentType().toString()))
                .andExpect(jsonPath("$.documentNumber").value(expectedResponse.getDocumentNumber()))
                .andExpect(jsonPath("$.mobileNumber").value(expectedResponse.getMobileNumber()));
    }

    @Test
    @DisplayName("Should throw a list of errors when trying to create a Customer with empty fields")
    void testPostCustomerAndThrowException() throws Exception {

        String json = new ObjectMapper().writeValueAsString(new CustomerRequest());

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .post(CUSTOMER_API)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isBadRequest())
                .andExpect(jsonPath("$[0].timestamp").isNotEmpty())
                .andExpect(jsonPath("$[0].message").isNotEmpty())
                .andExpect(jsonPath("$[0].path").isNotEmpty())
                .andExpect(jsonPath("$[0].httpStatus").isNotEmpty())
                .andExpect(jsonPath("$[4]").isNotEmpty())
                .andExpect(jsonPath("$[5]").doesNotExist());
    }

    @Test
    @DisplayName("Should return a Customer successfully")
    void testGetCustomer() throws Exception {

        given(customerService.findById(savedCustomer.getId())).willReturn(savedCustomer);
        given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API + "/{id}", savedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(expectedResponse.getId()))
                .andExpect(jsonPath("name").value(expectedResponse.getName()))
                .andExpect(jsonPath("email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("documentType").value(expectedResponse.getDocumentType().toString()))
                .andExpect(jsonPath("documentNumber").value(expectedResponse.getDocumentNumber())) //TODO change return to mask DocumentNumber
                .andExpect(jsonPath("mobileNumber").value(expectedResponse.getMobileNumber()));
    }

    @Test
    @DisplayName("Should return 404 when customer is not found")
    void testGetCustomerNotFoundOnFindByIdMethod() throws Exception {

        given(customerService.findById(Mockito.anyLong())).willThrow(new CustomerNotFoundException(10L));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API + "/{id}", 10L)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete a Customer successfully")
    void testDeleteCustomer() throws Exception {

        given(customerService.findById(Mockito.anyLong())).willReturn(savedCustomer);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CUSTOMER_API + "/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should update a Customer successfully")
    void testUpdateCustomer() throws Exception {

        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest("joao silva", "joao@email", "987654321");
        CustomerUpdateResponse updatedResponse = new CustomerUpdateResponse("joao silva", "joao@email", "987654321");

        String json = new ObjectMapper().writeValueAsString(updateRequest);

        Customer updatedCustomer = Customer.builder()
                .id(savedCustomer.getId())
                .name(updateRequest.getName())
                .email(updateRequest.getEmail())
                .address(savedCustomer.getAddress())
                .documentType(savedCustomer.getDocumentType())
                .documentNumber(savedCustomer.getDocumentNumber())
                .mobileNumber(updateRequest.getMobileNumber()).build();

        given(customerService.update(updateRequest, 1L)).willReturn(updatedCustomer);
        given(mapper.map(updatedCustomer, CustomerUpdateResponse.class)).willReturn(updatedResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .put(CUSTOMER_API + "/{id}", 1L)
                .contentType(MediaType.APPLICATION_JSON)
                .accept(MediaType.APPLICATION_JSON)
                .content(json);

        mockMvc.perform(request)
                .andExpect(status().isNoContent())
                .andExpect(jsonPath("name").value("joao silva"))
                .andExpect(jsonPath("email").value("joao@email"))
                .andExpect(jsonPath("mobileNumber").value("987654321"));
    }

    @Test
    @DisplayName("Should return a page of customers successfully")
    void testGetCustomers() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Customer> customerList = List.of(savedCustomer);
        Page<Customer> page = new PageImpl<Customer>(customerList, pageRequest, 1);

        given(customerService.findAll(any(Pageable.class))).willReturn(page);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API)
                .param("page", "0")
                .param("size", "10")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.content[0].email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("$.content[0].documentType").value(expectedResponse.getDocumentType().toString()))
                .andExpect(jsonPath("$.content[0].documentNumber").value(expectedResponse.getDocumentType().getMask())) //TODO change return to mask DocumentNumber
                .andExpect(jsonPath("$.content[0].mobileNumber").value(expectedResponse.getMobileNumber()));
    }

    @Test
    @DisplayName("Should filter customers by exact name")
    void testFilterCustomerByName() throws Exception { //TODO CONSERTAR ESSA COISA

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Customer> customerList = List.of(savedCustomer);
        Page<Customer> page = new PageImpl<Customer>(customerList, pageRequest, 1);

        given(customerService.findByCustomerNameLike("guilherme", pageRequest)).willReturn(page);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API)
                .param("page", "0")
                .param("size", "10")
                .param("name", "guilherme")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.content[0].email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("$.content[0].documentType").value(expectedResponse.getDocumentType().toString()))
                .andExpect(jsonPath("$.content[0].documentNumber").value(expectedResponse.getDocumentType().getMask())) //TODO change return to mask DocumentNumber
                .andExpect(jsonPath("$.content[0].mobileNumber").value(expectedResponse.getMobileNumber()));
    }

    @Test
    @DisplayName("Should return empty pages of Customers on getCustomers")
    void testEmptyPageOfCustomers() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Customer> emptyList = new ArrayList<>();
        Page<Customer> page = new PageImpl<>(Collections.emptyList(), pageRequest, 1);

        given(customerService.findByCustomerNameLike("joao", pageRequest)).willReturn(page);
        given(customerService.findAll(pageRequest)).willReturn(page);

        mockMvc.perform(MockMvcRequestBuilders
                        .get(CUSTOMER_API)
                        .param("page", "0")
                        .param("size", "10")
                        .param("name", "joao")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content").isEmpty())
                .andExpect(jsonPath("sort.empty").value(true))
                .andReturn();

        mockMvc.perform(MockMvcRequestBuilders
                        .get(CUSTOMER_API)
                        .param("page", "0")
                        .param("size", "10")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(jsonPath("content").isEmpty())
                .andExpect(jsonPath("empty").value(true))
                .andReturn();
    }

    @Test
    @DisplayName("Should return pages of Customers containing name query")
    void testReturnPageOfCustomerByNameContaining() throws Exception {

        PageRequest pageRequest = PageRequest.of(0, 10);
        List<Customer> customerList = List.of(savedCustomer);
        Page<Customer> page = new PageImpl<>(customerList, pageRequest, 1);

        given(customerService.findCustomerNameContaining("gui", pageRequest)).willReturn(page);
        given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API.concat("/search"))
                .param("page", "0")
                .param("size", "10")
                .param("name", "gui")
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.content[0].id").value(expectedResponse.getId()))
                .andExpect(jsonPath("$.content[0].name").value(expectedResponse.getName()))
                .andExpect(jsonPath("$.content[0].email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("$.content[0].documentType").value(expectedResponse.getDocumentType().toString()))
                .andExpect(jsonPath("$.content[0].documentNumber").value(expectedResponse.getDocumentNumber())) //TODO change return to mask DocumentNumber
                .andExpect(jsonPath("$.content[0].mobileNumber").value(expectedResponse.getMobileNumber()));
    }

    @Test
    @DisplayName("Should return a list of address of a specified customer")
    void testReturnListOfAddressesByCustomerId() throws Exception {

        Address address1 = new Address(1L, "rua nova", "100", "", "centro",
                "joao pessoa", "58037-605", "PB", false, savedCustomer);

        Address address2 = new Address(2L, "rua nova", "200", "", "centro",
                "joao pessoa", "58037-605", "PB", true, savedCustomer);

        AddressResponse addressResponse1 = new AddressResponse
                (1L, "rua nova", "100", "", "centro",
                        "joao pessoa", "58037-605", "PB", false);

        AddressResponse addressResponse2 = new AddressResponse
                (1L, "rua nova", "200", "", "centro",
                        "joao pessoa", "58037-605", "PB", false);

        List<Address> addressList = new ArrayList<>();
        addressList.add(address1);
        addressList.add(address2);
        List<AddressResponse> addressResponses = new ArrayList<>();
        addressResponses.add(addressResponse1);
        addressResponses.add(addressResponse2);

        savedCustomer.setAddress(addressList);

        given(customerService.getAllAddresses(anyLong())).willReturn(addressList);
        given(mapper.map(addressList.get(0), AddressResponse.class)).willReturn(addressResponses.get(0));
        given(mapper.map(addressList.get(1), AddressResponse.class)).willReturn(addressResponses.get(1));

        MvcResult result = mockMvc.perform(MockMvcRequestBuilders
                        .get(CUSTOMER_API + "/addresses/{id}", ID)
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andExpect(jsonPath("[0]").isNotEmpty())
                .andExpect(jsonPath("[1]").isNotEmpty())
                .andReturn();
    }
}