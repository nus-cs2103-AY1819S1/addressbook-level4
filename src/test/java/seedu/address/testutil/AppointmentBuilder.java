package seedu.address.testutil;

import java.text.ParseException;
import java.util.Calendar;
import java.util.Date;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.appointment.Appointment;
import seedu.address.model.appointment.DateTime;
import seedu.address.model.appointment.Type;

/**
 * A utility class for Appointment
 */
public class AppointmentBuilder {

    /** We don't construct a default DATE_TIME object here because of the IllegalValueException. */
    private static final Type DEFAULT_TYPE = Type.SURGICAL;
    private static final String DEFAULT_PROCEDURE = "Heart Bypass";
    private static final String DEFAULT_DATE_TIME = "12-12-2022 12:00";
    private static final String DEFAULT_DOCTOR = "Dr. Gregory House";

    private Type type;
    private String procedure;
    private Date dateTime;
    private String doctor;

    public AppointmentBuilder() throws IllegalValueException {
        type = DEFAULT_TYPE;
        procedure = DEFAULT_PROCEDURE;
        try {
            dateTime = DateTime.DATE_TIME_FORMAT.parse(DEFAULT_DATE_TIME);
        } catch (ParseException e) {
            throw new IllegalValueException("");
        }
        doctor = DEFAULT_DOCTOR;
    }

    /** Copy constructor. */
    public AppointmentBuilder(Appointment a) {
        type = a.getType();
        procedure = a.getProcedure_name();
        dateTime = a.getDate_time().getTime();
        doctor = a.getDoc_name();
    }

    /**
     * Setter for type
     * @param type The type of appointment
     * @return this
     */
    public AppointmentBuilder withType(Type type) {
        this.type = type;
        return this;
    }

    /**
     * Setter for procedure
     * @param proc The procedure object to defensively copy and use.
     * @return this
     */
    public AppointmentBuilder withProcedure(String proc) {
        this.procedure = proc;
        return this;
    }

    /**
     * Setter for DateTime
     * @param d The DateTime object to defensively copy and use.
     * @return this
     */
    public AppointmentBuilder withDateTime(Date d) {
        this.dateTime = d;
        return this;
    }

    /**
     * Setter for DateTime
     * @param docName The DateTime object to defensively copy and use.
     * @return this
     */
    public AppointmentBuilder withDocName(String docName) {
        this.doctor = docName;
        return this;
    }

    /**
     * Returns an {@code Appointment} object with the given characteristics.
     */
    public Appointment build() {
        Calendar dt = Calendar.getInstance();
        dt.setTime(dateTime);
        return new Appointment(type, procedure, dt, doctor);
    }
}

