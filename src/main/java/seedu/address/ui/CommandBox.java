package seedu.address.ui;

import java.io.File;
import java.nio.file.Paths;
import java.util.logging.Logger;
import java.util.regex.Pattern;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.CdCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;
import seedu.address.model.UserPrefs;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    /**
     * Used for initial separation of command word and args.
     */
    private static final Pattern BASIC_COMMAND_FORMAT = Pattern.compile("(?<commandWord>\\S+)(?<arguments>.*)");
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private final UserPrefs prefs;
    private String startDir;
    private ListElementPointer historySnapshot;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic, UserPrefs prefs) {
        super(FXML);
        this.logic = logic;
        this.prefs = prefs;
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
            keyEvent.consume();

            navigateToPreviousInput();
            break;
        case DOWN:
            keyEvent.consume();
            navigateToNextInput();
            break;
        case TAB:
            keyEvent.consume();
            completeDirName();
            commandTextField.requestFocus();
            commandTextField.positionCaret(commandTextField.getLength());
            break;
        default:
            // let JavaFx handle the keypress
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

    //@@author benedictcss
    /**
     * Auto complete {@code CommandBox}'s text field with the directory name
     * if it exists and resets {@code startDir} with the {@code UserPrefs}'s
     * current directory.
     */
    private void completeDirName() {
        startDir = prefs.getCurrDirectory().toString();
        String commandText = commandTextField.getText();
        if (commandText.trim().equals("")) {
            return;
        }

        String commandWord = getCommandWord(commandText.trim());
        if (commandWord.equals(CdCommand.COMMAND_WORD)) {
            String arguments = getArguments(commandText.trim());
            if (arguments.equals("")) { return; }
            searchDirectory(arguments);
        }
    }

    /**
     * Returns command word from given {@code commandText}.
     */
    private String getCommandWord(String commandText) {
        String[] commandLineArgs = commandText.split(" ");
        return commandLineArgs[0];
    }

    /**
     * Returns arguments from given {@code commandText}.
     */
    private String getArguments(String commandText) {
        String[] commandLineArgs = commandText.split(" ", 2);
        if (commandLineArgs.length == 1) { return ""; }
        return commandLineArgs[1];
    }

    /**
     * Searches if directories exists in the given {@code arguments}.
     */
    private void searchDirectory(String arguments) {
        StringBuilder copyArgs = new StringBuilder();

        String[] directories = arguments.split("/");
        int numDir = directories.length;

        for (int i = 0; i < numDir; i++) {
            String dir = directories[i];
            if (i == 0 && Paths.get(dir, "/").isAbsolute()) {
                startDir = dir;
                copyArgs.append(dir + "/");
            } else if (i == (numDir - 1)) {
                // Enters if directory is the last argument.
                File checkDir = new File(startDir);
                File[] fileList = checkDir.listFiles();

                for (File file : fileList) {
                    if (file.isDirectory()) {
                        if (file.getName().toUpperCase().startsWith(dir.toUpperCase())) {
                            copyArgs.append(file.getName());
                            String newCommandText = "cd " + copyArgs + "/";
                            replaceText(newCommandText);
                            return;
                        }
                    }
                }
            } else {
                // checks if directory exists
                String newDir = startDir + "/" + dir;

                File checkDir = new File(newDir);
                if (checkDir.isDirectory()) {
                    startDir = newDir;
                    copyArgs.append(checkDir.getName() + "/");
                } else {
                    return;
                }
            }
        }
    }
    //@@author

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
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
