package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.restaurant.Restaurant;

/**
 * A utility class containing a list of {@code Restaurant} objects to be used in tests.
 */
public class TypicalRestaurants {

    public static final Restaurant ALICE = new RestaurantBuilder().withName("Alice Pauline")
            .withAddress("123, Jurong West Ave 6, #08-111")
            .withPhone("94351253")
            .withTags("friends").build();
    public static final Restaurant BENSON = new RestaurantBuilder().withName("Benson Meier")
            .withAddress("311, Clementi Ave 2, #02-25")
            .withTags("owesMoney", "friends").build();
    public static final Restaurant CARL = new RestaurantBuilder().withName("Carl Kurz").withPhone("95352563")
            .withAddress("wall street").build();
    public static final Restaurant DANIEL = new RestaurantBuilder().withName("Daniel Meier").withPhone("87652533")
            .withAddress("10th street").withTags("friends").build();
    public static final Restaurant ELLE = new RestaurantBuilder().withName("Elle Meyer").withPhone("9482224")
            .withAddress("michegan ave").build();
    public static final Restaurant FIONA = new RestaurantBuilder().withName("Fiona Kunz").withPhone("9482427")
            .withAddress("little tokyo").build();
    public static final Restaurant GEORGE = new RestaurantBuilder().withName("George Best").withPhone("9482442")
            .withAddress("4th street").build();

    // Manually added
    public static final Restaurant HOON = new RestaurantBuilder().withName("Hoon Meier").withPhone("8482424")
            .withAddress("little india").build();
    public static final Restaurant IDA = new RestaurantBuilder().withName("Ida Mueller").withPhone("8482131")
            .withAddress("chicago ave").build();

    // Manually added - Restaurant's details found in {@code CommandTestUtil}
    public static final Restaurant AMY = new RestaurantBuilder().withName(VALID_NAME_AMY).withPhone(VALID_PHONE_AMY)
           .withAddress(VALID_ADDRESS_AMY).withTags(VALID_TAG_FRIEND).build();
    public static final Restaurant BOB = new RestaurantBuilder().withName(VALID_NAME_BOB).withPhone(VALID_PHONE_BOB)
            .withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND)
            .build();

    public static final String KEYWORD_MATCHING_MEIER = "Meier"; // A keyword that matches MEIER

    private TypicalRestaurants() {} // prevents instantiation

    /**
     * Returns an {@code AddressBook} with all the typical restaurants.
     */
    public static AddressBook getTypicalAddressBook() {
        AddressBook ab = new AddressBook();
        for (Restaurant restaurant : getTypicalRestaurants()) {
            ab.addRestaurant(restaurant);
        }
        return ab;
    }

    public static List<Restaurant> getTypicalRestaurants() {
        return new ArrayList<>(Arrays.asList(ALICE, BENSON, CARL, DANIEL, ELLE, FIONA, GEORGE));
    }
}
