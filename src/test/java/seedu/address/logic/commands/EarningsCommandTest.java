package seedu.address.logic.commands;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

public class EarningsCommandTest {

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructorNullDateThrowsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new EarningsCommand(null, null);
    }


}
