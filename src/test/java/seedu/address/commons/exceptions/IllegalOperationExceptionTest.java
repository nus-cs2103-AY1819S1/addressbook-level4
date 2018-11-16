package seedu.address.commons.exceptions;

import static org.junit.jupiter.api.Assertions.assertEquals;

import org.junit.jupiter.api.Test;

class IllegalOperationExceptionTest {

    @Test
    public void testExpectedMessage() {
        String message = "Expected";
        IllegalOperationException e = new IllegalOperationException(message);
        assertEquals(e.getMessage(), message);
    }

    @Test
    public void testExpectedThrowable() {
        Throwable t = new Throwable();
        String message = "Expected";
        IllegalOperationException e = new IllegalOperationException(message, t);
        assertEquals(e.getCause(), t);
    }
}
