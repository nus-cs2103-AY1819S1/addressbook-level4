package seedu.clinicio.ui;

import java.util.logging.Logger;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.clinicio.commons.core.LogsCenter;
import seedu.clinicio.commons.events.ui.NewResultAvailableEvent;
import seedu.clinicio.logic.ListElementPointer;
import seedu.clinicio.logic.Logic;
import seedu.clinicio.logic.commands.CommandResult;
import seedu.clinicio.logic.commands.exceptions.CommandException;
import seedu.clinicio.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private final StringBuilder tempPassword = new StringBuilder();
    private ListElementPointer historySnapshot;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();
    }

    /**
     * Handles the key press event, {@code keyEvent}.
     */
    @FXML
    private void handleKeyPress(KeyEvent keyEvent) {

        switch (keyEvent.getCode()) {
        case UP:
            // As up and down buttons will alter the position of the caret,
            // consuming it causes the caret's position to remain unchanged
            navigateToPreviousInput();
            maskPassword(true);
            break;
        case DOWN:
            navigateToNextInput();
            maskPassword(true);
            break;
        case SPACE: case LEFT: case RIGHT: case BACK_SPACE:
            maskPassword(false);
            break;
        default:
            // let JavaFx handle the keypress
            maskPassword(false);
            break;
        }

    }

    /**
     * Updates the text field with the previous input in {@code historySnapshot},
     * if there exists a previous input in {@code historySnapshot}
     */
    private void navigateToPreviousInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasPrevious()) {
            return;
        }

        resetTempPassword();
        replaceText(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot},
     * if there exists a next input in {@code historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        resetTempPassword();
        replaceText(historySnapshot.next());
    }

    /**
     * Sets {@code CommandBox}'s text field with {@code text} and
     * positions the caret to the end of the {@code text}.
     */
    private void replaceText(String text) {
        commandTextField.setText(text);
        commandTextField.positionCaret(commandTextField.getText().length());
    }

    //@@author jjlee050
    /**
     * Mask the password after pass/ prefix to '-'.
     */
    public void maskPassword(boolean isHistory) {
        if (commandTextField.getText().contains("pass/")) {
            int passwordPrefixIndex = commandTextField.getText().indexOf("pass/");
            int spaceAfterPasswordIndex = commandTextField.getText().indexOf(' ', passwordPrefixIndex);
            String password = commandTextField.getText()
                    .substring(passwordPrefixIndex + 5);
            if (spaceAfterPasswordIndex > 0) {
                password = commandTextField.getText()
                        .substring(passwordPrefixIndex + 5, spaceAfterPasswordIndex);
            }
            String otherCommand = commandTextField.getText().substring(0, passwordPrefixIndex);
            
            StringBuilder maskedPassword = new StringBuilder();

            int passwordLength = password.length();
            if (isHistory) {
                passwordLength -= 1;
            }
            for (int i = 0; i < passwordLength; i++) {
                if (password.charAt(i) != '-') {
                    tempPassword.append(password.charAt(i));
                }
                maskedPassword.append("-");
            }

            if (isHistory) {
                maskedPassword.append(password.charAt(password.length() - 1));
            }
            //System.out.println("Mask: " + maskedPassword);
            //System.out.println("Temp: " + tempPassword);
            if (spaceAfterPasswordIndex > 0) {
                String anyOtherCommand = commandTextField.getText().substring(spaceAfterPasswordIndex);
                replaceText(otherCommand + "pass/" + maskedPassword.toString() + anyOtherCommand);
            } else {
                replaceText(otherCommand + "pass/" + maskedPassword.toString());
            }
        }
    }

    //@@author jjlee050
    /**
     * Unmask the password that has been hidden with '-'.
     */
    private void unmaskPassword() {
        if (commandTextField.getText().contains("pass/")) {
            int passwordPrefixIndex = commandTextField.getText().indexOf("pass/");
            int spaceAfterPasswordIndex = commandTextField.getText().indexOf(' ', passwordPrefixIndex);

            String otherCommand = commandTextField.getText().substring(0, passwordPrefixIndex);
            String password = commandTextField.getText()
                    .substring(passwordPrefixIndex + 5);
            if (spaceAfterPasswordIndex > 0) {
                password = commandTextField.getText()
                        .substring(passwordPrefixIndex + 5, spaceAfterPasswordIndex);
            }

            String commandText = "";
            
            if (tempPassword.length() == password.length()) {
                commandText += (otherCommand + "pass/"
                        + tempPassword.toString());
            } else if (tempPassword.length() > password.length()) {
                commandText += (otherCommand + "pass/"
                        + tempPassword.substring(0, password.length()));
            } else {
                commandText +=(otherCommand + "pass/"
                        + tempPassword.toString()
                        + password.substring(tempPassword.length()));
            }

            if (spaceAfterPasswordIndex > 0) {
                String anyOtherCommand = commandTextField.getText().substring(spaceAfterPasswordIndex);
                commandText += anyOtherCommand;
            }
            
            replaceText(commandText);
            
            //Reset temp password.
            resetTempPassword();

        }
    }

    /**
     * Reset the temporary password in the command text field.
     */
    private void resetTempPassword() {
        tempPassword.setLength(0);
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        unmaskPassword();
        try {
            CommandResult commandResult = logic.execute(commandTextField.getText());
            initHistory();
            historySnapshot.next();
            // process result of the command
            commandTextField.setText("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextField.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    /**
     * Initializes the history snapshot.
     */
    private void initHistory() {
        historySnapshot = logic.getHistorySnapshot();
        // add an empty string to represent the most-recent end of historySnapshot, to be shown to
        // the user if she tries to navigate past the most-recent end of the historySnapshot.
        historySnapshot.add("");
    }

    /**
     * Sets the command box style to use the default style.
     */
    private void setStyleToDefault() {
        commandTextField.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextField.getStyleClass();

        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }

        styleClass.add(ERROR_STYLE_CLASS);
    }

}
