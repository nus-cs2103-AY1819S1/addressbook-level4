package seedu.inventory.testutil;

import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PRICE_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_PRICE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_OPPO;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_GADGET;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import seedu.inventory.model.Inventory;
import seedu.inventory.model.item.Item;

/**
 * A utility class containing a list of {@code Item} objects to be used in tests.
 */
public class TypicalItems {

    public static final Item IPHONE = new ItemBuilder().withName("iPhone XR")
            .withPrice("1500.00")
            .withImage("docs/images/iphone.jpg").withSku("apple-iphone-xr")
            .withQuantity("30")
            .withTags("apple").build();
    public static final Item GOOGLE = new ItemBuilder().withName("Google Pixel XL").withPrice("1459.99")
            .withQuantity("3").withSku("google-pixel-xl").withImage("docs/images/google.jpg").build();
    public static final Item LG = new ItemBuilder().withName("LG G7").withPrice("1099.00").withQuantity("20")
            .withSku("lg-g7").withImage("docs/images/lg.jpg").withTags("smartphone").build();
    public static final Item SAMSUNGNOTE = new ItemBuilder().withName("Samsung Note 9").withPrice("1590.00")
            .withQuantity("1000").withSku("samsung-note-9").withImage("docs/images/huawei.jpg").build();
    public static final Item ONEPLUS = new ItemBuilder().withName("OnePlus 6").withPrice("1234.56").withQuantity("10")
            .withSku("oneplus-6").withImage("docs/images/oneplus.jpg").build();
    public static final Item HTC = new ItemBuilder().withName("HTC U6").withPrice("980.00").withQuantity("75")
            .withSku("htc-u6").withImage("docs/images/htc.jpg").build();
    public static final Item SAMSUNG = new ItemBuilder().withName("Samsung S9").withPrice("1299.99")
            .withImage("docs/images/samsung.jpg")
            .withSku("samsung-s9").withQuantity("100")
            .withTags("samsung", "smartphone").build();

    // Manually added
    public static final Item NOKIA = new ItemBuilder().withName("Nokia 3310").withQuantity("900")
            .withSku("nokia-3310").withImage("docs/images/nokia.jpg").build();
    public static final Item XIAOMI = new ItemBuilder().withName("XIAOMI MI 8").withQuantity("50")
            .withSku("xiaomi-mi-8").withImage("docs/images/xiaomi.jpg").build();

    // Manually added - Item's details found in {@code CommandTestUtil}
    public static final Item OPPO = new ItemBuilder().withName(VALID_NAME_OPPO).withPrice(VALID_PRICE_OPPO)
            .withQuantity(VALID_QUANTITY_OPPO).withSku(VALID_SKU_OPPO).withImage(VALID_IMAGE_OPPO)
            .withTags(VALID_TAG_GADGET).build();
    public static final Item SONY = new ItemBuilder().withName(VALID_NAME_SONY).withPrice(VALID_PRICE_SONY)
            .withQuantity(VALID_QUANTITY_SONY).withSku(VALID_SKU_SONY).withImage(VALID_IMAGE_SONY)
            .withTags(VALID_TAG_SMARTPHONE, VALID_TAG_GADGET).build();

    public static final String KEYWORD_MATCHING_SAMSUNG = "Samsung"; // A keyword that matches SAMSUNG

    private TypicalItems() {} // prevents instantiation

    /**
     * Returns an {@code Inventory} with all the typical items.
     */
    public static Inventory getTypicalInventory() {
        Inventory inventory = new Inventory();
        for (Item item : getTypicalItems()) {
            inventory.addItem(item);
        }
        return inventory;
    }

    public static List<Item> getTypicalItems() {
        return new ArrayList<>(Arrays.asList(IPHONE, GOOGLE, LG, SAMSUNGNOTE, ONEPLUS, HTC, SAMSUNG));
    }
}
