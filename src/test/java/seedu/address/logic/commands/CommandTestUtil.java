package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_BIRTHDAY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_GENDER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SWITCH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.io.File;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.volunteer.NameContainsKeywordsPredicate;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.testutil.EditVolunteerDescriptorBuilder;
import seedu.address.testutil.TypicalEvents;
import seedu.address.testutil.TypicalRecords;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_COMMANDID_EVENT = "e";

    public static final int VALID_EVENTID_E1 = 1;
    public static final int VALID_EVENTID_E2 = 2;
    public static final int VALID_VOLUNTEERID_V1 = 1;
    public static final int VALID_VOLUNTEERID_V2 = 2;
    public static final String VALID_HOUR_H1 = "1";
    public static final String VALID_HOUR_H2 = "2";
    public static final String VALID_REMARK_R1 = "Emcee";
    public static final String VALID_REMARK_R2 = "Delivery man";

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_PHONE_AMY = "11111111";
    public static final String VALID_PHONE_BOB = "22222222";
    public static final String VALID_GENDER_AMY = "f";
    public static final String VALID_GENDER_BOB = "m";
    public static final String VALID_BIRTHDAY_AMY = "01-10-1995";
    public static final String VALID_BIRTHDAY_BOB = "11-02-1991";
    public static final String VALID_EMAIL_AMY = "amy@example.com";
    public static final String VALID_EMAIL_BOB = "bob@example.com";
    public static final String VALID_ADDRESS_AMY = "Block 312, Amy Street 1";
    public static final String VALID_ADDRESS_BOB = "Block 123, Bobby Street 3";
    public static final String VALID_TAG_DRIVER = "driver";
    public static final String VALID_TAG_STUDENT = "student";

    public static final String VALID_NAME_YOUTH = "Youth Humanitarian Challenge";
    public static final String VALID_LOCATION_YOUTH = "29 Havelock Road";
    public static final String VALID_START_DATE_YOUTH = "28-09-2018";
    public static final String VALID_END_DATE_YOUTH = "28-09-2018";
    public static final String VALID_START_TIME_YOUTH = "10:00";
    public static final String VALID_END_TIME_YOUTH = "14:00";
    public static final String VALID_DESCRIPTION_YOUTH = "To engage youths in humanitarianism.";
    public static final String VALID_TAG_PUBLIC = "Public";
    public static final String VALID_TAG_DONATION = "Donation";
    public static final String VALID_TAG_COMPETITION = "Competition";

    public static final String CONTEXT_VALID_DESC = " " + PREFIX_SWITCH + VALID_COMMANDID_EVENT;
    public static final String CONTEXT_INVALID_DESC = " " + PREFIX_SWITCH + "i"; // Not recognised

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String PHONE_DESC_AMY = " " + PREFIX_PHONE + VALID_PHONE_AMY;
    public static final String PHONE_DESC_BOB = " " + PREFIX_PHONE + VALID_PHONE_BOB;
    public static final String EMAIL_DESC_AMY = " " + PREFIX_EMAIL + VALID_EMAIL_AMY;
    public static final String EMAIL_DESC_BOB = " " + PREFIX_EMAIL + VALID_EMAIL_BOB;
    public static final String ADDRESS_DESC_AMY = " " + PREFIX_ADDRESS + VALID_ADDRESS_AMY;
    public static final String ADDRESS_DESC_BOB = " " + PREFIX_ADDRESS + VALID_ADDRESS_BOB;
    public static final String TAG_DESC_STUDENT = " " + PREFIX_TAG + VALID_TAG_STUDENT;
    public static final String TAG_DESC_DRIVER = " " + PREFIX_TAG + VALID_TAG_DRIVER;
    public static final String GENDER_DESC_AMY = " " + PREFIX_GENDER + VALID_GENDER_AMY;
    public static final String GENDER_DESC_BOB = " " + PREFIX_GENDER + VALID_GENDER_BOB;
    public static final String BIRTHDAY_DESC_AMY = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_AMY;
    public static final String BIRTHDAY_DESC_BOB = " " + PREFIX_BIRTHDAY + VALID_BIRTHDAY_BOB;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_VOLUNTEER_NAME_DESC = " " + PREFIX_NAME + "James&";
    // '&' not allowed in names
    public static final String INVALID_GENDER_DESC = " " + PREFIX_GENDER + "unknown";
    // only 'male' or 'female' is allowed
    public static final String INVALID_BIRTHDAY_DESC = " " + PREFIX_BIRTHDAY + "11-12-19913";
    //only 4 digits in year

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final EditCommand.EditVolunteerDescriptor DESC_AMY;
    public static final EditCommand.EditVolunteerDescriptor DESC_BOB;

    static {

        DESC_AMY = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_AMY)
                .withGender(VALID_GENDER_AMY).withBirthday(VALID_BIRTHDAY_AMY).withPhone(VALID_PHONE_AMY)
                .withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_STUDENT).build();
        DESC_BOB = new EditVolunteerDescriptorBuilder().withName(VALID_NAME_BOB)
                .withGender(VALID_GENDER_BOB).withBirthday(VALID_BIRTHDAY_BOB).withPhone(VALID_PHONE_BOB)
                .withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_DRIVER, VALID_TAG_STUDENT).build();
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
     * - the address book and the filtered volunteer list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command command, Model actualModel,
                                            CommandHistory actualCommandHistory, String expectedMessage) {
        // we are unable to defensively copy the model for comparison later, so we can
        // only do so by copying its components.
        AddressBook expectedAddressBook = new AddressBook(actualModel.getAddressBook());
        List<Volunteer> expectedFilteredList = new ArrayList<>(actualModel.getFilteredVolunteerList());

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAddressBook());
            assertEquals(expectedFilteredList, actualModel.getFilteredVolunteerList());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * Updates {@code model}'s filtered list to show only the volunteer at the given {@code targetIndex} in the
     * {@code model}'s application.
     */
    public static void showVolunteerAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredVolunteerList().size());

        Volunteer volunteer = model.getFilteredVolunteerList().get(targetIndex.getZeroBased());
        final String[] splitName = volunteer.getName().fullName.split("\\s+");
        model.updateFilteredVolunteerList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredVolunteerList().size());
    }

    /**
     * Deletes the first volunteer in {@code model}'s filtered list from {@code model}'s application.
     */
    public static void deleteFirstVolunteer(Model model) {
        Volunteer firstVolunteer = model.getFilteredVolunteerList().get(0);
        model.deleteVolunteer(firstVolunteer);
        model.commitAddressBook();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualCommandHistory} remains unchanged <br>
     * - a file with the {@code volunteer}'s name exists.
     */
    public static void assertExportCommandSuccess(Command command, Model model, CommandHistory actualCommandHistory,
                                            String expectedMessage, Volunteer volunteer) {
        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);
        try {
            CommandResult result = command.execute(model, actualCommandHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedCommandHistory, actualCommandHistory);

            // For now we just check if the exported file exists
            File file = new File(ExportCertCommand.getCurrentSavePath() + volunteer.getName() + "_"
                    + volunteer.getVolunteerId() + ".pdf");
            assertTrue(file.exists());
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Adds a new {@code Event} and it's corresponding {@code record} to the {@code model} for the first
     * {@code volunteer}.
     * @param model to add the event and it's corresponding record to
     */
    public static void addRecordForVolunteer(Model model) {
        model.addEvent(TypicalEvents.BLOOD);
        model.addRecord(TypicalRecords.R1);
    }

}
