package seedu.address.model.appointment;

import seedu.address.commons.exceptions.IllegalValueException;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class DateTime {
    public static final SimpleDateFormat DATE_TIME_FORMAT = new SimpleDateFormat("dd-MM-yyyy HH:mm");
    public static final String MESSAGE_DATE_TIME_BEFORE_NOW = "Duration must be a positive value!";

    private Calendar apptDate;

    public DateTime(Calendar apptDate) throws IllegalValueException {
        Date date = Calendar.getInstance().getTime();
        Date appt = apptDate.getTime();
        if (appt.before(date)) {
            throw new IllegalValueException(MESSAGE_DATE_TIME_BEFORE_NOW);
        }
        this.apptDate = apptDate;
    }

    @Override
    public String toString() {
        return DATE_TIME_FORMAT.format(apptDate);
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) {
            return true;
        }
        if (o instanceof DateTime) {
            DateTime d = (DateTime) o;
            return apptDate.get(Calendar.DAY_OF_YEAR) == d.apptDate.get(Calendar.DAY_OF_YEAR) // check proper way
                    && apptDate.get(Calendar.DATE) == d.apptDate.get(Calendar.DATE)
                    && apptDate.get(Calendar.MONTH) == d.apptDate.get(Calendar.MONTH)
                    && apptDate.get(Calendar.YEAR) == d.apptDate.get(Calendar.YEAR);
        }

        return false;
    }
}
