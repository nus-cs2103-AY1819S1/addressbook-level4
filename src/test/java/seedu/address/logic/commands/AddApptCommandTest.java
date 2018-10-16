package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalPersons.AMY_APPT;
import static seedu.address.testutil.TypicalPersons.CARL_APPT;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.appointment.Appointment;
import seedu.address.testutil.AppointmentBuilder;

public class AddApptCommandTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void constructor_nullAppointment_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddApptCommand(null);
    }

    @Test
    public void equals() {
        Appointment amy = new AppointmentBuilder(AMY_APPT).build();
        Appointment carl = new AppointmentBuilder(CARL_APPT).build();
        AddApptCommand addApptAmyCommand = new AddApptCommand(amy);
        AddApptCommand addApptCarlCommand = new AddApptCommand(carl);

        // same object -> returns true
        assertTrue(addApptAmyCommand.equals(addApptAmyCommand));

        // same values -> returns false because clash
        AddApptCommand addApptAmyCommandCopy = new AddApptCommand(amy);
        assertTrue(addApptAmyCommand.equals(addApptAmyCommandCopy));

        // different types -> returns false
        assertFalse(addApptAmyCommand.equals(1));

        // null -> returns false
        assertFalse(addApptAmyCommand.equals(null));

        // different person -> returns false
        assertFalse(addApptAmyCommand.equals(addApptCarlCommand));
    }
}
