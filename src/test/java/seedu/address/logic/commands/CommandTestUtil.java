package seedu.address.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.CliSyntax.PREFIX_ANSWER;
import static seedu.address.logic.parser.CliSyntax.PREFIX_QUESTION;
import static seedu.address.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.address.commons.core.index.Index;
import seedu.address.logic.CommandHistory;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;
import seedu.address.model.card.Card;
import seedu.address.model.card.QuestionContainsKeywordsPredicate;
import seedu.address.model.person.NameContainsKeywordsPredicate;
import seedu.address.model.person.Person;
import seedu.address.testutil.EditCardDescriptorBuilder;

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

    public static final String VALID_QUESTION_EARTH_FLAT = "Is earth flat?";
    public static final String VALID_ANSWER_EARTH_FLAT = "No.";
    public static final String VALID_TAG_PHYSICS = "Physics";
    public static final String VALID_QUESTION_GIT_COMMIT = "How to commit in git?";
    public static final String VALID_ANSWER_GIT_COMMIT = "git commit -m 'whatever you wanna say'";
    public static final String VALID_TAG_GIT = "Git";
    public static final String VALID_QUESTION_PM_OF_SG = "Who is the prime minister of Singapore?";
    public static final String VALID_ANSWER_PM_OF_SG = "Who is the prime minister of Singapore?";
    public static final String VALID_TAG_GEN_KNOWLEDGE = "GeneralKnowledge";
    public static final String VALID_TAG_NO_TAG = "NoTag";

    public static final String QUESTION_DESC_EARTH_FLAT = " " + PREFIX_QUESTION + VALID_QUESTION_EARTH_FLAT;
    public static final String QUESTION_DESC_GIT_COMMIT = " " + PREFIX_QUESTION + VALID_QUESTION_GIT_COMMIT;
    public static final String ANSWER_DESC_EARTH_FLAT = " " + PREFIX_ANSWER + VALID_ANSWER_EARTH_FLAT;
    public static final String ANSWER_DESC_GIT_COMMIT = " " + PREFIX_ANSWER + VALID_ANSWER_GIT_COMMIT;
    public static final String TAG_DESC_PHYSICS = " " + PREFIX_TAG + VALID_TAG_PHYSICS;
    public static final String TAG_DESC_GIT = " " + PREFIX_TAG + VALID_TAG_GIT;
    public static final String QUESTION_DESC_PM_OF_SG = " " + PREFIX_QUESTION + VALID_QUESTION_PM_OF_SG;
    public static final String ANSWER_DESC_PM_OF_SG = " " + PREFIX_ANSWER + VALID_ANSWER_PM_OF_SG;
    public static final String TAG_DESC_GEN_KNOWLEDGE = " " + PREFIX_TAG + VALID_TAG_GEN_KNOWLEDGE;
    public static final String TAG_DESC_NO_TAG = " " + PREFIX_TAG + VALID_TAG_NO_TAG;

    public static final String INVALID_QUESTION_DESC = " " + PREFIX_QUESTION + ""; // empty strings not allowed
    public static final String INVALID_ANSWER_DESC = " " + PREFIX_ANSWER + "  "; // empty strings not allowed
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "tags*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditCommand.EditCardDescriptor DESC_EARTH_FLAT;
    public static final EditCommand.EditCardDescriptor DESC_GIT_COMMIT;
    public static final EditCommand.EditCardDescriptor DESC_PM_OF_SG;

    static {
        DESC_EARTH_FLAT = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_EARTH_FLAT)
                .withAnswer(VALID_ANSWER_EARTH_FLAT)
                .withTags(VALID_TAG_PHYSICS).build();
        DESC_GIT_COMMIT = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_GIT_COMMIT)
                .withAnswer(VALID_ANSWER_GIT_COMMIT)
                .withTags(VALID_TAG_GIT).build();
        DESC_PM_OF_SG = new EditCardDescriptorBuilder().withQuestion(VALID_QUESTION_PM_OF_SG)
                .withAnswer(VALID_ANSWER_PM_OF_SG)
                .withTags(VALID_TAG_GEN_KNOWLEDGE).build();
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
     * Updates {@code model}'s filtered list to show only the card at the given {@code targetIndex} in the
     * {@code model}'s trivia bundle.
     */
    public static void showCardAtIndex(Model model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredCardList().size());

        Card card = model.getFilteredCardList().get(targetIndex.getZeroBased());
        final String[] splitName = card.getQuestion().value.split("\\s+");
        model.updateFilteredCardList(new QuestionContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredCardList().size());
    }

    /**
     * Deletes the first person in {@code model}'s filtered list from {@code model}'s address book.
     */
    public static void deleteFirstPerson(Model model) {
        Person firstPerson = model.getFilteredPersonList().get(0);
        model.deletePerson(firstPerson);
        model.commitAddressBook();
    }

}
