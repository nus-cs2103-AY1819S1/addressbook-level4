package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_END_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EVENT_DESCRIPTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FACULTY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_INDEX;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_START_TIME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.stream.Collectors;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.event.Event;
import seedu.address.model.event.EventDate;
import seedu.address.model.event.EventNameContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";
    public static final String VALID_FACULTY = "SOC";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;
    public static final String FACULTY_DESC = " " + PREFIX_FACULTY + VALID_FACULTY;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_FACULTY_DESC = " " + PREFIX_FACULTY + "law";

    // Events
    public static final String CLASHING_EVENT_START_TIME_DOCTORAPPT = "1410";
    public static final String CLASHING_EVENT_END_TIME_DOCTORAPPT = "1440";

    public static final String VALID_EVENT_NAME_DOCTORAPPT = "Doctor appointment";
    public static final String VALID_EVENT_NAME_MEETING = "Meeting";
    public static final String VALID_EVENT_DESC_DOCTORAPPT = "Consultation";
    public static final String VALID_EVENT_DESC_MEETING = "Group meeting desc";
    public static final String VALID_EVENT_DATE_DOCTORAPPT = "2018-09-01";
    public static final String VALID_EVENT_DATE_MEETING = "2018-09-09";
    public static final String VALID_EVENT_START_TIME_DOCTORAPPT = "1400";
    public static final String VALID_EVENT_START_TIME_MEETING = "0900";
    public static final String VALID_EVENT_END_TIME_DOCTORAPPT = "1500";
    public static final String VALID_EVENT_END_TIME_MEETING = "1200";
    public static final String VALID_EVENT_ADDRESS_DOCTORAPPT = "Blk 312, Amy Street 1";
    public static final String VALID_EVENT_ADDRESS_MEETING = "Block 123, Bobby Street 3";
    public static final String VALID_EVENT_CONTACT_INDEX_1 = "1";
    public static final String VALID_EVENT_CONTACT_INDEX_2 = "2";
    public static final String INVALID_EVENT_END_TIME_TOO_EARLY_DOCTORAPPT = "0800";

    public static final String VALID_TAG_APPOINTMENT = "Appointment";
    public static final String VALID_TAG_MEETING = "Meeting";
    public static final String TAG_DESC_APPOINTMENT = " " + PREFIX_TAG + VALID_TAG_APPOINTMENT;
    public static final String TAG_DESC_MEETING = " " + PREFIX_TAG + VALID_TAG_MEETING;

    public static final String EVENT_NAME_DESC_DOCTORAPPT = " " + PREFIX_NAME + VALID_EVENT_NAME_DOCTORAPPT;
    public static final String EVENT_NAME_DESC_MEETING = " " + PREFIX_NAME + VALID_EVENT_NAME_MEETING;
    public static final String EVENT_DESC_DESC_DOCTORAPPT =
            " " + PREFIX_EVENT_DESCRIPTION + VALID_EVENT_DESC_DOCTORAPPT;
    public static final String EVENT_DESC_DESC_MEETING = " " + PREFIX_EVENT_DESCRIPTION + VALID_EVENT_DESC_MEETING;
    public static final String EVENT_DATE_DESC_DOCTORAPPT = " " + PREFIX_DATE + VALID_EVENT_DATE_DOCTORAPPT;
    public static final String EVENT_DATE_DESC_MEETING = " " + PREFIX_DATE + VALID_EVENT_DATE_MEETING;
    public static final String EVENT_START_TIME_DESC_DOCTORAPPT =
            " " + PREFIX_START_TIME + VALID_EVENT_START_TIME_DOCTORAPPT;
    public static final String EVENT_START_TIME_DESC_MEETING =
            " " + PREFIX_START_TIME + VALID_EVENT_START_TIME_MEETING;
    public static final String EVENT_END_TIME_DESC_DOCTORAPPT =
            " " + PREFIX_END_TIME + VALID_EVENT_END_TIME_DOCTORAPPT;
    public static final String EVENT_END_TIME_DESC_MEETING =
            " " + PREFIX_END_TIME + VALID_EVENT_END_TIME_MEETING;
    public static final String EVENT_ADDRESS_DESC_DOCTORAPPT = " " + PREFIX_ADDRESS + VALID_EVENT_ADDRESS_DOCTORAPPT;
    public static final String EVENT_ADDRESS_DESC_MEETING = " " + PREFIX_ADDRESS + VALID_EVENT_ADDRESS_MEETING;
    public static final String EVENT_CONTACT_INDEX_DESC_DOCTORAPPT = " " + PREFIX_INDEX + VALID_EVENT_CONTACT_INDEX_1;
    public static final String EVENT_CONTACT_INDEX_DESC_MEETING = " " + PREFIX_INDEX + VALID_EVENT_CONTACT_INDEX_2;
    public static final String EVENT_TAG_DESC_DOCTORAPPT = " " + PREFIX_TAG + VALID_TAG_APPOINTMENT;
    public static final String EVENT_TAG_DESC_MEETING = " " + PREFIX_TAG + VALID_TAG_MEETING;

    public static final String INVALID_EVENT_NAME_DESC = " " + PREFIX_NAME + "Consultation-"; // - not allowed
    public static final String INVALID_EVENT_DESC_DESC = " " + PREFIX_EVENT_DESCRIPTION + "Important*"; // * not allowed
    public static final String INVALID_EVENT_DATE_DESC = " " + PREFIX_DATE + "2018-09-32";
    public static final String INVALID_EVENT_START_TIME_DESC = " " + PREFIX_START_TIME + "12:00"; // colon not allowed
    public static final String INVALID_EVENT_END_TIME_DESC = " " + PREFIX_END_TIME + "13:00"; // colon not allowed
    public static final String INVALID_EVENT_END_TIME_TOO_EARLY_DESC =
            " " + PREFIX_END_TIME + INVALID_EVENT_END_TIME_TOO_EARLY_DOCTORAPPT;
    public static final String INVALID_EVENT_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed
    public static final String INVALID_EVENT_CONTACT_INDEX = " " + PREFIX_INDEX + "-1"; // contact index negative


    public static final String NONEXISTENT_EVENT_TAG = "Class";

    public static final String INVALID_NOTIFICATION_PARAMETER = "set";
    public static final String VALID_DISABLE_NOTIFICATION_PARAMETER = "disable";

    // Import contacts
    public static final String FILE_DESC = " " + PREFIX_FILE;


    public static final String PREAMBLE_WHITESPACE = "\r  \t  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage, Model expectedModel) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(actualModel, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedCommandHistory, actualCommandHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s address book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered list to show only the event at the given {@code targetDate} and {@code
     * targetIndex} in the {@code model}'s address book.
     */
    public static void showEventAtDateAndIndex(Model model, Index targetIndex, EventDate targetDate) {
        assertTrue(model.getFilteredEventListByDate().stream()
                .anyMatch(list -> list.get(0).getEventDate().equals(targetDate)));

        List<List<Event>> eventList =
                model.getFilteredEventListByDate().stream()
                        .filter(list -> list.get(0).getEventDate().equals(targetDate))
                        .collect(Collectors.toList());

        assertTrue(eventList.size() == 1);
        assertTrue(targetIndex.getZeroBased() < eventList.get(0).size());

        Event event = eventList.get(0).get(targetIndex.getZeroBased());

        final String[] splitName = event.getEventName().eventName.split("\\s+");
        model.updateFilteredEventList(new EventNameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredEventList().size());
        assertEquals(1, model.getFilteredEventListByDate().size());
        assertEquals(1, model.getFilteredEventListByDate().get(0).size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Adds the input {@code event} into the filtered list from {@code model}'s address book.
     */
    public static void addNewEvent(Event event, Model model) {
        model.addEvent(event);
        model.commitAddressBook();
    }

}
