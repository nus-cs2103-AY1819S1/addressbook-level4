package seedu.souschef.logic.parser;

import org.junit.Test;

public class FindCommandParserTest {

    private FindCommandParser parser = new FindCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        //assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }

    @Test
    public void parse_validArgs_returnsFindCommand() {
        /*// no leading and trailing whitespaces
        FindCommand<Recipe> expectedFindCommand =
                new FindCommand<Recipe>(model, new NameContainsKeywordsPredicate(Arrays.asList("Alice", "Bob")));
        //assertParseSuccess(parser, "Alice Bob", expectedFindCommand);

        // multiple whitespaces between keywords
        //assertParseSuccess(parser, " \n Alice \n \t Bob  \t", expectedFindCommand);*/
    }

}
