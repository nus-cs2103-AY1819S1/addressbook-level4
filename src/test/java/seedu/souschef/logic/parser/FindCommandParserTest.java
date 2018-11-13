package seedu.souschef.logic.parser;

import static seedu.souschef.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.souschef.logic.parser.CommandParserTestUtil.assertFindParseFailure;
import static seedu.souschef.logic.parser.CommandParserTestUtil.assertFindParseSuccess;
import static seedu.souschef.testutil.TypicalRecipes.getTypicalAddressBook;

import java.util.Arrays;

import org.junit.Test;

import seedu.souschef.commons.core.Messages;
import seedu.souschef.logic.commands.FindCommand;
import seedu.souschef.logic.parser.commandparser.FindCommandParser;
import seedu.souschef.model.Model;
import seedu.souschef.model.ModelSetCoordinator;
import seedu.souschef.model.UserPrefs;
import seedu.souschef.model.recipe.Recipe;
import seedu.souschef.model.recipe.RecipeContainsKeywordsPredicate;

public class FindCommandParserTest {

    private Model<Recipe> model = new ModelSetCoordinator(getTypicalAddressBook(), new UserPrefs()).getRecipeModel();
    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertFindParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                Messages.MESSAGE_FIND_RECIPE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        FindCommand<Recipe> expectedFindCommand =
                new FindCommand<Recipe>(model, new RecipeContainsKeywordsPredicate(Arrays.asList("chicken", "egg")));
        assertFindParseSuccess(parser, "chicken egg", expectedFindCommand);

        // multiple whitespaces between keywords
        assertFindParseSuccess(parser, " \n chicken \n \t egg  \t", expectedFindCommand);
    }

}
