package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalCcas.getTypicalCcas;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_PERSON;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysCca;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.CcaCardHandle;
import guitests.guihandles.CcaListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.cca.Cca;
import seedu.address.storage.XmlSerializableBudgetBook;

//@@author ericyjw
public class CcaListPanelTest extends GuiUnitTest {
    private static final ObservableList<Cca> TYPICAL_CCAS =
        FXCollections.observableList(getTypicalCcas());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT = new JumpToListRequestEvent(INDEX_SECOND_PERSON);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private CcaListPanelHandle ccaListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_CCAS);

        for (int i = 0; i < TYPICAL_CCAS.size(); i++) {
            ccaListPanelHandle.navigateToCard(TYPICAL_CCAS.get(i));
            Cca expectedCca = TYPICAL_CCAS.get(i);
            CcaCardHandle actualCard = ccaListPanelHandle.getCcaCardHandle(i);

            assertCardDisplaysCca(expectedCca, actualCard);
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_CCAS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        CcaCardHandle expectedCca = ccaListPanelHandle.getCcaCardHandle(INDEX_SECOND_PERSON.getZeroBased());
        CcaCardHandle selectedCca = ccaListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedCca, selectedCca);
    }

    /**
     * Verifies that creating and deleting large number of ccas in {@code CcaListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Cca> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of cca cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code personCount} persons that is used to populate the
     * {@code PersonListPanel}.
     */
    private ObservableList<Cca> createBackingList(int ccaCount) throws Exception {
        Path xmlFile = createXmlFileWithCcas(ccaCount);
        XmlSerializableBudgetBook xmlBudgetBook =
            XmlUtil.getDataFromFile(xmlFile, XmlSerializableBudgetBook.class);
        return FXCollections.observableArrayList(xmlBudgetBook.toModelType().getCcaList());
    }

    /**
     * Returns a .xml file containing {@code ccaCount} ccas. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithCcas(int ccaCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<ccabook>\n");
        String name = "cca";
        for (int i = 0; i < ccaCount; i++) {
            builder.append("<ccas>\n");
            builder.append("<name>").append(name).append("</name>\n");
            name += "s";
            builder.append("<head>Steven Lim</head>\n");
            builder.append("<viceHead>Bumble Bee</viceHead>\n");
            builder.append("<budget>100</budget>\n");
            builder.append("<spent>100</spent>\n");
            builder.append("<outstanding>0</outstanding>\n");
            builder.append("</ccas>\n");
        }
        builder.append("</ccabook>\n");

        Path manyCcasFile = Paths.get(TEST_DATA_FOLDER + "manyCcas.xml");
        FileUtil.createFile(manyCcasFile);
        FileUtil.writeToFile(manyCcasFile, builder.toString());
        manyCcasFile.toFile().deleteOnExit();
        return manyCcasFile;
    }

    /**
     * Initializes {@code ccaListPanelHandle} with a {@code CcaListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code CcaListPanel}.
     */
    private void initUi(ObservableList<Cca> backingList) {
        CcaListPanel ccaListPanel = new CcaListPanel(backingList);
        uiPartRule.setUiPart(ccaListPanel);

        ccaListPanelHandle = new CcaListPanelHandle(getChildNode(ccaListPanel.getRoot(),
            CcaListPanelHandle.CCA_LIST_VIEW_ID));
    }
}
