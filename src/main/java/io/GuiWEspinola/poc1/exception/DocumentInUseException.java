package io.GuiWEspinola.poc1.exception;

public class DocumentInUseException extends RuntimeException {
    public DocumentInUseException(String document) {
        super(formatMessage(document));
    }

    private static String formatMessage(String document) {
        return String.format("The following document is already in use: '%s'", document);
    }
}
