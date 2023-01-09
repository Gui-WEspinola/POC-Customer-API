package io.GuiWEspinola.poc1.service.impl;

import io.GuiWEspinola.poc1.entities.Address;
import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequest;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerUpdateRequest;
import io.GuiWEspinola.poc1.entities.dto.response.CustomerUpdateResponse;
import io.GuiWEspinola.poc1.enums.DocumentType;
import io.GuiWEspinola.poc1.exception.CustomerNotFoundException;
import io.GuiWEspinola.poc1.exception.DocumentInUseException;
import io.GuiWEspinola.poc1.exception.ExistingEmailException;
import io.GuiWEspinola.poc1.repository.CustomerRepository;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.MockitoAnnotations;
import org.modelmapper.ModelMapper;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyLong;
import static org.mockito.Mockito.when;

class CustomerServiceImplTest {

    private static final long ID = 1L;

    private static final String NAME = "joao silva";
    private static final String EMAIL = "joao@gmail.com";
    private static final String DOCUMENT_NUMBER = "09716913478";
    private static final String MOBILE_NUMBER = "987654321";
    @Mock
    private CustomerRepository customerRepository;

    @InjectMocks
    private CustomerServiceImpl customerService;

    @Mock
    private ModelMapper mapper;

    private Customer customerCPF;

    private Customer customerCNPJ;

    private CustomerRequest requestDtoCPF;

    private CustomerRequest requestDtoCNPJ;

    private Optional<Customer> customerOptional;

    @BeforeEach
    void setUp() {
        MockitoAnnotations.openMocks(this);
        startCustomer();
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void findAll() {

    }

    @Test
    void findByCustomerName() {

    }

    @Test
    void findCustomerNameContaining() {

    }

    @Test
    @DisplayName("Find by ID and return a Customer Object")
    void testFindByIdAndReturnCustomerObject() {
        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

        Customer response = customerService.findById(ID);

        assertEquals(Customer.class, response.getClass());
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
    }

    @Test
    @DisplayName("Find by ID and throw a CustomerNotFoundException")
    void testFindByIdAndThrowCustomerNotFoundException() {
        when(customerRepository.findById(anyLong())).thenThrow(new CustomerNotFoundException(ID));

        try {
            customerService.findById(ID);
        } catch (CustomerNotFoundException e) {
            assertEquals(CustomerNotFoundException.class, e.getClass());
            assertEquals("Customer with ID '1' does not exist!", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should create a customer")
    void testCreateCustomerSuccessfully() {
        when(customerRepository.save(any())).thenReturn(customerCPF);

        Customer response = customerService.save(requestDtoCPF);

        assertNotNull(response);
        assertEquals(ID, response.getId());
        assertEquals(NAME, response.getName());
        assertEquals(EMAIL, response.getEmail());
        assertEquals(DOCUMENT_NUMBER, response.getDocumentNumber());
        assertEquals(DocumentType.CPF, response.getDocumentType());
    }

    @Test
    void testThrowExceptionWhenCreatingCustomer() {

    }

    @Test
    void delete() {
    }

    @Test
    void update() {
    }

    @Test
    @DisplayName("should return a list of addresses by Customer ID")
    void getAllAddresses() {
        List<Address> addresses = List.of(new Address(ID, "rua nova", "60", "apt 205",
                "centro", "recife", "1234567", "PB", true, customerCPF));

        customerCPF.setAddress(addresses);

        when(customerRepository.findById(any())).thenReturn(Optional.of(customerCPF));

        List<Address> expectedResult = customerService.getAllAddresses(ID);

        assertEquals(addresses, expectedResult);
        assertEquals(addresses.size(), expectedResult.size());
        assertTrue(expectedResult.stream().allMatch(a -> a != null));
    }

    @Test
    @DisplayName("Should throw ExistingEmailException when email is unavailable")
    void testChecksAvailableEmail() {
        when(customerRepository.existsByEmail(any())).thenReturn(true);

        try {
            customerService.checksAvailableEmail("gui@gmail.com");
        } catch (ExistingEmailException e) {
            assertEquals(ExistingEmailException.class, e.getClass());
            assertEquals("The email 'gui@gmail.com' is already in use.", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should throw DocumentInUseException when documentNumber is already in use")
    void testChecksDocumentNumber() {
        when(customerRepository.existsByDocumentNumber(any())).thenReturn(true);

        try {
            customerService.checksDocumentNumber("097.169.134-78");
        } catch (DocumentInUseException e) {
            assertEquals(DocumentInUseException.class, e.getClass());
            assertEquals("The following document is already in use: '097.169.134-78'", e.getMessage());
        }
    }

    @Test
    @DisplayName("Should update a customer with new information")
    void testCustomerUpdate() {
        CustomerUpdateRequest updateRequest = new CustomerUpdateRequest
                ("joão", "joao@gmail.com", "993347877");

        customerCPF.setName(updateRequest.getName());
        customerCPF.setEmail(updateRequest.getEmail());
        customerCPF.setMobileNumber(updateRequest.getMobileNumber());

        when(customerRepository.save(any(Customer.class))).thenReturn(customerCPF);
        when(customerRepository.findById(anyLong())).thenReturn(customerOptional);

        customerService.update(updateRequest, ID);

        assertEquals(customerCPF.getName(), updateRequest.getName());
        assertEquals(customerCPF.getEmail(), updateRequest.getEmail());
        assertEquals(customerCPF.getMobileNumber(), updateRequest.getMobileNumber());
    }

    private void startCustomer() {
        customerCPF = new Customer
                (ID, NAME, EMAIL, DocumentType.CPF, DOCUMENT_NUMBER, MOBILE_NUMBER, new ArrayList<>());

        requestDtoCPF = new CustomerRequest
                (NAME, EMAIL, DocumentType.CPF, DOCUMENT_NUMBER, MOBILE_NUMBER);

        requestDtoCPF = new CustomerRequest
                (NAME, EMAIL, DocumentType.CNPJ, DOCUMENT_NUMBER, MOBILE_NUMBER);

        customerOptional = Optional.of(new Customer
                (ID, NAME, EMAIL, DocumentType.CPF, DOCUMENT_NUMBER, MOBILE_NUMBER, new ArrayList<>()));
    }
}