package seedu.scheduler.ui;

import java.util.logging.Logger;

import org.controlsfx.control.textfield.TextFields;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.scheduler.commons.core.LogsCenter;
import seedu.scheduler.commons.events.ui.NewResultAvailableEvent;
import seedu.scheduler.logic.ListElementPointer;
import seedu.scheduler.logic.Logic;
import seedu.scheduler.logic.commands.AddCommand;
import seedu.scheduler.logic.commands.AddTagCommand;
import seedu.scheduler.logic.commands.ClearCommand;
import seedu.scheduler.logic.commands.CommandResult;
import seedu.scheduler.logic.commands.DeleteCommand;
import seedu.scheduler.logic.commands.EditCommand;
import seedu.scheduler.logic.commands.EnterGoogleCalendarModeCommand;
import seedu.scheduler.logic.commands.ExitCommand;
import seedu.scheduler.logic.commands.FindCommand;
import seedu.scheduler.logic.commands.HelpCommand;
import seedu.scheduler.logic.commands.HistoryCommand;
import seedu.scheduler.logic.commands.ListCommand;
import seedu.scheduler.logic.commands.RedoCommand;
import seedu.scheduler.logic.commands.SelectCommand;
import seedu.scheduler.logic.commands.UndoCommand;
import seedu.scheduler.logic.commands.exceptions.CommandException;
import seedu.scheduler.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        setUpInputListener();
        historySnapshot = logic.getHistorySnapshot();
        TextFields.bindAutoCompletion(
                commandTextField, AddCommand.COMMAND_WORD, AddCommand.COMMAND_ALIAS_ONE, AddCommand.COMMAND_ALIAS_TWO,
                AddTagCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_ALIAS_ONE,
                ClearCommand.COMMAND_ALIAS_TWO, ClearCommand.COMMAND_ALIAS_THREE, ClearCommand.COMMAND_ALIAS_FOUR,
                DeleteCommand.COMMAND_WORD, DeleteCommand.COMMAND_ALIAS_ONE, DeleteCommand.COMMAND_ALIAS_TWO,
                DeleteCommand.COMMAND_ALIAS_THREE, DeleteCommand.COMMAND_ALIAS_FOUR, DeleteCommand.COMMAND_ALIAS_FIVE,
                EditCommand.COMMAND_WORD, EditCommand.COMMAND_ALIAS_ONE, EditCommand.COMMAND_ALIAS_TWO,
                ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_ALIAS_ONE, ExitCommand.COMMAND_ALIAS_TWO,
                ExitCommand.COMMAND_ALIAS_TWO, ExitCommand.COMMAND_ALIAS_THREE, FindCommand.COMMAND_WORD,
                FindCommand.COMMAND_ALIAS_ONE, FindCommand.COMMAND_ALIAS_TWO, FindCommand.COMMAND_ALIAS_THREE,
                EnterGoogleCalendarModeCommand.COMMAND_WORD,
                HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_ALIAS_ONE,
                HelpCommand.COMMAND_ALIAS_TWO, HelpCommand.COMMAND_ALIAS_THREE, HistoryCommand.COMMAND_WORD,
                HistoryCommand.COMMAND_ALIAS_ONE, HistoryCommand.COMMAND_ALIAS_TWO, HistoryCommand.COMMAND_ALIAS_THREE,
                HistoryCommand.COMMAND_ALIAS_FOUR, HistoryCommand.COMMAND_ALIAS_FIVE, ListCommand.COMMAND_WORD,
                ListCommand.COMMAND_ALIAS_ONE, ListCommand.COMMAND_ALIAS_TWO, ListCommand.COMMAND_ALIAS_THREE,
                RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_ALIAS_ONE, RedoCommand.COMMAND_ALIAS_TWO,
                RedoCommand.COMMAND_ALIAS_THREE, SelectCommand.COMMAND_WORD, SelectCommand.COMMAND_ALIAS_ONE,
                SelectCommand.COMMAND_ALIAS_TWO, SelectCommand.COMMAND_ALIAS_THREE, SelectCommand.COMMAND_ALIAS_FOUR,
                SelectCommand.COMMAND_ALIAS_FIVE, UndoCommand.COMMAND_WORD, UndoCommand.COMMAND_ALIAS_ONE,
                UndoCommand.COMMAND_ALIAS_TWO, UndoCommand.COMMAND_ALIAS_THREE
        );
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
     * Sets {@code commandTextField} to detect when date time are input.
     * Then parses the date time to be displayed.
     * Also calls #setStyleToDefault() whenever there is a change to the text of the command box.
     */
    private void setUpInputListener() {
        commandTextField.textProperty().addListener((unused1, unused2, newValue) -> {
            setStyleToDefault();
            raise(new NewResultAvailableEvent(logic.parseDateTime(newValue)));
        });
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
