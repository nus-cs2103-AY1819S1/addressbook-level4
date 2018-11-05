package seedu.address.ui;

import static java.time.Duration.ofMillis;
import static org.junit.Assert.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.address.testutil.EventsUtil.postNow;
import static seedu.address.testutil.TypicalIndexes.INDEX_SECOND_VOLUNTEER;
import static seedu.address.testutil.TypicalVolunteers.getTypicalVolunteers;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardDisplaysVolunteer;
import static seedu.address.ui.testutil.GuiTestAssert.assertCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.VolunteerCardHandle;
import guitests.guihandles.VolunteerListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.address.commons.events.ui.JumpToListRequestEvent;
import seedu.address.commons.util.FileUtil;
import seedu.address.commons.util.XmlUtil;
import seedu.address.model.volunteer.Volunteer;
import seedu.address.storage.XmlSerializableAddressBook;

public class VolunteerListPanelTest extends GuiUnitTest {
    private static final ObservableList<Volunteer> TYPICAL_VOLUNTEERS =
            FXCollections.observableList(getTypicalVolunteers());

    private static final JumpToListRequestEvent JUMP_TO_SECOND_EVENT =
            new JumpToListRequestEvent(INDEX_SECOND_VOLUNTEER);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private VolunteerListPanelHandle volunteerListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_VOLUNTEERS);

        for (int i = 0; i < TYPICAL_VOLUNTEERS.size(); i++) {
            volunteerListPanelHandle.navigateToCard(TYPICAL_VOLUNTEERS.get(i));
            Volunteer expectedVolunteer = TYPICAL_VOLUNTEERS.get(i);
            VolunteerCardHandle actualCard = volunteerListPanelHandle.getVolunteerCardHandle(i);

            assertCardDisplaysVolunteer(expectedVolunteer, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToListRequestEvent() {
        initUi(TYPICAL_VOLUNTEERS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        VolunteerCardHandle expectedVolunteer = volunteerListPanelHandle
                .getVolunteerCardHandle(INDEX_SECOND_VOLUNTEER.getZeroBased());
        VolunteerCardHandle selectedVolunteer = volunteerListPanelHandle.getHandleToSelectedCard();
        assertCardEquals(expectedVolunteer, selectedVolunteer);
    }

    /**
     * Verifies that creating and deleting large number of persons in {@code VolunteerListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Volunteer> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of volunteer cards exceeded time limit");
    }

    /**
     * Returns a list of persons containing {@code volunteerCount} persons that is used to populate the
     * {@code VolunteerListPanel}.
     */
    private ObservableList<Volunteer> createBackingList(int volunteerCount) throws Exception {
        Path xmlFile = createXmlFileWithVolunteers(volunteerCount);
        XmlSerializableAddressBook xmlAddressBook =
                XmlUtil.getDataFromFile(xmlFile, XmlSerializableAddressBook.class);
        return FXCollections.observableArrayList(xmlAddressBook.toModelType().getVolunteerList());
    }

    /**
     * Returns a .xml file containing {@code volunteerCount} volunteer. This file will be deleted
     * when the JVM terminates.
     */
    private Path createXmlFileWithVolunteers(int volunteerCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<addressbook>\n");
        for (int i = 0; i < volunteerCount; i++) {
            builder.append("<volunteers>\n");
            builder.append("<volunteerId>").append(i + 1).append("</volunteerId>\n");
            builder.append("<name>").append(i).append("a</name>\n");
            builder.append("<gender>m</gender>\n");
            builder.append("<birthday>01-01-1991</birthday>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("</volunteers>\n");
        }
        builder.append("</addressbook>\n");

        Path manyVolunteersFile = TEST_DATA_FOLDER.resolve("manyVolunteers.xml");
        FileUtil.createFile(manyVolunteersFile);
        FileUtil.writeToFile(manyVolunteersFile, builder.toString());
        manyVolunteersFile.toFile().deleteOnExit();
        return manyVolunteersFile;
    }

    /**
     * Initializes {@code volunteerListPanelHandle} with a {@code VolunteerListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code VolunteerListPanel}.
     */
    private void initUi(ObservableList<Volunteer> backingList) {
        VolunteerListPanel volunteerListPanel = new VolunteerListPanel(backingList);
        uiPartRule.setUiPart(volunteerListPanel);

        volunteerListPanelHandle = new VolunteerListPanelHandle(getChildNode(volunteerListPanel.getRoot(),
                VolunteerListPanelHandle.VOLUNTEER_LIST_VIEW_ID));
    }
}
