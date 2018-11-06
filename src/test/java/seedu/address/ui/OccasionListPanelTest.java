package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_OCCASION;
import static seedu.address.testutil.TypicalOccasions.getTypicalOccasions;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysOccasion;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.OccasionCardHandle;
import guitests.guihandles.OccasionListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToOccasionListRequestEvent;
import seedu.address.commons.events.ui.JumpToPersonListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.occasion.Occasion;
import seedu.address.storage.XmlSerializableAddressBook;

public class OccasionListPanelTest extends GuiUnitTest {
    private static final ObservableList<Occasion> TYPICAL_OCCASIONS =
            FXCollections.observableList(getTypicalOccasions());

    private static final JumpToOccasionListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToOccasionListRequestEvent(INDEX_SECOND_OCCASION);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private OccasionListPanelHandle occasionListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_OCCASIONS);

        for (int i = 0; i < TYPICAL_OCCASIONS.size(); i++) {
            occasionListPanelHandle.navigateToCard(TYPICAL_OCCASIONS.get(i));
            Occasion expectedOccasion = TYPICAL_OCCASIONS.get(i);
            OccasionCardHandle actualCard = occasionListPanelHandle.getOccasionCardHandle(i);

            assertCardDisplaysOccasion(expectedOccasion, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_OCCASIONS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        OccasionCardHandle expectedOccasion = occasionListPanelHandle
                .getOccasionCardHandle(INDEX_SECOND_OCCASION.getZeroBased());
        OccasionCardHandle selectedOccasion = occasionListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedOccasion, selectedOccasion);
    }

    /**
     * Verifies that creating and deleting large number of occasions in {@code OccasionListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Occasion> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of occasion cards exceeded time limit");
    }

    /**
     * Returns a list of occasions containing {@code occasionCount} occasions that is used to populate the
     * {@code OccasionListPanel}.
     */
    private ObservableList<Occasion> createBackingList(int occasionCount) throws Exception {
        Path xmlFile = createXmlFileWithOccasions(occasionCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getOccasionList());
    }

    /**
     * Returns a .xml file containing {@code occasionCount} occasions. This file will be deleted
     * when the JVM terminates.
     */
    private Path createXmlFileWithOccasions(int occasionCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < occasionCount; i++) {
            builder.append("<occasions>\n");
            builder.append("<occasionName>").append(i).append("ab</occasionName>\n");
            builder.append("<occasionDateTime>2018-01-09</occasionDateTime>\n");
            builder.append("<location>SOC</location>\n");
            builder.append("</occasions>\n");
        }
        builder.append("</addressbook>\n");

        Path manyOccasionsFile = Paths.get(TEST_DATA_FOLDER + "manyOccasions.xml");
        FileUtil.createFile(manyOccasionsFile);
        FileUtil.writeToFile(manyOccasionsFile, builder.toString());
        manyOccasionsFile.toFile().deleteOnExit();
        return manyOccasionsFile;
    }

    /**
     * Initializes {@code occasionListPanelHandle} with a {@code OccasionListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code OccasionListPanel}.
     */
    private void initUi(ObservableList<Occasion> backingList) {
        OccasionListPanel occasionListPanel = new OccasionListPanel(backingList);
        uiPartRule.setUiPart(occasionListPanel);

        occasionListPanelHandle = new OccasionListPanelHandle(getChildNode(occasionListPanel.getRoot(),
                OccasionListPanelHandle.OCCASION_LIST_VIEW_ID));
    }

}
