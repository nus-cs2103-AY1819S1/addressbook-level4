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

import seedu.address.model.WishBook;
import seedu.address.model.wish.Wish;

/**
 * A utility class containing a list of {@code Person} objects to be used in tests.
 */
public class TypicalWishes {

    public static final Wish ALICE = new WishBuilder().withName("Alice Pauline")
            .withUrl("123, Jurong West Ave 6, #08-111").withEmail("alice@example.com")
            .withPhone("94351253")
            .withTags("friends")
            .build();
    public static final Wish BENSON = new WishBuilder().withName("Benson Meier")
            .withUrl("311, Clementi Ave 2, #02-25")
            .withEmail("johnd@example.com").withPhone("98765432")
            .withTags("owesMoney", "friends")
            .build();
    public static final Wish CARL = new WishBuilder().withName("Carl Kurz").withPhone("95352563")
            .withEmail("heinz@example.com").withUrl("wall street").build();
    public static final Wish DANIEL = new WishBuilder().withName("Daniel Meier").withPhone("87652533")
            .withEmail("cornelia@example.com").withUrl("10th street").withTags("friends").build();
    public static final Wish ELLE = new WishBuilder().withName("Elle Meyer").withPhone("9482224")
            .withEmail("werner@example.com").withUrl("michegan ave").build();
    public static final Wish FIONA = new WishBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withEmail("lydia@example.com").withUrl("little tokyo").build();
    public static final Wish GEORGE = new WishBuilder().withName("George Best").withPhone("9482442")
            .withEmail("anna@example.com").withUrl("4th street").build();

    // Manually added
    public static final Wish HOON = new WishBuilder().withName("Hoon Meier").withPhone("8482424")
            .withEmail("stefan@example.com").withUrl("little india").build();
    public static final Wish IDA = new WishBuilder().withName("Ida Mueller").withPhone("8482131")
            .withEmail("hans@example.com").withUrl("chicago ave").build();

    // Manually added - Person's details found in {@code CommandTestUtil}
    public static final Wish AMY = new WishBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
            .withEmail(VALID_EMAIL_AMY).withUrl(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Wish BOB = new WishBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withEmail(VALID_EMAIL_BOB).withUrl(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalWishes() {} // prevents instantiation

    /**
     * Returns an {@code WishBook} with all the typical persons.
     */
    public static WishBook getTypicalWishBook() {
        WishBook ab = new WishBook();
        for (Wish wish : getTypicalWishes()) {
            ab.addWish(wish);
        }
        return ab;
    }

    public static List<Wish> getTypicalWishes() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
