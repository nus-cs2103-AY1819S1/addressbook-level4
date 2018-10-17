package seedu.inventory.logic.parser;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

import static seedu.inventory.logic.parser.ParserUtil.MESSAGE_INVALID_INDEX;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_FIRST_ITEM;

import java.util.Arrays;
import java.util.Collections;
import java.util.HashSet;
import java.util.Set;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.logic.parser.exceptions.ParseException;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.model.tag.Tag;
import seedu.inventory.testutil.Assert;

public class ParserUtilTest {
    private static final String INVALID_IMAGE = " ";
    private static final String INVALID_NAME = "iPh0ne!";
    private static final String INVALID_PRICE = "99.";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_SKU = "asd!asd";
    private static final String INVALID_TAG = "#friend";

    private static final String VALID_IMAGE = "docs/images/iphone.jpg";
    private static final String VALID_NAME = "iPhone XR";
    private static final String VALID_PRICE = "1500.00";
    private static final String VALID_QUANTITY = "123456";
    private static final String VALID_SKU = "iphone-xr";
    private static final String VALID_TAG_1 = "friend";
    private static final String VALID_TAG_2 = "neighbour";

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
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("1"));

        // Leading and trailing whitespaces
        assertEquals(INDEX_FIRST_ITEM, ParserUtil.parseIndex("  1  "));
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
    public void parsePrice_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parsePrice((String) null));
    }

    @Test
    public void parsePrice_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parsePrice(INVALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithoutWhitespace_returnsPrice() throws Exception {
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(VALID_PRICE));
    }

    @Test
    public void parsePrice_validValueWithWhitespace_returnsTrimmedPrice() throws Exception {
        String priceWithWhitespace = WHITESPACE + VALID_PRICE + WHITESPACE;
        Price expectedPrice = new Price(VALID_PRICE);
        assertEquals(expectedPrice, ParserUtil.parsePrice(priceWithWhitespace));
    }

    @Test
    public void parseQuantity_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseQuantity((String) null));
    }

    @Test
    public void parseQuantity_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseQuantity(INVALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithoutWhitespace_returnsQuantity() throws Exception {
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(VALID_QUANTITY));
    }

    @Test
    public void parseQuantity_validValueWithWhitespace_returnsTrimmedQuantity() throws Exception {
        String quantityWithWhitespace = WHITESPACE + VALID_QUANTITY + WHITESPACE;
        Quantity expectedQuantity = new Quantity(VALID_QUANTITY);
        assertEquals(expectedQuantity, ParserUtil.parseQuantity(quantityWithWhitespace));
    }

    @Test
    public void parseImage_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseImage((String) null));
    }

    @Test
    public void parseImage_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseImage(INVALID_IMAGE));
    }

    @Test
    public void parseImage_validValueWithoutWhitespace_returnsImage() throws Exception {
        Image expectedImage = new Image(VALID_IMAGE);
        assertEquals(expectedImage, ParserUtil.parseImage(VALID_IMAGE));
    }

    @Test
    public void parseImage_validValueWithWhitespace_returnsTrimmedImage() throws Exception {
        String imageWithWhitespace = WHITESPACE + VALID_IMAGE + WHITESPACE;
        Image expectedImage = new Image(VALID_IMAGE);
        assertEquals(expectedImage, ParserUtil.parseImage(imageWithWhitespace));
    }

    @Test
    public void parseSku_null_throwsNullPointerException() {
        Assert.assertThrows(NullPointerException.class, () -> ParserUtil.parseSku((String) null));
    }

    @Test
    public void parseSku_invalidValue_throwsParseException() {
        Assert.assertThrows(ParseException.class, () -> ParserUtil.parseSku(INVALID_SKU));
    }

    @Test
    public void parseSku_validValueWithoutWhitespace_returnsSku() throws Exception {
        Sku expectedSku = new Sku(VALID_SKU);
        assertEquals(expectedSku, ParserUtil.parseSku(VALID_SKU));
    }

    @Test
    public void parseSku_validValueWithWhitespace_returnsTrimmedSku() throws Exception {
        String skuWithWhitespace = WHITESPACE + VALID_SKU + WHITESPACE;
        Sku expectedSku = new Sku(VALID_SKU);
        assertEquals(expectedSku, ParserUtil.parseSku(skuWithWhitespace));
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
