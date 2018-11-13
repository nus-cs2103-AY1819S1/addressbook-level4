package seedu.souschef.logic.parser;

import static seedu.souschef.commons.core.Messages.MESSAGE_DELETE_RECIPE_USAGE;
import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CommandParserTestUtil.assertDeleteParseFailure;
import static seedu.souschef.logic.parser.CommandParserTestUtil.assertDeleteParseSuccess;
import static seedu.souschef.testutil.TypicalIndexes.INDEX_FIRST_RECIPE;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import org.junit.jupiter.api.Test;

import seedu.souschef.logic.commands.DeleteCommand;
import seedu.souschef.logic.parser.commandparser.DeleteCommandParser;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.Recipe;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the DeleteCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the DeleteCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class DeleteCommandParserTest {

    private Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
    private DeleteCommandParser parser = new DeleteCommandParser();

    //TODO : delete
    @Test
    public void parse_validArgs_returnsDeleteCommand() {
        Recipe recipeToDelete = model.getFilteredList().get(INDEX_FIRST_RECIPE.getZeroBased());
        assertDeleteParseSuccess(parser, "1", new DeleteCommand(model, recipeToDelete));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertDeleteParseFailure(parser, "a",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, MESSAGE_DELETE_RECIPE_USAGE));
    }
}
