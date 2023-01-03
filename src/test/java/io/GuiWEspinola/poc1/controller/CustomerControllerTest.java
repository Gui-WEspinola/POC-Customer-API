package io.GuiWEspinola.poc1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequest;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerResponse;
import io.GuiWEspinola.poc1.enums.DocumentType;
import io.GuiWEspinola.poc1.exception.CustomerNotFoundException;
import io.GuiWEspinola.poc1.service.impl.CustomerServiceImpl;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.BDDMockito;
import org.mockito.Mockito;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.request.MockHttpServletRequestBuilder;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
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

    static String CUSTOMER_API = "/poc1/api/customers";

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

    @Test
    @DisplayName("Should successfully create a customer with a valid CPF")
    void testPostCustomerCPF() throws Exception {
        // Set up mock behavior
        BDDMockito.given(customerService.save(Mockito.any(CustomerRequest.class))).willReturn(savedCustomer);
        BDDMockito.given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);

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
        BDDMockito.given(customerService.save(Mockito.any(CustomerRequest.class))).willReturn(savedCustomer);
        BDDMockito.given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);
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
    @DisplayName("Should throw a list of errors when failing to create a Customer with empty fields")
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

        BDDMockito.given(customerService.findById(savedCustomer.getId())).willReturn(savedCustomer);
        BDDMockito.given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API + "/{id}", savedCustomer.getId())
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isOk())
                .andExpect(jsonPath("id").value(expectedResponse.getId()))
                .andExpect(jsonPath("name").value(expectedResponse.getName()))
                .andExpect(jsonPath("email").value(expectedResponse.getEmail()))
                .andExpect(jsonPath("documentType").value(expectedResponse.getDocumentType().toString()))
                .andExpect(jsonPath("documentNumber").value(expectedResponse.getDocumentNumber()))
                .andExpect(jsonPath("mobileNumber").value(expectedResponse.getMobileNumber()));
    }

    @Test
    @DisplayName("Should return 404 when customer is not found")
    void testGetCustomerNotFoundOnFindByIdMethod() throws Exception {

        BDDMockito.given(customerService.findById(Mockito.anyLong())).willThrow(new CustomerNotFoundException(10L));

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .get(CUSTOMER_API + "/{id}", 10L)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNotFound());
    }

    @Test
    @DisplayName("Should delete a Customer successfully")
    void testDeleteCustomer() throws Exception {

        BDDMockito.given(customerService.findById(Mockito.anyLong())).willReturn(savedCustomer);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CUSTOMER_API + "/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }

    @Test
    @DisplayName("Should throw 404 when failing a Customer successfully")
    void testFailToDeleteCustomer() throws Exception {

        BDDMockito.given(customerService.findById(Mockito.anyLong())).willReturn(savedCustomer);

        MockHttpServletRequestBuilder request = MockMvcRequestBuilders
                .delete(CUSTOMER_API + "/{id}", 1L)
                .accept(MediaType.APPLICATION_JSON);

        mockMvc.perform(request)
                .andExpect(status().isNoContent());
    }
}