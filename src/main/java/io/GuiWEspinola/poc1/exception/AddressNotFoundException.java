package io.GuiWEspinola.poc1.exception;

public class AddressNotFoundException extends RuntimeException {

    public AddressNotFoundException(Long id) {
        super(formatMessage(id));
    }

    private static String formatMessage(Long id) {
        return String.format("Address with ID '%d' does not exist!", id);
    }
}
