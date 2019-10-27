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
 * Contains integration tests (interaction with the Model) for {@code TagAddCommand}.
 */
public class TagAddCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    /** Build entrybook with just the default work entry */
    private EntryBook getDefaultEntryBook() {
        return new EntryBookBuilder().withEntry(WORK_FACEBOOK).build();
    }

    /** Add tags existing entry */
    private ResumeEntry addTags(ResumeEntry entry, Set<Tag> tags) {
        tags.addAll(entry.getTags());

        return new ResumeEntry(
                entry.getCategory(), entry.getEntryInfo(),
                tags, entry.getDescription());
    }

    private ResumeEntry addCategory(ResumeEntry entry, Category category) {
        return new ResumeEntry(
                category, entry.getEntryInfo(),
                entry.getTags(), entry.getDescription());
    }

    @Test
    public void equals() {
        Set<Tag> firstTags = new HashSet<Tag>();
        firstTags.add(new Tag("java"));

        Set<Tag> secondTags = new HashSet<Tag>();
        secondTags.add(new Tag("nus"));

        TagAddCommand firstTagAddCommand = new TagAddCommand(Index.fromZeroBased(0),
                new Category("work"), firstTags);
        TagAddCommand secondTagAddCommand = new TagAddCommand(Index.fromZeroBased(0),
                new Category("education"), secondTags);

        // same object -> returns true
        assertTrue(firstTagAddCommand.equals(firstTagAddCommand));

        // same values -> returns true
        TagAddCommand firstTagAddCommandCopy = new TagAddCommand(Index.fromZeroBased(0),
                new Category("work"), firstTags);
        assertTrue(firstTagAddCommand.equals(firstTagAddCommandCopy));

        // different types -> returns false
        assertFalse(firstTagAddCommand.equals(1));

        // null -> returns false
        assertFalse(firstTagAddCommand.equals(null));

        // different criteria -> returns false
        assertFalse(firstTagAddCommand.equals(secondTagAddCommand));
    }

    @Test
    public void execute_addTags_tagsAdded() {
        Model model = new ModelManager(getDefaultEntryBook(), new UserPrefs(), new Awareness());
        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("python"));
        Index index = Index.fromZeroBased(0);

        int newTagCount = 2;

        String expectedMessage = String.format(TagAddCommand.MESSAGE_SUCCESS);
        expectedModel.updateEntry(WORK_FACEBOOK, addTags(WORK_FACEBOOK, tags));
        expectedModel.commitEntryBook();

        TagAddCommand command = new TagAddCommand(index, null, tags);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().get(0).getTags().size() == newTagCount);
    }

    @Test
    public void execute_addCategory_categoryAdded() {
        Model model = new ModelManager(getDefaultEntryBook(), new UserPrefs(), new Awareness());
        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        Set<Tag> tags = new HashSet<Tag>();
        Category category = new Category("fb");
        Index index = Index.fromZeroBased(0);

        String expectedMessage = String.format(TagAddCommand.MESSAGE_SUCCESS);
        expectedModel.updateEntry(WORK_FACEBOOK, addCategory(WORK_FACEBOOK, category));
        expectedModel.commitEntryBook();

        TagAddCommand command = new TagAddCommand(index, category, tags);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().get(0).getCategory().equals(category));
    }
}
