package seedu.saveit.ui;

import java.util.LinkedList;
import java.util.List;
import java.util.logging.Logger;

import org.fxmisc.richtext.InlineCssTextArea;

import com.google.common.eventbus.Subscribe;

import javafx.beans.value.ObservableValue;
import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.geometry.Side;
import javafx.scene.control.ContextMenu;
import javafx.scene.control.CustomMenuItem;
import javafx.scene.control.Label;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.saveit.commons.core.LogsCenter;
import seedu.saveit.commons.events.ui.BrowserPanelFocusChangeEvent;
import seedu.saveit.commons.events.ui.NewResultAvailableEvent;
import seedu.saveit.commons.util.StringUtil;
import seedu.saveit.logic.ListElementPointer;
import seedu.saveit.logic.Logic;
import seedu.saveit.logic.SuggestionLogic;
import seedu.saveit.logic.commands.CommandResult;
import seedu.saveit.logic.commands.exceptions.CommandException;
import seedu.saveit.logic.parser.exceptions.ParseException;
import seedu.saveit.logic.suggestion.SuggestionResult;
import seedu.saveit.logic.suggestion.SuggestionValue;


/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "-fx-fill: #ff6060";
    private static final int DEFAULT_CARET_OFFSET = 8;
    private static final int MAX_NUMBER_SUGGESTIONS = 5;

    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private final SuggestionLogic suggestionLogic;
    private ListElementPointer historySnapshot;

    @FXML
    private InlineCssTextArea commandTextArea;

    private ContextMenu popUpWindow;

    public CommandBox(Logic logic, SuggestionLogic suggestionLogic) {
        super(FXML);
        this.logic = logic;
        this.suggestionLogic = suggestionLogic;
        this.popUpWindow = new ContextMenu();
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextArea.textProperty().addListener((observable, oldValue, newValue) -> setStyleToDefault());
        commandTextArea.textProperty().addListener((observable, oldValue, newValue) -> {
            highlight(observable, oldValue, newValue);
            suggestionLogic.updateCaretPosition(commandTextArea.getCaretPosition());
            handleSuggestion(newValue);
        });

        historySnapshot = logic.getHistorySnapshot();
        registerAsAnEventHandler(this);
    }

    /**
     * highlight different parameters for user friendly input command line
     * @param value
     * @param oldValue
     * @param newValue
     */
    private void highlight(ObservableValue<?> value, String oldValue, String newValue) {
        String userInput = newValue;
        // CommandHighlightManager highlightManager = CommandHighlightManager.getInstance();
        CommandHighlightManager.highlight(commandTextArea);
    }

    /**
     * Generates a {@code Suggestion} based on the {@code userInput}
     * and handles the {@code SuggestionResult}
     * @param userInput
     */
    private void handleSuggestion(String userInput) {
        SuggestionResult suggestionResult = suggestionLogic.evaluate(userInput);
        displaySuggestion(suggestionResult);
    }

    /**
     * Displays the contextMenu (or not) given the {@code SuggestionResult}
     * Populates the
     */
    private void displaySuggestion(SuggestionResult suggestionResult) {
        if (suggestionResult.getSuggestionValue().size() == 0) {
            popUpWindow.getItems().clear();
            popUpWindow.hide();
            return;
        }

        int count = Math.min(suggestionResult.getSuggestionValue().size(), MAX_NUMBER_SUGGESTIONS);
        // Builds the dropdown
        List<CustomMenuItem> menuItems = new LinkedList<>();
        for (int i = 0; i < count; i++) {
            final SuggestionValue value = suggestionResult.getSuggestionValue().get(i);
            Label entryLabel = new Label(value.getLabel());
            CustomMenuItem item = new CustomMenuItem(entryLabel, true);
            handleSelectOnItem(item, value, suggestionResult);
            menuItems.add(item);
        }

        if (checkInputValue(suggestionResult)) {
            //hide the dropdown when the suggested value has already been entered by user
            popUpWindow.getItems().clear();
            popUpWindow.hide();
        } else {
            popUpWindow.getItems().clear();
            popUpWindow.getItems().addAll(menuItems);
            getFocused();
            popUpWindow.show(commandTextArea, Side.BOTTOM,
                    (double) suggestionResult.getStartPosition() * DEFAULT_CARET_OFFSET, 0);
        }
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
        case LEFT:
        case RIGHT:
            suggestionLogic.updateCaretPosition(commandTextArea.getCaretPosition());
            break;
        case ENTER:
            keyEvent.consume();
            handleCommandEntered();
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
        setCommandInput(historySnapshot.previous());
    }

    /**
     * Updates the text field with the next input in {@code historySnapshot}, if there exists a next input in {@code
     * historySnapshot}
     */
    private void navigateToNextInput() {
        assert historySnapshot != null;
        if (!historySnapshot.hasNext()) {
            return;
        }

        setCommandInput(historySnapshot.next());
    }

    /**
     * Handles the Enter button pressed event.
     */
    @FXML
    private void handleCommandEntered() {
        try {
            CommandResult commandResult = logic.execute(commandTextArea.getText().trim().replaceAll("\\r|\\n", ""));
            initHistory();
            historySnapshot.next();
            // process result of the command
            setCommandInput("");
            logger.info("Result: " + commandResult.feedbackToUser);
            raise(new NewResultAvailableEvent(commandResult.feedbackToUser));

        } catch (CommandException | ParseException e) {
            initHistory();
            // handle command failure
            setCommandInput(commandTextArea.getText());
            setStyleToIndicateCommandFailure();
            logger.info("Invalid command: " + commandTextArea.getText());
            raise(new NewResultAvailableEvent(e.getMessage()));
        }
    }

    private void setCommandInput(String string) {
        commandTextArea.clear();
        commandTextArea.appendText(string.replaceAll("\\r|\\n", ""));

        // move the cursor to the end of the input string
        commandTextArea.moveTo(commandTextArea.getText().length());
    }

    /**
     * Makes the popup window get ready to get focused before next showing
     */
    private void getFocused() {
        popUpWindow.show(commandTextArea, Side.BOTTOM,
                (double) commandTextArea.getCaretPosition() * DEFAULT_CARET_OFFSET, 0);
        popUpWindow.hide();
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
        commandTextArea.getStyleClass().remove(ERROR_STYLE_CLASS);
    }

    /**
     * Sets the command box style to indicate a failed command.
     */
    private void setStyleToIndicateCommandFailure() {
        ObservableList<String> styleClass = commandTextArea.getStyleClass();
        if (styleClass.contains(ERROR_STYLE_CLASS)) {
            return;
        }
        commandTextArea.setStyle(0, commandTextArea.getText().length(), ERROR_STYLE_CLASS);
        styleClass.add(ERROR_STYLE_CLASS);
    }

    @Subscribe
    private void handleBrowserPanelFocusChangeEvent(BrowserPanelFocusChangeEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        commandTextArea.requestFocus();
    }

    // ======================= Suggestion display util ==========================================================

    /**
     * Checks if the input value is the same as the suggested keyword.
     */
    private boolean checkInputValue(SuggestionResult suggestionResult) {
        String oldValue = suggestionResult.getOldValue();
        return oldValue.equals(suggestionResult.getSuggestionValue().get(0).getResult());
    }

    /**
     * Sets on action for item in {@code CustomMenuItem}.
     */
    private void handleSelectOnItem(CustomMenuItem item, SuggestionValue value, SuggestionResult suggestionResult) {
        item.setOnAction(actionEvent -> {
            String oldText = commandTextArea.getText();
            String newText = StringUtil.replaceAt(oldText, value.getResult(), suggestionResult.getStartPosition(),
                    suggestionResult.getEndPosition());
            commandTextArea.replaceText(newText);
            commandTextArea.moveTo(suggestionResult.getStartPosition() + value.getResult().length());
            popUpWindow.hide();
        });
    }
}
