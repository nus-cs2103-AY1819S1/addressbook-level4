package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;
import seedu.address.model.meeting.Meeting;
import seedu.address.model.shared.Description;

/**
 * An UI component that displays information of a {@code Group}
 * {@author jeffreyooi}
 */
public class GroupCard extends UiPart<Region> {

    private static final String FXML = "GroupListCard.fxml";

    public final Group group;

    @FXML
    private HBox groupCardPane;

    @FXML
    private Label id;

    @FXML
    private Label groupTitle;

    @FXML
    private Label groupDescription;

    @FXML
    private Label groupMeeting;

    @FXML
    private Label memberCount;

    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        groupTitle.setText(group.getTitle().fullTitle);
        Description description = group.getDescription();
        String descriptionString = description != null ? description.statement : "";
        groupDescription.setText(descriptionString);
        Meeting meeting = group.getMeeting();
        String meetingString = meeting != null ? meeting.getTitle().fullTitle : "";
        groupMeeting.setText(meetingString);
        int membersCount = group.getMembersView().size();
        memberCount.setText(String.format("%d", membersCount));
    }

    @Override
    public boolean equals(Object other) {
        if (other == this) {
            return true;
        }

        if (!(other instanceof GroupCard)) {
            return false;
        }

        GroupCard card = (GroupCard) other;
        return id.getText().equals(card.id.getText())
                && group.equals(card.group);
    }
}
