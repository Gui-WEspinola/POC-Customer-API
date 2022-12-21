package io.GuiWEspinola.poc1.entities;

import io.GuiWEspinola.poc1.enums.DocumentType;
import lombok.Data;

import javax.persistence.*;
import java.io.Serializable;
import java.util.ArrayList;
import java.util.List;

@Entity
@Data
@Table(name = "tb_customer")
public class Customer implements Serializable {

    private static final long serialVersionUID = 1L;

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
    private String mobileNumber;

    @OneToMany(mappedBy = "customer", fetch = FetchType.EAGER, cascade = CascadeType.ALL)
    private List<Address> address = new ArrayList<>();
}
