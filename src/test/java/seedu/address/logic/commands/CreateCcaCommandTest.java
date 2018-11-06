package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;
import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;

import java.nio.file.Path;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Set;
import java.util.function.Predicate;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;
import org.simplejavamail.email.Email;

import javafx.collections.ObservableList;
import seedu.address.commons.events.model.EmailLoadedEvent;
import seedu.address.commons.events.storage.CalendarLoadedEvent;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.BudgetBook;
import seedu.address.model.Model;
import seedu.address.model.ReadOnlyAddressBook;
import seedu.address.model.ReadOnlyBudgetBook;
import seedu.address.model.calendar.Month;
import seedu.address.model.calendar.Year;
import seedu.address.model.cca.Cca;
import seedu.address.model.cca.CcaName;
import seedu.address.model.person.Name;
import seedu.address.model.person.Person;
import seedu.address.testutil.CcaBuilder;

//@@author ericyjw
public class CreateCcaCommandTest {

    private static final CommandHistory EMPTY_COMMAND_HISTORY = new CommandHistory();

    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private CommandHistory commandHistory = new CommandHistory();

    @Test
    public void constructor_nullCca_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        new CreateCcaCommand(null);
    }

    @Test
    public void execute_ccaAcceptedByModel_addSuccessful() throws Exception {
        ModelStubAcceptingCcaAdded modelStub = new ModelStubAcceptingCcaAdded();
        Cca validCca = new CcaBuilder().build();

        CommandResult commandResult = new CreateCcaCommand(validCca).execute(modelStub, commandHistory);

        assertEquals(String.format(CreateCcaCommand.MESSAGE_SUCCESS, validCca), commandResult.feedbackToUser);
        assertEquals(Arrays.asList(validCca), modelStub.ccasAdded);
        assertEquals(EMPTY_COMMAND_HISTORY, commandHistory);
    }

    @Test
    public void execute_duplicateCca_throwsCommandException() throws Exception {
        Cca validCca = new CcaBuilder().build();
        CreateCcaCommand createCcaCommand = new CreateCcaCommand(validCca);
        ModelStub modelStub = new ModelStubWithCca(validCca);

        thrown.expect(CommandException.class);
        thrown.expectMessage(CreateCcaCommand.MESSAGE_DUPLICATE_CCA);
        createCcaCommand.execute(modelStub, commandHistory);
    }

    @Test
    public void equals() {
        Cca basketball = new CcaBuilder().withCcaName("Basketball").build();
        Cca softball = new CcaBuilder().withCcaName("Softball").build();
        CreateCcaCommand createBasketballCommand = new CreateCcaCommand(basketball);
        CreateCcaCommand createSoftballCommand = new CreateCcaCommand(softball);

        // same object -> returns true
        assertTrue(createBasketballCommand.equals(createBasketballCommand));

        // same values -> returns true
        CreateCcaCommand createBasketballCommandCopy = new CreateCcaCommand(basketball);
        assertTrue(createBasketballCommand.equals(createBasketballCommandCopy));

        // different types -> returns false
        assertFalse(createBasketballCommand.equals(1));

        // null -> returns false
        assertFalse(createBasketballCommand.equals(null));

        // different cca -> returns false
        assertFalse(createBasketballCommand.equals(createSoftballCommand));
    }

    /**
     * A default model stub that have all of the methods failing.
     */
    private class ModelStub implements Model {

        @Override
        public void addPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addCca(Cca cca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void resetData(ReadOnlyAddressBook newData) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyAddressBook getAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ReadOnlyBudgetBook getBudgetBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Cca> getFilteredCcaList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public Set<String> getExistingEmails() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Person person) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean hasPerson(Name person) {
            return false;
        }

        @Override
        public boolean hasCca(CcaName ccaName) {
            return false;
        }

        @Override
        public boolean hasCca(Cca cca) {
            return false;
        }

        @Override
        public boolean hasCca(Person toAdd) {
            return false;
        }

        @Override
        public boolean hasEmail(String fileName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deletePerson(Person target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void clearMultiplePersons(List<Person> target) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void removeTagsFromPersons(List<Person> target, List<Person> original) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updatePerson(Person target, Person editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateCca(Cca target, Cca editedCca) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateMultiplePersons(List<Person> target, List<Person> editedPerson) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void exportAddressBook(Path filePath) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void addMultiplePersons(List<Person> personList) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public ObservableList<Person> getFilteredPersonList() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredPersonList(Predicate<Person> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateFilteredCcaList(Predicate<Cca> predicate) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canUndoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean canRedoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void undoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void redoAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitAddressBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void commitBudgetBook() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveEmail(Email email) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void saveComposedEmail(Email email) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEmail(String fileName) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleCalendarLoadedEvent(CalendarLoadedEvent event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isExistingCalendar(Year year, Month month) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isLoadedCalendar(Year year, Month month) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isValidDate(Year year, Month month, int date) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isValidTime(int hour, int minute) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isValidTimeFrame(int startDate, int startHour, int startMinute,
                                        int endDate, int endHour, int endMinute) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createCalendar(Year year, Month month) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void loadCalendar(Year year, Month month) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void handleEmailLoadedEvent(EmailLoadedEvent event) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createAllDayEvent(Year year, Month month, int date, String title) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void createEvent(Year year, Month month, int startDate, int startHour, int startMin,
                                int endDate, int endHour, int endMin, String title) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public boolean isExistingEvent(Year year, Month month, int startDate, int endDate, String title) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteEvent(Year year, Month month, int startDate, int endDate, String title) {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void updateExistingCalendar() {
            throw new AssertionError("This method should not be called.");
        }

        @Override
        public void deleteCca(Cca ccaToDelete) {
            throw new AssertionError("This method should not be called.");
        }
    }

    /**
     * A Model stub that contains a single person.
     */
    private class ModelStubWithCca extends ModelStub {
        private final Cca cca;

        ModelStubWithCca(Cca cca) {
            requireNonNull(cca);
            this.cca = cca;
        }

        @Override
        public boolean hasCca(Cca cca) {
            requireNonNull(cca);
            return this.cca.isSameCcaName(cca);
        }
    }

    /**
     * A Model stub that always accept the cca being added.
     */
    private class ModelStubAcceptingCcaAdded extends ModelStub {
        private final ArrayList<Cca> ccasAdded = new ArrayList<>();

        @Override
        public boolean hasCca(Cca cca) {
            requireNonNull(cca);
            return ccasAdded.stream().anyMatch(cca::isSameCcaName);
        }

        @Override
        public void addCca(Cca cca) {
            requireNonNull(cca);
            ccasAdded.add(cca);
        }

        @Override
        public void commitBudgetBook() {
            // called by {@code CreateCcaCommand#execute()}
        }

        @Override
        public ReadOnlyBudgetBook getBudgetBook() {
            return new BudgetBook();
        }
    }

}
