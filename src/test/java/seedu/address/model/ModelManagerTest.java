package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.model.Model.PREDICATE_SHOW_ALL_EVENTS;
import static seedu.address.testutil.TypicalEvents.JANUARY_1_2018_SINGLE;
import static seedu.address.testutil.TypicalEvents.PLAY_JANUARY_1_2018_SINGLE;

import java.nio.file.Paths;
import java.util.Arrays;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.testutil.SchedulerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasEvent_nullPerson_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasEvent(null);
    }

    @Test
    public void hasEvent_eventNotInScheduler_returnsFalse() {
        assertFalse(modelManager.hasEvent(JANUARY_1_2018_SINGLE));
    }

    @Test
    public void hasEvent_eventInScheduler_returnsTrue() {
        modelManager.addEvents(List.of(JANUARY_1_2018_SINGLE));
        assertTrue(modelManager.hasEvent(JANUARY_1_2018_SINGLE));
    }

    @Test
    public void getFilteredEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredEventList().remove(0);
    }

    @Test
    public void equals() {
        Scheduler scheduler = new SchedulerBuilder().withEvent(JANUARY_1_2018_SINGLE)
                .withEvent(PLAY_JANUARY_1_2018_SINGLE).build();
        Scheduler differentScheduler = new Scheduler();
        UserPrefs userPrefs = new UserPrefs();

        // same values -> returns true
        modelManager = new ModelManager(scheduler, userPrefs);
        ModelManager modelManagerCopy = new ModelManager(scheduler, userPrefs);
        assertTrue(modelManager.equals(modelManagerCopy));

        // same object -> returns true
        assertTrue(modelManager.equals(modelManager));

        // null -> returns false
        assertFalse(modelManager.equals(null));

        // different types -> returns false
        assertFalse(modelManager.equals(5));

        // different addressBook -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentScheduler, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = JANUARY_1_2018_SINGLE.getEventName().value.split("\\s+");
        modelManager.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(scheduler, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.updateFilteredEventList(PREDICATE_SHOW_ALL_EVENTS);

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSchedulerFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(scheduler, differentUserPrefs)));
    }
}
