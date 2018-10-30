package seedu.address.logic.commands;

import static java.util.Objects.requireNonNull;

import java.util.Collections;
import java.util.LinkedList;
import java.util.List;

import seedu.address.logic.CommandHistory;
import seedu.address.model.Model;

/**
 * Lists all the commands entered by user from the start of app launch.
 */
public class HistoryCommand extends Command {

    public static final String COMMAND_WORD = "history";
    public static final String COMMAND_ALIAS = "h";
    public static final String HISTORY_ALL_COMMANDS = "-c";
    public static final String HISTORY_ALL_SAVINGS = "-s";

    public static final String MESSAGE_USAGE = COMMAND_WORD + " " + HISTORY_ALL_COMMANDS
            + ": Lists all the commands that you have entered, in reverse chronological order.\n"
            + COMMAND_WORD + " " + HISTORY_ALL_SAVINGS
            + ": Lists a history of savings that you have allocated, from newest to oldest.\n";

    public static final String MESSAGE_SUCCESS = "Entered commands (from most recent to earliest):\n%1$s";
    public static final String MESSAGE_SUCCESS_SAVE_COMMANDS = "Entered save commands "
            + "(from most recent to earliest):\n%1$s";
    public static final String MESSAGE_NO_HISTORY = "You have not yet entered any commands.";
    public static final String MESSAGE_NO_SAVINGS_HISTORY = "You have not yet entered any save commands";

    /**
     * Types of History that can be shown.
     */
    public enum HistoryType { SHOW_COMMANDS, SHOW_SAVINGS }

    private final HistoryType historyType;

    public HistoryCommand(HistoryType historyType) {
        this.historyType = historyType;
    }

    @Override
    public CommandResult execute(Model model, CommandHistory history) {
        requireNonNull(history);
        requireNonNull(historyType);

        if (historyType.equals(HistoryType.SHOW_COMMANDS)) {
            return getCommandHistory(history);
        }
        return getSavingsHistory(history);
    }

    public CommandResult getCommandHistory(CommandHistory history) {
        List<String> previousCommands = history.getHistory();

        if (previousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        Collections.reverse(previousCommands);
        return new CommandResult(String.format(MESSAGE_SUCCESS, String.join("\n", previousCommands)));
    }

    public CommandResult getSavingsHistory(CommandHistory history) {
        String saveKeyWord = "save";
        List<String> allPreviousCommands = history.getHistory();

        if (allPreviousCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_HISTORY);
        }

        List<String> allSaveCommands = new LinkedList<>();

        for (String command : allPreviousCommands) {
            if (command.startsWith(saveKeyWord)) {
                allSaveCommands.add(command);
            }
        }

        if (allSaveCommands.isEmpty()) {
            return new CommandResult(MESSAGE_NO_SAVINGS_HISTORY);
        }

        Collections.reverse(allSaveCommands);
        return new CommandResult(String.format(MESSAGE_SUCCESS_SAVE_COMMANDS, String.join("\n", allSaveCommands)));
    }
}
