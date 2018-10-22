package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.model.group.Group;

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
    private Label memberCount;

    public GroupCard(Group group, int displayedIndex) {
        super(FXML);
        this.group = group;
        id.setText(displayedIndex + ". ");
        groupTitle.setText(group.getTitle().fullTitle);
        groupDescription.setText(group.getDescription().statement);
        memberCount.setText("Members: " + group.getMembers().asUnmodifiableObservableList().size());
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
