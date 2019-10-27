package seedu.address.logic.commands;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.commands.CommandTestUtil.assertCommandSuccess;
import static seedu.address.testutil.TypicalEntrys.WORK_FACEBOOK;

import java.util.HashSet;
import java.util.Set;

import org.junit.Test;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.model.EntryBook;
import seedu.address.model.Model;
import seedu.address.model.ModelManager;
import seedu.address.model.UserPrefs;
import seedu.address.model.awareness.Awareness;
import seedu.address.model.category.Category;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;

import seedu.address.testutil.EntryBookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code TagRetagCommand}.
 */
public class TagRetagCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    /** Build entrybook with just the default work entry */
    private EntryBook getDefaultEntryBook() {
        return new EntryBookBuilder().withEntry(WORK_FACEBOOK).build();
    }

    private ResumeEntry newEntry(ResumeEntry entry, Category category, Set<Tag> tags) {
        return new ResumeEntry(
                category, entry.getEntryInfo(),
                tags, entry.getDescription());
    }

    @Test
    public void equals() {
        Set<Tag> firstTags = new HashSet<Tag>();
        firstTags.add(new Tag("java"));

        Set<Tag> secondTags = new HashSet<Tag>();
        secondTags.add(new Tag("nus"));

        TagRetagCommand firstTagRetagCommand = new TagRetagCommand(Index.fromZeroBased(0),
                new Category("work"), firstTags);
        TagRetagCommand secondTagRetagCommand = new TagRetagCommand(Index.fromZeroBased(0),
                new Category("education"), secondTags);

        // same object -> returns true
        assertTrue(firstTagRetagCommand.equals(firstTagRetagCommand));

        // same values -> returns true
        TagRetagCommand firstTagRetagCommandCopy = new TagRetagCommand(Index.fromZeroBased(0),
                new Category("work"), firstTags);
        assertTrue(firstTagRetagCommand.equals(firstTagRetagCommandCopy));

        // different types -> returns false
        assertFalse(firstTagRetagCommand.equals(1));

        // null -> returns false
        assertFalse(firstTagRetagCommand.equals(null));

        // different criteria -> returns false
        assertFalse(firstTagRetagCommand.equals(secondTagRetagCommand));
    }

    @Test
    public void execute_category_added() {
        Model model = new ModelManager(getDefaultEntryBook(), new UserPrefs(), new Awareness());
        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        Set<Tag> tags = new HashSet<Tag>();
        Category category = new Category("fb");
        Index index = Index.fromZeroBased(0);

        String expectedMessage = String.format(TagRetagCommand.MESSAGE_SUCCESS);
        expectedModel.updateEntry(WORK_FACEBOOK, newEntry(WORK_FACEBOOK, category, tags));
        expectedModel.commitEntryBook();

        TagRetagCommand command = new TagRetagCommand(index, category, tags);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().get(0).getCategory().equals(category));
    }

    @Test
    public void execute_categoryAndTags_added() {
        Model model = new ModelManager(getDefaultEntryBook(), new UserPrefs(), new Awareness());
        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("intern"));
        tags.add(new Tag("computing"));

        Category category = new Category("fb");
        Index index = Index.fromZeroBased(0);

        String expectedMessage = String.format(TagRetagCommand.MESSAGE_SUCCESS);
        expectedModel.updateEntry(WORK_FACEBOOK, newEntry(WORK_FACEBOOK, category, tags));
        expectedModel.commitEntryBook();

        TagRetagCommand command = new TagRetagCommand(index, category, tags);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().get(0).getCategory().equals(category));
        assertTrue(model.getFilteredEntryList().get(0).getTags().size() == 2);
    }
}
