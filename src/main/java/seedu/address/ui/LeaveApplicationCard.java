package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.HBox;
import javafx.scene.layout.Region;
import seedu.address.commons.util.DateUtil;
import seedu.address.model.leaveapplication.LeaveApplicationWithEmployee;

/**
 * An UI component that displays information of a {@code LeaveApplicationWithEmployee}.
 */
public class LeaveApplicationCard extends UiPart<Region> {

    private static final String FXML = "LeaveListCard.fxml";

    /**
     * Note: Certain keywords such as "location" and "resources" are reserved keywords in JavaFX.
     * As a consequence, UI elements' variable names cannot be set to such keywords
     * or an exception will be thrown by JavaFX during runtime.
     *
     * @see <a href="https://github.com/se-edu/addressbook-level4/issues/336">The issue on AddressBook level 4</a>
     */

    public final LeaveApplicationWithEmployee leaveApplication;

    @FXML
    private HBox cardPane;
    @FXML
    private Label employee;
    @FXML
    private Label description;
    @FXML
    private Label id;
    @FXML
    private Label status;
    @FXML
    private FlowPane dates;

    public LeaveApplicationCard(LeaveApplicationWithEmployee leaveApplication, int displayedIndex) {
        super(FXML);
        this.leaveApplication = leaveApplication;
        id.setText(displayedIndex + ". ");
        employee.setText(leaveApplication.getEmployee().getName().fullName);
        description.setText("Description: " + leaveApplication.getDescription().value);
        status.setText("Status: " + leaveApplication.getLeaveStatus().value.toString());
        leaveApplication.getDates().forEach(date
            -> dates.getChildren()
                .add(new Label(DateUtil.convertToString(date) + " ")));
    }

    @Override
    public boolean equals(Object other) {
        // short circuit if same object
        if (other == this) {
            return true;
        }

        // instanceof handles nulls
        if (!(other instanceof LeaveApplicationCard)) {
            return false;
        }

        // state check
        LeaveApplicationCard card = (LeaveApplicationCard) other;
        return id.getText().equals(card.id.getText())
                && leaveApplication.equals(card.leaveApplication);
    }
}
