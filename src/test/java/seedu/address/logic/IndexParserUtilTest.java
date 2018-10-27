package seedu.address.logic;

import org.junit.Test;
import seedu.address.commons.core.index.Index;

import java.util.Optional;

import static org.junit.Assert.*;

public class IndexParserUtilTest {
    private static final String VALID_INDEX = "1";
    private static final String VALID_INDEX_LARGER = "1000";
    private static final String VALID_INDEX_WITHSPACES = "  10  ";
    private static final String INVALID_INDEX = "0";
    private static final String INVALID_NEGATIVE_INDEX = "-1";
    private static final String INVALID_INDEX_WITHSPACES = "  -10 ";

    @Test
    public void validIndex() {
        assertEquals(IndexParserUtil.getIndex(VALID_INDEX), Optional.of(Index.fromOneBased(1)));
        assertEquals(IndexParserUtil.getIndex(VALID_INDEX_LARGER), Optional.of(Index.fromOneBased(1000)));
        assertEquals(IndexParserUtil.getIndex(VALID_INDEX_WITHSPACES), Optional.of(Index.fromOneBased(10)));
    }

    @Test
    public void invalidIndex() {
        assertEquals(IndexParserUtil.getIndex((INVALID_INDEX)), Optional.empty());
        assertEquals(IndexParserUtil.getIndex((INVALID_NEGATIVE_INDEX)), Optional.empty());
        assertEquals(IndexParserUtil.getIndex((INVALID_INDEX_WITHSPACES)), Optional.empty());
    }
}