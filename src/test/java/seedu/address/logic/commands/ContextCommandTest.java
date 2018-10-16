package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class ContextCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullExpression_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new ContextCommand(null);
    }

    @Test
    public void constructor_validExpression_success() {
        String expression = "  orbital  ";
        assertEquals(new ContextCommand("orbital"), new ContextCommand(expression.trim()));
    }

}
