package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_COORDINATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_NUMBER_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARPARK_TYPE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREE_PARKING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_FREE_PARKING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOTS_AVAILABLE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_LOTS_AVAILABLE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NIGHT_PARKING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NIGHT_PARKING_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SHORT_TERM_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_SHORT_TERM_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_LOTS_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOTAL_LOTS_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_OF_PARKING_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TYPE_OF_PARKING_2;

import java.io.IOException;
import java.util.ArrayList;

import seedu.address.commons.util.GsonUtil;
import seedu.address.model.AddressBook;
import seedu.address.model.carpark.*;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalCarparks {

    public static final Carpark ALICE = new CarparkBuilder().withCarparkNumber("C7")
            .withAddress("BLK 349-355 CLEMENTI AVE 2").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("21017.6263, 33014.2438").withLotsAvailable("0").withTotalLots("0")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").build();
    public static final Carpark BENSON = new CarparkBuilder().withCarparkNumber("B46")
            .withAddress("BLK 537/538 BEDOK NORTH STREET 3").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("38112.1565, 34836.4398").withLotsAvailable("0").withTotalLots("0")
            .withFreeParking("NO").withNightParking("NO").withShortTerm("7AM-10.30PM")
            .withTypeOfParking("ELECTRONIC PARKING").build();
    public static final Carpark CARL = new CarparkBuilder().withCarparkNumber("U25")
            .withAddress("BLK 337-353/355-356 BUKIT BATOK ST 34").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("18728.7311, 38350.9289").withLotsAvailable("299").withTotalLots("625")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").build();
    public static final Carpark DANIEL = new CarparkBuilder().withCarparkNumber("W12M")
            .withAddress("BLK 34A MARSILING DR").withCarparkType("MULTI-STOREY CAR PARK")
            .withCoordinate("21198.8774, 47366.6434").withLotsAvailable("295").withTotalLots("459")
            .withFreeParking("NO").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").build();
    public static final Carpark ELLE = new CarparkBuilder().withCarparkNumber("PP5")
            .withAddress("BLK 113 TO 120 POTONG PASIR AVENUE 1").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("31328.0300, 35388.2500").withLotsAvailable("92").withTotalLots("321")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("NO")
            .withTypeOfParking("ELECTRONIC PARKING").build();
    public static final Carpark FIONA = new CarparkBuilder().withCarparkNumber("SE39")
            .withAddress("BLK 542A SERANGOON NORTH AVENUE 4").withCarparkType("MULTI-STOREY CAR PARK")
            .withCoordinate("32271.7043, 39500.6075").withLotsAvailable("39").withTotalLots("206")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("NO").withShortTerm("7AM-10.30PM")
            .withTypeOfParking("ELECTRONIC PARKING").build();
    public static final Carpark GEORGE = new CarparkBuilder().withCarparkNumber("W676")
            .withAddress("BLK 676 WOODLANDS DRIVE 71").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("24373.6740, 46827.1390").withLotsAvailable("186").withTotalLots("230")
            .withFreeParking("NO").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").build();

    // Manually added
    public static final Carpark HOON = new CarparkBuilder().withCarparkNumber("A29")
            .withAddress("BLK 347 ANG MO KIO AVENUE 3").withCarparkType("SURFACE CAR PARK")
            .withCoordinate("29713.5035, 38806.4544").withLotsAvailable("38").withTotalLots("466")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").build();
    public static final Carpark IDA = new CarparkBuilder().withCarparkNumber("JM16")
            .withAddress("BLK 659 JURONG WEST ST 65").withCarparkType("MULTI-STOREY CAR PARK")
            .withCoordinate("13399.3160, 35488.4096").withLotsAvailable("155").withTotalLots("534")
            .withFreeParking("SUN & PH FR 7AM-10.30PM").withNightParking("YES").withShortTerm("WHOLE DAY")
            .withTypeOfParking("ELECTRONIC PARKING").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Carpark AMY = new CarparkBuilder().withCarparkNumber(VALID_CARPARK_NUMBER_1)
            .withAddress(VALID_ADDRESS_1).withCarparkType(VALID_CARPARK_TYPE_1)
            .withCoordinate(VALID_COORDINATE_1).withLotsAvailable(VALID_LOTS_AVAILABLE_1).withTotalLots(VALID_TOTAL_LOTS_1)
            .withFreeParking(VALID_FREE_PARKING_1).withNightParking(VALID_NIGHT_PARKING_1).withShortTerm(VALID_SHORT_TERM_1)
            .withTypeOfParking(VALID_TYPE_OF_PARKING_1).build();
    public static final Carpark BOB = new CarparkBuilder().withCarparkNumber(VALID_CARPARK_NUMBER_2)
            .withAddress(VALID_ADDRESS_2).withCarparkType(VALID_CARPARK_TYPE_2)
            .withCoordinate(VALID_COORDINATE_2).withLotsAvailable(VALID_LOTS_AVAILABLE_2).withTotalLots(VALID_TOTAL_LOTS_2)
            .withFreeParking(VALID_FREE_PARKING_2).withNightParking(VALID_NIGHT_PARKING_2).withShortTerm(VALID_SHORT_TERM_2)
            .withTypeOfParking(VALID_TYPE_OF_PARKING_2).build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER*/

    private TypicalCarparks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Carpark carpark : getTypicalCarparks()) {
            ab.addCarpark(carpark);
        }
        return ab;
    }

    public static ArrayList<Carpark> getTypicalCarparks()  {
        try {
            //return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
            ArrayList<ArrayList<String>> info = GsonUtil.fetchCarparkInfo();

            ArrayList<Carpark> carparkInfo = null;
            for (ArrayList<String> str : info) {
                carparkInfo.add(new Carpark(new Address(str.get(0)),
                        new CarparkNumber(str.get(1)),
                        new CarparkType(str.get(2)),
                        new Coordinate(str.get(3)),
                        new FreeParking(str.get(4)),
                        new LotsAvailable(str.get(5)),
                        new NightParking(str.get(6)),
                        new ShortTerm(str.get(7)),
                        new TotalLots(str.get(8)),
                        new TypeOfParking(str.get(9)),
                        null

                ));
            }
            return carparkInfo;
        } catch (IOException e) {
            e.printStackTrace();
            return null;
        }
    }
}
