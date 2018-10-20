package ssp.scheduleplanner.storage;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;
import javax.xml.bind.annotation.XmlRootElement;

import ssp.scheduleplanner.commons.exceptions.IllegalValueException;
import ssp.scheduleplanner.model.ReadOnlySchedulePlanner;
import ssp.scheduleplanner.model.SchedulePlanner;
import ssp.scheduleplanner.model.task.Task;

/**
 * An Immutable rangeOfWeek for schedule planner that is serializable to XML format
 */
@XmlRootElement(name = "rangeofweek")
public class XmlSerializableRangeOfWeek {

    public static final String MESSAGE_DUPLICATE_TASK = "Tasks list contains duplicate task(s).";
    private static final int WEEKS_IN_SEMESTER = 17;

    @XmlElement
    private List<XmlAdaptedRangeOfWeek> rangeOfWeeks;

    /**
     * Creates an empty XmlSerializableRangeOfWeek.
     * This empty constructor is required for marshalling.
     */
    public XmlSerializableRangeOfWeek() {
        rangeOfWeeks = new ArrayList<>();
    }

    /**
     * Conversion
     */
    public XmlSerializableRangeOfWeek(String[][] src) {
        this();
        for (int i = 0; i < WEEKS_IN_SEMESTER; i++) {
            rangeOfWeeks.add(new XmlAdaptedRangeOfWeek(src[i][0], src[i][1], src[i][2]));
            }
        }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlSerializableRangeOfWeek)) {
            return false;
        }
        return rangeOfWeeks.equals(((XmlSerializableRangeOfWeek) other).rangeOfWeeks);
    }
}
