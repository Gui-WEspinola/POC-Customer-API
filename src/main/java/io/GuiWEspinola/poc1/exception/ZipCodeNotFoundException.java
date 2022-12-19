package io.GuiWEspinola.poc1.exception;

public class ZipCodeNotFoundException extends RuntimeException{

    public ZipCodeNotFoundException(String zipCode) {
        super(formatMessage(zipCode));
    }

    private static String formatMessage(String zipCode) {
        return String.format("Zipcode '%s' does not exist!", zipCode);
    }
}
