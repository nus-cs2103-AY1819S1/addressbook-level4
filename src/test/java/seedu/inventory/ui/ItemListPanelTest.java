package seedu.inventory.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.inventory.testutil.EventsUtil.postNow;
import static seedu.inventory.testutil.TypicalIndexes.INDEX_SECOND_ITEM;
import static seedu.inventory.testutil.TypicalItems.getTypicalItems;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardDisplaysItem;
import static seedu.inventory.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.ItemCardHandle;
import guitests.guihandles.ItemListPanelHandle;

import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.inventory.commons.events.ui.JumpToListRequestEvent;
import seedu.inventory.commons.util.FileUtil;
import seedu.inventory.commons.util.XmlUtil;
import seedu.inventory.model.item.Item;
import seedu.inventory.storage.XmlSerializableInventory;

public class ItemListPanelTest extends GuiUnitTest {
    private static final ObservableList<Item> TYPICAL_ITEMS =
            FXCollections.observableList(getTypicalItems());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_ITEM);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private ItemListPanelHandle itemListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ITEMS);

        for (int i = 0; i < TYPICAL_ITEMS.size(); i++) {
            itemListPanelHandle.navigateToCard(TYPICAL_ITEMS.get(i));
            Item expectedItem = TYPICAL_ITEMS.get(i);
            ItemCardHandle actualCard = itemListPanelHandle.getItemCardHandle(i);

            assertCardDisplaysItem(expectedItem, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_ITEMS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        ItemCardHandle expectedItem = itemListPanelHandle.getItemCardHandle(INDEX_SECOND_ITEM.getZeroBased());
        ItemCardHandle selectedItem = itemListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedItem, selectedItem);
    }

    /**
     * Verifies that creating and deleting large number of items in {@code ItemListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Item> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of item cards exceeded time limit");
    }

    /**
     * Returns a list of items containing {@code itemCount} items that is used to populate the
     * {@code ItemListPanel}.
     */
    private ObservableList<Item> createBackingList(int itemCount) throws Exception {
        Path xmlFile = createXmlFileWithItems(itemCount);
        XmlSerializableInventory xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableInventory.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getItemList());
    }

    /**
     * Returns a .xml file containing {@code itemCount} items. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithItems(int itemCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<inventory>\n");
        for (int i = 0; i < itemCount; i++) {
            builder.append("<items>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<price>123.00</price>\n");
            builder.append("<quantity>333</quantity>\n");
            builder.append("<sku>sku-" + i + "</sku>\n");
            builder.append("<image>docs/images/iphone.jpg</image>\n");
            builder.append("</items>\n");
        }
        builder.append("</inventory>\n");

        Path manyItemsFile = Paths.get(TEST_DATA_FOLDER + "manyItems.xml");
        FileUtil.createFile(manyItemsFile);
        FileUtil.writeToFile(manyItemsFile, builder.toString());
        manyItemsFile.toFile().deleteOnExit();
        return manyItemsFile;
    }

    /**
     * Initializes {@code itemListPanelHandle} with a {@code ItemListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code ItemListPanel}.
     */
    private void initUi(ObservableList<Item> backingList) {
        ItemListPanel itemListPanel = new ItemListPanel(backingList);
        uiPartRule.setUiPart(itemListPanel);

        itemListPanelHandle = new ItemListPanelHandle(getChildNode(itemListPanel.getRoot(),
                ItemListPanelHandle.ITEM_LIST_VIEW_ID));
    }
}
