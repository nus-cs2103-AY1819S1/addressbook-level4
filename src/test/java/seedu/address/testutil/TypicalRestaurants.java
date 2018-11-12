package seedu.address.testutil;

import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_AMY;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_FRIEND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.logic.commands.CommandTestUtil.VALID_WAA_COW_REVIEWS;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.model.AddressBook;
import seedu.address.model.restaurant.Restaurant;

/**
 * A utility class containing a list of {@code Restaurant} objects to be used in tests.
 */
public class TypicalRestaurants {

    public static final Restaurant RESTAURANT_A = new RestaurantBuilder().withName("Waa Cow")
            .withAddress("Stephen Riady Centre")
            .withPhone("63421111")
            .withTags("Western")
            .withReviews(VALID_WAA_COW_REVIEWS)
            .build();
    public static final Restaurant RESTAURANT_B = new RestaurantBuilder().withName("The Royals Bistro")
            .withAddress("Town Plaza")
            .withPhone("61221218")
            .withTags("Italian", "Halal").build();
    public static final Restaurant RESTAURANT_C = new RestaurantBuilder().withName("Subway").withPhone("66596109")
            .withAddress("Town Plaza").build();
    public static final Restaurant RESTAURANT_D = new RestaurantBuilder().withName("Starbucks").withPhone("66596081")
            .withAddress("Education Resource Centre").withTags("Cafe").build();
    public static final Restaurant RESTAURANT_E = new RestaurantBuilder().withName("Spice Table by Pines")
            .withPhone("63399912")
            .withAddress("Town Plaza").build();
    public static final Restaurant RESTAURANT_F = new RestaurantBuilder().withName("Sapore Italian Restaurant")
            .withPhone("62620287")
            .withAddress("Town Plaza").build();
    public static final Restaurant RESTAURANT_G = new RestaurantBuilder().withName("Pizza Hut").withPhone("62353535")
            .withAddress("Stephen Riady Centre").build();

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

    public static final String KEYWORD_MATCHING_STARBUCKS = "starbucks"; // A keyword that matches MEIER

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
        return new ArrayList<>(Arrays.asList(RESTAURANT_A, RESTAURANT_B,
                RESTAURANT_C, RESTAURANT_D, RESTAURANT_E, RESTAURANT_F, RESTAURANT_G));
    }
}
