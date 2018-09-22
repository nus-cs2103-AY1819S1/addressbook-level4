package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Optional;

import javafx.application.Platform;
import javafx.geometry.Insets;
import javafx.scene.Node;
import javafx.scene.control.ButtonBar;
import javafx.scene.control.ButtonType;
import javafx.scene.control.Dialog;
import javafx.scene.control.Label;
import javafx.scene.control.PasswordField;
import javafx.scene.control.TextInputDialog;
import javafx.scene.layout.GridPane;
import seedu.address.logic.CommandHistory;
import seedu.address.model.AddressBook;
import seedu.address.model.Model;

/**
 * Clears the address book.
 */
public class ClearCommand extends Command {

    public static final String COMMAND_WORD = "clear";
    public static final String MESSAGE_SUCCESS = "Address book has been cleared!";
    public static final String MESSAGE_FAIL_WRONG_PASSWORD = "Wrong admin password, address book not cleared!";
    public static final String MESSAGE_FAIL_CANCEL = "Cancelled clear command";

    public static final String ADMIN_PASSWORD = "admin";

    private PasswordField passwordField;

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(model);

        // Create the custom dialog.
        Dialog<String> dialog = new Dialog<>();
        dialog.setTitle("Clear all entries");
        dialog.setHeaderText("Are you sure you want to clear all entries?" + "\n" +
                "Please enter admin password to confirm.");

        // Set the button types.
        ButtonType loginButtonType = new ButtonType("Clear", ButtonBar.ButtonData.OK_DONE);
        dialog.getDialogPane().getButtonTypes().addAll(loginButtonType, ButtonType.CANCEL);

        // Create the username and password labels and fields.
        GridPane grid = new GridPane();
        grid.setHgap(10);
        grid.setPadding(new Insets(20, 150, 10, 10));

        PasswordField password = new PasswordField();
        password.setPromptText("Password");

        grid.add(new Label("Password:"), 0, 0);
        grid.add(password, 1, 0);

        // Enable/Disable login button depending on whether a username was entered.
        Node loginButton = dialog.getDialogPane().lookupButton(loginButtonType);
        loginButton.setDisable(true);

        // Do some validation (using the Java 8 lambda syntax).
        password.textProperty().addListener((observable, oldValue, newValue) -> {
            loginButton.setDisable(newValue.trim().isEmpty());
        });

        dialog.getDialogPane().setContent(grid);

        // Request focus on the username field by default.
        Platform.runLater(() -> password.requestFocus());

        // Convert the result to a username-password-pair when the login button is clicked.
        dialog.setResultConverter(dialogButton -> {
            if (dialogButton == loginButtonType) {
                return password.getText();
            }
            return null;
        });

        Optional<String> result = dialog.showAndWait();

        return result.map(passwordString -> {
            if (passwordString.equals(ADMIN_PASSWORD)) {
                model.resetData(new AddressBook());
                model.commitAddressBook();
                return new CommandResult(MESSAGE_SUCCESS);
            } else {
                return new CommandResult(MESSAGE_FAIL_WRONG_PASSWORD);
            }
        }).orElse(new CommandResult(MESSAGE_FAIL_CANCEL));

    }
}
