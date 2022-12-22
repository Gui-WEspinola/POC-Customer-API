package io.GuiWEspinola.poc1.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequest;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerResponse;
import io.GuiWEspinola.poc1.enums.DocumentType;
import io.GuiWEspinola.poc1.service.impl.CustomerServiceImpl;
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
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;

import java.util.ArrayList;

import static org.assertj.core.api.Assertions.assertThat;
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

    @Test
    void testPostCustomer() throws Exception {
        // Set up test data
        CustomerRequest dto = CustomerRequest.builder()
                .mobileNumber("83993347877")
                .documentNumber("097.169.134-78")
                .name("guilherme")
                .documentType(DocumentType.CPF)
                .email("gui@email.com").build();

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

        // Set up mock behavior
        BDDMockito.given(customerService.save(Mockito.any(CustomerRequest.class))).willReturn(savedCustomer);
        BDDMockito.given(mapper.map(savedCustomer, CustomerResponse.class)).willReturn(expectedResponse);

        // Convert test data to JSON
        String json = new ObjectMapper().writeValueAsString(dto);

        // Send POST request to the controller
        MvcResult result = mockMvc.perform(MockMvcRequestBuilders.post(CUSTOMER_API)
                        .contentType(MediaType.APPLICATION_JSON)
                        .accept(MediaType.APPLICATION_JSON)
                        .content(json))
                .andExpect(status().isCreated())
                .andReturn();

        // Convert response to an object
        CustomerResponse actualResponse =
                new ObjectMapper().readValue(result.getResponse().getContentAsString(), CustomerResponse.class);

//        mockMvc.perform(result)
//                .andExpect(status().isCreated())
//                .andExpect(jsonPath("id").value(savedCustomer.getId()))
//                .andExpect(jsonPath("name").value(savedCustomer.getName()))
//                .andExpect(jsonPath("email").value(savedCustomer.getEmail()))
//                .andExpect(jsonPath("documentType").value(savedCustomer.getDocumentType().toString()))
//                .andExpect(jsonPath("documentNumber").value(savedCustomer.getDocumentNumber()))
//                .andExpect(jsonPath("mobileNumber").value(savedCustomer.getMobileNumber()));

        // Verify that the response is correct
        assertThat(actualResponse.getName()).isEqualTo(expectedResponse.getName());
        assertThat(actualResponse.getEmail()).isEqualTo(expectedResponse.getEmail());
        assertThat(actualResponse.getDocumentNumber()).isEqualTo(expectedResponse.getDocumentNumber());
        assertThat(actualResponse.getDocumentType()).isEqualTo(expectedResponse.getDocumentType());
        assertThat(actualResponse.getDocumentNumber()).isEqualTo(expectedResponse.getDocumentNumber());
    }
}
