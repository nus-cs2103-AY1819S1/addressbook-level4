package guitests.guihandles;

import java.util.List;
import java.util.Set;

import javafx.scene.Node;
import javafx.scene.control.ListView;
import seedu.meeting.model.meeting.Meeting;

// @@author jeffreyooi
/**
 * Provides a handle for {@code MeetingListPanel} containing the list of {@code MeetingCard}
 */
public class MeetingListPanelHandle extends NodeHandle<ListView<Meeting>> {
    public static final String MEETING_LIST_VIEW_ID = "#meetingListView";

    private static final String CARD_PANE_ID = "#meetingCardPane";

    public MeetingListPanelHandle(ListView<Meeting> meetingListPanelNode) {
        super(meetingListPanelNode);
    }

    public MeetingCardHandle getHandleToSelectedCard() {
        List<Meeting> selectedMeetingList = getRootNode().getSelectionModel().getSelectedItems();

        if (selectedMeetingList.size() != 1) {
            throw new AssertionError("Meeting list size expected 1.");
        }

        return getAllCardNodes().stream()
            .map(MeetingCardHandle::new)
            .filter(handle -> handle.equals(selectedMeetingList.get(0)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    /**
     * Returns the meeting card handle of a meeting associated with the {@code index} in the list.
     * @throws IllegalStateException if the selected card is currently not in the scene graph.
     */
    public MeetingCardHandle getMeetingCardHandle(int index) {
        return getAllCardNodes().stream()
            .map(MeetingCardHandle::new)
            .filter(handle -> handle.equals(getMeeting(index)))
            .findFirst()
            .orElseThrow(IllegalStateException::new);
    }

    private Meeting getMeeting(int index) {
        return getRootNode().getItems().get(index);
    }

    /**
     * Returns all card nodes in the scene graph.
     * Card nodes that are visible in the listview are definitely in the scene graph, while some nodes that are not
     * visible in the listview may also be in the scene graph.
     */
    private Set<Node> getAllCardNodes() {
        return guiRobot.lookup(CARD_PANE_ID).queryAll();
    }
}
