package seedu.meeting.ui;

import static org.junit.jupiter.api.Assertions.assertTimeoutPreemptively;
import static seedu.meeting.testutil.EventsUtil.postNow;
import static seedu.meeting.testutil.TypicalGroups.getTypicalGroups;
import static seedu.meeting.testutil.TypicalIndexes.INDEX_SECOND_MEETING;
import static seedu.meeting.ui.testutil.GuiTestAssert.assertMeetingCardEquals;

import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.Duration;
import java.util.List;
import java.util.stream.Collectors;

import org.junit.Test;

import guitests.guihandles.MeetingCardHandle;
import guitests.guihandles.MeetingListPanelHandle;
import javafx.collections.FXCollections;
import javafx.collections.ObservableList;
import seedu.meeting.commons.events.ui.JumpToMeetingListRequestEvent;
import seedu.meeting.commons.util.FileUtil;
import seedu.meeting.commons.util.XmlUtil;
import seedu.meeting.model.group.Group;
import seedu.meeting.model.meeting.Meeting;
import seedu.meeting.storage.XmlSerializableMeetingBook;

public class MeetingListPanelTest extends GuiUnitTest {
    private static final ObservableList<Meeting> TYPICAL_MEETINGS;

    private static final JumpToMeetingListRequestEvent JUMP_TO_SECOND_EVENT =
        new JumpToMeetingListRequestEvent(INDEX_SECOND_MEETING);

    private static final Path TEST_DATA_FOLDER = Paths.get("src", "test", "data", "sandbox");

    private static final long CARD_CREATION_AND_DELETION_TIMEOUT = 2500;

    private MeetingListPanelHandle meetingListPanelHandle;

    static {
        List<Meeting> meetings = getTypicalGroups().stream().map(Group::getMeeting).collect(Collectors.toList());
        TYPICAL_MEETINGS = FXCollections.observableList(meetings);
    }

    @Test
    public void handleJumpToMeetingListRequestEvent() {
        initUi(TYPICAL_MEETINGS);
        postNow(JUMP_TO_SECOND_EVENT);
        guiRobot.pauseForHuman();

        MeetingCardHandle expectedMeeting =
            meetingListPanelHandle.getMeetingCardHandle(INDEX_SECOND_MEETING.getZeroBased());
        MeetingCardHandle selectedMeeting = meetingListPanelHandle.getHandleToSelectedCard();
        assertMeetingCardEquals(expectedMeeting, selectedMeeting);
    }

    /**
     * Verifies that creating and deleting large number of meetings in {@code MeetingListPanel} requires lesser than
     * {@code CARD_CREATION_AND_DELETION_TIMEOUT} milliseconds to execute.
     */
    @Test
    public void performanceTest() throws Exception {
        ObservableList<Meeting> backingList = createBackingList(10000);

        assertTimeoutPreemptively(Duration.ofMillis(CARD_CREATION_AND_DELETION_TIMEOUT), () -> {
            initUi(backingList);
            guiRobot.interact(backingList::clear);
        }, "Creation and deletion of meeting cards exceeded time limit");
    }

    /**
     * Initializes {@code meetingListPanelHandle} with a {@code MeetingListPanel} backed by {@code backingList}.
     * Also shows the {@code Stage} that displays only {@code MeetingListPanel}
     */
    private void initUi(ObservableList<Meeting> backingList) {
        MeetingListPanel meetingListPanel = new MeetingListPanel(backingList);
        uiPartRule.setUiPart(meetingListPanel);

        meetingListPanelHandle = new MeetingListPanelHandle(getChildNode(meetingListPanel.getRoot(),
            MeetingListPanelHandle.MEETING_LIST_VIEW_ID));
    }

    /**
     * Returns a list of meetings containing {@code meetingCount} meetings that is used to populate the
     * {@code MeetingListPanel}
     */
    private ObservableList<Meeting> createBackingList(int meetingCount) throws Exception {
        Path xmlFile = createXmlFileWithMeetings(meetingCount);
        XmlSerializableMeetingBook xmlMeetingBook =
            XmlUtil.getDataFromFile(xmlFile, XmlSerializableMeetingBook.class);
        List<Meeting> meetingList = xmlMeetingBook.toModelType().getGroupList()
            .stream().map(Group::getMeeting).collect(Collectors.toList());
        return FXCollections.observableArrayList(meetingList);
    }

    /**
     * Returns a .xml file containing {@code meetingCount} groups and meetings. Each meeting is associated with one
     * group. This file will be deleted when the JVM terminates.
     */
    private Path createXmlFileWithMeetings(int meetingCount) throws Exception {
        StringBuilder builder = new StringBuilder();
        builder.append("<?xml version=\"1.0\" encoding=\"UTF-8\" standalone=\"yes\"?>\n");
        builder.append("<meetingbook>\n");
        for (int i = 0; i < meetingCount; i++) {
            builder.append("<groups>\n");
            builder.append("<title>").append(i).append("</title>\n");
            builder.append("<description>Test</description>\n");
            builder.append("<meeting>\n");
            builder.append("<title>Test meeting ").append(i).append("</title>\n");
            builder.append("<time>22-02-2017@10:10</time>\n");
            builder.append("<location>COM1-0202</location>\n");
            builder.append("<description>Test description</description>\n");
            builder.append("</meeting>\n");
            builder.append("</groups>\n");
        }
        builder.append("</meetingbook>\n");

        Path manyMeetingsFile = Paths.get(TEST_DATA_FOLDER + "manyMeetings.xml");
        FileUtil.createFile(manyMeetingsFile);
        FileUtil.writeToFile(manyMeetingsFile, builder.toString());
        manyMeetingsFile.toFile().deleteOnExit();

        return manyMeetingsFile;
    }
}
