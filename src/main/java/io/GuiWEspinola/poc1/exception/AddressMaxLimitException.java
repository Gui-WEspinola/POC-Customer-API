package io.GuiWEspinola.poc1.exception;

import org.springframework.http.HttpStatus;

public class AddressMaxLimitException extends RuntimeException{

    private static final HttpStatus httpStatus = HttpStatus.CONFLICT;

    public AddressMaxLimitException(Long id) {
        super(formatMessage(id));
    }

    public HttpStatus getHttpStatus() {
        return httpStatus;
    }

    private static String formatMessage(Long id) {
        return String.format("The Customer of ID '%d' has already the maximum number of addresses allowed.", id);
    }
}
