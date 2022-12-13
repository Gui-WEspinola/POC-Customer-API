package io.GuiWEspinola.poc1.exception;

public class MainAddressDeleteException extends RuntimeException {
    public MainAddressDeleteException() {
        super("You can't delete your main address.");
    }
}
