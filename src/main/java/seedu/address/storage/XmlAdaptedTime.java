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

    /**
     * Converts a given Time into this class for JAXB use.
     *
     * @param time future changes to this will not affect the created
     */
    public XmlAdaptedTime(Time time) {
        this.time = time.toString();
    }

    /**
     * Constructs a {@code XmlAdaptedTag} with the given {@code tagName}.
     */
    public XmlAdaptedTime(String time) {
        this.time = time;
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
        case "Tuesday":
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
            break;
        }

        String startTime = correctedTimeFormat(splittedTime[1]);
        String endTime = correctedTimeFormat(splittedTime[2]);

        return new Time(dayString + " " + startTime + " " + endTime);
    }

    /**
     * Checks if the given Time String follows the required format.
     * Prepends a "0" to the a Time string to ensure that the String follows a 4-digit format required.
     */
    public String correctedTimeFormat(String time) {
        if (time.length() == 3) {
            time = "0" + time;
        }
        return time;
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedTime)) {
            return false;
        }

        return time.equals(((XmlAdaptedTime) other).time);
    }
}
