package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.FlowPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.VolunteerPanelSelectionChangedEvent;
import seedu.address.commons.util.BirthdayUtil;
import seedu.address.commons.util.GenderUtil;
import seedu.address.model.record.Record;
import seedu.address.model.volunteer.Volunteer;

/**
 * Panel containing the volunteer details.
 */
public class VolunteerPanel extends UiPart<Region> {
    private static final String FXML = "VolunteerPanel.fxml";
    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label volunteerNameLabel;
    @FXML
    private Label numOfVolunteersLabel;
    @FXML
    private Label volunteerGenderLabel;
    @FXML
    private Label volunteerBirthdayLabel;
    @FXML
    private Label volunteerAddressLabel;
    @FXML
    private Label volunteerPhoneLabel;
    @FXML
    private Label volunteerEmailLabel;
    @FXML
    private FlowPane volunteerTag;

    private ObservableList<Record> recordList;

    public VolunteerPanel() {
        super(FXML);
        registerAsAnEventHandler(this);
    }

    private void setLabelText(Volunteer volunteer) {
        volunteerNameLabel.setText(volunteer.getName().fullName);
        volunteerGenderLabel.setText(GenderUtil.getFriendlyGenderFromVolunteerGender(volunteer.getGender()));
        volunteerBirthdayLabel.setText(BirthdayUtil.getFriendlyDateFromVolunteerBirthday(volunteer.getBirthday()));
        volunteerPhoneLabel.setText(volunteer.getPhone().value);
        volunteerEmailLabel.setText(volunteer.getEmail().value);
        volunteerAddressLabel.setText(volunteer.getAddress().value);
        volunteerTag.getChildren().clear();
        volunteer.getTags().forEach(tag -> volunteerTag.getChildren().add(new Label(tag.tagName)));
    }

    @Subscribe
    private void handleVolunteerPanelSelectionChangedEvent(VolunteerPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        setLabelText(event.getNewSelection());
    }

    /**
     * Clears details in the volunteer panel.
     */
    public void clearDetails() {
        volunteerNameLabel.setText("");
        volunteerGenderLabel.setText("");
        volunteerBirthdayLabel.setText("");
        volunteerPhoneLabel.setText("");
        volunteerEmailLabel.setText("");
        volunteerAddressLabel.setText("");
        volunteerTag.getChildren().clear();
    }
}
