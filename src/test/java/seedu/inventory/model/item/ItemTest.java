package seedu.inventory.model.item;

import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_NAME_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_QUANTITY_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_SKU_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.SONY;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.testutil.ItemBuilder;

public class ItemTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    @Test
    public void asObservableList_modifyList_throwsUnsupportedOperationException() {
        Item item = new ItemBuilder().build();
        thrown.expect(UnsupportedOperationException.class);
        item.getTags().remove(0);
    }

    @Test
    public void isSameItem() {
        // same object -> returns true
        assertTrue(IPHONE.isSameItem(IPHONE));

        // null -> returns false
        assertFalse(IPHONE.isSameItem(null));

        // different quantity and sku -> returns false
        Item editedIphone = new ItemBuilder(IPHONE).withQuantity(VALID_QUANTITY_SONY).withSku(VALID_SKU_SONY).build();
        assertFalse(IPHONE.isSameItem(editedIphone));

        // different sku -> returns false
        editedIphone = new ItemBuilder(IPHONE).withSku(VALID_SKU_SONY).build();
        assertFalse(IPHONE.isSameItem(editedIphone));

        // same name, different sku, different attributes -> returns false
        editedIphone = new ItemBuilder(IPHONE).withSku(VALID_SKU_SONY).withImage(VALID_IMAGE_SONY)
                .withTags(VALID_TAG_SMARTPHONE).build();
        assertFalse(IPHONE.isSameItem(editedIphone));

        // same name, same image, different attributes -> returns true
        editedIphone = new ItemBuilder(IPHONE).withQuantity(VALID_QUANTITY_SONY).withImage(VALID_IMAGE_SONY)
                .withTags(VALID_TAG_SMARTPHONE).build();
        assertTrue(IPHONE.isSameItem(editedIphone));

        // same name, same quantity, same image, different attributes -> returns true
        editedIphone = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE).build();
        assertTrue(IPHONE.isSameItem(editedIphone));
    }

    @Test
    public void equals() {
        // same values -> returns true
        Item iphoneCopy = new ItemBuilder(IPHONE).build();
        assertTrue(IPHONE.equals(iphoneCopy));

        // same object -> returns true
        assertTrue(IPHONE.equals(IPHONE));

        // null -> returns false
        assertFalse(IPHONE.equals(null));

        // different type -> returns false
        assertFalse(IPHONE.equals(5));

        // different item -> returns false
        assertFalse(IPHONE.equals(SONY));

        // different name -> returns false
        Item editedAlice = new ItemBuilder(IPHONE).withName(VALID_NAME_SONY).build();
        assertFalse(IPHONE.equals(editedAlice));

        // different quantity -> returns false
        editedAlice = new ItemBuilder(IPHONE).withQuantity(VALID_QUANTITY_SONY).build();
        assertFalse(IPHONE.equals(editedAlice));

        // different SKU -> returns false
        editedAlice = new ItemBuilder(IPHONE).withSku(VALID_SKU_SONY).build();
        assertFalse(IPHONE.equals(editedAlice));

        // different image -> returns false
        editedAlice = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).build();
        assertFalse(IPHONE.equals(editedAlice));

        // different tags -> returns false
        editedAlice = new ItemBuilder(IPHONE).withTags(VALID_TAG_SMARTPHONE).build();
        assertFalse(IPHONE.equals(editedAlice));
    }
}
