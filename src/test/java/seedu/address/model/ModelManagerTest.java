package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_GUESTS;
import static seedu.address.testutil.TypicalGuests.ALICE;
import static seedu.address.testutil.TypicalGuests.BENSON;

import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.guest.GuestNameContainsKeywordsPredicate;
import seedu.address.model.guest.Name;
import seedu.address.testutil.ConciergeBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    /*===================== Guests Test =========================================================== */

    @Test
    public void hasGuest_nullGuest_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasGuest(null);
    }

    @Test
    public void hasGuest_guestNotInConcierge_returnsFalse() {
        assertFalse(modelManager.hasGuest(ALICE));
    }

    @Test
    public void hasGuest_guestInConcierge_returnsTrue() {
        modelManager.addGuest(ALICE);
        assertTrue(modelManager.hasGuest(ALICE));
    }

    @Test
    public void getFilteredGuestList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredGuestList().remove(0);
    }

    /*===================== Rooms Test =========================================================== */

    // Note: no need to test the other room methods, because they only call the methods that belong to the following
    // class, which have all already been tested in the classes' own tests.

    @Test
    public void getFilteredRoomList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRoomList().remove(0);
    }

    /*===================== Utils Test =========================================================== */

    @Test
    public void equals() {
        Concierge concierge = new ConciergeBuilder().withGuest(ALICE).withGuest(BENSON).build();
        Concierge differentConcierge = new Concierge();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(concierge, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(concierge, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different concierge -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentConcierge, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = ALICE.getName().fullName.split("\\s+");
        List<Name> namesList = new LinkedList<>();
        for (String name : keywords) {
            namesList.add(new Name(name));
        }
        modelManager.updateFilteredGuestList(new GuestNameContainsKeywordsPredicate(namesList));
        assertFalse(modelManager.equals(new ModelManager(concierge, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredGuestList(PREDICATE_SHOW_ALL_GUESTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setConciergeFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(concierge, differentUserPrefs)));
    }
}
