package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARD_ANSWER_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARD_QUESTION_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_ANSWER_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_QUESTION_ARGS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;

import org.junit.Test;

import seedu.address.logic.commands.ClassifyCommand;
import seedu.address.model.deck.Performance;
import seedu.address.model.deck.Question;

public class ClassifyCardParserTest {

    private static final String MESSAGE_INVALID_CLASSIFY_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassifyCommand.MESSAGE_USAGE);

    private ClassifyCommandParser parser = new ClassifyCommandParser();

    @Test
    public void parse_validArgs_success() {
        // GOOD
        assertParseSuccess(parser, String.valueOf(Performance.GOOD), new ClassifyCommand(Performance.GOOD));
    }


    @Test
    public void parse_invalidValue_failure() {
        // Invalid Rating
        assertParseFailure(parser,"Terrible", MESSAGE_INVALID_CLASSIFY_FORMAT);

        // Blank
        assertParseFailure(parser,"", MESSAGE_INVALID_CLASSIFY_FORMAT);
    }

}
