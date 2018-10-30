package seedu.parking.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.parking.testutil.EventsUtil.postNow;
import static seedu.parking.testutil.TypicalCarparks.getTypicalCarparks;
import static seedu.parking.testutil.TypicalIndexes.INDEX_SECOND_CARPARK;
import static seedu.parking.ui.testutil.GuiTestAssert.assertCardDisplaysCarpark;
import static seedu.parking.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.CarparkCardHandle;
import guitests.guihandles.CarparkListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.parking.commons.events.ui.JumpToListRequestEvent;
import seedu.parking.commons.util.FileUtil;
import seedu.parking.commons.util.XmlUtil;
import seedu.parking.model.carpark.Carpark;
import seedu.parking.storage.XmlSerializableCarparkFinder;

public class CarparkListPanelTest extends GuiUnitTest {
    private static final ObservableList<Carpark> TYPICAL_CARPARKS = FXCollections.observableList(getTypicalCarparks());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_CARPARK);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private CarparkListPanelHandle carparkListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CARPARKS);

        for (int i = 0; i < TYPICAL_CARPARKS.size(); i++) {
            carparkListPanelHandle.navigateToCard(TYPICAL_CARPARKS.get(i));
            Carpark expectedCarpark = TYPICAL_CARPARKS.get(i);
            CarparkCardHandle actualCard = carparkListPanelHandle.getCarparkCardHandle(i);

            assertCardDisplaysCarpark(expectedCarpark, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_CARPARKS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        CarparkCardHandle expectedCarpark =
                carparkListPanelHandle.getCarparkCardHandle(INDEX_SECOND_CARPARK.getZeroBased());
        CarparkCardHandle selectedCarpark = carparkListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCarpark, selectedCarpark);
    }

    /**
     * Verifies that creating and deleting large number of car parks in {@code CarparkListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Carpark> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of car park cards exceeded time limit");
    }

    /**
     * Returns a list of car parks containing {@code carparkCount} car parks that is used to populate the
     * {@code CarparkListPanel}.
     */
    private ObservableList<Carpark> createBackingList(int carparkCount) throws Exception {
        Path xmlFile = createXmlFileWithCarparks(carparkCount);
        XmlSerializableCarparkFinder xmlCarparkFinder =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableCarparkFinder.class);
        return FXCollections.observableArrayList(xmlCarparkFinder.toModelType().getCarparkList());
    }

    /**
     * Returns a .xml file containing {@code carparkCount}
     * carparks. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithCarparks(int carparkCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<carparkfinder>\n");
        for (int i = 0; i < carparkCount; i++) {
            builder.append("<carparks>\n");
            builder.append("<carparkNumber>").append(i).append("a</carparkNumber>\n");
            builder.append("<address>a</address>\n");
            builder.append("<postalCode>123456</postalCode>\n");
            builder.append("<carparkType>b</carparkType>\n");
            builder.append("<coordinate>456.456, 123.123</coordinate>\n");
            builder.append("<totalLots>2</totalLots>\n");
            builder.append("<lotsAvailable>1</lotsAvailable>\n");
            builder.append("<freeParking>y</freeParking>\n");
            builder.append("<nightParking>n</nightParking>\n");
            builder.append("<shortTerm>s</shortTerm>\n");
            builder.append("<typeOfParking>t</typeOfParking>\n");
            builder.append("</carparks>\n");
        }
        builder.append("</carparkfinder>\n");

        Path manyCarparksFile = TEST_DATA_FOLDER.resolve("manyCarparks.xml");
        FileUtil.createFile(manyCarparksFile);
        FileUtil.writeToFile(manyCarparksFile, builder.toString());
        manyCarparksFile.toFile().deleteOnExit();
        return manyCarparksFile;
    }

    /**
     * Initializes {@code carparkListPanelHandle} with a {@code CarparkListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code CarparkListPanel}.
     */
    private void initUi(ObservableList<Carpark> backingList) {
        CarparkListPanel carparkListPanel = new CarparkListPanel(backingList);
        uiPartRule.setUiPart(carparkListPanel);

        carparkListPanelHandle = new CarparkListPanelHandle(getChildNode(carparkListPanel.getRoot(),
                CarparkListPanelHandle.CARPARK_LIST_VIEW_ID));
    }
}
