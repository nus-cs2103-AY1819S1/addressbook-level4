package seedu.parking.testutil;

import static seedu.parking.logic.commands.CommandTestUtil.VALID_ADDRESS_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_ADDRESS_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_COORDINATE_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_COORDINATE_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_FREE_PARKING_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_FREE_PARKING_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_LOTS_AVAILABLE_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_LOTS_AVAILABLE_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_NIGHT_PARKING_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_NIGHT_PARKING_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_SHORT_TERM_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_SHORT_TERM_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TOTAL_LOTS_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TOTAL_LOTS_KILO;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TYPE_OF_PARKING_JULIETT;
import static seedu.parking.logic.commands.CommandTestUtil.VALID_TYPE_OF_PARKING_KILO;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.parking.model.CarparkFinder;
import seedu.parking.model.carpark.Carpark;

/**
 * A utility class containing a list of {@code Carpark} objects to be used in tests.
 */
public class TypicalCarparks {

    public static final Carpark ALFA = new CarparkBuilder().withCarparkNumber("C7")
            .withAddress("BLK 349-355 CLEMENTI AVE 2").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("21017.6263, 33014.2438").withLotsAvailable("0").withTotalLots("0")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").withTags("Office").build();
    public static final Carpark BRAVO = new CarparkBuilder().withCarparkNumber("SK88")
            .withAddress("BLK 451 SENGKANG WEST WAY").withCarparkType("MULTI-STOREY CAR PARK")
            .withCoordinate("32538.8707, 41769.3259").withLotsAvailable("109").withTotalLots("369")
            .withFreeParking("NO").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").withTags("Home", "Office").build();
    public static final Carpark CHARLIE = new CarparkBuilder().withCarparkNumber("U25")
            .withAddress("BLK 337-353/355-356 BUKIT BATOK ST 34").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("18728.7311, 38350.9289").withLotsAvailable("299").withTotalLots("625")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").withTags("School").build();
    public static final Carpark DELTA = new CarparkBuilder().withCarparkNumber("SK23")
            .withAddress("BLK 121E SENGKANG EAST WAY").withCarparkType("MULTI-STOREY CAR PARK")
            .withCoordinate("36099.0459, 40907.1776").withLotsAvailable("410").withTotalLots("672")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").withTags("Office").build();
    public static final Carpark ECHO = new CarparkBuilder().withCarparkNumber("PP5")
            .withAddress("BLK 113 TO 120 POTONG PASIR AVENUE 1").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("31328.0300, 35388.2500").withLotsAvailable("92").withTotalLots("321")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("NO")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").build();
    public static final Carpark FOXTROT = new CarparkBuilder().withCarparkNumber("SE39")
            .withAddress("BLK 542A SERANGOON NORTH AVENUE 4").withCarparkType("MULTI-STOREY CAR PARK")
            .withCoordinate("32271.7043, 39500.6075").withLotsAvailable("39").withTotalLots("206")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("NO").withShortTerm("7AM-10.30PM")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").build();
    public static final Carpark GOLF = new CarparkBuilder().withCarparkNumber("W676")
            .withAddress("BLK 676 WOODLANDS DRIVE 71").withCarparkType("BASEMENT CAR PARK")
            .withCoordinate("24373.6740, 46827.1390").withLotsAvailable("186").withTotalLots("230")
            .withFreeParking("NO").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").build();

    // Manually added
    public static final Carpark HOTEL = new CarparkBuilder().withCarparkNumber("A29")
            .withAddress("BLK 347 ANG MO KIO AVENUE 3").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("29713.5035, 38806.4544").withLotsAvailable("38").withTotalLots("466")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").build();
    public static final Carpark INDIA = new CarparkBuilder().withCarparkNumber("JM16")
            .withAddress("BLK 659 JURONG WEST ST 65").withCarparkType("MULTI-STOREY CAR PARK")
            .withCoordinate("13399.3160, 35488.4096").withLotsAvailable("155").withTotalLots("534")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").withPostalCode("123456").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Carpark JULIETT = new CarparkBuilder().withCarparkNumber(VALID_CARPARK_NUMBER_JULIETT)
            .withAddress(VALID_ADDRESS_JULIETT).withCarparkType(VALID_CARPARK_TYPE_JULIETT)
            .withCoordinate(VALID_COORDINATE_JULIETT).withLotsAvailable(VALID_LOTS_AVAILABLE_JULIETT)
            .withTotalLots(VALID_TOTAL_LOTS_JULIETT).withFreeParking(VALID_FREE_PARKING_JULIETT)
            .withNightParking(VALID_NIGHT_PARKING_JULIETT).withShortTerm(VALID_SHORT_TERM_JULIETT)
            .withTypeOfParking(VALID_TYPE_OF_PARKING_JULIETT).build();
    public static final Carpark KILO = new CarparkBuilder().withCarparkNumber(VALID_CARPARK_NUMBER_KILO)
            .withAddress(VALID_ADDRESS_KILO).withCarparkType(VALID_CARPARK_TYPE_KILO)
            .withCoordinate(VALID_COORDINATE_KILO).withLotsAvailable(VALID_LOTS_AVAILABLE_KILO)
            .withTotalLots(VALID_TOTAL_LOTS_KILO).withFreeParking(VALID_FREE_PARKING_KILO)
            .withNightParking(VALID_NIGHT_PARKING_KILO).withShortTerm(VALID_SHORT_TERM_KILO)
            .withTypeOfParking(VALID_TYPE_OF_PARKING_KILO).build();

    public static final String KEYWORD_MATCHING_SENGKANG = "SENGKANG"; // A keyword that matches SENGKANG*/

    private TypicalCarparks() {} // prevents instantiation

    /**
     * Returns an {@code CarparkFinder} with all the typical car parks.
     */
    public static CarparkFinder getTypicalCarparkFinder() {
        CarparkFinder ab = new CarparkFinder();
        for (Carpark carpark : getTypicalCarparks()) {
            ab.addCarpark(carpark);
        }
        return ab;
    }

    public static List<Carpark> getTypicalCarparks() {
        return new ArrayList<>(Arrays.asList(ALFA, BRAVO, CHARLIE, DELTA, ECHO, FOXTROT, GOLF));
    }
}
