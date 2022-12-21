package io.GuiWEspinola.poc1.controller;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;

import static org.junit.jupiter.api.Assertions.*;

@ExtendWith(SpringExtension.class)
@WebMvcTest // utilizado para testes unitários
@AutoConfigureMockMvc // Spring configura automaticamente nossos testes
class CustomerControllerTest {

    @Autowired
    MockMvc mvc;

    @BeforeEach
    void setUp() {
    }

    @AfterEach
    void tearDown() {
    }

    @Test
    void getCustomers() {
    }

    @Test
    void getCustomerById() {
    }

    @Test
    void searchCustomerByName() {
    }

    @Test
    void getAllAddressesByCustomer() {
    }

    @Test
    @DisplayName("Must successfully create a Customer")
    void postCustomerTest() {

    }

    @Test
    void deleteCustomerById() {
    }

    @Test
    void updateCustomer() {
    }
}