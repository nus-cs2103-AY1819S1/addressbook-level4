package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAINTENANCE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_MAINTENANCE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAIT_TIME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAIT_TIME_BOB;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.ThanePark;
import seedu.address.model.ride.Ride;

/**
 * A utility class containing a list of {@code Ride} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Ride ACCELERATOR = new RideBuilder().withName("Accelerator")
            .withAddress("123, Jurong West Ave 6, #08-111").withWaitTime("1")
            .withMaintenance("94351253")
            .withTags("friends").build();
    public static final Ride BIG = new RideBuilder().withName("Big Thunder Mountain Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withWaitTime("16").withMaintenance("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Ride CASTLE = new RideBuilder().withName("Castle Carrousel").withMaintenance("95352563")
            .withWaitTime("13").withAddress("wall street").build();
    public static final Ride DUMBO = new RideBuilder().withName("Dumbo The Flying Elephant Meier").withMaintenance(
            "87652533")
            .withWaitTime("3").withAddress("10th street").withTags("friends").build();
    public static final Ride ENCHANTED = new RideBuilder().withName("Enchanted Airways").withMaintenance("9482224")
            .withWaitTime("21").withAddress("michegan ave").build();
    public static final Ride FANTASY = new RideBuilder().withName("Final Fantasy").withMaintenance("9482427")
            .withWaitTime("12").withAddress("little tokyo").build();
    public static final Ride GALAXY = new RideBuilder().withName("Galaxy Road").withMaintenance("9482442")
            .withWaitTime("1").withAddress("4th street").build();

    // Manually added
    public static final Ride HAUNTED = new RideBuilder().withName("The Haunted Mansion").withMaintenance("8482424")
            .withWaitTime("19").withAddress("little india").build();
    public static final Ride IDA = new RideBuilder().withName("Ida Mueller").withMaintenance("8482131")
            .withWaitTime("13").withAddress("chicago ave").build();

    // Manually added - Ride's details found in {@code CommandTestUtil}
    public static final Ride AMY = new RideBuilder().withName(VALID_NAME_AMY).withMaintenance(VALID_MAINTENANCE_AMY)
            .withWaitTime(VALID_WAIT_TIME_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Ride BOB = new RideBuilder().withName(VALID_NAME_BOB).withMaintenance(VALID_MAINTENANCE_BOB)
            .withWaitTime(VALID_WAIT_TIME_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code ThanePark} with all the typical persons.
     */
    public static ThanePark getTypicalAddressBook() {
        ThanePark ab = new ThanePark();
        for (Ride ride : getTypicalPersons()) {
            ab.addRide(ride);
        }
        return ab;
    }

    public static List<Ride> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ACCELERATOR, BIG, CASTLE, DUMBO, ENCHANTED, FANTASY, GALAXY));
    }
}
