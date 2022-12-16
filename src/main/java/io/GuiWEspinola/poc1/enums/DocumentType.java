package io.GuiWEspinola.poc1.enums;

import io.GuiWEspinola.poc1.validation.groupValidation.CnpjGroup;
import io.GuiWEspinola.poc1.validation.groupValidation.CpfGroup;

public enum DocumentType {

    CPF ("CPF", CpfGroup.class),
    CNPJ ("CNPJ", CnpjGroup.class);

    private final String document;
    private final Class<?> group;

    DocumentType(String documentType, Class<?> group) {
        this.document = documentType;
        this.group = group;
    }

    public String getDocument() {
        return document;
    }

    public Class<?> getGroup() {
        return group;
    }
}
