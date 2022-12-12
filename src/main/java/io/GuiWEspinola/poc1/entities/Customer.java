package io.GuiWEspinola.poc1.entities;

import com.fasterxml.jackson.annotation.JsonIgnore;
import io.GuiWEspinola.poc1.enums.DocumentType;
import io.GuiWEspinola.poc1.validation.CustomerSequenceProvider;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.validator.group.GroupSequenceProvider;

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
    private String email;

    @Column
    private DocumentType documentType;

    @Column
    private String documentNumber;

    @Column(length = 15)
    private Integer mobileNumber;

    @JsonIgnore
    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();
}
