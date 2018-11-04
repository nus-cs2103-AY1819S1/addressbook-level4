package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARD_ANSWER_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.INVALID_CARD_QUESTION_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_ANSWER_ARGS;
import static seedu.address.logic.commands.CommandTestUtil.VALID_CARD_QUESTION_ARGS;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;

import org.junit.Test;

import seedu.address.logic.commands.ClassifyCommand;
import seedu.address.model.deck.Performance;
import seedu.address.model.deck.Question;

public class ClassifyCardParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
        String.format(MESSAGE_INVALID_COMMAND_FORMAT, ClassifyCommand.MESSAGE_USAGE);

    private ClassifyCommandParser parser = new ClassifyCommandParser();

    @Test
    public void parse_missingParts_failure() {
        // no index specified
        assertParseFailure(parser, String.valueOf(Performance.GOOD), MESSAGE_INVALID_FORMAT);

        // no index and no field specified
        assertParseFailure(parser, "", MESSAGE_INVALID_FORMAT);
    }


    @Test
    public void parse_invalidValue_failure() {
        // All tests involve valid indices

        // invalid question
        assertParseFailure(parser, "1" + INVALID_CARD_QUESTION_ARGS,
            Question.MESSAGE_QUESTION_CONSTRAINTS);

        // invalid question followed by valid answer
        assertParseFailure(parser, "1" + INVALID_CARD_QUESTION_ARGS + VALID_CARD_ANSWER_ARGS,
            Question.MESSAGE_QUESTION_CONSTRAINTS);

        // valid question followed by invalid question. The test case for invalid question followed by valid question
        // is tested at {@code parse_invalidValueFollowedByValidValue_success()}
        assertParseFailure(parser, "1" + VALID_CARD_QUESTION_ARGS + " " + INVALID_CARD_QUESTION_ARGS,
            Question.MESSAGE_QUESTION_CONSTRAINTS);

        // multiple invalid values, but only the first invalid value is captured
        assertParseFailure(parser, "1" + INVALID_CARD_QUESTION_ARGS + INVALID_CARD_ANSWER_ARGS,
            Question.MESSAGE_QUESTION_CONSTRAINTS);
    }

    @Test
    public void parse_validClassification_success() {
        //TODO
    }
}
