package seedu.restaurant.testutil.reservation;

import java.util.HashSet;
import java.util.Set;

import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Remark;
import seedu.restaurant.model.reservation.Reservation;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.model.tag.Tag;
import seedu.restaurant.model.util.SampleDataUtil;

//@@author m4dkip
/**
 * A utility class to help with building Reservation objects.
 */
public class ReservationBuilder {

    public static final String DEFAULT_NAME = "Alice Pauline";
    public static final String DEFAULT_PAX = "4";
    public static final String DEFAULT_DATE = "03-12-2019";
    public static final String DEFAULT_TIME = "10:00";
    public static final String DEFAULT_REMARK = "";

    private Name name;
    private Pax pax;
    private Date date;
    private Time time;
    private Remark remark;
    private Set<Tag> tags;

    public ReservationBuilder() {
        name = new Name(DEFAULT_NAME);
        pax = new Pax(DEFAULT_PAX);
        date = new Date(DEFAULT_DATE);
        time = new Time(DEFAULT_TIME);
        remark = new Remark(DEFAULT_REMARK);
        tags = new HashSet<>();
    }

    /**
     * Initializes the ReservationBuilder with the data of {@code reservationToCopy}.
     */
    public ReservationBuilder(Reservation reservationToCopy) {
        name = reservationToCopy.getName();
        pax = reservationToCopy.getPax();
        date = reservationToCopy.getDate();
        time = reservationToCopy.getTime();
        remark = reservationToCopy.getRemark();
        tags = new HashSet<>(reservationToCopy.getTags());
    }

    /**
     * Sets the {@code Name} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withName(String name) {
        this.name = new Name(name);
        return this;
    }

    /**
     * Parses the {@code tags} into a {@code Set<Tag>} and set it to the {@code Reservation} that we are building.
     */
    public ReservationBuilder withTags(String ... tags) {
        this.tags = SampleDataUtil.getTagSet(tags);
        return this;
    }

    /**
     * Sets the {@code date} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withDate(String date) {
        this.date = new Date(date);
        return this;
    }

    /**
     * Sets the {@code time} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withTime(String time) {
        this.time = new Time(time);
        return this;
    }

    /**
     * Sets the {@code Pax} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withPax(String pax) {
        this.pax = new Pax(pax);
        return this;
    }

    /**
     * Sets the {@code Remark} of the {@code Reservation} that we are building.
     */
    public ReservationBuilder withRemark(String remark) {
        this.remark = new Remark(remark);
        return this;
    }

    public Reservation build() {
        return new Reservation(name, pax, date, time, remark, tags);
    }

}
