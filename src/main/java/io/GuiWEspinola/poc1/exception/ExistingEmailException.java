package io.GuiWEspinola.poc1.exception;

public class ExistingEmailException extends RuntimeException{
    public ExistingEmailException(String email) {
        super(formatMessage(email));
    }

    private static String formatMessage(String message) {
        return String.format("The email '%s' is already in use.", message);
    }
}
