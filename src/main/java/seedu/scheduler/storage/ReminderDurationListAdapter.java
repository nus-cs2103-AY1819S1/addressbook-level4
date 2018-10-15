package seedu.scheduler.storage;

import java.time.Duration;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

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
        ReminderDurationList reminderDurationList = new ReminderDurationList();
        if (v == ReminderDurationList.EMPTY_VALUE) {
            return reminderDurationList;
        } else {
            List<String> keyValuePairs = Arrays.asList(v.split(","));
            for (String keyValueString: keyValuePairs) {
                ArrayList<String> durationValue = (ArrayList<String>)Arrays.asList(keyValueString.split(":"));
                reminderDurationList.add(Duration.parse(durationValue.get(0).trim()),
                        Boolean.parseBoolean(durationValue.get(1).trim()));
            }
        }
        return reminderDurationList;
    }
}
