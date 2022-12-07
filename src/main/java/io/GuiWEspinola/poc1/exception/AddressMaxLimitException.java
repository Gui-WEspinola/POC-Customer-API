package io.GuiWEspinola.poc1.exception;

public class AddressMaxLimitException extends RuntimeException{

    public AddressMaxLimitException(Long id) {
        super(formatMessage(id));
    }

    private static String formatMessage(Long id) {
        return String.format("The Customer of ID '%d' has already the maximum number of addresses allowed.", id);
    }
}
