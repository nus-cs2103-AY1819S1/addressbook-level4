package seedu.restaurant.logic.parser.menu;

import static seedu.restaurant.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.logic.parser.util.CliSyntax.PREFIX_RECIPE;
import static seedu.restaurant.testutil.TypicalIndexes.INDEX_FIRST;

import org.junit.Test;

import seedu.restaurant.commons.core.index.Index;
import seedu.restaurant.logic.commands.menu.RecipeItemCommand;
import seedu.restaurant.model.menu.Recipe;

//@@author yican95
public class RecipeItemCommandParserTest {
    private RecipeItemCommandParser parser = new RecipeItemCommandParser();
    private final String nonEmptyRecipe = "Some recipe.";

    @Test
    public void parse_indexSpecified_success() {
        // have recipe
        Index targetIndex = INDEX_FIRST;
        String userInput = targetIndex.getOneBased() + " " + PREFIX_RECIPE + nonEmptyRecipe;
        RecipeItemCommand expectedCommand = new RecipeItemCommand(INDEX_FIRST, new Recipe(nonEmptyRecipe));
        assertParseSuccess(parser, userInput, expectedCommand);

        // no remark
        userInput = targetIndex.getOneBased() + " " + PREFIX_RECIPE;
        expectedCommand = new RecipeItemCommand(INDEX_FIRST, new Recipe(""));
        assertParseSuccess(parser, userInput, expectedCommand);
    }

    @Test
    public void parse_missingCompulsoryField_failure() {
        String expectedMessage = String.format(MESSAGE_INVALID_COMMAND_FORMAT, RecipeItemCommand.MESSAGE_USAGE);

        // no parameters
        assertParseFailure(parser, RecipeItemCommand.COMMAND_WORD, expectedMessage);

        // no index
        assertParseFailure(parser, RecipeItemCommand.COMMAND_WORD + " " + nonEmptyRecipe, expectedMessage);
    }
}
