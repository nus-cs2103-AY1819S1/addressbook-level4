package seedu.address.ui;

import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.fxml.FXML;
import javafx.scene.control.Label;
import javafx.scene.layout.AnchorPane;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.PersonPanelSelectionChangedEvent;
import seedu.address.model.person.Person;

/**
 * The Staff Panel of the App.
 */
public class StaffPanel extends UiPart<Region> {

    private static final String FXML = "StaffPanel.fxml";

    private final Logger logger = LogsCenter.getLogger(getClass());

    @FXML
    private AnchorPane staffAnchor;
    @FXML
    private Label nameLabel;
    @FXML
    private Label phoneLabel;
    @FXML
    private Label emailLabel;
    @FXML
    private Label addressLabel;
    @FXML
    private Label salaryLabel;
    @FXML
    private Label departmentLabel;
    @FXML
    private Label managerLabel;
    @FXML
    private Label ratingLabel;
    @FXML
    private Label feedbackLabel;

    public StaffPanel() {
        super(FXML);

        registerAsAnEventHandler(this);
    }

    /**
     * Set the labels according to the person selection
     * @param person
     */
    private void loadStaffPanel(Person person) {
        nameLabel.setText(person.getName().fullName);
        if (person.getPhone().isPrivate()) {
            phoneLabel.setText("Private");
        } else {
            phoneLabel.setText(person.getPhone().value);
        }
        if (person.getEmail().isPrivate()) {
            emailLabel.setText("Private");
        } else {
            emailLabel.setText(person.getEmail().value);
        }
        if (person.getAddress().isPrivate()) {
            addressLabel.setText("Private");
        } else {
            addressLabel.setText(person.getAddress().value);
        }
        double salary = Double.valueOf(person.getSalary().salary);
        double otPay = Double.valueOf(person.getOtHours().overTimeHour)
            * Double.valueOf(person.getOtRate().overTimeRate);
        double deductibles = Double.valueOf(person.getDeductibles().payDeductibles);
        double netSalary = salary + otPay - deductibles;
        salaryLabel.setText(Double.toString(netSalary));
        departmentLabel.setText(person.getDepartment().value);
        managerLabel.setText(person.getManager().fullName);
        ratingLabel.setText(person.getRating().value);
        feedbackLabel.setText(person.getFeedback().value);
    }

    @Subscribe
    private void handlePersonPanelSelectionChangedEvent(PersonPanelSelectionChangedEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        loadStaffPanel(event.getNewSelection());
    }
}
