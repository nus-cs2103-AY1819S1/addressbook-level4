package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_IMAGE;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;

public class ParserUtilTest {

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndexInvalidInputThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndexOutOfRangeInputThrowsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndexValidInputSuccess() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_IMAGE, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_IMAGE, ParserUtil.parseIndex("  1  "));
    }
}

