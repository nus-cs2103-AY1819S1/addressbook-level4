package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.AchievementsCommand;

public class AchievementsCommandParserTest {
    private AchievementsCommandParser parser = new AchievementsCommandParser();

    @Test
    public void parse_validArgs_returnsAchievementCommand() {
        assertParseSuccess(parser, "all-time", new AchievementsCommand(AchievementsCommand.ALL_TIME_OPTION));
        assertParseSuccess(parser, "today", new AchievementsCommand(AchievementsCommand.TODAY_OPTION));
        assertParseSuccess(parser, "this week", new AchievementsCommand(AchievementsCommand.THIS_WEEK_OPTION));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        // empty string
        assertParseFailure(parser, "",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AchievementsCommand.MESSAGE_USAGE));

        // invalid string
        assertParseFailure(parser, "all time",
                String.format(MESSAGE_INVALID_COMMAND_FORMAT, AchievementsCommand.MESSAGE_USAGE));
    }
}
