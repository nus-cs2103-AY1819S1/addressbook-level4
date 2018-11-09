package seedu.parking.ui;

import static seedu.parking.commons.core.Messages.MESSAGE_INVALID_COMMAND_FOR_AUTOCOMPLETE;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_CLEAR_OR_CALCULATE_COMMAND;
import static seedu.parking.commons.core.Messages.MESSAGE_UNCERTAIN_FIND_OR_FILTER_COMMAND;
import static seedu.parking.logic.commands.CalculateCommand.FIRST_ARG;
import static seedu.parking.logic.commands.FilterCommand.CARPARKTYPE_ARG;
import static seedu.parking.logic.commands.FilterCommand.FREEPARKING_FIRST_ARG;
import static seedu.parking.logic.commands.FilterCommand.FREEPARKING_SECOND_ARG;
import static seedu.parking.logic.commands.FilterCommand.FREEPARKING_THIRD_ARG;
import static seedu.parking.logic.commands.FilterCommand.SYSTEMTYPE_ARG;
import static seedu.parking.logic.parser.CarparkFinderParser.containsFromFirstLetter;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_AVAILABLE_PARKING;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_CAR_TYPE;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_NIGHT_PARKING;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_PARKING_TIME;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_SHORT_TERM;
import static seedu.parking.logic.parser.CliSyntax.PREFIX_SYSTEM_TYPE;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Logger;

import com.google.common.eventbus.Subscribe;

import javafx.collections.ObservableList;
import javafx.fxml.FXML;
import javafx.scene.control.TextField;
import javafx.scene.input.KeyEvent;
import javafx.scene.layout.Region;
import seedu.parking.commons.core.LogsCenter;
import seedu.parking.commons.events.model.DataFetchExceptionEvent;
import seedu.parking.commons.events.ui.NewResultAvailableEvent;
import seedu.parking.commons.events.ui.ToggleTextFieldRequestEvent;
import seedu.parking.logic.ListElementPointer;
import seedu.parking.logic.Logic;
import seedu.parking.logic.commands.CalculateCommand;
import seedu.parking.logic.commands.CommandResult;
import seedu.parking.logic.commands.FilterCommand;
import seedu.parking.logic.commands.FindCommand;
import seedu.parking.logic.commands.NotifyCommand;
import seedu.parking.logic.commands.SelectCommand;
import seedu.parking.logic.commands.exceptions.CommandException;
import seedu.parking.logic.parser.exceptions.ParseException;

/**
 * The UI component that is responsible for receiving user command inputs.
 */
public class CommandBox extends UiPart<Region> {

    public static final String ERROR_STYLE_CLASS = "error";
    private static final String FXML = "CommandBox.fxml";

    private final Logger logger = LogsCenter.getLogger(CommandBox.class);
    private final Logic logic;
    private ListElementPointer historySnapshot;

    //autocomplete variables
    private Map<String, String> autoCompleteCommands = new HashMap<>();
    private int anchorPosition;
    private int caretPosition;
    private String selectedText = "";
    private String rawText = "";

    @FXML
    private TextField commandTextField;

    public CommandBox(Logic logic) {
        super(FXML);
        this.logic = logic;
        registerAsAnEventHandler(this);
        // calls #setStyleToDefault() whenever there is a change to the text of the command box.
        commandTextField.textProperty().addListener((unused1, unused2, unused3) -> setStyleToDefault());
        historySnapshot = logic.getHistorySnapshot();

        autoCompleteCommands.put(FindCommand.COMMAND_WORD, FindCommand.FORMAT);
        autoCompleteCommands.put(SelectCommand.COMMAND_WORD, SelectCommand.FORMAT);
        autoCompleteCommands.put(NotifyCommand.COMMAND_WORD, NotifyCommand.FORMAT);
        autoCompleteCommands.put(FilterCommand.COMMAND_WORD, FilterCommand.FORMAT);
        autoCompleteCommands.put(CalculateCommand.COMMAND_WORD, CalculateCommand.FORMAT);
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
            logger.info("----------------[AUTO COMPLETE][" + commandTextField.getText() + "]");
            try {
                autoComplete();
            } catch (ParseException e) {
                // handle command failure
                setStyleToIndicateCommandFailure();
                logger.info("Invalid command: " + commandTextField.getText());
                raise(new NewResultAvailableEvent(e.getMessage()));
            }
            break;
        default:
            // let JavaFx handle the keypress
        }
    }

    /**
     * Handles autocomplete logic to either display full format, or select next
     * select next argument placeholder if full format is already displayed.
     */
    private void autoComplete() throws ParseException {

        String input = commandTextField.getText().trim();

        if (input.equals("f") || input.equals("fi")) {

            throw new ParseException(String.format("%s\n%s",
                MESSAGE_INVALID_COMMAND_FOR_AUTOCOMPLETE, MESSAGE_UNCERTAIN_FIND_OR_FILTER_COMMAND));

        } else if (input.equals("c")) {

            throw new ParseException(String.format("%s\n%s",
                MESSAGE_INVALID_COMMAND_FOR_AUTOCOMPLETE, MESSAGE_UNCERTAIN_CLEAR_OR_CALCULATE_COMMAND));

        } else if (!input.equals("") && containedInAutoCompleteCommands(input)) {

            displayFormat(input);

            // auto select first argument placeholder
            commandTextField.requestFocus();
            if (containsFromFirstLetter(FindCommand.COMMAND_WORD, input)
                || containsFromFirstLetter(SelectCommand.COMMAND_WORD, input)
                || containsFromFirstLetter(NotifyCommand.COMMAND_WORD, input)) {

                String text = commandTextField.getText();
                int indexOfFirstSpace = text.indexOf(" ");
                commandTextField.selectRange(
                    indexOfFirstSpace + 1, text.length());

            } else if (!input.equals("f") && !input.equals("fi")
                && containsFromFirstLetter(FilterCommand.COMMAND_WORD, input)) {

                int indexOfFirstArg = FilterCommand.FORMAT.indexOf(FREEPARKING_FIRST_ARG);
                commandTextField.selectRange(
                    indexOfFirstArg, indexOfFirstArg + FREEPARKING_FIRST_ARG.length());
                caretPosition = commandTextField.getCaretPosition();

            } else if (containsFromFirstLetter(CalculateCommand.COMMAND_WORD, input)) {

                int indexOfFirstArg = CalculateCommand.FORMAT.indexOf(FIRST_ARG);
                commandTextField.selectRange(
                    indexOfFirstArg, indexOfFirstArg + FIRST_ARG.length());
                caretPosition = commandTextField.getCaretPosition();

            }

        } else if (isFilterCommandFormat(input)) {
            int positionOfWeekDay = input.indexOf(FREEPARKING_FIRST_ARG);
            int positionOfStartTime = input.indexOf(FREEPARKING_SECOND_ARG);
            int positionOfEndTime = input.indexOf(FREEPARKING_THIRD_ARG);
            int positionOfCarparkType = input.indexOf(CARPARKTYPE_ARG);
            int positionOfSystemType = input.indexOf(SYSTEMTYPE_ARG);
            int positionOfAvailableParking = input.indexOf(PREFIX_AVAILABLE_PARKING.toString());
            int positionOfNightParking = input.indexOf(PREFIX_NIGHT_PARKING.toString());
            int positionOfShortTermParking = input.indexOf(PREFIX_SHORT_TERM.toString());
            int[] argumentsArray = {positionOfWeekDay, positionOfStartTime,
                positionOfEndTime, positionOfCarparkType, positionOfSystemType,
                positionOfAvailableParking, positionOfNightParking,
                positionOfShortTermParking};

            selectNextField(argumentsArray);
        } else {
            throw new ParseException(MESSAGE_INVALID_COMMAND_FOR_AUTOCOMPLETE);
        }
    }

    /**
     * Checks if this user input is a valid auto-complete command
     *
     * @param actualCommand actual command input by the user
     * @return true if list of auto-complete command contains the user input from index 0
     * and false otherwise.
     */
    private boolean containedInAutoCompleteCommands(String actualCommand) {
        boolean isAutoCompleteCommand = false;
        for (Map.Entry<String, String> entry : autoCompleteCommands.entrySet()) {
            String command = entry.getKey();
            if (containsFromFirstLetter(command, actualCommand)) {
                isAutoCompleteCommand = true;
                break;
            }
        }
        return isAutoCompleteCommand;
    }

    /**
     * Displays the entire command format in command box using our HashMap of
     * auto-complete command and format pairs
     * @param actualCommand input by the user
     */
    private void displayFormat(String actualCommand) {
        for (Map.Entry<String, String> entry : autoCompleteCommands.entrySet()) {
            String command = entry.getKey();
            if (containsFromFirstLetter(command, actualCommand)) {
                replaceText(entry.getValue());
            }
        }
    }

    /**
     * autocomplete helper function to check if the text input is already in
     * filter command format.
     * @param input input by the user
     * @return true if it is of filter command format and false otherwise
     */
    private boolean isFilterCommandFormat(String input) {
        return input.startsWith("filter") && (input.contains(PREFIX_PARKING_TIME.toString())
            || input.contains(PREFIX_NIGHT_PARKING.toString())
            || input.contains(PREFIX_CAR_TYPE.toString())
            || input.contains(PREFIX_AVAILABLE_PARKING.toString())
            || input.contains(PREFIX_SYSTEM_TYPE.toString())
            || input.contains(PREFIX_SHORT_TERM.toString()));
    }

    /**
     * Checks the current position is in between which two fields
     * And navigates to the next field
     * @param argumentsArray array of field positions in the order of left to right
     * last element is the end position of text input
     */
    private void selectNextField(int[] argumentsArray) {
        for (int i: argumentsArray) {
            System.out.println(String.format("[%d]", i));
        }
        boolean updatedSelection = false;
        for (int i = 0; i < argumentsArray.length - 1; i++) {
            //check if the current position is in between arg[i] and arg[i + 1], if so, change selection
            //to the placeholder of arg[i + 1]
            System.out.println("caret at: " + caretPosition + " current at: " + argumentsArray[i]);
            if (caretPosition > argumentsArray[i] && caretPosition < argumentsArray[i + 1]) {
                commandTextField.positionCaret(argumentsArray[i + 1]);
                changeSelectionToNextField();
                updatedSelection = true;
                break;
            }
        }
        if (!updatedSelection) {
            //if caret position is not changed in the above for loop, it means
            //the caret is currently at the last field, then change selection to
            //the first arg so that continuously pressing tab will go in a cycle
            commandTextField.positionCaret(argumentsArray[0]);
            changeSelectionToNextField();
        }
        commandTextField.requestFocus();
        commandTextField.selectRange(anchorPosition, anchorPosition + selectedText.length());
        selectedText = "";
        caretPosition = commandTextField.getCaretPosition();
    }

    /**
     * Selects the word following the current caret position
     */
    private void changeSelectionToNextField() {
        commandTextField.selectNextWord();
        anchorPosition = commandTextField.getAnchor();
        selectedText = commandTextField.getSelectedText().trim();
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
            rawText = commandTextField.getText();
            CommandResult commandResult = logic.execute(rawText);
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

    @Subscribe
    private void handleToggleTextFieldRequestEvent(ToggleTextFieldRequestEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        if (!commandTextField.isDisable()) {
            commandTextField.setDisable(true);
            commandTextField.setPromptText("");
        } else {
            commandTextField.setDisable(false);
            commandTextField.setPromptText("Enter command here...");
        }
    }

    @Subscribe
    private void handleDataFetchExceptionEventEvent(DataFetchExceptionEvent event) {
        logger.info(LogsCenter.getEventHandlingLogMessage(event));
        initHistory();
        // handle command failure
        raise(new NewResultAvailableEvent(event.exception.getMessage()));
        commandTextField.setDisable(false);
        replaceText(rawText);
        setStyleToIndicateCommandFailure();
        commandTextField.setPromptText("Enter command here...");
        logger.info("Invalid command: " + commandTextField.getText());
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
