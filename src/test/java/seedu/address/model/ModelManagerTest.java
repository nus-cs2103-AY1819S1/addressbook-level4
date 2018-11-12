package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_RESTAURANTS;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_A;
import static seedu.address.testutil.TypicalRestaurants.RESTAURANT_B;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.restaurant.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void modelManagerIsNotNull() {
        Assert.assertNotNull(new ModelManager());
    }
    @Test
    public void hasRestaurant_nullRestaurant_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRestaurant(null);
    }

    @Test
    public void hasRestaurant_restaurantNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasRestaurant(RESTAURANT_A));
    }

    @Test
    public void hasRestaurant_restaurantInAddressBook_returnsTrue() {
        modelManager.addRestaurant(RESTAURANT_A);
        assertTrue(modelManager.hasRestaurant(RESTAURANT_A));
    }

    @Test
    public void getFilteredRestaurantList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRestaurantList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder().withRestaurant(RESTAURANT_A)
                .withRestaurant(RESTAURANT_B).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();
        UserData userData = new UserData();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs, userData);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs, userData);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs, userData)));

        // different filteredList -> returns false
        String[] keywords = RESTAURANT_A.getName().fullName.split("\\s+");
        modelManager.updateFilteredRestaurantList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs, userData)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredRestaurantList(PREDICATE_SHOW_ALL_RESTAURANTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs, userData)));
    }
}
