package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static seedu.address.logic.parser.CliSyntax.PREFIX_CATEGORY;
import static seedu.address.logic.parser.CliSyntax.PREFIX_DURATION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_SUBHEADER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TITLE;

import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.Model;
import seedu.address.model.entry.ResumeEntry;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {
    public static final String VALID_CAT_EDUCATION = "EDUCATION";
    public static final String VALID_TITLE_SOURCE = "THE SOURCE ACADEMY";
    public static final String VALID_SUBTITLE_COMPUTING = "Bachelor of Computing";
    public static final String VALID_DURATION_1 = "JAN 2006 - DEC 2010";
    public static final String VALID_TITLE_FACEBOOK = "Facebook";
    public static final String VALID_SUBTITLE_SE = "software engineering intern";
    public static final String VALID_DURATION_2 = "2010 - 2013";
    public static final String VALID_TAG_JAVA = "java";
    public static final String VALID_TAG_SE = "SE";

    public static final String TAG_DESC_JAVA = " " + PREFIX_TAG + VALID_TAG_JAVA;
    public static final String TAG_DESC_SE = " " + PREFIX_TAG + VALID_TAG_SE;

    public static final String CAT_DESC_WORK = " " + PREFIX_CATEGORY + VALID_CAT_EDUCATION;
    public static final String TITLE_DESC_NUS = " " + PREFIX_TITLE + VALID_TITLE_SOURCE;
    public static final String SUBTITLE_DESC = " " + PREFIX_SUBHEADER + VALID_SUBTITLE_COMPUTING;
    public static final String DURATION_DESC = " " + PREFIX_DURATION + VALID_DURATION_1;
    public static final String TITLE_DESC_FACEBOOK = " " + PREFIX_TITLE + VALID_TITLE_FACEBOOK;
    public static final String SUBTITLE_DESC_FACEBOOK = " " + PREFIX_SUBHEADER + VALID_SUBTITLE_SE;
    public static final String DURATION_FACEBOOK = " " + PREFIX_DURATION + VALID_DURATION_2;

    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags
    public static final String INVALID_TITLE_DESC = " " + PREFIX_TITLE + "HS_sadji*";

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final String DESC_BULLET_FINANCIAL_HACK = "attained Best Financial Hack Award";

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
        // TODO: do comparison for entryBook

        CommandHistory expectedCommandHistory = new CommandHistory(actualCommandHistory);

        try {
            command.execute(actualModel, actualCommandHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedCommandHistory, actualCommandHistory);
        }
    }

    /**
     * adds an entry to a given model and commit the newly added entry.
     */
    public static void addEntry(Model model, ResumeEntry entry) {
        model.addEntry(entry);
        model.commitEntryBook();
    }

}
