package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.deck.Performance;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_PERFORMANCE_STRING = "tough";

    private static final Performance VALID_PERFORMANCE = Performance.HARD;
    private static final String VALID_PERFORMANCE_STRING = VALID_PERFORMANCE.toString();
    private static final String VALID_PERFORMANCE_STRING_MIXED_CASE = "hArD";


    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parsePerformance_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePerformance(null));
    }

    @Test
    public void parsePerformance_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePerformance(INVALID_PERFORMANCE_STRING));
    }

    @Test
    public void parsePerformance_validValueWithoutWhitespace_returnsPerformance() throws Exception {
        assertEquals(VALID_PERFORMANCE, ParserUtil.parsePerformance(VALID_PERFORMANCE_STRING));
    }

    @Test
    public void parsePerformance_validValueWithWhitespace_returnsTrimmedPerformance() throws Exception {
        String performanceWithWhitespace = WHITESPACE + VALID_PERFORMANCE_STRING + WHITESPACE;
        assertEquals(VALID_PERFORMANCE, ParserUtil.parsePerformance(performanceWithWhitespace));
    }

    @Test
    public void parsePerformance_validValueWithMixedCase_returnsTrimmedPerformance() throws Exception {
        assertEquals(VALID_PERFORMANCE, ParserUtil.parsePerformance(VALID_PERFORMANCE_STRING_MIXED_CASE));
    }
}
