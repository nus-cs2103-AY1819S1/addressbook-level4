package seedu.address.storage;

import javax.xml.bind.annotation.XmlValue;

import seedu.address.model.person.Time;

/**
 * JAXB-friendly adapted version of the Time
 */
public class XmlAdaptedTime {
    @XmlValue
    private String time;

    /**
     * Constructs an XmlAdaptedTime.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedTime() {
    }

    public XmlAdaptedTime(Time time) {
        this.time = time.toString();
    }

    /**
     * Converts this jaxb-friendly adapted tag object into the model's Time object.
     */
    public Time toModelType() {
        String[] splittedTime = time.split("\\s+");
        String dayString = "";
        switch(splittedTime[0]) {
        case "Sunday":
            dayString = "sun";
            break;
        case "Monday":
            dayString = "mon";
            break;
        case "Tueday":
            dayString = "tue";
            break;
        case "Wednesday":
            dayString = "wed";
            break;
        case "Thursday":
            dayString = "thu";
            break;
        case "Friday":
            dayString = "fri";
            break;
        default:
            dayString = "sat";
        }

        return new Time(dayString + " " + splittedTime[1] + " " + splittedTime[2]);
    }
}
