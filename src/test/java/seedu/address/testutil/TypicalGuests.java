package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_EMAIL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.guest.Guest;
import seedu.address.model.guest.UniqueGuestList;

/**
 * A utility class containing a list of {@code Guest} objects to be used in tests.
 */
public class TypicalGuests {

    public static final Guest ALICE = new GuestBuilder().withName("Alice Pauline")
            .withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags(TypicalTags.GUEST_TAG_VIP.tagName).build();
    public static final Guest BENSON = new GuestBuilder().withName("Benson Meier")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags(TypicalTags.GUEST_TAG_SPECIAL_NEEDS.tagName, TypicalTags.GUEST_TAG_VIP.tagName).build();
    public static final Guest CARL = new GuestBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").build();
    public static final Guest DANIEL = new GuestBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withTags("friends").build();
    public static final Guest ELLE = new GuestBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").build();
    public static final Guest FIONA = new GuestBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").build();
    public static final Guest GEORGE = new GuestBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").build();

    // Manually added
    public static final Guest HOON = new GuestBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").build();
    public static final Guest IDA = new GuestBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").build();
    public static final Guest JAKOB =
            new GuestBuilder().withName("Jakob Hitlier").withPhone("89817221")
            .withEmail("naaazi@example.de").build();

    // Manually added - Guest's details found in {@code CommandTestUtil}
    public static final Guest AMY = new GuestBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Guest BOB = new GuestBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalGuests() {} // prevents instantiation

    public static List<Guest> getTypicalGuests() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }

    public static UniqueGuestList getTypicalUniqueGuestList() {
        UniqueGuestList uniqueGuestList = new UniqueGuestList();
        uniqueGuestList.setGuests(getTypicalGuests());
        return uniqueGuestList;
    }
}
