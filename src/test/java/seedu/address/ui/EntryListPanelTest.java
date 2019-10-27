package seedu.address.ui;

import static org.junit.Assert.assertEquals;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalEntrys.getTypicalEntries;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_ENTRY;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysEntry;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.EntryCardHandle;
import guitests.guihandles.EntryListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToEntryListRequestEvent;
import seedu.address.model.entry.ResumeEntry;

public class EntryListPanelTest extends GuiUnitTest {
    private static final ObservableList<ResumeEntry> TYPICAL_ENTRIES =
            FXCollections.observableList(getTypicalEntries());

    private static final JumpToEntryListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToEntryListRequestEvent(INDEX_SECOND_ENTRY);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private EntryListPanelHandle entryListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_ENTRIES);

        for (int i = 0; i < TYPICAL_ENTRIES.size(); i++) {
            entryListPanelHandle.navigateToCard(TYPICAL_ENTRIES.get(i));
            ResumeEntry expectedEntry = TYPICAL_ENTRIES.get(i);
            EntryCardHandle actualCard = entryListPanelHandle.getEntryCardHandle(i);

            assertCardDisplaysEntry(expectedEntry, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToEntryListRequestEvent() {
        initUi(TYPICAL_ENTRIES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        EntryCardHandle expectedEntry = entryListPanelHandle.getEntryCardHandle(INDEX_SECOND_ENTRY.getZeroBased());
        EntryCardHandle selectedEntry = entryListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedEntry, selectedEntry);
    }



    /**
     * Initializes {@code personListPanelHandle} with a {@code PersonListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code PersonListPanel}.
     */
    private void initUi(ObservableList<ResumeEntry> backingList) {
        EntryListPanel entryListPanel = new EntryListPanel(backingList);
        uiPartRule.setUiPart(entryListPanel);

        entryListPanelHandle = new EntryListPanelHandle(getChildNode(entryListPanel.getRoot(),
                EntryListPanelHandle.ENTRY_LIST_VIEW_ID));
    }



    /**
     * Verifies that creating and deleting large number of persons in {@code PersonListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    /*
    // to be completed at a later stage
    @Test
    public void performanceTest() throws Exception {
        ObservableList<ResumeEntry> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of person cards exceeded time limit");
    }*/

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    /*
    private ObservableList<ResumeEntry> createBackingList(int personCount) throws Exception {
        Path xmlFile = createXmlFileWithPersons(personCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getPersonList());
    }
    */

    /**
     * Returns a .xml file containing {@code personCount} persons. This file will be deleted when the JVM terminates.
     */
    /*
    private Path createXmlFileWithPersons(int personCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < personCount; i++) {
            builder.append("<persons>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</persons>\n");
        }
        builder.append("</addressbook>\n");

        Path manyPersonsFile = Paths.get(TEST_DATA_FOLDER + "manyPersons.xml");
        FileUtil.createFile(manyPersonsFile);
        FileUtil.writeToFile(manyPersonsFile, builder.toString());
        manyPersonsFile.toFile().deleteOnExit();
        return manyPersonsFile;
    } */

}
