package io.GuiWEspinola.poc1.validation;

import io.GuiWEspinola.poc1.entities.Customer;
import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequestDTO;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class CustomerSequenceProvider implements DefaultGroupSequenceProvider<CustomerRequestDTO> {

    @Override
    public List<Class<?>> getValidationGroups(CustomerRequestDTO customer) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(CustomerRequestDTO.class);

        if (isCustomerSelected(customer)) {
            groups.add(customer.getDocumentType().getGroup());
        }

        return groups;
    }

    protected boolean isCustomerSelected(CustomerRequestDTO customer){
        return customer != null && customer.getDocumentType() != null;
    }
}
