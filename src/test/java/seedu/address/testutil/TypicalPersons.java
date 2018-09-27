package seedu.address.testutil;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.ride.Ride;

import static seedu.address.logic.commands.CommandTestUtil.*;

/**
 * A utility class containing a list of {@code Ride} objects to be used in tests.
 */
public class TypicalPersons {

    public static final Ride ALICE = new RideBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withMaintenance("94351253")
            .withTags("friends").build();
    public static final Ride BENSON = new RideBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withMaintenance("98765432")
            .withTags("owesMoney", "friends").build();
    public static final Ride CARL = new RideBuilder().withName("Carl Kurz").withMaintenance("95352563")
            .withEmail("heinz@example.com").withAddress("wall street").build();
    public static final Ride DANIEL = new RideBuilder().withName("Daniel Meier").withMaintenance("87652533")
            .withEmail("cornelia@example.com").withAddress("10th street").withTags("friends").build();
    public static final Ride ELLE = new RideBuilder().withName("Elle Meyer").withMaintenance("9482224")
            .withEmail("werner@example.com").withAddress("michegan ave").build();
    public static final Ride FIONA = new RideBuilder().withName("Fiona Kunz").withMaintenance("9482427")
            .withEmail("lydia@example.com").withAddress("little tokyo").build();
    public static final Ride GEORGE = new RideBuilder().withName("George Best").withMaintenance("9482442")
            .withEmail("anna@example.com").withAddress("4th street").build();

    // Manually added
    public static final Ride HOON = new RideBuilder().withName("Hoon Meier").withMaintenance("8482424")
            .withEmail("stefan@example.com").withAddress("little india").build();
    public static final Ride IDA = new RideBuilder().withName("Ida Mueller").withMaintenance("8482131")
            .withEmail("hans@example.com").withAddress("chicago ave").build();

    // Manually added - Ride's details found in {@code CommandTestUtil}
    public static final Ride AMY = new RideBuilder().withName(VALID_NAME_AMY).withMaintenance(VALID_MAINTENANCE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Ride BOB = new RideBuilder().withName(VALID_NAME_BOB).withMaintenance(VALID_MAINTENANCE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalPersons() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Ride ride : getTypicalPersons()) {
            ab.addPerson(ride);
        }
        return ab;
    }

    public static List<Ride> getTypicalPersons() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
