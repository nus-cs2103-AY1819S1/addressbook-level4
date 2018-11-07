package guitests.guihandles;

import javafx.scene.Node;
import javafx.scene.control.Label;
import seedu.meeting.model.meeting.Meeting;

// @@author jeffreyooi
/**
 * Provides a handle to a meeting card in the meeting list panel.
 */
public class MeetingCardHandle extends NodeHandle<Node> {
    private static final String ID_FIELD_ID = "#id";
    private static final String MEETING_TITLE_FIELD_ID = "#meetingTitle";
    private static final String MEETING_DESC_FIELD_ID = "#meetingDescription";
    private static final String MEETING_TIME_FIELD_ID = "#meetingTime";
    private static final String MEETING_LOCATION_FIELD_ID = "#meetingLocation";

    private final Label idLabel;
    private final Label meetingTitleLabel;
    private final Label meetingDescriptionLabel;
    private final Label meetingTimeLabel;
    private final Label meetingLocationLabel;

    public MeetingCardHandle(Node cardNode) {
        super(cardNode);
        idLabel = getChildNode(ID_FIELD_ID);
        meetingTitleLabel = getChildNode(MEETING_TITLE_FIELD_ID);
        meetingDescriptionLabel = getChildNode(MEETING_DESC_FIELD_ID);
        meetingTimeLabel = getChildNode(MEETING_TIME_FIELD_ID);
        meetingLocationLabel = getChildNode(MEETING_LOCATION_FIELD_ID);
    }

    public String getId() {
        return idLabel.getText();
    }

    public String getMeetingTitle() {
        return meetingTitleLabel.getText();
    }

    public String getMeetingDescription() {
        return meetingDescriptionLabel.getText();
    }

    public String getMeetingTime() {
        return meetingTimeLabel.getText();
    }

    public String getMeetingLocation() {
        return meetingLocationLabel.getText();
    }

    /**
     * Returns true if this handle contains {@code meeting}.
     */
    public boolean equals(Meeting meeting) {
        return getMeetingTitle().equals(meeting.getTitle().fullTitle)
            && getMeetingDescription().equals(meeting.getDescription().statement)
            && getMeetingTime().equals(meeting.getTime().toString())
            && getMeetingLocation().equals(meeting.getLocation().value);
    }
}
