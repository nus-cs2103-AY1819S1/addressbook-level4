package seedu.thanepark.storage;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;
import javax.xml.bind.annotation.XmlElement;

import seedu.thanepark.commons.exceptions.IllegalValueException;
import seedu.thanepark.model.ride.Maintenance;
import seedu.thanepark.model.ride.Name;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.model.ride.Status;
import seedu.thanepark.model.ride.WaitTime;
import seedu.thanepark.model.ride.Zone;
import seedu.thanepark.model.tag.Tag;

/**
 * JAXB-friendly version of the Ride.
 */
public class XmlAdaptedRide {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Ride's %s field is missing!";

    private static final Status DEFAULT_STATUS = Status.OPEN;
    private static final String DEFAULT_STATUS_STRING = DEFAULT_STATUS.name();

    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String daysSinceMaintenanceString;
    @XmlElement(required = true)
    private String waitingTimeString;
    @XmlElement(required = true)
    private String zone;
    @XmlElement(required = true)
    private String statusString;

    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();

    /**
     * Constructs an XmlAdaptedRide.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedRide() {}

    /**
     * Constructs an {@code XmlAdaptedRide} with the given ride details.
     */
    public XmlAdaptedRide(String name, String daysSinceMaintenanceString, String waitingTimeString, String zone,
                          List<XmlAdaptedTag> tagged) {
        this(name, daysSinceMaintenanceString, waitingTimeString, zone, tagged, DEFAULT_STATUS_STRING);
    }

    /**
     * Constructs an {@code XmlAdaptedRide} with the given ride details.
     */
    public XmlAdaptedRide(String name, String daysSinceMaintenanceString, String waitingTimeString, String zone,
                          List<XmlAdaptedTag> tagged, String statusString) {
        this.name = name;
        this.daysSinceMaintenanceString = daysSinceMaintenanceString;
        this.waitingTimeString = waitingTimeString;
        this.zone = zone;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
        this.statusString = statusString;
    }

    /**
     * Converts a given Ride into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedRide
     */
    public XmlAdaptedRide(Ride source) {
        name = source.getName().fullName;
        daysSinceMaintenanceString = String.valueOf(source.getDaysSinceMaintenance().getValue());
        waitingTimeString = String.valueOf(source.getWaitingTime().getValue());
        zone = source.getZone().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
        statusString = source.getStatus().name();
    }

    /**
     * Converts this jaxb-friendly adapted ride object into the model's Ride object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted ride
     */
    public Ride toModelType() throws IllegalValueException {
        final List<Tag> personTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            personTags.add(tag.toModelType());
        }

        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);

        if (daysSinceMaintenanceString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT,
                    Maintenance.class.getSimpleName()));
        }
        if (!Maintenance.isValidMaintenance(daysSinceMaintenanceString)) {
            throw new IllegalValueException(Maintenance.MESSAGE_MAINTENANCE_CONSTRAINTS);
        }
        final Maintenance modelMaintenance = new Maintenance(daysSinceMaintenanceString);

        if (waitingTimeString == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, WaitTime.class.getSimpleName()));
        }
        if (!WaitTime.isValidWaitTime(waitingTimeString)) {
            throw new IllegalValueException(WaitTime.MESSAGE_WAIT_TIME_CONSTRAINTS);
        }
        final WaitTime modelWaitTime = new WaitTime(waitingTimeString);

        if (zone == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Zone.class.getSimpleName()));
        }
        if (!Zone.isValidZone(zone)) {
            throw new IllegalValueException(Zone.MESSAGE_ZONE_CONSTRAINTS);
        }
        final Zone modelZone = new Zone(zone);

        final Set<Tag> modelTags = new HashSet<>(personTags);

        if (statusString == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Status.class.getSimpleName()));
        }

        Status status = Status.valueOf(statusString);

        return new Ride(modelName, modelMaintenance, modelWaitTime, modelZone, modelTags, status);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedRide)) {
            return false;
        }

        XmlAdaptedRide otherRide = (XmlAdaptedRide) other;
        return Objects.equals(name, otherRide.name)
                && Objects.equals(daysSinceMaintenanceString, otherRide.daysSinceMaintenanceString)
                && Objects.equals(waitingTimeString, otherRide.waitingTimeString)
                && Objects.equals(zone, otherRide.zone)
                && tagged.equals(otherRide.tagged)
                && Objects.equals(statusString, otherRide.statusString);
    }
}
