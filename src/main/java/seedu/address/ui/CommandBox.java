package seedu.address.ui;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.logging.Logger;
import java.util.stream.Collectors;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.address.commons.core.LogsCenter;
import seedu.address.commons.events.ui.NewResultAvailableEvent;
import seedu.address.logic.ListElementPointer;
import seedu.address.logic.Logic;
import seedu.address.logic.commands.AddCommand;
import seedu.address.logic.commands.AddEventCommand;
import seedu.address.logic.commands.AddEventTagCommand;
import seedu.address.logic.commands.ClearCommand;
import seedu.address.logic.commands.CommandResult;
import seedu.address.logic.commands.DeleteCommand;
import seedu.address.logic.commands.EditCommand;
import seedu.address.logic.commands.ExitCommand;
import seedu.address.logic.commands.FavouriteCommand;
import seedu.address.logic.commands.FindCommand;
import seedu.address.logic.commands.GenerateLocationCommand;
import seedu.address.logic.commands.HelpCommand;
import seedu.address.logic.commands.HistoryCommand;
import seedu.address.logic.commands.ImportContactsCommand;
import seedu.address.logic.commands.ListCommand;
import seedu.address.logic.commands.ListEventCommand;
import seedu.address.logic.commands.NotificationCommand;
import seedu.address.logic.commands.RedoCommand;
import seedu.address.logic.commands.SelectCommand;
import seedu.address.logic.commands.ShowLocationCommand;
import seedu.address.logic.commands.UndoCommand;
import seedu.address.logic.commands.exceptions.CommandException;
import seedu.address.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final List<String> ALL_COMMAND_WORDS = new ArrayList<>(Arrays.asList(AddCommand.COMMAND_WORD,
            AddCommand.COMMAND_WORD_ALIAS, AddEventCommand.COMMAND_WORD, AddEventTagCommand.COMMAND_WORD,
            ClearCommand.COMMAND_WORD, ClearCommand.COMMAND_WORD_ALIAS, DeleteCommand.COMMAND_WORD,
            DeleteCommand.COMMAND_WORD_ALIAS, EditCommand.COMMAND_WORD, EditCommand.COMMAND_WORD_ALIAS,
            ExitCommand.COMMAND_WORD, ExitCommand.COMMAND_WORD_ALIAS, FavouriteCommand.COMMAND_WORD,
            FavouriteCommand.COMMAND_WORD_ALIAS, FindCommand.COMMAND_WORD, FindCommand.COMMAND_WORD_ALIAS,
            GenerateLocationCommand.COMMAND_WORD, GenerateLocationCommand.COMMAND_WORD_ALIAS,
            HelpCommand.COMMAND_WORD, HelpCommand.COMMAND_WORD_ALIAS, HistoryCommand.COMMAND_WORD,
            HistoryCommand.COMMAND_WORD_ALIAS, ImportContactsCommand.COMMAND_WORD,
            ListCommand.COMMAND_WORD, ListCommand.COMMAND_WORD_ALIAS, ListEventCommand.COMMAND_WORD,
            NotificationCommand.COMMAND_WORD, NotificationCommand.COMMAND_WORD_ALIAS,
            RedoCommand.COMMAND_WORD, RedoCommand.COMMAND_WORD, SelectCommand.COMMAND_WORD,
            SelectCommand.COMMAND_WORD_ALIAS, ShowLocationCommand.COMMAND_WORD,
            ShowLocationCommand.COMMAND_WORD_ALIAS, UndoCommand.COMMAND_WORD,
            UndoCommand.COMMAND_WORD_ALIAS)).stream()
            .sorted()
            .collect(Collectors.toList());

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
            autocompleteCommand();
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
     * Sets the displayed text in {@code CommandBox}'s text field to the nearest valid command word
     * from the current text in {@code CommandBox}'s text field, if such a command word can be found.
     * Else, does nothing.
     */
    private void autocompleteCommand() {
        Optional<String> nearestCommandWord = getNearestCommandWord(commandTextField.getText());
        // required as commandTextField loses focus between start of execution of this method and this step
        commandTextField.requestFocus();
        commandTextField.end();
        if (nearestCommandWord.isPresent()) {
            replaceText(nearestCommandWord.get());
        }
    }

    /**
     * Returns the nearest VALID command word to {@code text}, if exists.
     * Else, return an empty Optional.
     */
    private Optional<String> getNearestCommandWord(String text) {
        String nearestCommandWord = null;
        for (String commandWord : ALL_COMMAND_WORDS) {
            if (commandWord.startsWith(text)) {
                nearestCommandWord = commandWord;
                break; // prevent checking longer words which could also contain text as a prefix
            }
        }
        return Optional.ofNullable(nearestCommandWord);
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
