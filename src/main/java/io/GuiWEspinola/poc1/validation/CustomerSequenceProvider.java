package io.GuiWEspinola.poc1.validation;

import io.GuiWEspinola.poc1.entities.dto.request.CustomerRequest;
import org.hibernate.validator.spi.group.DefaultGroupSequenceProvider;

import java.util.ArrayList;
import java.util.List;

public class CustomerSequenceProvider implements DefaultGroupSequenceProvider<CustomerRequest> {

    @Override
    public List<Class<?>> getValidationGroups(CustomerRequest customer) {
        List<Class<?>> groups = new ArrayList<>();
        groups.add(CustomerRequest.class);

        if (isCustomerSelected(customer)) {
            groups.add(customer.getDocumentType().getGroup());
        }

        return groups;
    }

    protected boolean isCustomerSelected(CustomerRequest customer){
        return customer != null && customer.getDocumentType() != null;
    }
}
