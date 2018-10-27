package seedu.address.logic;

import static org.junit.Assert.assertEquals;

import java.util.Optional;

import org.junit.Test;

import seedu.address.commons.core.index.Index;

public class IndexParserUtilTest {
    private static final String VALID_INDEX = "1";
    private static final String VALID_INDEX_LARGER = "1000";
    private static final String VALID_INDEX_WITHSPACES = "  10  ";
    private static final String INVALID_INDEX = "0";
    private static final String INVALID_NEGATIVE_INDEX = "-1";
    private static final String INVALID_INDEX_WITHSPACES = "  -10 ";

    @Test
    public void validIndex() {
        assertEquals(Optional.of(Index.fromOneBased(1)), IndexParserUtil.getIndex(VALID_INDEX));
        assertEquals(Optional.of(Index.fromOneBased(1000)), IndexParserUtil.getIndex(VALID_INDEX_LARGER));
        assertEquals(Optional.of(Index.fromOneBased(10)), IndexParserUtil.getIndex(VALID_INDEX_WITHSPACES));
    }

    @Test
    public void invalidIndex() {
        assertEquals(Optional.empty(), IndexParserUtil.getIndex((INVALID_INDEX)));
        assertEquals(Optional.empty(), IndexParserUtil.getIndex((INVALID_NEGATIVE_INDEX)));
        assertEquals(Optional.empty(), IndexParserUtil.getIndex((INVALID_INDEX_WITHSPACES)));
    }
}
