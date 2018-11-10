package seedu.restaurant.logic.parser.menu;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.model.menu.Name;
import seedu.restaurant.model.menu.Price;
import seedu.restaurant.testutil.Assert;

//@@author yican95
public class ItemParserUtilTest {
    private static final String INVALID_NAME = "F@ies";
    private static final String INVALID_PERCENT = "1.00000000";
    private static final String INVALID_PRICE = "+651234";


    private static final String VALID_NAME = "Fries";
    private static final String VALID_PERCENT = "10";
    private static final String VALID_PRICE = "123456";

    private static final String WHITESPACE = " \t\r\n";

    private static final double DELTA = 1e-15;

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ItemParserUtil.parseName((String) null));
    }

    @Test
    public void parseName_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ItemParserUtil.parseName(INVALID_NAME));
    }

    @Test
    public void parseName_validValueWithoutWhitespace_returnsName() throws Exception {
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ItemParserUtil.parseName(VALID_NAME));
    }

    @Test
    public void parseName_validValueWithWhitespace_returnsTrimmedName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        Name expectedName = new Name(VALID_NAME);
        assertEquals(expectedName, ItemParserUtil.parseName(nameWithWhitespace));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ItemParserUtil.parsePrice((String) null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ItemParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ItemParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ItemParserUtil.parsePrice(priceWithWhitespace));
    }

    @Test
    public void parsePercent_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ItemParserUtil.parsePercent((String) null));
    }

    @Test
    public void parsePercent_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ItemParserUtil.parsePercent(INVALID_PERCENT));
    }

    @Test
    public void parsePercent_validValueWithoutWhitespace_returnsPercent() throws Exception {
        double expectedPercent = Double.parseDouble(VALID_PERCENT);
        assertEquals(expectedPercent, ItemParserUtil.parsePercent(VALID_PERCENT), DELTA);
    }

    @Test
    public void parsePercent_validValueWithWhitespace_returnsTrimmedPercent() throws Exception {
        String percentWithWhitespace = WHITESPACE + VALID_PERCENT + WHITESPACE;
        double expectedPercent = Double.parseDouble(VALID_PERCENT);
        assertEquals(expectedPercent, ItemParserUtil.parsePercent(percentWithWhitespace), DELTA);
    }
}
