package seedu.address.model;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_VOLUNTEERS;
import static seedu.address.testutil.TypicalEvents.BLOOD;
import static seedu.address.testutil.TypicalEvents.YOUTH;
import static seedu.address.testutil.TypicalRecords.R1;
import static seedu.address.testutil.TypicalRecords.R2;
import static seedu.address.testutil.TypicalVolunteers.CARL;
import static seedu.address.testutil.TypicalVolunteers.DANIEL;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.volunteer.NameContainsKeywordsPredicate;
import seedu.address.testutil.AddressBookBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    //// Test switch
    @Test
    public void setCurrentContext_nullContextId_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.setCurrentContext(null);
    }

    @Test
    public void setCurrentContext_validContextId_assertTrue() {
        modelManager.setCurrentContext(Context.EVENT_CONTEXT_ID);
        assertEquals(modelManager.getContextId(), Context.EVENT_CONTEXT_ID);
        assertEquals(modelManager.getContextName(), Context.EVENT_CONTEXT_NAME);

        modelManager.setCurrentContext(Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(modelManager.getContextId(), Context.VOLUNTEER_CONTEXT_ID);
        assertEquals(modelManager.getContextName(), Context.VOLUNTEER_CONTEXT_NAME);
    }


    //// Test volunteer
    @Test
    public void hasVolunteer_nullVolunteer_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasVolunteer(null);
    }

    @Test
    public void hasVolunteer_volunteerNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasVolunteer(CARL));
    }

    @Test
    public void hasVolunteer_volunteerInAddressBook_returnsTrue() {
        modelManager.addVolunteer(CARL);
        assertTrue(modelManager.hasVolunteer(CARL));
    }

    @Test
    public void getFilteredVolunteerList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredVolunteerList().remove(0);
    }

    //// Test event
    @Test
    public void hasEvent_nullEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEvent(null);
    }

    @Test
    public void hasEvent_eventNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasEvent(BLOOD));
    }

    @Test
    public void hasEvent_eventInAddressBook_returnsTrue() {
        modelManager.addEvent(BLOOD);
        assertTrue(modelManager.hasEvent(BLOOD));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }

    //// Test Record
    @Test
    public void hasRecord_nullRecord_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasRecord(null);
    }

    @Test
    public void hasRecord_recordNotInAddressBook_returnsFalse() {
        assertFalse(modelManager.hasRecord(R1));
    }

    @Test
    public void hasRecord_recordInAddressBook_returnsTrue() {
        modelManager.addRecord(R1);
        assertTrue(modelManager.hasRecord(R1));
    }

    @Test
    public void getFilteredRecordList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredRecordList().remove(0);
    }

    @Test
    public void equals() {
        AddressBook addressBook = new AddressBookBuilder()
                .withVolunteer(CARL).withVolunteer(DANIEL)
                .withEvent(BLOOD).withEvent(YOUTH)
                .withRecord(R1).withRecord(R2).build();
        AddressBook differentAddressBook = new AddressBook();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(addressBook, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(addressBook, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentAddressBook, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = CARL.getName().fullName.split("\\s+");
        modelManager.updateFilteredVolunteerList(new NameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(addressBook, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredVolunteerList(PREDICATE_SHOW_ALL_VOLUNTEERS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setAddressBookFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(addressBook, differentUserPrefs)));
    }
}
