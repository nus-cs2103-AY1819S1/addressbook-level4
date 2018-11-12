package seedu.address.model.restaurant;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_NAME_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_PHONE_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalRestaurants.BOB;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_A;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.testutil.RestaurantBuilder;

public class RestaurantTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Restaurant restaurant = new RestaurantBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        restaurant.getTags().remove(0);
    }

    @Test
    public void isSameRestaurant() {
        // same object -> returns true
        assertTrue(RESTAURANT_A.isSameRestaurant(RESTAURANT_A));

        // null -> returns false
        assertFalse(RESTAURANT_A.isSameRestaurant(null));

        // different phone and email -> returns false
        Restaurant editedAlice = new RestaurantBuilder(RESTAURANT_A)
                .withPhone(VALID_PHONE_BOB).build();
        assertFalse(RESTAURANT_A.isSameRestaurant(editedAlice));

        // different name -> returns false
        editedAlice = new RestaurantBuilder(RESTAURANT_A).withName(VALID_NAME_BOB).build();
        assertFalse(RESTAURANT_A.isSameRestaurant(editedAlice));

        // same name, same phone, different attributes -> returns true
        editedAlice = new RestaurantBuilder(RESTAURANT_A).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertTrue(RESTAURANT_A.isSameRestaurant(editedAlice));

        // same name, same email, different attributes -> returns true
        editedAlice = new RestaurantBuilder(RESTAURANT_A).withPhone(VALID_PHONE_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND).build();
        assertFalse(RESTAURANT_A.isSameRestaurant(editedAlice));

        // same name, same phone, same email, different attributes -> returns true
        editedAlice = new RestaurantBuilder(RESTAURANT_A).withAddress(VALID_ADDRESS_BOB).withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(RESTAURANT_A.isSameRestaurant(editedAlice));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Restaurant aliceCopy = new RestaurantBuilder(RESTAURANT_A).build();
        assertTrue(RESTAURANT_A.equals(aliceCopy));

        // same object -> returns true
        assertTrue(RESTAURANT_A.equals(RESTAURANT_A));

        // null -> returns false
        assertFalse(RESTAURANT_A.equals(null));

        // different type -> returns false
        assertFalse(RESTAURANT_A.equals(5));

        // different restaurant -> returns false
        assertFalse(RESTAURANT_A.equals(BOB));

        // different name -> returns false
        Restaurant editedAlice = new RestaurantBuilder(RESTAURANT_A).withName(VALID_NAME_BOB).build();
        assertFalse(RESTAURANT_A.equals(editedAlice));

        // different phone -> returns false
        editedAlice = new RestaurantBuilder(RESTAURANT_A).withPhone(VALID_PHONE_BOB).build();
        assertFalse(RESTAURANT_A.equals(editedAlice));

        // different address -> returns false
        editedAlice = new RestaurantBuilder(RESTAURANT_A).withAddress(VALID_ADDRESS_BOB).build();
        assertFalse(RESTAURANT_A.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new RestaurantBuilder(RESTAURANT_A).withTags(VALID_TAG_HUSBAND).build();
        assertFalse(RESTAURANT_A.equals(editedAlice));
    }
}
