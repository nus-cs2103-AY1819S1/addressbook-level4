package seedu.address.logic.parser;

import static seedu.address.commons.core.Messages.MESSAGE_INVALID_COMMAND_FORMAT;
import static seedu.address.logic.parser.CliSyntax.PREFIX_RATING;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseFailure;
import static seedu.address.logic.parser.CommandParserTestUtil.assertParseSuccess;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import org.junit.Test;

import seedu.address.logic.commands.RateCommand;
import seedu.address.model.person.Rating;

/**
 * As we are only doing white-box testing, our test cases do not cover path variations
 * outside of the RateCommand code. For example, inputs "1" and "1 abc" take the
 * same path through the RateCommand, and therefore we test only one of them.
 * The path variation for those two cases occur inside the ParserUtil, and
 * therefore should be covered by the ParserUtilTest.
 */
public class RateCommandParserTest {

    private static final String MESSAGE_INVALID_FORMAT =
            String.format(MESSAGE_INVALID_COMMAND_FORMAT, RateCommand.MESSAGE_USAGE);

    private RateCommandParser parser = new RateCommandParser();

    @Test
    public void parse_validArgs_success() {
        assertParseSuccess(parser, "1 " + PREFIX_RATING + "5",
                new RateCommand(INDEX_FIRST_PERSON, new Rating("5")));
    }

    @Test
    public void parse_invalidPreamble_failure() {
        // negative index
        assertParseFailure(parser, "-5 " + PREFIX_RATING + "5", MESSAGE_INVALID_FORMAT);

        // zero index
        assertParseFailure(parser, "0 " + PREFIX_RATING + "5", MESSAGE_INVALID_FORMAT);

        // invalid arguments being parsed as preamble
        assertParseFailure(parser, "1 some random string", MESSAGE_INVALID_FORMAT);

        // invalid prefix being parsed as preamble
        assertParseFailure(parser, "1 i/ string", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_compulsoryFieldMissing_failure() {
        // missing index
        assertParseFailure(parser, PREFIX_RATING + "8", MESSAGE_INVALID_FORMAT);

        // missing rating prefix
        assertParseFailure(parser, "1 8", MESSAGE_INVALID_FORMAT);
    }

    @Test
    public void parse_invalidArgs_failure() {
        // larger than 10
        assertParseFailure(parser, "1 " + PREFIX_RATING + "11", Rating.MESSAGE_CONSTRAINTS);

        // non-numeric argument
        assertParseFailure(parser, "1 " + PREFIX_RATING + "five", Rating.MESSAGE_CONSTRAINTS);

        // empty input
        assertParseFailure(parser, "1 " + PREFIX_RATING, Rating.MESSAGE_CONSTRAINTS);
    }
}
