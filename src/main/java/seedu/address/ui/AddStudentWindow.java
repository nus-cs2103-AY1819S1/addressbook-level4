package seedu.address.ui;

import javafx.fxml.FXML;
import javafx.scene.control.Alert;
import javafx.scene.control.TextField;
import javafx.scene.image.Image;
import javafx.scene.layout.Region;
import javafx.stage.Stage;
import seedu.address.commons.core.Messages;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * Window where the user keys in the data of the student to be added.
 */
public class AddStudentWindow extends UiPart {

    private static final String MESSAGE_ADD_FAILED = "Add Student Failed!";

    @FXML
    private TextField nameField;
    @FXML
    private TextField phoneField;
    @FXML
    private TextField addressField;
    @FXML
    private TextField emailField;
    @FXML
    private TextField tagOneField;
    @FXML
    private TextField tagTwoField;
    @FXML
    private TextField tagThreeField;

    // Implement logger in the future
    // private final Logger logger = LogsCenter.getLogger(getClass());

    private Stage primaryStage;
    private Logic logic;



    public AddStudentWindow(){
    }

    @FXML
    private void initialize() {
    }

    // // Test empty tags
    /**
     * Adds student with entered details into student list.
     * Alert shown if invalid details entered.
     */
    @FXML
    private void handleOk() {

        String name = nameField.getText();
        String phoneNumber = phoneField.getText();
        String address = addressField.getText();
        String email = emailField.getText();
        String tagsCombined = "";
        if (isNotNull(tagOneField)) {
            tagsCombined += " t/" + tagOneField.getText();
        }
        if (isNotNull(tagTwoField)) {
            tagsCombined += " t/" + tagTwoField.getText();
        }
        if (isNotNull(tagThreeField)) {
            tagsCombined += " t/" + tagThreeField.getText();
        }

        String commandText;
        CommandResult commandResult;
        if (tagsCombined.equals("")) {
            commandText = "add n/" + name + " p/" + phoneNumber + " e/" + email + " a/" + address;
        } else {
            commandText = "add n/" + name + " p/" + phoneNumber + " e/" + email + " a/" + address + tagsCombined;
        }

        try {
            commandResult = logic.execute(commandText);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));
            primaryStage.close();
        } catch (CommandException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            configAlert(alert, MESSAGE_ADD_FAILED, e.getMessage(), null, Region.USE_PREF_SIZE);

            // setting icon on top left of alert window to be same as it's graphic icon
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/images/error.png"));

            alert.showAndWait();
        } catch (ParseException e) {
            e.printStackTrace();
            Alert alert = new Alert(Alert.AlertType.ERROR);
            configAlert(alert, MESSAGE_ADD_FAILED, Messages.MESSAGE_INVALID_COMMAND, e.getMessage(),
                    Region.USE_PREF_SIZE);

            // setting icon on top left of alert window to be same as it's graphic icon
            Stage stage = (Stage) alert.getDialogPane().getScene().getWindow();
            stage.getIcons().add(new Image("/images/error.png"));

            alert.showAndWait();
        }
    }

    /**
     * Closes window when user clicks on the Cancel button.
     */
    @FXML
    private void handleCancel() {
        primaryStage.close();
    }

    public void setLogic(Logic logic) {
        this.logic = logic;
    }

    public void setStage(Stage stage) {
        this.primaryStage = stage;
    }

    /**
     * Checks if field is not empty
     */
    private boolean isNotNull(TextField field) {
        return !field.getText().equals("");
    }

    /**
     * Sets alert window size and text
     */
    private void configAlert(Alert alert, String m1, String m2, String m3, double height) {
        alert.setTitle(m1);
        alert.setHeaderText(m2);
        alert.setContentText(m3);
        alert.getDialogPane().setMinHeight(height);
    }
}
