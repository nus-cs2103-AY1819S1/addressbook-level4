package seedu.address.storage;

import java.util.Objects;

import javax.xml.bind.annotation.XmlElement;

import seedu.address.commons.exceptions.IllegalValueException;
import seedu.address.model.guest.Guest;
import seedu.address.model.room.Capacity;
import seedu.address.model.room.booking.Booking;
import seedu.address.model.room.booking.BookingPeriod;

/**
 * JAXB-friendly version of the Booking.
 */
public class XmlAdaptedBooking {

    public static final String MISSING_FIELD_MESSAGE_FORMAT = "Booking's %s field is missing!";

    @XmlElement(required = true)
    private XmlAdaptedGuest guest;
    @XmlElement(required = true)
    private XmlAdaptedBookingPeriod bookingPeriod;
    @XmlElement(required = true)
    private Boolean isCheckedIn;

    /**
     * Constructs an XmlAdaptedBooking.
     * This is the no-arg constructor that is required by JAXB.
     */
    public XmlAdaptedBooking() {}

    /**
     * Constructor FOR TESTING in XmlAdaptedBookingTest
     */
    public XmlAdaptedBooking(Guest guest, BookingPeriod bookingPeriod, Boolean isCheckedIn) {
        this.guest = new XmlAdaptedGuest(guest);
        this.bookingPeriod = new XmlAdaptedBookingPeriod(bookingPeriod);
        this.isCheckedIn = isCheckedIn;
    }

    /**
     * Converts a given booking into this class for JAXB use.
     *
     * @param source future changes to this will not affect the created XmlAdaptedBooking
     */
    public XmlAdaptedBooking(Booking source) {
        guest = new XmlAdaptedGuest(source.getGuest());
        bookingPeriod = new XmlAdaptedBookingPeriod(source.getBookingPeriod());
        isCheckedIn = source.getIsCheckedIn();
    }

    /**
     * Converts this jaxb-friendly adapted booking object into the model's booking object.
     *
     * @throws IllegalValueException if there were any data constraints violated in the adapted booking
     */
    public Booking toModelType() throws IllegalValueException {

        if (guest == null) {
            throw new IllegalValueException(
                    String.format(MISSING_FIELD_MESSAGE_FORMAT, Guest.class.getSimpleName()));
        }
        if (bookingPeriod == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Capacity.class.getSimpleName()));
        }
        if (isCheckedIn == null) {
            throw new IllegalValueException(
                String.format(MISSING_FIELD_MESSAGE_FORMAT, Boolean.class.getSimpleName()));
        }

        return new Booking(guest.toModelType(), bookingPeriod.toModelType(), isCheckedIn);
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof XmlAdaptedBooking)) {
            return false;
        }

        XmlAdaptedBooking otherbooking = (XmlAdaptedBooking) other;
        return Objects.equals(guest, otherbooking.guest)
                && Objects.equals(bookingPeriod, otherbooking.bookingPeriod)
                && Objects.equals(isCheckedIn, otherbooking.isCheckedIn);
    }
}
