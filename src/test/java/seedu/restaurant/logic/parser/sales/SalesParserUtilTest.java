package seedu.restaurant.logic.parser.sales;

import static org.junit.Assert.assertEquals;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.restaurant.logic.parser.exceptions.ParseException;
import seedu.restaurant.logic.parser.menu.ItemParserUtil;
import seedu.restaurant.model.sales.Date;
import seedu.restaurant.model.sales.ItemName;
import seedu.restaurant.model.sales.Price;
import seedu.restaurant.model.sales.QuantitySold;
import seedu.restaurant.testutil.Assert;

public class SalesParserUtilTest {

    private static final String INVALID_DATE_ONE = "31-02-2018";
    private static final String INVALID_DATE_TWO = "1st Feb 2018";
    private static final String INVALID_DATE_THREE = "31/02/2018";
    private static final String INVALID_NAME = "John!";
    private static final String INVALID_QUANTITY_SOLD_ONE = "3.5";
    private static final String INVALID_QUANTITY_SOLD_TWO = "-1";
    private static final String INVALID_QUANTITY_SOLD_THREE = "twenty six";
    private static final String INVALID_PRICE_ONE = "-2";
    private static final String INVALID_PRICE_TWO = "five fifty";

    private static final String VALID_DATE = "12-12-2018";
    private static final String VALID_NAME = "NKJ";
    private static final String VALID_QUANTITY_SOLD = "52";
    private static final String VALID_PRICE = "12.99";

    private static final String WHITESPACE = " \t";

    @Rule
    public final ExpectedException thrown = ExpectedException.none();

    @Test
    public void parseDate_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> SalesParserUtil.parseDate(null));
    }

    @Test
    public void parseDate_invalidDate_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parseDate(INVALID_DATE_ONE));
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parseDate(INVALID_DATE_TWO));
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parseDate(INVALID_DATE_THREE));
    }

    @Test
    public void parseDate_validDateWithoutWhitespace_returnsDate() throws ParseException {
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, SalesParserUtil.parseDate(VALID_DATE));
    }

    @Test
    public void parseDate_validDateWithWhitespace_returnsDate() throws ParseException {
        String dateWithWhitespace = WHITESPACE + VALID_DATE + WHITESPACE;
        Date expectedDate = new Date(VALID_DATE);
        assertEquals(expectedDate, SalesParserUtil.parseDate(dateWithWhitespace));
    }

    @Test
    public void parseName_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> SalesParserUtil.parseItemName(null));
    }

    @Test
    public void parseName_invalidName_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parseItemName(INVALID_NAME));
    }

    @Test
    public void parseName_validNameWithoutWhitespace_returnsName() throws Exception {
        ItemName expectedName = new ItemName(VALID_NAME);
        assertEquals(expectedName, SalesParserUtil.parseItemName(VALID_NAME));
    }

    @Test
    public void parseName_validNameWithWhitespace_returnsName() throws Exception {
        String nameWithWhitespace = WHITESPACE + VALID_NAME + WHITESPACE;
        ItemName expectedName = new ItemName(VALID_NAME);
        assertEquals(expectedName, SalesParserUtil.parseItemName(nameWithWhitespace));
    }

    @Test
    public void parseQuantitySold_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> SalesParserUtil.parseQuantitySold(null));
    }

    @Test
    public void parseQuantitySold_invalidQuantity_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parseQuantitySold(INVALID_QUANTITY_SOLD_ONE));
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parseQuantitySold(INVALID_QUANTITY_SOLD_TWO));
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parseQuantitySold(INVALID_QUANTITY_SOLD_THREE));
    }

    @Test
    public void parseQuantitySold_validQuantityWithoutWhitespace_returnsQuantitySold() throws Exception {
        QuantitySold expectedQuantity = new QuantitySold(VALID_QUANTITY_SOLD);
        assertEquals(expectedQuantity, SalesParserUtil.parseQuantitySold(VALID_QUANTITY_SOLD));
    }

    @Test
    public void parseQuantitySold_validQuantityWithWhitespace_returnsQuantitySold() throws Exception {
        String quantityWithWhitespace = WHITESPACE + VALID_QUANTITY_SOLD + WHITESPACE;
        QuantitySold expectedQuantity = new QuantitySold(VALID_QUANTITY_SOLD);
        assertEquals(expectedQuantity, SalesParserUtil.parseQuantitySold(quantityWithWhitespace));
    }

    @Test
    public void parsePrice_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> SalesParserUtil.parsePrice(null));
    }

    @Test
    public void parsePrice_invalidPrice_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parsePrice(INVALID_PRICE_ONE));
        Assert.assertThrows(ParseException.class, () -> SalesParserUtil.parsePrice(INVALID_PRICE_TWO));
    }

    @Test
    public void parsePrice_validPriceWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, SalesParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validPriceWithWhitespace_returnsPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, SalesParserUtil.parsePrice(priceWithWhitespace));
    }
}
