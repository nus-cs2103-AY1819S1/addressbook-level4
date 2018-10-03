package seedu.address.ui;
//
//import static java.time.Duration.ofMillis;
//import static org.junit.Assert.assertEquals;
//import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
//import static seedu.address.testutil.EventsUtil.postNow;
//import static seedu.address.testutil.TypicalCarparks.getTypicalCarparks;
//import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_CARPARK;
//import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCarpark;
//import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;
//
//import java.nio.file.Path;
//import java.nio.file.Paths;
//
//import org.junit.Test;
//
//import guitests.guihandles.CarparkCardHandle;
//import guitests.guihandles.CarparkListPanelHandle;
//import javafx.collections.FXCollections;
//import javafx.collections.ObservableList;
//import seedu.address.commons.events.ui.JumpToListRequestEvent;
//import seedu.address.commons.util.FileUtil;
//import seedu.address.commons.util.XmlUtil;
//import seedu.address.model.carpark.Carpark;
//import seedu.address.storage.XmlSerializableAddressBook;

public class CarparkListPanelTest extends GuiUnitTest {
//    private static final ObservableList<Carpark> TYPICAL_CARPARKS
// = FXCollections.observableList(getTypicalCarparks());
//
//    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT
// = new JumpToListRequestEvent(INDEX_SECOND_CARPARK);
//
//    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");
//
//    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;
//
//    private CarparkListPanelHandle carparkListPanelHandle;
//
//    @Test
//    public void display() {
//        initUi(TYPICAL_CARPARKS);
//
//        for (int i = 0; i < TYPICAL_CARPARKS.size(); i++) {
//            carparkListPanelHandle.navigateToCard(TYPICAL_CARPARKS.get(i));
//            Carpark expectedCarpark = TYPICAL_CARPARKS.get(i);
//            CarparkCardHandle actualCard = carparkListPanelHandle.getCarparkCardHandle(i);
//
//            assertCardDisplaysCarpark(expectedCarpark, actualCard);
//            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
//        }
//    }
//
//    @Test
//    public void handleJumpToListRequestEvent() {
//        initUi(TYPICAL_CARPARKS);
//        postNow(JUMP_TO_SECOND_EVENT);
//        guiRobot.pauseForHuman();
//
//        CarparkCardHandle expectedCarpark =
// carparkListPanelHandle.getCarparkCardHandle(INDEX_SECOND_CARPARK.getZeroBased());
//        CarparkCardHandle selectedCarpark = carparkListPanelHandle.getHandleToSelectedCard();
//        assertCardEquals(expectedCarpark, selectedCarpark);
//    }
//
//    /**
//     * Verifies that creating and deleting large number of carparks in {@code CarparkListPanel} requires lesser than
//     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
//     */
//    @Test
//    public void performanceTest() throws Exception {
//        ObservableList<Carpark> backingList = createBackingList(10000);
//
//        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
//            initUi(backingList);
//            guiRobot.interact(backingList::clear);
//        }, "Creation and deletion of carpark cards exceeded time limit");
//    }
//
//    /**
//     * Returns a list of carparks containing {@code carparkCount} carparks that is used to populate the
//     * {@code CarparkListPanel}.
//     */
//    private ObservableList<Carpark> createBackingList(int carparkCount) throws Exception {
//        Path xmlFile = createXmlFileWithCarparks(carparkCount);
//        XmlSerializableAddressBook xmlAddressBook =
//                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
//        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getCarparkList());
//    }
//
//    /**
//     * Returns a .xml file containing {@code carparkCount}
// carparks. This file will be deleted when the JVM terminates.
//     */
//    private Path createXmlFileWithCarparks(int carparkCount) throws Exception {
//        StringBuilder builder = new StringBuilder();
//        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
//        builder.append("<addressbook>\n");
//        for (int i = 0; i < carparkCount; i++) {
//            builder.append("<carparks>\n");
//            builder.append("<name>").append(i).append("a</name>\n");
//            builder.append("<phone>000</phone>\n");
//            builder.append("<email>a@aa</email>\n");
//            builder.append("<address>a</address>\n");
//            builder.append("</carparks>\n");
//        }
//        builder.append("</addressbook>\n");
//
//        Path manyCarparksFile = TEST_DATA_FOLDER.resolve("manyCarparks.xml");
//        FileUtil.createFile(manyCarparksFile);
//        FileUtil.writeToFile(manyCarparksFile, builder.toString());
//        manyCarparksFile.toFile().deleteOnExit();
//        return manyCarparksFile;
//    }
//
//    /**
//     * Initializes {@code carparkListPanelHandle} with a {@code CarparkListPanel} backed by {@code backingList}.
//     * Also shows the {@code Stage} that displays only {@code CarparkListPanel}.
//     */
//    private void initUi(ObservableList<Carpark> backingList) {
//        CarparkListPanel carparkListPanel = new CarparkListPanel(backingList);
//        uiPartRule.setUiPart(carparkListPanel);
//
//        carparkListPanelHandle = new CarparkListPanelHandle(getChildNode(carparkListPanel.getRoot(),
//                CarparkListPanelHandle.CARPARK_LIST_VIEW_ID));
//    }
}
