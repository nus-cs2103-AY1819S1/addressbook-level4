package seedu.address.model.restaurant;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.VALID_ADDRESS_BOB;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TAG_HUSBAND;
import static seedu.address.testutil.TypicalRestaurants.BOB;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_A;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.restaurant.exceptions.DuplicateRestaurantException;
import seedu.address.model.restaurant.exceptions.RestaurantNotFoundException;
import seedu.address.testutil.RestaurantBuilder;

public class UniqueRestaurantListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueRestaurantList uniqueRestaurantList = new UniqueRestaurantList();

    @Test
    public void contains_nullRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRestaurantList.contains(null);
    }

    @Test
    public void contains_restaurantNotInList_returnsFalse() {
        assertFalse(uniqueRestaurantList.contains(RESTAURANT_A));
    }

    @Test
    public void contains_restaurantInList_returnsTrue() {
        uniqueRestaurantList.add(RESTAURANT_A);
        assertTrue(uniqueRestaurantList.contains(RESTAURANT_A));
    }

    @Test
    public void contains_restaurantWithSameIdentityFieldsInList_returnsTrue() {
        uniqueRestaurantList.add(RESTAURANT_A);
        Restaurant editedAlice = new RestaurantBuilder(RESTAURANT_A).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        assertTrue(uniqueRestaurantList.contains(editedAlice));
    }

    @Test
    public void add_nullRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRestaurantList.add(null);
    }

    @Test
    public void add_duplicateRestaurant_throwsDuplicateRestaurantException() {
        uniqueRestaurantList.add(RESTAURANT_A);
        thrown.expect(DuplicateRestaurantException.class);
        uniqueRestaurantList.add(RESTAURANT_A);
    }

    @Test
    public void setRestaurant_nullTargetRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRestaurantList.setRestaurant(null, RESTAURANT_A);
    }

    @Test
    public void setRestaurant_nullEditedRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRestaurantList.setRestaurant(RESTAURANT_A, null);
    }

    @Test
    public void setRestaurant_targetRestaurantNotInList_throwsRestaurantNotFoundException() {
        thrown.expect(RestaurantNotFoundException.class);
        uniqueRestaurantList.setRestaurant(RESTAURANT_A, RESTAURANT_A);
    }

    @Test
    public void setRestaurant_editedRestaurantIsSameRestaurant_success() {
        uniqueRestaurantList.add(RESTAURANT_A);
        uniqueRestaurantList.setRestaurant(RESTAURANT_A, RESTAURANT_A);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(RESTAURANT_A);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setRestaurant_editedRestaurantHasSameIdentity_success() {
        uniqueRestaurantList.add(RESTAURANT_A);
        Restaurant editedAlice = new RestaurantBuilder(RESTAURANT_A).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND)
                .build();
        uniqueRestaurantList.setRestaurant(RESTAURANT_A, editedAlice);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(editedAlice);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setRestaurant_editedRestaurantHasDifferentIdentity_success() {
        uniqueRestaurantList.add(RESTAURANT_A);
        uniqueRestaurantList.setRestaurant(RESTAURANT_A, BOB);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(BOB);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setRestaurant_editedRestaurantHasNonUniqueIdentity_throwsDuplicateRestaurantException() {
        uniqueRestaurantList.add(RESTAURANT_A);
        uniqueRestaurantList.add(BOB);
        thrown.expect(DuplicateRestaurantException.class);
        uniqueRestaurantList.setRestaurant(RESTAURANT_A, BOB);
    }

    @Test
    public void remove_nullRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRestaurantList.remove(null);
    }

    @Test
    public void remove_restaurantDoesNotExist_throwsRestaurantNotFoundException() {
        thrown.expect(RestaurantNotFoundException.class);
        uniqueRestaurantList.remove(RESTAURANT_A);
    }

    @Test
    public void remove_existingRestaurant_removesRestaurant() {
        uniqueRestaurantList.add(RESTAURANT_A);
        uniqueRestaurantList.remove(RESTAURANT_A);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setRestaurants_nullUniqueRestaurantList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRestaurantList.setRestaurants((UniqueRestaurantList) null);
    }

    @Test
    public void setRestaurants_uniqueRestaurantList_replacesOwnListWithProvidedUniqueRestaurantList() {
        uniqueRestaurantList.add(RESTAURANT_A);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(BOB);
        uniqueRestaurantList.setRestaurants(expectedUniqueRestaurantList);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setRestaurants_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueRestaurantList.setRestaurants((List<Restaurant>) null);
    }

    @Test
    public void setRestaurants_list_replacesOwnListWithProvidedList() {
        uniqueRestaurantList.add(RESTAURANT_A);
        List<Restaurant> restaurantList = Collections.singletonList(BOB);
        uniqueRestaurantList.setRestaurants(restaurantList);
        UniqueRestaurantList expectedUniqueRestaurantList = new UniqueRestaurantList();
        expectedUniqueRestaurantList.add(BOB);
        assertEquals(expectedUniqueRestaurantList, uniqueRestaurantList);
    }

    @Test
    public void setRestaurants_listWithDuplicateRestaurants_throwsDuplicateRestaurantException() {
        List<Restaurant> listWithDuplicateRestaurants = Arrays.asList(RESTAURANT_A, RESTAURANT_A);
        thrown.expect(DuplicateRestaurantException.class);
        uniqueRestaurantList.setRestaurants(listWithDuplicateRestaurants);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueRestaurantList.asUnmodifiableObservableList().remove(0);
    }
}
