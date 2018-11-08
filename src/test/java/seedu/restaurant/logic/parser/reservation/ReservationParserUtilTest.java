package seedu.restaurant.logic.parser.reservation;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.reservation.Date;
import seedu.restaurant.model.reservation.Name;
import seedu.restaurant.model.reservation.Pax;
import seedu.restaurant.model.reservation.Time;
import seedu.restaurant.testutil.Assert;

//@@author m4dkip
public class ReservationParserUtilTest {
    private static final String INVALID_NAME = "$%@#%$@";
    private static final String INVALID_PAX = "4$5%";
    private static final String INVALID_DATE = "hi im a date";
    private static final String INVALID_TIME = "hi im a time";


    private static final String VALID_NAME = "Mingxian";
    private static final String VALID_PAX = "2";
    private static final String VALID_DATE = "31-12-2020";
    private static final String VALID_TIME = "12:00";

    private static final String WHITESPACE = " \t\r\n";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ReservationParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ReservationParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ReservationParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ReservationParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePax_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ReservationParserUtil.parsePax((String) null));
    }

    @Test
    public void parsePax_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ReservationParserUtil.parsePax(INVALID_PAX));
    }

    @Test
    public void parsePax_validValueWithoutWhitespace_returnsPax() throws Exception {
        Pax expectedPrice = new Pax(VALID_PAX);
        assertEquals(expectedPrice, ReservationParserUtil.parsePax(VALID_PAX));
    }

    @Test
    public void parsePax_validValueWithWhitespace_returnsTrimmedPax() throws Exception {
        String paxWithWhitespace = WHITESPACE + VALID_PAX + WHITESPACE;
        Pax expectedPrice = new Pax(VALID_PAX);
        assertEquals(expectedPrice, ReservationParserUtil.parsePax(paxWithWhitespace));
    }

    @Test
    public void parseDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ReservationParserUtil.parseDate((String) null));
    }

    @Test
    public void parseDate_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ReservationParserUtil.parseDate(INVALID_DATE));
    }

    @Test
    public void parseDate_validValueWithoutWhitespace_returnsDate() throws Exception {
        Date expectedPrice = new Date(VALID_DATE);
        assertEquals(expectedPrice, ReservationParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validValueWithWhitespace_returnsTrimmedDate() throws Exception {
        String paxWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedPrice = new Date(VALID_DATE);
        assertEquals(expectedPrice, ReservationParserUtil.parseDate(paxWithWhitespace));
    }

    @Test
    public void parseTime_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ReservationParserUtil.parseTime((String) null));
    }

    @Test
    public void parseTime_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ReservationParserUtil.parseTime(INVALID_TIME));
    }

    @Test
    public void parseTime_validValueWithoutWhitespace_returnsTime() throws Exception {
        Time expectedPrice = new Time(VALID_TIME);
        assertEquals(expectedPrice, ReservationParserUtil.parseTime(VALID_TIME));
    }

    @Test
    public void parseTime_validValueWithWhitespace_returnsTrimmedTime() throws Exception {
        String paxWithWhitespace = WHITESPACE + VALID_TIME + WHITESPACE;
        Time expectedPrice = new Time(VALID_TIME);
        assertEquals(expectedPrice, ReservationParserUtil.parseTime(paxWithWhitespace));
    }
}
