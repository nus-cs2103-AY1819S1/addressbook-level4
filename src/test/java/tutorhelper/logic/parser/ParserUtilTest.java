package tutorhelper.logic.parser;

import static org.junit.Assert.assertEquals;

import static tutorhelper.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static tutorhelper.logic.parser.ParserUtil.parseAddress;
import static tutorhelper.logic.parser.ParserUtil.parseEmail;
import static tutorhelper.logic.parser.ParserUtil.parseIndex;
import static tutorhelper.logic.parser.ParserUtil.parseName;
import static tutorhelper.logic.parser.ParserUtil.parsePhone;
import static tutorhelper.logic.parser.ParserUtil.parseTag;
import static tutorhelper.logic.parser.ParserUtil.parseTags;
import static tutorhelper.testutil.TypicalIndexes.INDEX_FIRST_STUDENT;

import java.util.Arrays;
import java.util.HashSet;
import java.util.Set;

import org.junit.Assert;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import tutorhelper.logic.parser.exceptions.ParseException;
import tutorhelper.model.student.Address;
import tutorhelper.model.student.Email;
import tutorhelper.model.student.Name;
import tutorhelper.model.student.Phone;
import tutorhelper.model.tag.Tag;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PHONE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_EMAIL = "example.com";
    private static final String INVALID_TAG = "#nonalphanumeric";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_PHONE = "12345678";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_EMAIL = "rachel@example.com";
    private static final String VALID_TAG_1 = "weak";
    private static final String VALID_TAG_2 = "smart";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseIndex_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parseIndex("10 a");
    }

    @Test
    public void parseIndex_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        parseIndex(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseIndex_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(INDEX_FIRST_STUDENT, parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_STUDENT, parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        tutorhelper.testutil.Assert.assertThrows(NullPointerException.class, () -> parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        tutorhelper.testutil.Assert.assertThrows(ParseException.class, () -> parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        Assert.assertEquals(expectedName, parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        Assert.assertEquals(expectedName, parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        tutorhelper.testutil.Assert.assertThrows(NullPointerException.class, () -> parsePhone((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        tutorhelper.testutil.Assert.assertThrows(ParseException.class, () -> parsePhone(INVALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        Phone expectedPhone = new Phone(VALID_PHONE);
        Assert.assertEquals(expectedPhone, parsePhone(VALID_PHONE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_PHONE + WHITESPACE;
        Phone expectedPhone = new Phone(VALID_PHONE);
        Assert.assertEquals(expectedPhone, parsePhone(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        tutorhelper.testutil.Assert.assertThrows(NullPointerException.class, () -> parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        tutorhelper.testutil.Assert.assertThrows(ParseException.class, () -> parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        Assert.assertEquals(expectedAddress, parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        Assert.assertEquals(expectedAddress, parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        tutorhelper.testutil.Assert.assertThrows(NullPointerException.class, () -> parseEmail((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        tutorhelper.testutil.Assert.assertThrows(ParseException.class, () -> parseEmail(INVALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        Email expectedEmail = new Email(VALID_EMAIL);
        Assert.assertEquals(expectedEmail, parseEmail(VALID_EMAIL));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_EMAIL + WHITESPACE;
        Email expectedEmail = new Email(VALID_EMAIL);
        Assert.assertEquals(expectedEmail, parseEmail(emailWithWhitespace));
    }

    @Test
    public void parseTag_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        parseTag(null);
    }

    @Test
    public void parseTag_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parseTag(INVALID_TAG);
    }

    @Test
    public void parseTag_validValueWithoutWhitespace_returnsTag() throws Exception {
        Tag expectedTag = new Tag(VALID_TAG_1);
        Assert.assertEquals(expectedTag, parseTag(VALID_TAG_1));
    }

    @Test
    public void parseTag_validValueWithWhitespace_returnsTrimmedTag() throws Exception {
        String tagWithWhitespace = WHITESPACE + VALID_TAG_1 + WHITESPACE;
        Tag expectedTag = new Tag(VALID_TAG_1);
        Assert.assertEquals(expectedTag, parseTag(tagWithWhitespace));
    }

    @Test
    public void parseTags_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        parseTags(null);
    }

    @Test
    public void parseTags_collectionWithInvalidTags_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        parseTags(VALID_TAG_1 + "," + INVALID_TAG);
    }

    @Test
    public void parseTags_collectionWithValidTags_returnsTagSet() throws Exception {
        Set<Tag> actualTagSet = parseTags(VALID_TAG_1 + "," + VALID_TAG_2);
        Set<Tag> expectedTagSet = new HashSet<Tag>(Arrays.asList(new Tag(VALID_TAG_1), new Tag(VALID_TAG_2)));

        assertEquals(expectedTagSet, actualTagSet);
    }
}
