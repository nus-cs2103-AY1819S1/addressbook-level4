package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.learnvocabulary.logic.commands.GroupdeleteCommand;
import seedu.learnvocabulary.model.tag.Tag;


//@@author Harryqu123
public class GroupDeleteCommandParserTest {

    private GroupDeleteCommandParser parser = new GroupDeleteCommandParser();
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        GroupdeleteCommand expectedGroupDeleteCommand =
                new GroupdeleteCommand(new Tag("test"));
        assertParseSuccess(parser, "   test", expectedGroupDeleteCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n test \n", expectedGroupDeleteCommand);
    }
}
//@@author
