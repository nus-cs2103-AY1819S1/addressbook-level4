package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_PERSON;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.category.Category;
import seedu.address.model.entry.EntryInfo;
import seedu.address.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_TAG = "#friend";
    private static final String INVALID_CATEGORY = "#work";
    private static final String INVALID_TITLE = "SOURCE ** ACADEMY";

    private static final String VALID_TAG_1 = "java";
    private static final String VALID_TAG_2 = "python";
    private static final String VALID_CATEGORY = "work";
    private static final String VALID_TITLE = "SOURCE ACADEMY";
    private static final String VALID_SUBHEADER = "Bachelor Of Computing";
    private static final String VALID_DURATION = "2010 JAN - 2012 JAN";



    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_PERSON, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseCategory_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseCategory(null);
    }

    @Test
    public void parseCategory_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseCategory(INVALID_CATEGORY);
    }

    @Test
    public void parseCategory_validValueWithoutWhitespace_returnsCategory() throws Exception {
        Category expectedTag = new Category(VALID_CATEGORY);
        assertEquals(expectedTag, ParserUtil.parseCategory(VALID_CATEGORY));
    }

    @Test
    public void parseCategory_validValueWithWhitespace_returnsTrimmedCategory() throws Exception {
        String cateWithWhitespace = WHITESPACE + VALID_CATEGORY + WHITESPACE;
        Category expectedCate = new Category(VALID_CATEGORY);
        assertEquals(expectedCate, ParserUtil.parseCategory(cateWithWhitespace));
    }

    @Test
    public void parseEntryInfo_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseEntryInfo(null, null, null);
    }

    @Test
    public void parseEntryInfo_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseEntryInfo(INVALID_TITLE, VALID_SUBHEADER, VALID_DURATION);
    }

    @Test
    public void parseEntryInfo_validValue_returnsEntryInfo() throws Exception {
        EntryInfo expectedEntryInfo = new EntryInfo(VALID_TITLE, VALID_SUBHEADER, VALID_DURATION);
        assertEquals(expectedEntryInfo, ParserUtil
                .parseEntryInfo(VALID_TITLE, VALID_SUBHEADER, VALID_DURATION));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        assertEquals(expectedTag, ParserUtil.parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, INVALID_TAG));
    }

    @Test
    public void parseTags_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseTags(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = ParserUtil.parseTags(Arrays.asList(VALID_TAG_1, VALID_TAG_2));
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
