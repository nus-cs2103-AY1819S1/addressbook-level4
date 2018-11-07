package seedu.souschef.logic.commands;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_COOKTIME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_DIFFICULTY;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_NAME;
import static seedu.souschef.logic.parser.CliSyntax.PREFIX_TAG;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.souschef.commons.core.index.Index;
import seedu.souschef.logic.EditRecipeDescriptor;
import seedu.souschef.logic.History;
import seedu.souschef.logic.commands.exceptions.CommandException;
import seedu.souschef.model.AppContent;
import seedu.souschef.model.Model;
import seedu.souschef.model.UniqueType;
import seedu.souschef.model.recipe.NameContainsKeywordsPredicate;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.testutil.EditRecipeDescriptorBuilder;

/**
 * Contains helper methods for testing commands.
 */
public class CommandTestUtil {

    public static final String VALID_NAME_AMY = "Amy Bee";
    public static final String VALID_NAME_BOB = "Bob Choo";
    public static final String VALID_DIFFICULTY_AMY = "5";
    public static final String VALID_DIFFICULTY_BOB = "1";
    public static final String VALID_COOKTIME_AMY = "20M";
    public static final String VALID_COOKTIME_BOB = "24M";
    public static final String VALID_TAG_HUSBAND = "husband";
    public static final String VALID_TAG_FRIEND = "friend";

    public static final String NAME_DESC_AMY = " " + PREFIX_NAME + VALID_NAME_AMY;
    public static final String NAME_DESC_BOB = " " + PREFIX_NAME + VALID_NAME_BOB;
    public static final String DIFFICULTY_DESC_AMY = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_AMY;
    public static final String DIFFICULTY_DESC_BOB = " " + PREFIX_DIFFICULTY + VALID_DIFFICULTY_BOB;
    public static final String COOKTIME_DESC_AMY = " " + PREFIX_COOKTIME + VALID_COOKTIME_AMY;
    public static final String COOKTIME_DESC_BOB = " " + PREFIX_COOKTIME + VALID_COOKTIME_BOB;
    public static final String TAG_DESC_FRIEND = " " + PREFIX_TAG + VALID_TAG_FRIEND;
    public static final String TAG_DESC_HUSBAND = " " + PREFIX_TAG + VALID_TAG_HUSBAND;

    public static final String INVALID_NAME_DESC = " " + PREFIX_NAME + "James&"; // '&' not allowed in names
    public static final String INVALID_DIFFICULTY_DESC = " " + PREFIX_DIFFICULTY + "911a"; // 'a' not allowed in phones
    public static final String INVALID_COOKTIME_DESC = " " + PREFIX_COOKTIME + "bob!yahoo"; // missing '@' symbol
    public static final String INVALID_TAG_DESC = " " + PREFIX_TAG + "hubby*"; // '*' not allowed in tags

    public static final String PREAMBLE_WHITESPACE = "\t  \r  \n";
    public static final String PREAMBLE_NON_EMPTY = "NonEmptyPreamble";

    public static final EditRecipeDescriptor DESC_AMY;
    public static final EditRecipeDescriptor DESC_BOB;

    static {
        DESC_AMY = new EditRecipeDescriptorBuilder().withName(VALID_NAME_AMY)
                .withDifficulty(VALID_DIFFICULTY_AMY).withCooktime(VALID_COOKTIME_AMY)
                .withTags(VALID_TAG_FRIEND).build();
        DESC_BOB = new EditRecipeDescriptorBuilder().withName(VALID_NAME_BOB)
                .withDifficulty(VALID_DIFFICULTY_BOB).withCooktime(VALID_COOKTIME_BOB)
                .withTags(VALID_TAG_HUSBAND, VALID_TAG_FRIEND).build();
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - the result message matches {@code expectedMessage} <br>
     * - the {@code actualModel} matches {@code expectedModel} <br>
     * - the {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandSuccess(Command<UniqueType> command, Model actualModel,
                                            History actualHistory,
                                            String expectedMessage, Model expectedModel) {
        History expectedHistory = new History(actualHistory);
        try {
            CommandResult result = command.execute(actualHistory);
            assertEquals(expectedMessage, result.feedbackToUser);
            assertEquals(expectedModel, actualModel);
            assertEquals(expectedHistory, actualHistory);
        } catch (CommandException ce) {
            throw new AssertionError("Execution of command should not fail.", ce);
        }
    }

    /**
     * Executes the given {@code command}, confirms that <br>
     * - a {@code CommandException} is thrown <br>
     * - the CommandException message matches {@code expectedMessage} <br>
     * - the address book and the filtered recipe list in the {@code actualModel} remain unchanged <br>
     * - {@code actualCommandHistory} remains unchanged.
     */
    public static void assertCommandFailure(Command<UniqueType> command, Model actualModel,
                                            History actualHistory,
                                            String expectedMessage) {
        // we are unable to defensively copy the recipeModel for comparison later, so we can
        // only do so by copying its components.
        AppContent expectedAddressBook = new AppContent(actualModel.getAppContent());
        List<Recipe> expectedFilteredList = new ArrayList<>(actualModel.getFilteredList());

        History expectedHistory = new History(actualHistory);

        try {
            command.execute(actualHistory);
            throw new AssertionError("The expected CommandException was not thrown.");
        } catch (CommandException e) {
            assertEquals(expectedMessage, e.getMessage());
            assertEquals(expectedAddressBook, actualModel.getAppContent());
            assertEquals(expectedFilteredList, actualModel.getFilteredList());
            assertEquals(expectedHistory, actualHistory);
        }
    }

    /**
     * Updates {@code recipeModel}'s filtered list to show only the recipe at the given {@code targetIndex} in the
     * {@code recipeModel}'s address book.
     */
    public static void showPersonAtIndex(Model<Recipe> model, Index targetIndex) {
        assertTrue(targetIndex.getZeroBased() < model.getFilteredList().size());

        Recipe recipe = model.getFilteredList().get(targetIndex.getZeroBased());
        final String[] splitName = recipe.getName().fullName.split("\\s+");
        model.updateFilteredList(new NameContainsKeywordsPredicate(Arrays.asList(splitName[0])));

        assertEquals(1, model.getFilteredList().size());
    }

    /**
     * Deletes the first recipe in {@code recipeModel}'s filtered list from {@code recipeModel}'s address book.
     */
    public static void deleteFirstPerson(Model<Recipe> model) {
        Recipe firstRecipe = model.getFilteredList().get(0);
        model.delete(firstRecipe);
        model.commitAppContent();
    }

}
