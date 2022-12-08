package io.GuiWEspinola.poc1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.GuiWEspinola.poc1.enums.PersonType;
import jakarta.persistence.*;
import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@NoArgsConstructor
@AllArgsConstructor
@Table(name = "tb_customer")
public class Customer {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "customer_name")
    private String name;

    @Column
    @Email(message = "The e-mail you entered is either invalid or taken.")
    private String email;

    @Column
    private PersonType documentType;

    //    @CPF(message = "Please enter a valid CPF.")
//    @CNPJ(message = "Please enter a valid CNPJ.")
    private String documentNumber;

    @Column(length = 15)
    @NotNull(message = "Please enter a phone number.")
    private Integer mobileNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    @JsonIgnore
    private List<Address> address = new ArrayList<>();
}
