package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The Staff Panel of the App.
 */
public class StudentPanel extends UiPart<Region> {

    private static final String FXML = "StudentPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label educationLabel;
    @FXML
    private Label feeLabel;
    @FXML
    private Label gradesLabel;
    @FXML
    private Label timeLabel;

    public StudentPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
    }

    /**
     * Set the labels according to the person selection
     * @param person
     */
    private void loadStaffPanel(Person person) {
        nameLabel.setText(person.getName().fullName);
        if (person.getPhone() == null) {
            phoneLabel.setText("NA");
        } else {
            phoneLabel.setText(person.getPhone().value);
        }
        if (person.getEmail() == null) {
            emailLabel.setText("NA");
        } else {
            emailLabel.setText(person.getEmail().value);
        }
        if (person.getAddress() == null) {
            addressLabel.setText("NA");
        } else {
            addressLabel.setText(person.getAddress().value);
        }
        if (person.getEducation() == null) {
            educationLabel.setText("NA");
        } else {
            educationLabel.setText(person.getEducation().toString());
        }
        if (person.getFees() == null) {
            feeLabel.setText("NA");
        } else {
            feeLabel.setText(person.getFees().value);
        }
        if (person.getGrades() == null) {
            gradesLabel.setText("NA");
        } else {
            String[] arr = person.getGrades().keySet().toArray(new String[person.getGrades().size()]);
            String result = "";
            String grade = "";
            for (int i = 0; i < arr.length; i++) {
                grade = person.getGrades().get(arr[i]).value;
                result += arr[i] + " " + grade + "\n";
            }
            gradesLabel.setText(result);
        }
        if (person.getTime() == null) {
            timeLabel.setText("NA");
        } else {
            timeLabel.setText(person.getTime().toString());
        }
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadStaffPanel(event.getNewSelection());
    }
}
