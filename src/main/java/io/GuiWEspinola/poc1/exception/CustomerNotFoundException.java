package io.GuiWEspinola.poc1.exception;

public class CustomerNotFoundException extends RuntimeException {

    public CustomerNotFoundException(Long id) {
        super(formatMessage(id));
    }

    private static String formatMessage(Long id) {
        return String.format("Customer with ID '%d' does not exist!", id);
    }
}
