package seedu.restaurant.logic.parser.ingredient;

import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NAME_BROCCOLI;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NUMUNITS_APPLE;
import static seedu.restaurant.logic.commands.CommandTestUtil.VALID_NUMUNITS_BROCCOLI;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.restaurant.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.restaurant.model.ingredient.IngredientName.MESSAGE_NAME_CONSTRAINTS;
import static seedu.restaurant.model.ingredient.NumUnits.MESSAGE_NUMUNITS_CONSTRAINTS;

import java.util.LinkedHashMap;
import java.util.Map;

import org.junit.Test;

import seedu.restaurant.commons.core.Messages;
import seedu.restaurant.logic.commands.ingredient.StockUpCommand;
import seedu.restaurant.model.ingredient.IngredientName;

public class StockUpCommandParserTest {
    private StockUpCommandParser parser = new StockUpCommandParser();

    @Test
    public void parse_noFields_failure() {
        assertParseFailure(parser, "", String.format(Messages.MESSAGE_INVALID_COMMAND_FORMAT,
                StockUpCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_invalidName_failure() {
        assertParseFailure(parser, "stockup n/Apple+ nu/10",
                MESSAGE_NAME_CONSTRAINTS);
    }

    @Test
    public void parse_invalidNumUnits_failure() {
        assertParseFailure(parser, "stockup n/Granny Smith Apple nu/-1",
                MESSAGE_NUMUNITS_CONSTRAINTS);
    }

    @Test
    public void parse_validArgumentPair_success() {
        Map<IngredientName, Integer> ingredientHashMap = new LinkedHashMap<>();
        ingredientHashMap.put(new IngredientName(VALID_NAME_APPLE), 10);
        StockUpCommand expectedCommand = new StockUpCommand(ingredientHashMap);
        assertParseSuccess(parser, "stockup n/Granny Smith Apple nu/10", expectedCommand);
    }

    @Test
    public void parse_validMultipleArgumentPairs_success() {
        Map<IngredientName, Integer> ingredientHashMap = new LinkedHashMap<>();
        ingredientHashMap.put(new IngredientName(VALID_NAME_APPLE), VALID_NUMUNITS_APPLE);
        ingredientHashMap.put(new IngredientName(VALID_NAME_BROCCOLI), VALID_NUMUNITS_BROCCOLI);
        ingredientHashMap.put(new IngredientName("Fresh Eggs"), 1000);
        StockUpCommand expectedCommand = new StockUpCommand(ingredientHashMap);
        assertParseSuccess(parser,
                "stockup n/Granny Smith Apple nu/10 n/Australian Broccoli nu/28 n/Fresh Eggs nu/1000",
                expectedCommand);
    }
}
