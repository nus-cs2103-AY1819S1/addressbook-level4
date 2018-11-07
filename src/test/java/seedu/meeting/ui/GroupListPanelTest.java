package seedu.meeting.ui;

import static java.time.Duration.ofMillis;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.meeting.testutil.EventsUtil.postNow;
import static seedu.meeting.testutil.TypicalGroups.getTypicalGroups;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_SECOND_GROUP;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertCardDisplaysGroup;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertGroupCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;

import org.junit.Test;

import guitests.guihandles.GroupCardHandle;
import guitests.guihandles.GroupListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.meeting.commons.events.ui.JumpToGroupListRequestEvent;
import seedu.meeting.commons.util.FileUtil;
import seedu.meeting.commons.util.XmlUtil;
import seedu.meeting.model.group.Group;
import seedu.meeting.storage.XmlSerializableMeetingBook;

public class GroupListPanelTest extends GuiUnitTest {
    private static final ObservableList<Group> TYPICAL_GROUPS =
        FXCollections.observableList(getTypicalGroups());

    private static final JumpToGroupListRequestEvent JUMP_TO_SECOND_EVENT =
        new JumpToGroupListRequestEvent(INDEX_SECOND_GROUP);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private GroupListPanelHandle groupListPanelHandle;

    @Test
    public void display() {
        initUi(TYPICAL_GROUPS);

        for (int i = 0; i < TYPICAL_GROUPS.size(); i++) {
            groupListPanelHandle.navigateToCard(TYPICAL_GROUPS.get(i));
            Group expectedGroup = TYPICAL_GROUPS.get(i);
            GroupCardHandle actualCard = groupListPanelHandle.getGroupCardHandle(i);

            assertCardDisplaysGroup(expectedGroup, actualCard);
            assertEquals(Integer.toString(i + 1) + ". ", actualCard.getId());
        }
    }

    @Test
    public void handleJumpToGroupListRequestEvent() {
        initUi(TYPICAL_GROUPS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        GroupCardHandle expectedGroup = groupListPanelHandle.getGroupCardHandle(INDEX_SECOND_GROUP.getZeroBased());
        GroupCardHandle selectedGroup = groupListPanelHandle.getHandleToSelectedCard();
        assertGroupCardEquals(expectedGroup, selectedGroup);
    }

    /**
     * Verifies that creating and deleting large number of groups in {@code GroupListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Group> backingList = createBackingList(10000);

        assertTimeoutPreemptively(ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of group cards exceeded time limit");
    }

    /**
     * Initializes {@code groupListPanelHandle} with a {@code GroupListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code GroupListPanel}
     */
    private void initUi(ObservableList<Group> backingList) {
        GroupListPanel groupListPanel = new GroupListPanel(backingList);
        uiPartRule.setUiPart(groupListPanel);

        groupListPanelHandle = new GroupListPanelHandle(getChildNode(groupListPanel.getRoot(),
                GroupListPanelHandle.GROUP_LIST_VIEW_ID));
    }

    /**
     * Returns a list of groups containing {@code groupCount} groups that is used to populate the
     * {@code GroupListPanel}
     */
    private ObservableList<Group> createBackingList(int groupCount) throws Exception {
        Path xmlFile = createXmlFileWithGroups(groupCount);
        XmlSerializableMeetingBook xmlMeetingBook =
            XmlUtil.getDataFromFile(xmlFile, XmlSerializableMeetingBook.class);
        return FXCollections.observableArrayList(xmlMeetingBook.toModelType().getGroupList());
    }

    /**
     * Returns a .xml file containing {@code groupCount} persons and groups. Each person is associated with one group.
     * This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithGroups(int groupCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<meetingbook>\n");
        for (int i = 0; i < groupCount; i++) {
            builder.append("<persons>\n");
            builder.append("<name>").append(i).append("b</name>\n");
            builder.append("<phone>000</phone>\n");
            builder.append("<email>a@aa</email>\n");
            builder.append("<address>a</address>\n");
            builder.append("<grouped>").append(i).append("g</grouped>\n");
            builder.append("</persons>\n");
        }
        builder.append("</meetingbook>\n");

        Path manyGroupsFile = Paths.get(TEST_DATA_FOLDER + "manyGroups.xml");
        FileUtil.createFile(manyGroupsFile);
        FileUtil.writeToFile(manyGroupsFile, builder.toString());
        manyGroupsFile.toFile().deleteOnExit();
        return manyGroupsFile;
    }
}
