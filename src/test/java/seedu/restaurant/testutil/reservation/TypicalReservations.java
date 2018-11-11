package seedu.restaurant.testutil.reservation;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.restaurant.model.RestaurantBook;
import seedu.restaurant.model.reservation.Reservation;

//@@author m4dkip
/**
 * A utility class containing a list of {@code Reservation} objects to be used in tests.
 */
public class TypicalReservations {
    public static final Reservation RESERVATION_DEFAULT = new ReservationBuilder().build();

    public static final Reservation ANDREW = new ReservationBuilder().withName("Andrew")
            .withPax("2")
            .withDate("03-12-2019")
            .withTime("12:00").build();
    public static final Reservation BILLY = new ReservationBuilder().withName("Billy")
            .withPax("4")
            .withDate("05-12-2019")
            .withTime("18:00").build();
    public static final Reservation CARELL = new ReservationBuilder().withName("Carell")
            .withPax("1")
            .withDate("03-10-2019")
            .withTime("15:00").build();
    public static final Reservation DANNY = new ReservationBuilder().withName("Danny Phantom")
            .withPax("3")
            .withDate("13-09-2019")
            .withTime("12:00")
            .withTags("Driving").build();
    public static final Reservation ELSA = new ReservationBuilder().withName("Elsa")
            .withPax("2")
            .withDate("13-10-2019")
            .withTime("13:00")
            .withTags("Allergies").build();
    public static final Reservation FROPPY = new ReservationBuilder().withName("Froppy")
            .withPax("8")
            .withDate("30-10-2019")
            .withTime("18:00").build();
    public static final Reservation GUILE = new ReservationBuilder().withName("Guile")
            .withPax("2")
            .withDate("23-10-2018")
            .withTime("19:00").build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalReservations() {} // prevents instantiation

    /**
     * Returns an {@code RestaurantBook} with all the typical reservations.
     */
    public static RestaurantBook getTypicalRestaurantBookWithReservationsOnly() {
        RestaurantBook restaurantBook = new RestaurantBook();
        for (Reservation reservation : getTypicalReservations()) {
            restaurantBook.addReservation(reservation);
        }
        return restaurantBook;
    }

    public static List<Reservation> getTypicalReservations() {
        return new ArrayList<>(Arrays.asList(ANDREW, BILLY, CARELL, DANNY, ELSA, FROPPY, GUILE));
    }
}
