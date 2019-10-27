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
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.tag.Tag;

import seedu.address.testutil.EntryBookBuilder;

/**
 * Contains integration tests (interaction with the Model) for {@code TagRmCommand}.
 */
public class TagRmCommandTest {
    private CommandHistory commandHistory = new CommandHistory();

    /** Build entrybook with just the default work entry */
    private EntryBook getDefaultEntryBook() {
        return new EntryBookBuilder().withEntry(WORK_FACEBOOK).build();
    }

    /** Add tags existing entry */
    private ResumeEntry rmTags(ResumeEntry entry, Set<Tag> rmTags) {
        Set<Tag> tags = new HashSet<>(entry.getTags());

        for (Tag tag : rmTags) {
            if (tags.contains(tag)) {
                tags.remove(tag);
            }
        }

        return new ResumeEntry(
                entry.getCategory(), entry.getEntryInfo(),
                tags, entry.getDescription());
    }

    @Test
    public void equals() {
        Set<Tag> firstTags = new HashSet<Tag>();
        firstTags.add(new Tag("java"));

        Set<Tag> secondTags = new HashSet<Tag>();
        secondTags.add(new Tag("nus"));

        TagRmCommand firstTagRmCommand = new TagRmCommand(Index.fromZeroBased(0), firstTags);
        TagRmCommand secondTagRmCommand = new TagRmCommand(Index.fromZeroBased(0), secondTags);

        // same object -> returns true
        assertTrue(firstTagRmCommand.equals(firstTagRmCommand));

        // same values -> returns true
        TagRmCommand firstTagRmCommandCopy = new TagRmCommand(Index.fromZeroBased(0), firstTags);
        assertTrue(firstTagRmCommand.equals(firstTagRmCommandCopy));

        // different types -> returns false
        assertFalse(firstTagRmCommand.equals(1));

        // null -> returns false
        assertFalse(firstTagRmCommand.equals(null));

        // different criteria -> returns false
        assertFalse(firstTagRmCommand.equals(secondTagRmCommand));
    }

    @Test
    public void execute_rmTags_tagsRemoved() {
        Model model = new ModelManager(getDefaultEntryBook(), new UserPrefs(), new Awareness());
        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("java"));
        Index index = Index.fromZeroBased(0);

        int newTagCount = 0;

        String expectedMessage = String.format(TagRmCommand.MESSAGE_SUCCESS);
        expectedModel.updateEntry(WORK_FACEBOOK, rmTags(WORK_FACEBOOK, tags));
        expectedModel.commitEntryBook();

        TagRmCommand command = new TagRmCommand(index, tags);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().get(0).getTags().size() == newTagCount);
    }

    @Test
    public void execute_rmTags_allRemoved() {
        Model model = new ModelManager(getDefaultEntryBook(), new UserPrefs(), new Awareness());
        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        Set<Tag> tags = new HashSet<Tag>();
        Index index = Index.fromZeroBased(0);

        int newTagCount = 0;

        String expectedMessage = String.format(TagRmCommand.MESSAGE_SUCCESS);
        expectedModel.updateEntry(WORK_FACEBOOK, rmTags(WORK_FACEBOOK, WORK_FACEBOOK.getTags()));
        expectedModel.commitEntryBook();

        TagRmCommand command = new TagRmCommand(index, tags);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().get(0).getTags().size() == newTagCount);
    }

    @Test
    public void execute_rmTags_nothingRemoved() {
        Model model = new ModelManager(getDefaultEntryBook(), new UserPrefs(), new Awareness());
        Model expectedModel = new ModelManager(new EntryBook(model.getEntryBook()), new UserPrefs(), new Awareness());

        Set<Tag> tags = new HashSet<Tag>();
        tags.add(new Tag("python"));
        Index index = Index.fromZeroBased(0);

        int newTagCount = 1;

        String expectedMessage = String.format(TagRmCommand.MESSAGE_SUCCESS);
        expectedModel.commitEntryBook();

        TagRmCommand command = new TagRmCommand(index, tags);
        assertCommandSuccess(command, model, commandHistory, expectedMessage, expectedModel);
        assertTrue(model.getFilteredEntryList().get(0).getTags().size() == newTagCount);
    }
}
