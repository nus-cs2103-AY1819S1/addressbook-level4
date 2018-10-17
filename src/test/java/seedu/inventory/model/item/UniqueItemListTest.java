package seedu.inventory.model.item;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertFalse;
import static org.junit.Assert.assertTrue;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_IMAGE_SONY;
import static seedu.inventory.logic.commands.CommandTestUtil.VALID_TAG_SMARTPHONE;
import static seedu.inventory.testutil.TypicalItems.IPHONE;
import static seedu.inventory.testutil.TypicalItems.SONY;

import java.util.Arrays;
import java.util.Collections;
import java.util.List;

import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import seedu.inventory.model.item.exceptions.DuplicateItemException;
import seedu.inventory.model.item.exceptions.ItemNotFoundException;
import seedu.inventory.testutil.ItemBuilder;

public class UniqueItemListTest {
    @Rule
    public ExpectedException thrown = ExpectedException.none();

    private final UniqueItemList uniqueItemList = new UniqueItemList();

    @Test
    public void contains_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.contains(null);
    }

    @Test
    public void contains_itemNotInList_returnsFalse() {
        assertFalse(uniqueItemList.contains(IPHONE));
    }

    @Test
    public void contains_itemInList_returnsTrue() {
        uniqueItemList.add(IPHONE);
        assertTrue(uniqueItemList.contains(IPHONE));
    }

    @Test
    public void contains_itemWithSameIdentityFieldsInList_returnsTrue() {
        uniqueItemList.add(IPHONE);
        Item editedAlice = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        assertTrue(uniqueItemList.contains(editedAlice));
    }

    @Test
    public void add_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.add(null);
    }

    @Test
    public void add_duplicateItem_throwsDuplicateItemException() {
        uniqueItemList.add(IPHONE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.add(IPHONE);
    }

    @Test
    public void setItem_nullTargetItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(null, IPHONE);
    }

    @Test
    public void setItem_nullEditedItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItem(IPHONE, null);
    }

    @Test
    public void setItem_targetItemNotInList_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.setItem(IPHONE, IPHONE);
    }

    @Test
    public void setItem_editedItemIsSameItem_success() {
        uniqueItemList.add(IPHONE);
        uniqueItemList.setItem(IPHONE, IPHONE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(IPHONE);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasSameIdentity_success() {
        uniqueItemList.add(IPHONE);
        Item editedAlice = new ItemBuilder(IPHONE).withImage(VALID_IMAGE_SONY).withTags(VALID_TAG_SMARTPHONE)
                .build();
        uniqueItemList.setItem(IPHONE, editedAlice);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(editedAlice);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasDifferentIdentity_success() {
        uniqueItemList.add(IPHONE);
        uniqueItemList.setItem(IPHONE, SONY);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(SONY);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItem_editedItemHasNonUniqueIdentity_throwsDuplicateItemException() {
        uniqueItemList.add(IPHONE);
        uniqueItemList.add(SONY);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItem(IPHONE, SONY);
    }

    @Test
    public void remove_nullItem_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.remove(null);
    }

    @Test
    public void remove_itemDoesNotExist_throwsItemNotFoundException() {
        thrown.expect(ItemNotFoundException.class);
        uniqueItemList.remove(IPHONE);
    }

    @Test
    public void remove_existingItem_removesItem() {
        uniqueItemList.add(IPHONE);
        uniqueItemList.remove(IPHONE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullUniqueItemList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((UniqueItemList) null);
    }

    @Test
    public void setItems_uniqueItemList_replacesOwnListWithProvidedUniqueItemList() {
        uniqueItemList.add(IPHONE);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(SONY);
        uniqueItemList.setItems(expectedUniqueItemList);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_nullList_throwsNullPointerException() {
        thrown.expect(NullPointerException.class);
        uniqueItemList.setItems((List<Item>) null);
    }

    @Test
    public void setItems_list_replacesOwnListWithProvidedList() {
        uniqueItemList.add(IPHONE);
        List<Item> itemList = Collections.singletonList(SONY);
        uniqueItemList.setItems(itemList);
        UniqueItemList expectedUniqueItemList = new UniqueItemList();
        expectedUniqueItemList.add(SONY);
        assertEquals(expectedUniqueItemList, uniqueItemList);
    }

    @Test
    public void setItems_listWithDuplicateItems_throwsDuplicateItemException() {
        List<Item> listWithDuplicateItems = Arrays.asList(IPHONE, IPHONE);
        thrown.expect(DuplicateItemException.class);
        uniqueItemList.setItems(listWithDuplicateItems);
    }

    @Test
    public void asUnmodifiableObservableList_modifyList_throwsUnsupportedOperationException() {
        thrown.expect(UnsupportedOperationException.class);
        uniqueItemList.asUnmodifiableObservableList().remove(0);
    }
}
