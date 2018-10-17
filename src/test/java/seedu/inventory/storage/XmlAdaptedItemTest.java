package seedu.inventory.storage;

import static org.junit.Assert.assertEquals;
import static seedu.inventory.storage.XmlAdaptedItem.MISSING_FIELD_MESSAGE_FORMAT;
import static seedu.inventory.testutil.TypicalItems.SAMSUNG;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import seedu.inventory.commons.exceptions.IllegalValueException;
import seedu.inventory.model.item.Image;
import seedu.inventory.model.item.Name;
import seedu.inventory.model.item.Price;
import seedu.inventory.model.item.Quantity;
import seedu.inventory.model.item.Sku;
import seedu.inventory.testutil.Assert;

public class XmlAdaptedItemTest {
    private static final String INVALID_IMAGE = " ";
    private static final String INVALID_NAME = "R@chel";
    private static final String INVALID_PRICE = "99.";
    private static final String INVALID_QUANTITY = "+651234";
    private static final String INVALID_SKU = "example.test";
    private static final String INVALID_TAG = "#gadget";

    private static final String VALID_IMAGE = SAMSUNG.getImage().toString();
    private static final String VALID_NAME = SAMSUNG.getName().toString();
    private static final String VALID_PRICE = SAMSUNG.getPrice().toString();
    private static final String VALID_QUANTITY = SAMSUNG.getQuantity().toString();
    private static final String VALID_SKU = SAMSUNG.getSku().toString();
    private static final List<XmlAdaptedTag> VALID_TAGS = SAMSUNG.getTags().stream()
            .map(XmlAdaptedTag::new)
            .collect(Collectors.toList());

    @Test
    public void toModelType_validItemDetails_returnsItem() throws Exception {
        XmlAdaptedItem item = new XmlAdaptedItem(SAMSUNG);
        assertEquals(SAMSUNG, item.toModelType());
    }

    @Test
    public void toModelType_invalidName_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(INVALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Name.MESSAGE_NAME_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullName_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(null, VALID_PRICE, VALID_QUANTITY, VALID_SKU,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Name.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidPrice_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, INVALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Price.MESSAGE_PRICE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullPrice_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, null, VALID_QUANTITY, VALID_SKU,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Price.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidQuantity_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_PRICE, INVALID_QUANTITY, VALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Quantity.MESSAGE_QUANTITY_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullQuantity_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_PRICE, null, VALID_SKU,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Quantity.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidSku_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, INVALID_SKU, VALID_IMAGE, VALID_TAGS);
        String expectedMessage = Sku.MESSAGE_SKU_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullSku_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, null,
                VALID_IMAGE, VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Sku.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidImage_throwsIllegalValueException() {
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, INVALID_IMAGE, VALID_TAGS);
        String expectedMessage = Image.MESSAGE_IMAGE_CONSTRAINTS;
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_nullImage_throwsIllegalValueException() {
        XmlAdaptedItem item = new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, null,
                VALID_TAGS);
        String expectedMessage = String.format(MISSING_FIELD_MESSAGE_FORMAT, Image.class.getSimpleName());
        Assert.assertThrows(IllegalValueException.class, expectedMessage, item::toModelType);
    }

    @Test
    public void toModelType_invalidTags_throwsIllegalValueException() {
        List<XmlAdaptedTag> invalidTags = new ArrayList<>(VALID_TAGS);
        invalidTags.add(new XmlAdaptedTag(INVALID_TAG));
        XmlAdaptedItem item =
                new XmlAdaptedItem(VALID_NAME, VALID_PRICE, VALID_QUANTITY, VALID_SKU, VALID_IMAGE, invalidTags);
        Assert.assertThrows(IllegalValueException.class, item::toModelType);
    }

}
