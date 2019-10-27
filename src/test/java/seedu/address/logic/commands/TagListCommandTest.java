package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.commons.core.Messages.MESSAGE_ENTRIES_LISTED_OVERVIEW;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntryBook;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import org.junit.Test;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;

/**
 * Contains integration tests (interaction with the Model) for {@code TagListCommand}.
 */
public class TagListCommandTest {
    private Model model = new ModelManager(getTypicalEntryBook(), new UserPrefs(), new Awareness());
    private Model expectedModel = new ModelManager(getTypicalEntryBook(), new UserPrefs(), new Awareness());
    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void equals() {
        TagListCommand firstTagListCommand = new TagListCommand("work", Arrays.asList("java"));
        TagListCommand secondTagListCommand = new TagListCommand("education", Arrays.asList("nus"));

        // same object -> returns true
        assertTrue(firstTagListCommand.equals(firstTagListCommand));

        // same values -> returns true
        TagListCommand firstTagListCommandCopy = new TagListCommand("work", Arrays.asList("java"));
        assertTrue(firstTagListCommand.equals(firstTagListCommandCopy));

        // different types -> returns false
        assertFalse(firstTagListCommand.equals(1));

        // null -> returns false
        assertFalse(firstTagListCommand.equals(null));

        // different criteria -> returns false
        assertFalse(firstTagListCommand.equals(secondTagListCommand));
    }

    @Test
    public void execute_zeroKeywords_fullListDisplay() {
        String category = "";
        List<String> tags = new ArrayList<>();

        int count = 5;

        String expectedMessage = String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW, count);
        TagListCommand command = new TagListCommand(category, tags);
        expectedModel.updateFilteredEntryList(expectedModel.mkPredicate(category, tags));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().size() == count);
    }

    @Test
    public void execute_onlyCategory_works() {
        String category = "work";
        List<String> tags = new ArrayList<>();

        int count = 2;

        String expectedMessage = String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW, count);
        TagListCommand command = new TagListCommand(category, tags);
        expectedModel.updateFilteredEntryList(expectedModel.mkPredicate(category, tags));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().size() == count);
    }

    @Test
    public void execute_onlyTags_works() {
        String category = "";
        List<String> tags = new ArrayList<>();
        tags.add("java");

        int count = 2;

        String expectedMessage = String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW, count);
        TagListCommand command = new TagListCommand(category, tags);
        expectedModel.updateFilteredEntryList(expectedModel.mkPredicate(category, tags));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().size() == count);
    }

    @Test
    public void execute_bothCategoryAndTags_works() {
        String category = "work";
        List<String> tags = new ArrayList<>();
        tags.add("machinelearning");

        int count = 0;

        String expectedMessage = String.format(MESSAGE_ENTRIES_LISTED_OVERVIEW, count);
        TagListCommand command = new TagListCommand(category, tags);
        expectedModel.updateFilteredEntryList(expectedModel.mkPredicate(category, tags));
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().size() == count);
    }
}
