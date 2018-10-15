package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
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

import seedu.address.model.Anakin;
import seedu.address.model.anakindeck.AnakinDeck;
import seedu.address.model.person.Person;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class AnakinTypicalDecks {

    public static final AnakinDeck DECK_A = new AnakinDeckBuilder().withName("Algebra").build();
    public static final AnakinDeck DECK_B = new AnakinDeckBuilder().withName("Bacon Salad Recipe").build();
    public static final AnakinDeck DECK_C = new AnakinDeckBuilder().withName("Calculus").build();
    public static final AnakinDeck DECK_D = new AnakinDeckBuilder().withName("Darwinian Studies").build();
    public static final AnakinDeck DECK_E = new AnakinDeckBuilder().withName("Epistemology (Philosophy)").build();

    // Manually added
    public static final Person DECK_F = new PersonBuilder().withName("Finance").build();
    public static final Person DECK_G = new PersonBuilder().withName("Geometry").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Person AMY = new PersonBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Person BOB = new PersonBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private AnakinTypicalDecks() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical persons.
     */
    public static Anakin getTypicalAnakin() {
        Anakin ak = new Anakin();
        for (AnakinDeck deck : getTypicalDecks()) {
            ak.addDeck(deck);
        }
        return ak;
    }

    public static List<AnakinDeck> getTypicalDecks() {
        return new ArrayList<>(Arrays.asList(DECK_A, DECK_B, DECK_B, DECK_C, DECK_D, DECK_E));
    }
}
