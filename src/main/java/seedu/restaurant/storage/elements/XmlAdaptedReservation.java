package seedu.restaurant.storage.elements;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Objects;
import java.util.Set;
import java.util.stream.Collectors;

import javax.xml.bind.annotation.XmlElement;

import seedu.restaurant.commons.exceptions.IllegalValueException;
import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Remark;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.storage.XmlAdaptedTag;

//@@author m4dkip
/**
 * JAXB-friendly version of the Reservation.
 */
public class XmlAdaptedReservation {
    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Reservation's %s field is missing!";
    @XmlElement(required = true)
    private String name;
    @XmlElement(required = true)
    private String pax;
    @XmlElement(required = true)
    private String date;
    @XmlElement(required = true)
    private String time;
    @XmlElement(required = true)
    private String remark;
    @XmlElement
    private List<XmlAdaptedTag> tagged = new ArrayList<>();
    /**
     * Constructs an XmlAdaptedReservation.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedReservation() {}
    /**
     * Constructs an {@code XmlAdaptedReservation} with the given reservation details.
     */
    public XmlAdaptedReservation(String name, String pax, String date, String time, String remark,
            List<XmlAdaptedTag> tagged) {
        this.name = name;
        this.pax = pax;
        this.date = date;
        this.time = time;
        this.remark = remark;
        if (tagged != null) {
            this.tagged = new ArrayList<>(tagged);
        }
    }
    /**
     * Converts a given Reservation into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedReservation
     */
    public XmlAdaptedReservation(Reservation source) {
        name = source.getName().toString();
        pax = source.getPax().toString();
        date = source.getDate().toString();
        time = source.getTime().toString();
        remark = source.getRemark().value;
        tagged = source.getTags().stream()
                .map(XmlAdaptedTag::new)
                .collect(Collectors.toList());
    }
    /**
     * Converts this jaxb-friendly adapted reservation object into the model's Reservation object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted reservation
     */
    public Reservation toModelType() throws IllegalValueException {
        final List<Tag> reservationTags = new ArrayList<>();
        for (XmlAdaptedTag tag : tagged) {
            reservationTags.add(tag.toModelType());
        }
        if (name == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName()));
        }
        if (!Name.isValidName(name)) {
            throw new IllegalValueException(Name.MESSAGE_NAME_CONSTRAINTS);
        }
        final Name modelName = new Name(name);
        if (pax == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Pax.class.getSimpleName()));
        }
        if (!Pax.isValidPax(pax)) {
            throw new IllegalValueException(Pax.MESSAGE_PAX_CONSTRAINTS);
        }
        final Pax modelPax = new Pax(pax);
        if (date == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Date.class.getSimpleName()));
        }
        if (!Date.isValidDate(date)) {
            throw new IllegalValueException(Date.MESSAGE_DATE_CONSTRAINTS);
        }
        final Date modelDate = new Date(date);
        if (time == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Time.class.getSimpleName()));
        }
        if (!Time.isValidTime(time)) {
            throw new IllegalValueException(Time.MESSAGE_TIME_CONSTRAINTS);
        }
        final Time modelTime = new Time(time);
        if (remark == null) {
            throw new IllegalValueException(String.format(MISSING_FIELD_MESSAGE_FORMAT, Remark.class.getSimpleName()));
        }
        final Remark modelRemark = new Remark(remark);
        final Set<Tag> modelTags = new HashSet<>(reservationTags);
        return new Reservation(modelName, modelPax, modelDate, modelTime, modelRemark, modelTags);
    }
    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }
        if (!(other instanceof XmlAdaptedReservation)) {
            return false;
        }
        XmlAdaptedReservation otherReservation = (XmlAdaptedReservation) other;
        return Objects.equals(name, otherReservation.name)
                && Objects.equals(pax, otherReservation.pax)
                && Objects.equals(date, otherReservation.date)
                && Objects.equals(time, otherReservation.time)
                && tagged.equals(otherReservation.tagged);
    }
}
