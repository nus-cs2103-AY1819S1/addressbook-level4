package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.attendance.Attendance;

/**
 * A utility class containing a list of {@code Assignment} objects to be used in tests.
 */
public class TypicalAttendance {

    public static final Attendance ATTENDANCE_AA = new AttendanceBuilder().withName("Lesson AA")
            .withDate("01/01/2019").build();
    public static final Attendance ATTENDANCE_BB = new AttendanceBuilder().withName("Lesson BB")
            .withDate("02/02/2019").build();
    public static final Attendance ATTENDANCE_CC = new AttendanceBuilder().withName("Lesson CC")
            .withDate("03/03/2019").build();

    // Manually added - Lesson's details found in {@code CommandTestUtil}
    public static final Attendance ATTENDANCE_A = new AttendanceBuilder().withName("Lesson A")
            .withDate("04/04/2019").build();
    public static final Attendance ATTENDANCE_B = new AttendanceBuilder().withName("Lesson B")
            .withDate("05/05/2019").build();

    private TypicalAttendance() {} // prevents instantiation

    public static List<Attendance> getTypicalAttendance() {
        return new ArrayList<>(Arrays.asList(ATTENDANCE_AA, ATTENDANCE_BB, ATTENDANCE_CC));
    }
}
