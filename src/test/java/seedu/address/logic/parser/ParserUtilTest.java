package seedu.address.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;
import static seedu.address.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.address.testutil.TypicalIndexes.INDEX_FIRST_TASK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.person.Description;
import seedu.address.model.person.DueDate;
import seedu.address.model.person.Name;
import seedu.address.model.person.PriorityValue;
import seedu.address.model.tag.Label;
import seedu.address.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_DUEDATE = "+651234";
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_PRIORITY_VALUE = "0";
    private static final String INVALID_LABEL = "#friend";

    private static final String VALID_NAME = "Rachel Walker";
    private static final String VALID_DUEDATE = "05-05-18";
    private static final String VALID_ADDRESS = "123 Main Street #0505";
    private static final String VALID_PRIORITY_VALUE = "8";
    private static final String VALID_LABEL_1 = "friend";
    private static final String VALID_LABEL_2 = "neighbour";

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
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_TASK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePhone_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDueDate((String) null));
    }

    @Test
    public void parsePhone_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDueDate(INVALID_DUEDATE));
    }

    @Test
    public void parsePhone_validValueWithoutWhitespace_returnsPhone() throws Exception {
        DueDate expectedPhone = new DueDate(VALID_DUEDATE);
        assertEquals(expectedPhone, ParserUtil.parseDueDate(VALID_DUEDATE));
    }

    @Test
    public void parsePhone_validValueWithWhitespace_returnsTrimmedPhone() throws Exception {
        String phoneWithWhitespace = WHITESPACE + VALID_DUEDATE + WHITESPACE;
        DueDate expectedPhone = new DueDate(VALID_DUEDATE);
        assertEquals(expectedPhone, ParserUtil.parseDueDate(phoneWithWhitespace));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseDescription((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseDescription(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Description expectedAddress = new Description(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseDescription(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Description expectedAddress = new Description(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseDescription(addressWithWhitespace));
    }

    @Test
    public void parseEmail_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePriorityValue((String) null));
    }

    @Test
    public void parseEmail_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePriorityValue(INVALID_PRIORITY_VALUE));
    }

    @Test
    public void parseEmail_validValueWithoutWhitespace_returnsEmail() throws Exception {
        PriorityValue expectedEmail = new PriorityValue(VALID_PRIORITY_VALUE);
        assertEquals(expectedEmail, ParserUtil.parsePriorityValue(VALID_PRIORITY_VALUE));
    }

    @Test
    public void parseEmail_validValueWithWhitespace_returnsTrimmedEmail() throws Exception {
        String emailWithWhitespace = WHITESPACE + VALID_PRIORITY_VALUE + WHITESPACE;
        PriorityValue expectedEmail = new PriorityValue(VALID_PRIORITY_VALUE);
        assertEquals(expectedEmail, ParserUtil.parsePriorityValue(emailWithWhitespace));
    }

    @Test
    public void parseLabel_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseLabel(null);
    }

    @Test
    public void parseLabel_invalidValue_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseLabel(INVALID_LABEL);
    }

    @Test
    public void parseLabel_validValueWithoutWhitespace_returnsLabel() throws Exception {
        Label expectedLabel = new Label(VALID_LABEL_1);
        assertEquals(expectedLabel, ParserUtil.parseLabel(VALID_LABEL_1));
    }

    @Test
    public void parseLabel_validValueWithWhitespace_returnsTrimmedLabel() throws Exception {
        String labelWithWhitespace = WHITESPACE + VALID_LABEL_1 + WHITESPACE;
        Label expectedLabel = new Label(VALID_LABEL_1);
        assertEquals(expectedLabel, ParserUtil.parseLabel(labelWithWhitespace));
    }

    @Test
    public void parseLabels_null_throwsNullPointerException() throws Exception {
        thrown.expect(NullPointerException.class);
        ParserUtil.parseLabels(null);
    }

    @Test
    public void parseLabels_collectionWithInvalidLabels_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseLabels(Arrays.asList(VALID_LABEL_1, INVALID_LABEL));
    }

    @Test
    public void parseLabels_emptyCollection_returnsEmptySet() throws Exception {
        assertTrue(ParserUtil.parseLabels(Collections.emptyList()).isEmpty());
    }

    @Test
    public void parseLabels_collectionWithValidLabels_returnsLabelSet() throws Exception {
        Set<Label> actualLabelSet = ParserUtil.parseLabels(Arrays.asList(VALID_LABEL_1, VALID_LABEL_2));
        Set<Label> expectedLabelSet = new HashSet<Label>(Arrays.asList(new Label(VALID_LABEL_1),
              new Label(VALID_LABEL_2)));

        assertEquals(expectedLabelSet, actualLabelSet);
    }
}
