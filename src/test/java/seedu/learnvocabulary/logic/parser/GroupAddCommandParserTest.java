package seedu.learnvocabulary.logic.parser;

import static seedu.learnvocabulary.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.learnvocabulary.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.learnvocabulary.logic.commands.FindCommand;
import seedu.learnvocabulary.logic.commands.GroupaddCommand;
import seedu.learnvocabulary.model.tag.Tag;

//@@author Harryqu123
public class GroupAddCommandParserTest {

    private GroupAddCommandParser parser = new GroupAddCommandParser();

    @Test
    public void parse_emptyArg_throwsParseException() {
        assertParseFailure(parser, "     ", String.format(MESSAGE_INVALID_COMMAND_FORMAT, FindCommand.MESSAGE_USAGE));
    }
    @Test
    public void parse_validArgs_returnsFindCommand() {
        // no leading and trailing whitespaces
        GroupaddCommand expectedGroupAddCommand =
                new GroupaddCommand(new Tag("test"));
        assertParseSuccess(parser, "test", expectedGroupAddCommand);

        // multiple whitespaces between keywords
        assertParseSuccess(parser, " \n test \n", expectedGroupAddCommand);
    }
}
//@@author
