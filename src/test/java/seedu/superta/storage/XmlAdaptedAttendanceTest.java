package seedu.superta.storage;

import static junit.framework.TestCase.assertTrue;
import static seedu.superta.logic.commands.CommandTestUtil.VALID_STUDENT_ID_AMY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.superta.commons.exceptions.IllegalValueException;
import seedu.superta.model.attendance.Attendance;
import seedu.superta.model.attendance.Presence;
import seedu.superta.model.student.StudentId;

public class XmlAdaptedAttendanceTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void toModelType_success() throws IllegalValueException {
        Attendance source = new Attendance(new StudentId(VALID_STUDENT_ID_AMY), Presence.PRESENT);
        XmlAdaptedAttendance xmlAttendance = new XmlAdaptedAttendance(source);
        assertTrue(xmlAttendance.toModelType().equals(source));
    }

    @Test
    public void toModelType_failure() throws IllegalValueException {
        XmlAdaptedAttendance xmlAttendance = new XmlAdaptedAttendance();
        thrown.expect(IllegalValueException.class);
        xmlAttendance.toModelType();
    }
}
