package seedu.thanepark.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.thanepark.testutil.EventsUtil.postNow;
import static seedu.thanepark.testutil.TypicalIndexes.INDEX_SECOND_RIDE;
import static seedu.thanepark.testutil.TypicalRides.getTypicalRides;
import static seedu.thanepark.ui.testutil.GuiTestAssert.assertCardDisplaysRide;
import static seedu.thanepark.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.RideCardHandle;
import guitests.guihandles.RideListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;

import seedu.thanepark.commons.events.ui.JumpToListRequestEvent;
import seedu.thanepark.commons.util.FileUtil;
import seedu.thanepark.commons.util.XmlUtil;
import seedu.thanepark.model.ride.Ride;
import seedu.thanepark.storage.XmlSerializableThanePark;

public class RideListPanelTest extends GuiUnitTest {
    private static final ObservableList<Ride> TYPICAL_RIDES =
            FXCollections.observableList(getTypicalRides());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_RIDE);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private RideListPanelHandle rideListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_RIDES);

        for (int i = 0; i < TYPICAL_RIDES.size(); i++) {
            rideListPanelHandle.navigateToCard(TYPICAL_RIDES.get(i));
            Ride expectedRide = TYPICAL_RIDES.get(i);
            RideCardHandle actualCard = rideListPanelHandle.getRideCardHandle(i);

            assertCardDisplaysRide(expectedRide, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_RIDES);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        RideCardHandle expectedRide = rideListPanelHandle.getRideCardHandle(INDEX_SECOND_RIDE.getZeroBased());
        RideCardHandle selectedRide = rideListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedRide, selectedRide);
    }

    /**
     * Verifies that creating and deleting large number of rides in {@code RideListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Ride> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of ride cards exceeded time limit");
    }

    /**
     * Returns a list of rides containing {@code rideCount} rides that is used to populate the
     * {@code RideListPanel}.
     */
    private ObservableList<Ride> createBackingList(int rideCount) throws Exception {
        Path xmlFile = createXmlFileWithRides(rideCount);
        XmlSerializableThanePark xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableThanePark.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getRideList());
    }

    /**
     * Returns a .xml file containing {@code rideCount} rides. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithRides(int rideCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<thanepark>\n");
        for (int i = 0; i < rideCount; i++) {
            builder.append("<rides>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<zone>000</zone>\n");
            builder.append("<daysSinceMaintenanceString>000</daysSinceMaintenanceString>\n");
            builder.append("<waitingTimeString>123</waitingTimeString>\n");
            builder.append("<thanepark>a</thanepark>\n");
            builder.append("<statusString>OPEN</statusString>\n");
            builder.append("</rides>\n");
        }
        builder.append("</thanepark>\n");

        Path manyRidesFile = Paths.get(TEST_DATA_FOLDER + "manyRides.xml");
        FileUtil.createFile(manyRidesFile);
        FileUtil.writeToFile(manyRidesFile, builder.toString());
        manyRidesFile.toFile().deleteOnExit();
        return manyRidesFile;
    }

    /**
     * Initializes {@code rideListPanelHandle} with a {@code RideListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code RideListPanel}.
     */
    private void initUi(ObservableList<Ride> backingList) {
        RideListPanel rideListPanel = new RideListPanel(backingList);
        uiPartRule.setUiPart(rideListPanel);

        rideListPanelHandle = new RideListPanelHandle(getChildNode(rideListPanel.getRoot(),
                RideListPanelHandle.RIDE_LIST_VIEW_ID));
    }
}
