package seedu.address.testutil;

import seedu.address.model.attendance.Attendance;
import seedu.address.model.attendance.Session;
import seedu.address.model.attendance.SessionDate;

/**
 * A utility class to help with building Attendance objects.
 */
public class AttendanceBuilder {

    public static final String DEFAULT_NAME = "A1";
    public static final String DEFAULT_DATE = "01/01/0001";
    public static final String DEFAULT_UID = null;

    private Session name;
    private SessionDate date;
    private String uniqueId;

    public AttendanceBuilder() {
        name = new Session(DEFAULT_NAME);
        date = new SessionDate(DEFAULT_DATE);
        uniqueId = DEFAULT_UID;
    }

    /**
     * Initializes the PersonBuilder with the data of {@code personToCopy}.
     */
    public AttendanceBuilder(Attendance attendanceToCopy) {
        name = attendanceToCopy.getSession();
        date = attendanceToCopy.getDate();
        uniqueId = attendanceToCopy.getUniqueId();
    }

    /**
     * Sets the {@code Session} of the {@code Attendance} that we are building.
     */
    public AttendanceBuilder withName(String name) {
        this.name = new Session(name);
        return this;
    }

    /**
     * Sets the {@code SessionDate} of the {@code Attendance} that we are building.
     */
    public AttendanceBuilder withDate(String date) {
        this.date = new SessionDate(date);
        return this;
    }


    /**
     * Return the {@code Attendance} that we are building.
     */
    public Attendance build() {
        if (uniqueId == null) {
            return new Attendance(name, date);
        } else {
            return new Attendance(name, date, uniqueId);
        }
    }

}
