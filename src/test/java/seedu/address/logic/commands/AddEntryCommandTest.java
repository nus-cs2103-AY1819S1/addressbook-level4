package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import javafx.collections.ObservableList;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.EntryBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyEntryBook;
import seedu.address.model.UserParticulars;
import seedu.address.model.entry.ResumeEntry;
import seedu.address.model.resume.Resume;
import seedu.address.model.template.Template;
import seedu.address.model.util.EntryBuilder;

public class AddEntryCommandTest {
    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullEntry_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new AddEntryCommand(null);
    }

    @Test
    public void execute_entryAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingEntryAdded modelStub = new ModelStubAcceptingEntryAdded();
        ResumeEntry entry = new EntryBuilder().build();

        CommandResult commandResult = new AddEntryCommand(entry).execute(modelStub, commandHistory);

        assertEquals(String.format(AddEntryCommand.MESSAGE_SUCCESS, entry), commandResult.feedbackToUser);
        //to be implemented later
        assertEquals(Arrays.asList(entry), modelStub.entriesAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateEntry_throwsCommandException() throws Exception {
        ResumeEntry validEntry = new EntryBuilder().build();
        AddEntryCommand addEntryCommand = new AddEntryCommand(validEntry);
        ModelStub modelStub = new ModelStubWithEntry(validEntry);

        thrown.expect(CommandException.class);
        thrown.expectMessage(AddEntryCommand.MESSAGE_DUPLICATE_ENTRY);
        addEntryCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        ResumeEntry sourceAcademy = new EntryBuilder().withTitle("SOURCE ACADEMY").build();
        ResumeEntry facebook = new EntryBuilder().withTitle("Facebook").build();

        // same object -> returns true
        assertTrue(sourceAcademy.equals(sourceAcademy));

        // same values -> returns true
        AddEntryCommand copied = new AddEntryCommand(sourceAcademy);
        assertTrue(new AddEntryCommand(sourceAcademy).equals(copied));

        // different types -> returns false
        assertFalse(sourceAcademy.equals(1));

        // null -> returns false
        assertFalse(sourceAcademy.equals(null));

        // different person -> returns false
        assertFalse(sourceAcademy.equals(facebook));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {
        @Override
        public ReadOnlyEntryBook getEntryBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasEntry(ResumeEntry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addEntry(ResumeEntry entry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEntry(ResumeEntry target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateEntry(ResumeEntry target, ResumeEntry editedEntry) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ResumeEntry> getFilteredEntryList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ResumeEntry> getFilteredEntryList(Predicate<ResumeEntry> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<ResumeEntry> getFilteredEntryList(String category, List<String> tags) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredEntryList(Predicate<ResumeEntry> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public UserParticulars getUserParticulars() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void loadTemplate(Path filePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitEntryBook() {
            throw new AssertionError("This method should not be called.");
        }

        public void saveLastResume(Path filepath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void generateResume() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Resume> getLastResume() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<Template> getLoadedTemplate() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public String getPossibleEventName(String expression) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Predicate<ResumeEntry> mkPredicate(String category, List<String> tags) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Optional<ResumeEntry> getContextualResumeEntry(String possibleEventName) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single entry
     * This replaces actual model so that any test carried out here does
     * not depend on the actual model
     */
    private class ModelStubWithEntry extends ModelStub {
        private final ResumeEntry entry;

        ModelStubWithEntry(ResumeEntry entry) {
            requireNonNull(entry);
            this.entry = entry;
        }

        @Override
        public boolean hasEntry(ResumeEntry entry) {
            requireNonNull(entry);
            return this.entry.isSameEntry(entry);
        }

    }

    /**
     * A Model stub that always accept the person being added.
     */

    private class ModelStubAcceptingEntryAdded extends ModelStub {
        final ArrayList<ResumeEntry> entriesAdded = new ArrayList<>();

        @Override
        public boolean hasEntry(ResumeEntry entry) {
            requireNonNull(entry);
            return entriesAdded.stream().anyMatch(entry::isSameEntry);
        }

        @Override
        public void addEntry(ResumeEntry entry) {
            requireNonNull(entry);
            entriesAdded.add(entry);
        }


        @Override
        public void commitEntryBook() {
            // called by {@code AddEntryCommand#execute()}
        }

        @Override
        public ReadOnlyEntryBook getEntryBook() {
            return new EntryBook();
        }
    }
}
