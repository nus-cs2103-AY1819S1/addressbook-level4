package seedu.address.model.appointment;

import java.util.Calendar;

import seedu.address.commons.util.CollectionUtil;

/**
 * This class holds information for appointments
 *
 * @author jeffypie369
 *
 */
public class Appointment {
    private static final String DIVIDER = " | ";

    /** Type of medical procedure */
    private Type type;

    /** Name of the medical procedure */
    private String procedureName;

    /** Date and time of procedure */
    private Calendar dateTime;

    /** Name of the doctor-in-charge */
    private String docName;

    public Appointment(Type type, String procedureName, Calendar dateTime, String docName) {
        CollectionUtil.requireAllNonNull(type, dateTime, docName);
        this.type = type;
        this.procedureName = procedureName;
        this.dateTime = dateTime;
        this.docName = docName;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String dt = DateTime.DATE_TIME_FORMAT.format(dateTime.getTime());
        sb.append(type).append(DIVIDER).append(procedureName).append(DIVIDER).append(dt).append(DIVIDER)
                .append(docName);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Appointment) {
            Appointment a = (Appointment) o;
            return type.equals(a.type) && procedureName.equals(a.procedureName)
                    && dateTime.equals(a.dateTime) && docName.equals(a.docName);
        }

        return false;
    }

    public Type getType() {
        return type;
    }

    public Calendar getDate_time() {
        return dateTime;
    }

    public String getDoc_name() {
        return docName;
    }

    public String getProcedure_name() {
        return procedureName;
    }
}
