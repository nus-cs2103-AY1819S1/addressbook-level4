package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.TOPIC_DESC_PHYSICS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_TOPIC_PHYSICS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.MatchTestCommand;
import seedu.address.model.topic.Topic;

public class MatchTestCommandParserTest {
    private MatchTestCommandParser parser = new MatchTestCommandParser();

    @Test
    public void parse_validArgs_returnsMatchTestCommand() {
        assertParseSuccess(parser, TOPIC_DESC_PHYSICS, new MatchTestCommand(new Topic(VALID_TOPIC_PHYSICS)));
    }

    @Test
    public void parse_invalidArgs_throwsParseException() {
        assertParseFailure(parser, "-sdds", String.format(MESSAGE_INVALID_COMMAND_FORMAT,
                MatchTestCommand.MESSAGE_USAGE));
    }

}
