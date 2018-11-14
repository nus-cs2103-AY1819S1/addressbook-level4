package seedu.parking.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.parking.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.parking.testutil.TypicalIndexes.INDEX_FIRST_CARPARK;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.parking.logic.parser.exceptions.ParseException;
import seedu.parking.model.carpark.Address;
import seedu.parking.model.carpark.CarparkNumber;
import seedu.parking.model.carpark.CarparkType;
import seedu.parking.model.carpark.Coordinate;
import seedu.parking.model.carpark.FreeParking;
import seedu.parking.model.carpark.LotsAvailable;
import seedu.parking.model.carpark.NightParking;
import seedu.parking.model.carpark.ShortTerm;
import seedu.parking.model.carpark.TotalLots;
import seedu.parking.model.carpark.TypeOfParking;
import seedu.parking.model.tag.Tag;
import seedu.parking.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_ADDRESS = " ";
    private static final String INVALID_CARPARK_NUMBER = "R@chel";
    private static final String INVALID_CARPARK_TYPE = "";
    private static final String INVALID_COORDINATE = "+651234, a&890";
    private static final String INVALID_FREE_PARKING = " ";
    private static final String INVALID_LOTS_AVAILABLE = "YES";
    private static final String INVALID_NIGHT_PARKING = " ";
    private static final String INVALID_SHORT_TERM = "";
    private static final String INVALID_TOTAL_LOTS = "-10";
    private static final String INVALID_TYPE_OF_PARKING = " ";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_ADDRESS = "BLK 55 LENGKOK BAHRU";
    private static final String VALID_CARPARK_NUMBER = "BM12";
    private static final String VALID_CARPARK_TYPE = "SURFACE CAR PARK";
    private static final String VALID_COORDINATE = "25769.3044, 30009.7592";
    private static final String VALID_FREE_PARKING = "SUN & PH FR 7AM-10:30PM";
    private static final String VALID_LOTS_AVAILABLE = "26";
    private static final String VALID_NIGHT_PARKING = "YES";
    private static final String VALID_SHORT_TERM = "WHOLE DAY";
    private static final String VALID_TOTAL_LOTS = "60";
    private static final String VALID_TYPE_OF_PARKING = "ELECTRONIC PARKING";
    private static final String VALID_TAG_1 = "Home";
    private static final String VALID_TAG_2 = "Office";

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
        assertEquals(INDEX_FIRST_CARPARK, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_CARPARK, ParserUtil.parseIndex("  1  "));
    }

    @Test
    public void parseTime_invalidInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        ParserUtil.parseTime("60.8");
    }

    @Test
    public void parseTime_outOfRangeInput_throwsParseException() throws Exception {
        thrown.expect(ParseException.class);
        thrown.expectMessage(MESSAGE_INVALID_INDEX);
        ParserUtil.parseTime(Long.toString(Integer.MAX_VALUE + 1));
    }

    @Test
    public void parseTime_validInput_success() throws Exception {
        // No whitespaces
        assertEquals(60, ParserUtil.parseTime("60"));

        // Leading and trailing whitespaces
        assertEquals(60, ParserUtil.parseTime("  60  "));
    }

    @Test
    public void parseAddress_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseAddress((String) null));
    }

    @Test
    public void parseAddress_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseAddress(INVALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithoutWhitespace_returnsAddress() throws Exception {
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(VALID_ADDRESS));
    }

    @Test
    public void parseAddress_validValueWithWhitespace_returnsTrimmedAddress() throws Exception {
        String addressWithWhitespace = WHITESPACE + VALID_ADDRESS + WHITESPACE;
        Address expectedAddress = new Address(VALID_ADDRESS);
        assertEquals(expectedAddress, ParserUtil.parseAddress(addressWithWhitespace));
    }

    @Test
    public void parseCarparkNumber_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCarparkNumber((String) null));
    }

    @Test
    public void parseCarparkNumber_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCarparkNumber(INVALID_CARPARK_NUMBER));
    }

    @Test
    public void parseCarparkNumber_validValueWithoutWhitespace_returnsCarparkNumber() throws Exception {
        CarparkNumber expectedCarparkNumber = new CarparkNumber(VALID_CARPARK_NUMBER);
        assertEquals(expectedCarparkNumber, ParserUtil.parseCarparkNumber(VALID_CARPARK_NUMBER));
    }

    @Test
    public void parseCarparkNumber_validValueWithWhitespace_returnsTrimmedCarparkNumber() throws Exception {
        String carparkNumberWithWhitespace = WHITESPACE + VALID_CARPARK_NUMBER + WHITESPACE;
        CarparkNumber expectedCarparkNumber = new CarparkNumber(VALID_CARPARK_NUMBER);
        assertEquals(expectedCarparkNumber, ParserUtil.parseCarparkNumber(carparkNumberWithWhitespace));
    }

    @Test
    public void parseCarparkType_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCarparkType((String) null));
    }

    @Test
    public void parseCarparkType_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCarparkType(INVALID_CARPARK_TYPE));
    }

    @Test
    public void parseCarparkType_validValueWithoutWhitespace_returnsCarparkType() throws Exception {
        CarparkType expectedCarparkType = new CarparkType(VALID_CARPARK_TYPE);
        assertEquals(expectedCarparkType, ParserUtil.parseCarparkType(VALID_CARPARK_TYPE));
    }

    @Test
    public void parseCarparkType_validValueWithWhitespace_returnsTrimmedCarparkType() throws Exception {
        String carparkTypeWithWhitespace = WHITESPACE + VALID_CARPARK_TYPE + WHITESPACE;
        CarparkType expectedCarparkType = new CarparkType(VALID_CARPARK_TYPE);
        assertEquals(expectedCarparkType, ParserUtil.parseCarparkType(carparkTypeWithWhitespace));
    }

    @Test
    public void parseCoordinate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseCoordinate((String) null));
    }

    @Test
    public void parseCoordinate_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseCoordinate(INVALID_COORDINATE));
    }

    @Test
    public void parseCoordinate_validValueWithoutWhitespace_returnsCoordinate() throws Exception {
        Coordinate expectedCoordinate = new Coordinate(VALID_COORDINATE);
        assertEquals(expectedCoordinate, ParserUtil.parseCoordinate(VALID_COORDINATE));
    }

    @Test
    public void parseCoordinate_validValueWithWhitespace_returnsTrimmedCoordinate() throws Exception {
        String coordinateWithWhitespace = WHITESPACE + VALID_COORDINATE + WHITESPACE;
        Coordinate expectedCoordinate = new Coordinate(VALID_COORDINATE);
        assertEquals(expectedCoordinate, ParserUtil.parseCoordinate(coordinateWithWhitespace));
    }

    @Test
    public void parseFreeParking_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseFreeParking((String) null));
    }

    @Test
    public void parseFreeParking_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseFreeParking(INVALID_FREE_PARKING));
    }

    @Test
    public void parseFreeParking_validValueWithoutWhitespace_returnsFreeParking() throws Exception {
        FreeParking expectedFreeParking = new FreeParking(VALID_FREE_PARKING);
        assertEquals(expectedFreeParking, ParserUtil.parseFreeParking(VALID_FREE_PARKING));
    }

    @Test
    public void parseFreeParking_validValueWithWhitespace_returnsTrimmedFreeParking() throws Exception {
        String freeParkingWithWhitespace = WHITESPACE + VALID_FREE_PARKING + WHITESPACE;
        FreeParking expectedFreeParking = new FreeParking(VALID_FREE_PARKING);
        assertEquals(expectedFreeParking, ParserUtil.parseFreeParking(freeParkingWithWhitespace));
    }

    @Test
    public void parseLotsAvailable_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseLotsAvailable((String) null));
    }

    @Test
    public void parseLotsAvailable_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseLotsAvailable(INVALID_LOTS_AVAILABLE));
    }

    @Test
    public void parseLotsAvailable_validValueWithoutWhitespace_returnsLotsAvailable() throws Exception {
        LotsAvailable expectedLotsAvailable = new LotsAvailable(VALID_LOTS_AVAILABLE);
        assertEquals(expectedLotsAvailable, ParserUtil.parseLotsAvailable(VALID_LOTS_AVAILABLE));
    }

    @Test
    public void parseLotsAvailable_validValueWithWhitespace_returnsTrimmedLotsAvailable() throws Exception {
        String lotsAvailableWithWhitespace = WHITESPACE + VALID_LOTS_AVAILABLE + WHITESPACE;
        LotsAvailable expectedLotsAvailable = new LotsAvailable(VALID_LOTS_AVAILABLE);
        assertEquals(expectedLotsAvailable, ParserUtil.parseLotsAvailable(lotsAvailableWithWhitespace));
    }

    @Test
    public void parseNightParking_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseNightParking((String) null));
    }

    @Test
    public void parseNightParking_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseNightParking(INVALID_NIGHT_PARKING));
    }

    @Test
    public void parseNightParking_validValueWithoutWhitespace_returnsNightParking() throws Exception {
        NightParking expectedNightParking = new NightParking(VALID_NIGHT_PARKING);
        assertEquals(expectedNightParking, ParserUtil.parseNightParking(VALID_NIGHT_PARKING));
    }

    @Test
    public void parseNightParking_validValueWithWhitespace_returnsTrimmedNightParking() throws Exception {
        String nightParkingWithWhitespace = WHITESPACE + VALID_NIGHT_PARKING + WHITESPACE;
        NightParking expectedNightParking = new NightParking(VALID_NIGHT_PARKING);
        assertEquals(expectedNightParking, ParserUtil.parseNightParking(nightParkingWithWhitespace));
    }

    @Test
    public void parseShortTerm_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseShortTerm((String) null));
    }

    @Test
    public void parseShortTerm_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseShortTerm(INVALID_SHORT_TERM));
    }

    @Test
    public void parseShortTerm_validValueWithoutWhitespace_returnsShortTerm() throws Exception {
        ShortTerm expectedShortTerm = new ShortTerm(VALID_SHORT_TERM);
        assertEquals(expectedShortTerm, ParserUtil.parseShortTerm(VALID_SHORT_TERM));
    }

    @Test
    public void parseShortTerm_validValueWithWhitespace_returnsTrimmedShortTerm() throws Exception {
        String shortTermWithWhitespace = WHITESPACE + VALID_SHORT_TERM + WHITESPACE;
        ShortTerm expectedShortTerm = new ShortTerm(VALID_SHORT_TERM);
        assertEquals(expectedShortTerm, ParserUtil.parseShortTerm(shortTermWithWhitespace));
    }

    @Test
    public void parseTotalLots_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTotalLots((String) null));
    }

    @Test
    public void parseTotalLots_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTotalLots(INVALID_TOTAL_LOTS));
    }

    @Test
    public void parseTotalLots_validValueWithoutWhitespace_returnsTotalLots() throws Exception {
        TotalLots expectedTotalLots = new TotalLots(VALID_TOTAL_LOTS);
        assertEquals(expectedTotalLots, ParserUtil.parseTotalLots(VALID_TOTAL_LOTS));
    }

    @Test
    public void parseTotalLots_validValueWithWhitespace_returnsTrimmedTotalLots() throws Exception {
        String totalLotsWithWhitespace = WHITESPACE + VALID_TOTAL_LOTS + WHITESPACE;
        TotalLots expectedTotalLots = new TotalLots(VALID_TOTAL_LOTS);
        assertEquals(expectedTotalLots, ParserUtil.parseTotalLots(totalLotsWithWhitespace));
    }

    @Test
    public void parseTypeOfParking_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseTypeOfParking((String) null));
    }

    @Test
    public void parseTypeOfParking_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseTypeOfParking(INVALID_TYPE_OF_PARKING));
    }

    @Test
    public void parseTypeOfParking_validValueWithoutWhitespace_returnsTypeOfParking() throws Exception {
        TypeOfParking expectedTypeOfParking = new TypeOfParking(VALID_TYPE_OF_PARKING);
        assertEquals(expectedTypeOfParking, ParserUtil.parseTypeOfParking(VALID_TYPE_OF_PARKING));
    }

    @Test
    public void parseTypeOfParking_validValueWithWhitespace_returnsTrimmedTypeOfParking() throws Exception {
        String typeOfParkingWithWhitespace = WHITESPACE + VALID_TYPE_OF_PARKING + WHITESPACE;
        TypeOfParking expectedTypeOfParking = new TypeOfParking(VALID_TYPE_OF_PARKING);
        assertEquals(expectedTypeOfParking, ParserUtil.parseTypeOfParking(typeOfParkingWithWhitespace));
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
