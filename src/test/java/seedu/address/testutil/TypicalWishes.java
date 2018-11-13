package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_1;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_2;
import static seedu.address.logic.commands.CommandTestUtil.VALID_DATE_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ID_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PRICE_CHARLES;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_URL_CHARLES;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.WishBook;
import seedu.address.model.WishTransaction;
import seedu.address.model.wish.Wish;

/**
 * A utility class containing a list of {@code Wish} objects to be used in tests.
 */
public class TypicalWishes {
    /* Following wishes must have unique names and must be in ascending dates. Or else the
     * following tests will fail:
     * EditCommandTest#execute_duplicateWishFilteredList_failure
     * CommandTestUtil#showWishAtIndex
     */
    public static final Wish ALICE = new WishBuilder().withName("Alice Pauline")
            .withUrl("https://www.lazada.sg/products/"
                    + "ps4-092-hori-real-arcade-pron-hayabusaps4ps3pc-i223784444-s340908955.html")
            .withDate("4/09/2020")
            .withPrice("94.35")
            .withTags("friends")
            .withSavedAmountIncrement("0.00")
            .withId("bf557b11-6b8d-4aa0-83cb-b730253bb6e2")
            .build();
    public static final Wish BENSON = new WishBuilder().withName("Benson Meier")
            .withUrl("https://www.amazon.com/Apple-iPhone-Fully-Unlocked-32GB/dp/B0731HBTZ7")
            .withDate("21/09/2020")
            .withPrice("98.76")
            .withTags("owesMoney", "friends")
            .withSavedAmountIncrement("0.00")
            .withId("b38ceb7a-2cd3-48c9-860c-3918155e4fd6")
            .build();
    public static final Wish CARL = new WishBuilder().withName("Carl Kurz")
            .withPrice("98.76")
            .withDate("21/09/2020")
            .withUrl("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV")
            .withSavedAmountIncrement("0.00")
            .withId("a758eec0-6056-4737-9c05-d3bf60f6f67d")
            .build();
    public static final Wish DANIEL = new WishBuilder().withName("Daniel Meier")
            .withPrice("87.65")
            .withDate("21/11/2023")
            .withUrl("https://www.amazon.com/Logitech-Mechanical-Keyboard-Romer-Switches/dp/B071VHYZ62")
            .withTags("friends")
            .withSavedAmountIncrement("0.00")
            .withId("a2dde843-9cde-4417-946d-40bc465462cf")
            .build();
    public static final Wish ELLE = new WishBuilder().withName("Elle Meyer")
            .withPrice("94.82")
            .withDate("19/07/2020")
            .withUrl("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV")
            .withSavedAmountIncrement("0.00")
            .withId("6387ec7d-da35-4569-a549-a8332e650429")
            .build();
    public static final Wish FIONA = new WishBuilder().withName("Fiona Kunz")
            .withPrice("94.82")
            .withDate("24/09/2010")
            .withUrl("https://www.lazada.sg/products/"
                    + "nintendo-switch-neon-console-1-year-local-warranty-best-seller-i180040203-s230048296.html")
            .withSavedAmountIncrement("0.00")
            .withId("dcb605d6-af12-444d-a2ee-44b5fccfe137")
            .build();
    public static final Wish GEORGE = new WishBuilder().withName("George Best")
            .withPrice("94.82")
            .withDate("24/09/2019")
            .withUrl("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV")
            .withSavedAmountIncrement("0.00")
            .withId("32ea1ae8-f1da-435c-a6a8-ca501a9650c1")
            .build();

    // Manually added
    public static final Wish HOON = new WishBuilder().withName("Hoon Meier")
            .withPrice("84.82")
            .withDate("28/08/2019")
            .withUrl("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV")
            .withSavedAmountIncrement("0.00")
            .withId("ace52214-c1d5-4dff-a05c-b0e6ac1c5fa9")
            .build();
    public static final Wish IDA = new WishBuilder().withName("Ida Mueller")
            .withPrice("84.82")
            .withDate("02/09/2019")
            .withUrl("https://www.amazon.com/EVGA-GeForce-Gaming-GDDR5X-Technology/dp/B0762Q49NV")
            .withSavedAmountIncrement("0.00")
            .withId("c8e1624c-9d91-4c3d-85e1-dee255126944")
            .build();

    // Manually added - Wish's details found in {@code CommandTestUtil}
    public static final Wish AMY = new WishBuilder().withName(VALID_NAME_AMY).withPrice(VALID_PRICE_AMY)
            .withDate(VALID_DATE_1).withUrl(VALID_URL_AMY).withTags(VALID_TAG_FRIEND)
            .withSavedAmountIncrement("0.00")
            .withId(VALID_ID_AMY)
            .build();
    public static final Wish BOB = new WishBuilder().withName(VALID_NAME_BOB).withPrice(VALID_PRICE_BOB)
            .withDate(VALID_DATE_2).withUrl(VALID_URL_BOB)
            .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).withSavedAmountIncrement("0.00")
            .withId(VALID_ID_BOB)
            .build();
    public static final Wish CHARLES = new WishBuilder().withName(VALID_NAME_CHARLES).withPrice(VALID_PRICE_CHARLES)
            .withDate(VALID_DATE_CHARLES).withUrl(VALID_URL_CHARLES)
            .withId(VALID_ID_CHARLES)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalWishes() {} // prevents instantiation1

    /**
     * Returns an {@code WishBook} with all the typical wishes.
     */
    public static WishBook getTypicalWishBook() {
        WishBook ab = new WishBook();
        for (Wish wish : getTypicalWishes()) {
            ab.addWish(wish);
        }
        return ab;
    }

    /**
     * Returns a {@code WishTransaction} with all the typical wish transactions.
     * Assumption: no previous wish transaction available other than current state of {@code WishBook}.
     */
    public static WishTransaction getTypicalWishTransaction() {
        return new WishTransaction(getTypicalWishBook());
    }

    public static List<Wish> getTypicalWishes() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
