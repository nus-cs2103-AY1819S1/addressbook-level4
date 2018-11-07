package seedu.meeting.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNotEquals;
import static org.junit.Assert.assertTrue;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_DESCRIPTION;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_GROUP;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_LOCATION;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.meeting.logic.parser.CliSyntax.PREFIX_TIMESTAMP;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import seedu.meeting.commons.core.index.Index;
import seedu.meeting.logic.CommandHistory;
import seedu.meeting.logic.commands.exceptions.CommandException;
import seedu.meeting.model.MeetingBook;
import seedu.meeting.model.Model;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.group.util.GroupContainsPersonPredicate;
import seedu.meeting.model.group.util.GroupTitleContainsKeywordsPredicate;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.model.meeting.util.MeetingTitleContainsKeywordsPredicate;
import seedu.meeting.model.person.Person;
import seedu.meeting.model.person.util.PersonNameContainsKeywordsPredicate;
import seedu.meeting.testutil.EditPersonDescriptorBuilder;

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

    public static final String VALID_GROUP_TITLE_DESC_GROUP_0 = " " + PREFIX_NAME + "group";
    public static final String VALID_JOIN_GROUP_TITLE_DESC_GROUP_0 = " " + PREFIX_GROUP + "group";
    public static final String VALID_GROUP_TITLE_DESC_GROUP_2101 = " " + PREFIX_NAME + "CS2101";
    public static final String VALID_JOIN_GROUP_TITLE_DESC_GROUP_2101 = " " + PREFIX_GROUP + "CS2101";

    public static final String VALID_GROUP_TITLE_GROUP_0 = "group";

    public static final String INVALID_GROUP_TITLE_DESC = " " + PREFIX_NAME + "€project"; // '€' not allowed in title

    // @@author NyxF4ll
    public static final String VALID_MEETING_TITLE_DESC_WEEKLY = " " + PREFIX_NAME + "Weekly meetup";
    public static final String VALID_MEETING_TITLE_DESC_URGENT = " " + PREFIX_NAME + "Urgent affair";
    public static final String VALID_MEETING_TIME_DESC = " " + PREFIX_TIMESTAMP + "22-02-2017@10:10";
    public static final String VALID_MEETING_DESCRIPTION_DESC_WEEKLY = " " + PREFIX_DESCRIPTION
            + "Weekly report of progress";
    public static final String VALID_MEETING_DESCRIPTION_DESC_URGENT = " " + PREFIX_DESCRIPTION
            + "Urgent meeting on the project direction";
    public static final String VALID_MEETING_LOCATION_DESC = " " + PREFIX_LOCATION + "COM1-0202";
    public static final String VALID_MEETING_DESC_WEEKLY = " " + VALID_MEETING_TITLE_DESC_WEEKLY
            + VALID_MEETING_LOCATION_DESC + VALID_MEETING_TIME_DESC + VALID_MEETING_DESCRIPTION_DESC_WEEKLY;
    // @@author

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_GROUP_DESC = " " + PREFIX_GROUP + "CS*123";

    // @@author NyxF4ll
    // '#' not allowed in title
    public static final String INVALID_MEETING_TITLE_DESC = " " + PREFIX_NAME + "Group Me#ting";
    // invalid timestamp
    public static final String INVALID_MEETING_TIME_DESC = " " + PREFIX_TIMESTAMP + "22-02-2017@100:10";
    // blank string for description is not allowed
    public static final String INVALID_MEETING_DESCRIPTION_DESC = " " + PREFIX_DESCRIPTION;
    // empty string not allowed
    public static final String INVALID_MEETING_LOCATION_DESC = " " + PREFIX_LOCATION + "";
    // @@author

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
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
     * - the MeetingBook and the filtered person list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel, CommandHistory actualCommandHistory,
            String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        MeetingBook expectedMeetingBook = new MeetingBook(actualModel.getMeetingBook());
        List<Person> expectedFilteredList = new ArrayList<>(actualModel.getFilteredPersonList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedMeetingBook, actualModel.getMeetingBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredPersonList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the person at the given {@code targetIndex} in the
     * {@code model}'s MeetingBook.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new PersonNameContainsKeywordsPredicate(
            Collections.emptyList(), Arrays.asList(splitName[0]), Collections.emptyList()));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Updates {@code model}'s filtered meeting list to show only the meeting at the given {@code targetIndex} in the
     * {@code model}'s MeetingBook.
     */
    public static void showMeetingAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredMeetingList().size());

        Meeting meeting = model.getFilteredMeetingList().get(targetIndex.getZeroBased());
        final String[] splitName = meeting.getTitle().fullTitle.split("\\s+");
        model.updateFilteredMeetingList(new MeetingTitleContainsKeywordsPredicate(Collections.emptyList(),
            Arrays.asList(splitName[0]), Collections.emptyList()));

        /**
         * TODO this is a temp hack because there may be multiple meetings with same identities, will have to wait for
         * the issue is rectified
         */
        assertNotEquals(0, model.getFilteredMeetingList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s MeetingBook.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitMeetingBook();
    }

    /**
     * Updates {@code model}'s filtered group list to show only the group at the given {@code targetIndex} in the
     * {@code model}'s MeetingBook.
     */
    public static void showGroupAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredGroupList().size());

        Group group = model.getFilteredGroupList().get(targetIndex.getZeroBased());

        final String[] splitGroupTitle = group.getTitle().fullTitle.split("\\s+");
        model.updateFilteredGroupList(new GroupTitleContainsKeywordsPredicate(Collections.emptyList(),
            Arrays.asList(splitGroupTitle[0]), Collections.emptyList()));
        model.updateFilteredPersonList(new GroupContainsPersonPredicate(Collections.singletonList(group)));

        assertEquals(1, model.getFilteredGroupList().size());
    }
}
