package seedu.address.model;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.testutil.TypicalEvents.CHOIR_PRACTICE;
import static seedu.address.testutil.TypicalEvents.CS2103_LECTURE;
import static seedu.address.testutil.TypicalEvents.CS2104_TUTORIAL;

import java.nio.file.Paths;
import java.util.Arrays;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.model.calendarevent.FuzzySearchFilterPredicate;
import seedu.address.testutil.SchedulerBuilder;

public class ModelManagerTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private ModelManager modelManager = new ModelManager();

    @Test
    public void hasCalendarEvent_nullCalendarEvent_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        modelManager.hasCalendarEvent(null);
    }

    @Test
    public void hasCalendarEvent_calendarEventNotInScheduler_returnsFalse() {
        assertFalse(modelManager.hasCalendarEvent(CS2103_LECTURE));
    }

    @Test
    public void hasCalendarEvent_calendarEventInScheduler_returnsTrue() {
        modelManager.addCalendarEvent(CS2103_LECTURE);
        assertTrue(modelManager.hasCalendarEvent(CS2103_LECTURE));
    }

    @Test
    public void getFilteredCalendarEventList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        modelManager.getFilteredAndSortedCalendarEventList().remove(0);
    }

    @Test
    public void equals() {
        Scheduler scheduler = new SchedulerBuilder().withCalendarEvent(CS2103_LECTURE)
                                    .withCalendarEvent(CS2104_TUTORIAL).withCalendarEvent(CHOIR_PRACTICE).build();
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

        // different scheduler -> returns false
        assertFalse(modelManager.equals(new ModelManager(differentScheduler, userPrefs)));

        // different filteredList -> returns false
        String[] keywords = CS2103_LECTURE.getTitle().value.split("\\s+");
        modelManager.updateFilteredCalendarEventList(new FuzzySearchFilterPredicate(Arrays.asList(keywords)));
        assertFalse(modelManager.equals(new ModelManager(scheduler, userPrefs)));

        // resets modelManager to initial state for upcoming tests
        modelManager.clearAllPredicatesAndComparators();

        // different userPrefs -> returns true
        UserPrefs differentUserPrefs = new UserPrefs();
        differentUserPrefs.setSchedulerFilePath(Paths.get("differentFilePath"));
        assertTrue(modelManager.equals(new ModelManager(scheduler, differentUserPrefs)));
    }
}
