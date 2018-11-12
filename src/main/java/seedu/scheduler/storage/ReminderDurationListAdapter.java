package seedu.scheduler.storage;

import javax.xml.bind.annotation.adapters.XmlAdapter;

import seedu.scheduler.model.event.ReminderDurationList;

/**
 * JAXB-friendly adapted version of the {@code ReminderDurationList}.
 */
public class ReminderDurationListAdapter extends XmlAdapter<String, ReminderDurationList> {

    @Override
    public String marshal(ReminderDurationList v) {
        return v.toString();
    }

    @Override
    public ReminderDurationList unmarshal(String v) {
        ReminderDurationList reminderDurationList = new ReminderDurationList(v);
        return reminderDurationList;
    }
}
