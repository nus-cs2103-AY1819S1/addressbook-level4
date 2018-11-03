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

    public static final Reservation ANDREW = new ReservationBuilder().withName("Andrew")
            .withPax("2")
            .withDateTime("2018-12-03T12:00:00").build();
    public static final Reservation BILLY = new ReservationBuilder().withName("Billy")
            .withPax("4")
            .withDateTime("2018-12-05T18:00:00").build();
    public static final Reservation CARELL = new ReservationBuilder().withName("Carell")
            .withPax("1")
            .withDateTime("2018-10-03T15:00:00").build();
    public static final Reservation DANNY = new ReservationBuilder().withName("Danny Phantom")
            .withPax("3")
            .withDateTime("2018-09-13T12:00:00")
            .withTags("driving").build();
    public static final Reservation ELSA = new ReservationBuilder().withName("Elsa")
            .withPax("2")
            .withDateTime("2018-10-31T13:00:00").build();
    public static final Reservation FROPPY = new ReservationBuilder().withName("Froppy")
            .withPax("8")
            .withDateTime("2018-10-30T18:00:00").build();
    public static final Reservation GUILE = new ReservationBuilder().withName("Guile")
            .withPax("2")
            .withDateTime("2018-10-23T19:00:00").build();

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
