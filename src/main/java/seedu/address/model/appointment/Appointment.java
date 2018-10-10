package seedu.address.model.appointment;


import seedu.address.commons.util.CollectionUtil;

import java.util.Calendar;

/**
 * This class holds information for appointments
 *
 * @author Jefferson Sie
 *
 */
public class Appointment {
    private static final String DIVIDER = " | ";

    /** Type of medical procedure */
    private Type type;

    /** Name of the medical procedure */
    private String procedure_name;

    /** Date and time of procedure */
    private Calendar date_time;

    /** Name of the doctor-in-charge */
    private String doc_name;

    public Appointment(Type type, String procedure_name, Calendar date_time, String doc_name) {
        CollectionUtil.requireAllNonNull(type, date_time, doc_name);
        this.type = type;
        this.procedure_name = procedure_name;
        this.date_time = date_time;
        this.doc_name = doc_name;
    }

    @Override
    public String toString() {
        StringBuilder sb = new StringBuilder();
        String dt = DateTime.DATE_TIME_FORMAT.format(date_time.getTime());
        sb.append(type).append(DIVIDER).append(procedure_name).append(DIVIDER).append(dt).append(DIVIDER)
                .append(doc_name);

        return sb.toString();
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }

        if (o instanceof Appointment) {
            Appointment a = (Appointment) o;
            return type.equals(a.type) && procedure_name.equals(a.procedure_name)
                    && date_time.equals(a.date_time) && doc_name.equals(a.doc_name);
        }

        return false;
    }

    public Type getType() {
        return type;
    }

    public Calendar getDate_time() {
        return date_time;
    }

    public String getDoc_name() {
        return doc_name;
    }

    public String getProcedure_name() {
        return procedure_name;
    }
}
