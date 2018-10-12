package seedu.jxmusic.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_ADDRESS;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_EMAIL;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_PHONE;
import static seedu.jxmusic.logic.parser.CliSyntax.PREFIX_TRACK;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.jxmusic.commons.core.index.Index;
import seedu.jxmusic.logic.commands.exceptions.CommandException;
import seedu.jxmusic.model.AddressBook;
import seedu.jxmusic.model.Model;
import seedu.jxmusic.model.NameContainsKeywordsPredicate;
import seedu.jxmusic.testutil.EditPersonDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_ANIME = "Anime music";
    public static final String VALID_NAME_METAL = "METAL";
    public static final String VALID_TRACK_ALIEZ = "aliez";
    public static final String VALID_TRACK_EXISTENCE = "EXiSTENCE";

    public static final String NAME_DESC_ANIME = " " + PREFIX_NAME + VALID_NAME_ANIME;
    public static final String NAME_DESC_METAL = " " + PREFIX_NAME + VALID_NAME_METAL;
    public static final String TRACK_DESC_ALIEZ = " " + PREFIX_TRACK + VALID_TRACK_ALIEZ;
    public static final String TRACK_DESC_EXISTENCE = " " + PREFIX_TRACK + VALID_TRACK_EXISTENCE;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_PHONE_DESC = " " + PREFIX_PHONE + "911a"; // 'a' not allowed in phones
    public static final String INVALID_EMAIL_DESC = " " + PREFIX_EMAIL + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_ADDRESS_DESC = " " + PREFIX_ADDRESS; // empty string not allowed for addresses
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditPersonDescriptor DESC_AMY;
    public static final EditCommand.EditPersonDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditPersonDescriptorBuilder().withName(VALID_NAME_ANIME)
                .withTracks(VALID_TRACK_EXISTENCE).build();
        DESC_BOB = new EditPersonDescriptorBuilder().withName(VALID_NAME_METAL)
                .withPhone(VALID_PHONE_BOB).withEmail(VALID_EMAIL_BOB).withAddress(VALID_ADDRESS_BOB)
                .withTRACKs(VALID_TRACK_ALIEZ, VALID_TRACK_EXISTENCE).build();
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
     * - the jxmusic book and the filtered playlist list in the {@code actualModel} remain unchanged <br>
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
     * Updates {@code model}'s filtered list to show only the playlist at the given {@code targetIndex} in the
     * {@code model}'s jxmusic book.
     */
    public static void showPersonAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredPersonList().size());

        Person person = model.getFilteredPersonList().get(targetIndex.getZeroBased());
        final String[] splitName = person.getName().nameString.split("\\s+");
        model.updateFilteredPersonList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredPersonList().size());
    }

    /**
     * Deletes the first playlist in {@code model}'s filtered list from {@code model}'s jxmusic book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

}
