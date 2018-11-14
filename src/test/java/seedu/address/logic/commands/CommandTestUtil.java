package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DATE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.address.logic.parser.CliSyntax.PREFIX_FILEPATH;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ID;
import static seedu.address.logic.parser.CliSyntax.PREFIX_MARK;
import static seedu.address.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.address.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_WEIGHT;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.EditAssignmentCommand.EditAssignmentDescriptor;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.common.EditPersonDescriptor;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditAssignmentDescriptorBuilder;
import seedu.address.testutil.EditPersonDescriptorBuilder;
import seedu.address.testutil.PersonBuilder;
import seedu.address.testutil.TestUtil;

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
    public static final String VALID_NOTE_TEXT = "Test";
    public static final String VALID_NOTE_TEXT_WITH_FULL_STOP = "Test.";
    public static final String VALID_NOTE_TEXT_WITH_EXCLAMATION = "Test!";
    public static final String VALID_NOTE_TEXT_WITH_QUESTION = "Test?";
    public static final String VALID_NOTE_NUMBERS = "999";
    public static final String VALID_NOTE_SPECIALCHAR = "[(/.\\)]";

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

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String VALID_LOCAL_IMAGE_JPG = "src/test/resources/ProfilePhotoTest/zacharytan.jpg";
    public static final String VALID_LOCAL_IMAGE_5MB = "src/test/resources/ProfilePhotoTest/5mbTestJpg.jpg";
    public static final String VALID_LOCAL_IMAGE_PNG = "src/main/resources/images/help_icon.png";

    public static final String INVALID_LOCAL_FILE_NONIMAGE =
            "src/test/resources/ProfilePhotoTest/nonImageFile.txt";
    public static final String INVALID_LOCAL_FILE_NONIMAGE_WITH_IMAGE_FILETYPE =
            "src/test/resources/ProfilePhotoTest/nonImageFileWithJpgPrefix.jpg";
    public static final String VALID_LOCAL_IMAGE_BIGGER_THAN_5MB =
            "src/test/resources/ProfilePhotoTest/GreaterThan5MBTestJPG.jpg";

    public static final String PHOTO_DESC_LOCAL_IMAGE = " " + PREFIX_FILEPATH + VALID_LOCAL_IMAGE_JPG;
    public static final String PHOTO_DESC_LOCAL_IMAGE_5MB = " " + PREFIX_FILEPATH + VALID_LOCAL_IMAGE_5MB;

    public static final String INVALID_PHOTO_DESC_NONIMAGE = " " + PREFIX_FILEPATH + INVALID_LOCAL_FILE_NONIMAGE;
    public static final String INVALID_PHOTO_DESC_NONIMAGE_WITH_IMAGE_FILETYPE = " " + PREFIX_FILEPATH
            + INVALID_LOCAL_FILE_NONIMAGE_WITH_IMAGE_FILETYPE;
    public static final String INVALID_PHOTO_DESC_IMAGE_GREATER_THAN_5MB = " " + PREFIX_FILEPATH
            + VALID_LOCAL_IMAGE_BIGGER_THAN_5MB;

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";
    public static final String WHITESPACE = " ";

    public static final EditPersonDescriptor DESC_AMY;
    public static final EditPersonDescriptor DESC_BOB;
    public static final EditAssignmentDescriptor DESC_A;
    public static final EditAssignmentDescriptor DESC_B;

    public static final String VALID_ASSIGNMENTNAME_A = "Assignment A";
    public static final String VALID_ASSIGNMENTNAME_B = "Assignment B";
    public static final String VALID_DEADLINE_A = "04/04/2019";
    public static final String VALID_DEADLINE_B = "05/05/2019";
    public static final String VALID_WEIGHT_A = "40";
    public static final String VALID_WEIGHT_B = "50";
    public static final String VALID_MAXMARK_A = "40";
    public static final String VALID_MAXMARK_B = "50";
    public static final String VALID_ASSIGNMENTID = "12";
    public static final String VALID_MARK = "15";

    public static final String ASSIGNMENTNAME_DESC_A = " " + PREFIX_NAME + VALID_ASSIGNMENTNAME_A;
    public static final String ASSIGNMENTNAME_DESC_B = " " + PREFIX_NAME + VALID_ASSIGNMENTNAME_B;
    public static final String DEADLINE_DESC_A = " " + PREFIX_DATE + VALID_DEADLINE_A;
    public static final String DEADLINE_DESC_B = " " + PREFIX_DATE + VALID_DEADLINE_B;
    public static final String WEIGHT_DESC_A = " " + PREFIX_WEIGHT + VALID_WEIGHT_A;
    public static final String WEIGHT_DESC_B = " " + PREFIX_WEIGHT + VALID_WEIGHT_B;
    public static final String MAXMARK_DESC_A = " " + PREFIX_MARK + VALID_MAXMARK_A;
    public static final String MAXMARK_DESC_B = " " + PREFIX_MARK + VALID_MAXMARK_B;
    public static final String VALID_ASSIGNMENTID_DESC = " " + PREFIX_ID + VALID_ASSIGNMENTID;
    public static final String VALID_MARK_DESC = " " + PREFIX_MARK + VALID_MARK;

    public static final String INVALID_ASSIGNMENTNAME_DESC = " " + PREFIX_NAME + "  ";
    public static final String INVALID_DEADLINE_DESC = " " + PREFIX_DATE + "29/02/2003";
    public static final String INVALID_WEIGHT_DESC = " " + PREFIX_WEIGHT + "10Ol";
    public static final String INVALID_MAXMARK_DESC = " " + PREFIX_MARK + "C20AB";
    public static final String INVALID_ASSIGNMENTID_DESC = " " + PREFIX_ID + "1a1";
    public static final String INVALID_MARK_DESC = " " + PREFIX_MARK + "332.4a";


    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_AMY)
                .withPhone(VALID_PHONE_AMY).withEmail(VALID_EMAIL_AMY).withAddress(VALID_ADDRESS_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_BOB)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
        DESC_A = new EditAssignmentDescriptorBuilder().withName(VALID_ASSIGNMENTNAME_A)
                .withDeadline(VALID_DEADLINE_A).withMaxMark(VALID_MAXMARK_A).withWeight(VALID_WEIGHT_A).build();
        DESC_B = new EditAssignmentDescriptorBuilder().withName(VALID_ASSIGNMENTNAME_B)
                .withDeadline(VALID_DEADLINE_B).withMaxMark(VALID_MAXMARK_B).withWeight(VALID_WEIGHT_B).build();

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

        Person person = TestUtil.getPerson(model, targetIndex);
        final String[] splitName = person.getName().fullName.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = TestUtil.getPerson(model, Index.fromZeroBased(0));
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

    /**
     * Returns a copied {@code Person} with a new note with given text added to it, overwrites any existing note.
     * @param person to be copied.
     * @param text of note to be added.
     */
    public static Person copyPersonWithNote(Person person, String text) {
        return new PersonBuilder(person).withNote(text).build();
    }

    /**
     * Adds a note with {@code VALID_NOTE_TEXT_WITH_FULL_STOP} text to person in {@code model}.
     * @param person to add the note to, must exist in model.
     * @param model that is updated.
     * @param text that is in the note.
     */
    public static void updatePersonInModelWithNote(Person person, Model model, String text) {
        Person personWithNote = copyPersonWithNote(person, text);
        model.updatePerson(person, personWithNote);
    }
}
