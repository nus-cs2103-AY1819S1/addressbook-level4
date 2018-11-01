package seedu.clinicio.model.appointment;

import seedu.clinicio.commons.util.StringUtil;
import seedu.clinicio.model.person.Person;

import java.util.List;
import java.util.function.Predicate;

public class AppointmentContainsDatePredicate implements Predicate<Appointment> {
    private final List<String> date;

    public AppointmentContainsKeywordsPredicate(List<String> appointment) {
        this.date = date;
    }

    @Override
    public boolean test(Appointment appointment) {
        return date.stream()
                .anyMatch(keyword -> StringUtil.containsWordIgnoreCase(appointment.getAppointmentDate(), keyword));
    }
}
